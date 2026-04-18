package stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StockDAO {

    // =========================
    // DB 연결
    // =========================
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        return dataFactory.getConnection();
    }

    // =========================
    // 품목코드 기준 구분
    // CP-A : 완제품
    // CP-P / CP-M : 자재
    // 나머지 : 재고
    // =========================
    private String getItemTypeCaseSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("CASE ");
        sb.append("    WHEN i.ITEM_CODE LIKE 'CP-A%' THEN '완제품' ");
        sb.append("    WHEN i.ITEM_CODE LIKE 'CP-P%' THEN '자재' ");
        sb.append("    WHEN i.ITEM_CODE LIKE 'CP-M%' THEN '자재' ");
        sb.append("    ELSE '재고' ");
        sb.append("END ");
        return sb.toString();
    }

    // =========================
    // 목록 조회
    // type
    // all     : 전체
    // stock   : 재고
    // product : 완제품
    // item    : 자재
    // =========================
    public List<StockDTO> selectList(int startRow, int endRow, String keyword, String type) {
        List<StockDTO> list = new ArrayList<StockDTO>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM ( ");
            sql.append("    SELECT ROWNUM rnum, A.* FROM ( ");
            sql.append("        SELECT ");
            sql.append("            s.STOCK_KEY, ");
            sql.append("            s.LOT, ");
            sql.append("            s.DONE_QC, ");
            sql.append("            s.WAIT_QC, ");
            sql.append("            s.CURRENT_QTY, ");
            sql.append("            s.SAFE_QTY, ");
            sql.append("            s.UPDATED_AT, ");
            sql.append("            s.ITEM_KEY, ");
            sql.append("            i.ITEM_CODE, ");
            sql.append("            i.ITEM_NAME, ");
            sql.append("            i.SPEC, ");
            sql.append("            i.UNIT, ");
            sql.append("            i.PRICE, ");
            sql.append("            ");
            sql.append(getItemTypeCaseSql());
            sql.append(" AS ITEM_TYPE ");
            sql.append("        FROM TB_STOCK s ");
            sql.append("        LEFT OUTER JOIN TB_ITEM i ");
            sql.append("            ON s.ITEM_KEY = i.ITEM_KEY ");
            sql.append("        WHERE 1=1 ");

            // LOT 검색
            if (keyword != null && !keyword.trim().equals("")) {
                sql.append(" AND UPPER(s.LOT) LIKE UPPER(?) ");
            }

            // 구분 검색
            if ("product".equals(type)) {
                sql.append(" AND i.ITEM_CODE LIKE 'CP-A%' ");
            } else if ("item".equals(type)) {
                sql.append(" AND (i.ITEM_CODE LIKE 'CP-P%' OR i.ITEM_CODE LIKE 'CP-M%') ");
            } else if ("stock".equals(type)) {
                sql.append(" AND i.ITEM_CODE NOT LIKE 'CP-A%' ");
                sql.append(" AND i.ITEM_CODE NOT LIKE 'CP-P%' ");
                sql.append(" AND i.ITEM_CODE NOT LIKE 'CP-M%' ");
            }

            sql.append("        ORDER BY s.STOCK_KEY ASC ");
            sql.append("    ) A WHERE ROWNUM <= ? ");
            sql.append(") WHERE rnum >= ? ");

            ps = conn.prepareStatement(sql.toString());

            int idx = 1;

            if (keyword != null && !keyword.trim().equals("")) {
                ps.setString(idx++, "%" + keyword.trim() + "%");
            }

            ps.setInt(idx++, endRow);
            ps.setInt(idx++, startRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                StockDTO dto = new StockDTO();

                dto.setStock_key(rs.getInt("STOCK_KEY"));
                dto.setLot(rs.getString("LOT"));
                dto.setCurrent_qty(rs.getInt("CURRENT_QTY"));
                dto.setSafe_qty(rs.getInt("SAFE_QTY"));
                dto.setUpdated_at(rs.getTimestamp("UPDATED_AT"));
                dto.setItem_key(rs.getInt("ITEM_KEY"));

                dto.setItem_code(rs.getString("ITEM_CODE"));
                dto.setItem_name(rs.getString("ITEM_NAME"));
                dto.setItem_type(rs.getString("ITEM_TYPE"));
                dto.setSpec(rs.getString("SPEC"));
                dto.setUnit(rs.getString("UNIT"));
                dto.setPrice(rs.getInt("PRICE"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }

        return list;
    }

    // =========================
    // 전체 개수 조회
    // =========================
    public int getTotalCount(String keyword, String type) {
        int total = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT COUNT(*) ");
            sql.append("FROM TB_STOCK s ");
            sql.append("LEFT OUTER JOIN TB_ITEM i ");
            sql.append("    ON s.ITEM_KEY = i.ITEM_KEY ");
            sql.append("WHERE 1=1 ");

            if (keyword != null && !keyword.trim().equals("")) {
                sql.append(" AND UPPER(s.LOT) LIKE UPPER(?) ");
            }

            if ("product".equals(type)) {
                sql.append(" AND i.ITEM_CODE LIKE 'CP-A%' ");
            } else if ("item".equals(type)) {
                sql.append(" AND (i.ITEM_CODE LIKE 'CP-P%' OR i.ITEM_CODE LIKE 'CP-M%') ");
            } else if ("stock".equals(type)) {
                sql.append(" AND i.ITEM_CODE NOT LIKE 'CP-A%' ");
                sql.append(" AND i.ITEM_CODE NOT LIKE 'CP-P%' ");
                sql.append(" AND i.ITEM_CODE NOT LIKE 'CP-M%' ");
            }

            ps = conn.prepareStatement(sql.toString());

            int idx = 1;

            if (keyword != null && !keyword.trim().equals("")) {
                ps.setString(idx++, "%" + keyword.trim() + "%");
            }

            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }

        return total;
    }

    // =========================
    // 품목 목록 조회
    // 등록/수정 모달에서 select에 사용
    // =========================
    public List<StockDTO> selectItemList() {
        List<StockDTO> list = new ArrayList<StockDTO>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("    i.ITEM_KEY, ");
            sql.append("    i.ITEM_CODE, ");
            sql.append("    i.ITEM_NAME, ");
            sql.append("    i.SPEC, ");
            sql.append("    i.UNIT, ");
            sql.append("    i.PRICE, ");
            sql.append("    ");
            sql.append(getItemTypeCaseSql());
            sql.append(" AS ITEM_TYPE ");
            sql.append("FROM TB_ITEM i ");
            sql.append("ORDER BY i.ITEM_KEY ASC ");

            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setItem_key(rs.getInt("ITEM_KEY"));
                dto.setItem_code(rs.getString("ITEM_CODE"));
                dto.setItem_name(rs.getString("ITEM_NAME"));
                dto.setSpec(rs.getString("SPEC"));
                dto.setUnit(rs.getString("UNIT"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setItem_type(rs.getString("ITEM_TYPE"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }

        return list;
    }

    // =========================
    // 등록
    // =========================
    public int insert(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();

            String sql = ""
                    + "INSERT INTO TB_STOCK ( "
                    + "    STOCK_KEY, LOT, CURRENT_QTY, SAFE_QTY, UPDATED_AT, ITEM_KEY "
                    + ") VALUES ( "
                    + "    STOCK_SEQ.NEXTVAL, ?, ?, ?, CURRENT_TIMESTAMP, ? "
                    + ")";

            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getLot());
            ps.setInt(2, dto.getCurrent_qty());
            ps.setInt(3, dto.getSafe_qty());
            ps.setInt(4, dto.getItem_key());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(conn, ps, null);
        }
    }

    // =========================
    // 수정
    // =========================
    public int update(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();

            String sql = ""
                    + "UPDATE TB_STOCK "
                    + "SET LOT = ?, "
                    + "    CURRENT_QTY = ?, "
                    + "    SAFE_QTY = ?, "
                    + "    UPDATED_AT = CURRENT_TIMESTAMP, "
                    + "    ITEM_KEY = ? "
                    + "WHERE STOCK_KEY = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getLot());
            ps.setInt(2, dto.getCurrent_qty());
            ps.setInt(3, dto.getSafe_qty());
            ps.setInt(4, dto.getItem_key());
            ps.setInt(5, dto.getStock_key());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(conn, ps, null);
        }
    }

    // =========================
    // 삭제
    // =========================
    public int delete(String ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();

            String sql = "DELETE FROM TB_STOCK WHERE STOCK_KEY IN (" + ids + ")";
            stmt = conn.createStatement();

            return stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
    }

    // =========================
    // 자원 반납
    // =========================
    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
        }
    }
}