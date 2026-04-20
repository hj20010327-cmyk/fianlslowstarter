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
    // 목록 조회
    // - 구분 / 안전재고는 TB_ITEM에서 가져옴
    // - 최근업데이트는 데이터로만 유지
    // =========================
    public List<StockDTO> selectList(int startRow, int endRow, String lotKeyword, String itemType,
                                     String itemCodeKeyword, String itemNameKeyword) {
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
            sql.append("            i.SAFE_QTY, ");
            sql.append("            s.UPDATED_AT, ");
            sql.append("            s.ITEM_KEY, ");
            sql.append("            i.ITEM_CODE, ");
            sql.append("            i.ITEM_NAME, ");
            sql.append("            i.SPEC, ");
            sql.append("            i.UNIT, ");
            sql.append("            i.PRICE, ");
            sql.append("            i.ITEM_TYPE ");
            sql.append("        FROM TB_STOCK s ");
            sql.append("        LEFT OUTER JOIN TB_ITEM i ");
            sql.append("            ON s.ITEM_KEY = i.ITEM_KEY ");
            sql.append("        WHERE 1=1 ");

            // LOT 검색
            if (lotKeyword != null && !lotKeyword.trim().equals("")) {
                sql.append(" AND UPPER(s.LOT) LIKE UPPER(?) ");
            }

            // 품목코드 검색
            if (itemCodeKeyword != null && !itemCodeKeyword.trim().equals("")) {
                sql.append(" AND UPPER(i.ITEM_CODE) LIKE UPPER(?) ");
            }

            // 품목명 검색
            if (itemNameKeyword != null && !itemNameKeyword.trim().equals("")) {
                sql.append(" AND UPPER(i.ITEM_NAME) LIKE UPPER(?) ");
            }

            // 구분 검색
            if ("product".equals(itemType)) {
                sql.append(" AND i.ITEM_TYPE = '완제품' ");
            } else if ("item".equals(itemType)) {
                sql.append(" AND i.ITEM_TYPE = '자재' ");
            }

            sql.append("        ORDER BY s.STOCK_KEY ASC ");
            sql.append("    ) A WHERE ROWNUM <= ? ");
            sql.append(") WHERE rnum >= ? ");

            ps = conn.prepareStatement(sql.toString());

            int idx = 1;

            if (lotKeyword != null && !lotKeyword.trim().equals("")) {
                ps.setString(idx++, "%" + lotKeyword.trim() + "%");
            }

            if (itemCodeKeyword != null && !itemCodeKeyword.trim().equals("")) {
                ps.setString(idx++, "%" + itemCodeKeyword.trim() + "%");
            }

            if (itemNameKeyword != null && !itemNameKeyword.trim().equals("")) {
                ps.setString(idx++, "%" + itemNameKeyword.trim() + "%");
            }

            ps.setInt(idx++, endRow);
            ps.setInt(idx++, startRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                StockDTO dto = new StockDTO();

                dto.setStock_key(rs.getInt("STOCK_KEY"));
                dto.setLot(rs.getString("LOT"));
                dto.setDone_qc(rs.getInt("DONE_QC"));
                dto.setWait_qc(rs.getInt("WAIT_QC"));
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
    public int getTotalCount(String lotKeyword, String itemType, String itemCodeKeyword, String itemNameKeyword) {
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

            if (lotKeyword != null && !lotKeyword.trim().equals("")) {
                sql.append(" AND UPPER(s.LOT) LIKE UPPER(?) ");
            }

            if (itemCodeKeyword != null && !itemCodeKeyword.trim().equals("")) {
                sql.append(" AND UPPER(i.ITEM_CODE) LIKE UPPER(?) ");
            }

            if (itemNameKeyword != null && !itemNameKeyword.trim().equals("")) {
                sql.append(" AND UPPER(i.ITEM_NAME) LIKE UPPER(?) ");
            }

            if ("product".equals(itemType)) {
                sql.append(" AND i.ITEM_TYPE = '완제품' ");
            } else if ("item".equals(itemType)) {
                sql.append(" AND i.ITEM_TYPE = '자재' ");
            }

            ps = conn.prepareStatement(sql.toString());

            int idx = 1;

            if (lotKeyword != null && !lotKeyword.trim().equals("")) {
                ps.setString(idx++, "%" + lotKeyword.trim() + "%");
            }

            if (itemCodeKeyword != null && !itemCodeKeyword.trim().equals("")) {
                ps.setString(idx++, "%" + itemCodeKeyword.trim() + "%");
            }

            if (itemNameKeyword != null && !itemNameKeyword.trim().equals("")) {
                ps.setString(idx++, "%" + itemNameKeyword.trim() + "%");
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
    // 검색용 전체 품목 목록
    // - 검색 드롭다운용
    // =========================
    public List<StockDTO> selectAllItemList() {
        List<StockDTO> list = new ArrayList<StockDTO>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = ""
                    + "SELECT ITEM_KEY, ITEM_CODE, ITEM_NAME, SPEC, UNIT, PRICE, SAFE_QTY, ITEM_TYPE "
                    + "FROM TB_ITEM "
                    + "ORDER BY ITEM_CODE ASC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setItem_key(rs.getInt("ITEM_KEY"));
                dto.setItem_code(rs.getString("ITEM_CODE"));
                dto.setItem_name(rs.getString("ITEM_NAME"));
                dto.setSpec(rs.getString("SPEC"));
                dto.setUnit(rs.getString("UNIT"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setSafe_qty(rs.getInt("SAFE_QTY"));
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
    // 신규 등록용 품목 목록
    // - 현재 TB_STOCK에 없는 품목만 조회
    // =========================
    public List<StockDTO> selectInsertItemList() {
        List<StockDTO> list = new ArrayList<StockDTO>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = ""
                    + "SELECT i.ITEM_KEY, i.ITEM_CODE, i.ITEM_NAME, i.SPEC, i.UNIT, i.PRICE, i.SAFE_QTY, i.ITEM_TYPE "
                    + "FROM TB_ITEM i "
                    + "WHERE NOT EXISTS ( "
                    + "    SELECT 1 FROM TB_STOCK s WHERE s.ITEM_KEY = i.ITEM_KEY "
                    + ") "
                    + "ORDER BY i.ITEM_CODE ASC";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setItem_key(rs.getInt("ITEM_KEY"));
                dto.setItem_code(rs.getString("ITEM_CODE"));
                dto.setItem_name(rs.getString("ITEM_NAME"));
                dto.setSpec(rs.getString("SPEC"));
                dto.setUnit(rs.getString("UNIT"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setSafe_qty(rs.getInt("SAFE_QTY"));
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
    // LOT 검색 목록
    // =========================
    public List<String> selectLotList() {
        List<String> list = new ArrayList<String>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT DISTINCT LOT FROM TB_STOCK WHERE LOT IS NOT NULL ORDER BY LOT ASC");
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("LOT"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }

        return list;
    }

    // =========================
    // LOT 자동 생성
    // 예: LOT-240420-001
    // =========================
    private String getNextLot(Connection conn) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = ""
                    + "SELECT 'LOT-' || TO_CHAR(SYSDATE, 'YYMMDD') || '-' || "
                    + "       LPAD(NVL(MAX(TO_NUMBER(SUBSTR(LOT, -3))), 0) + 1, 3, '0') AS NEXT_LOT "
                    + "FROM TB_STOCK "
                    + "WHERE LOT LIKE 'LOT-' || TO_CHAR(SYSDATE, 'YYMMDD') || '-%'";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("NEXT_LOT");
            }

        } finally {
            if (rs != null) try { rs.close(); } catch (Exception e) {}
            if (ps != null) try { ps.close(); } catch (Exception e) {}
        }

        return "LOT-" + new java.text.SimpleDateFormat("yyMMdd").format(new java.util.Date()) + "-001";
    }

    // =========================
    // 등록
    // - 사용자가 입력하는 값: ITEM_KEY, CURRENT_QTY
    // - 안전재고는 TB_ITEM.SAFE_QTY 사용
    // - LOT 자동 생성
    // - UPDATED_AT = SYSDATE
    // =========================
    public int insert(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();

            String nextLot = getNextLot(conn);

            String sql = ""
                    + "INSERT INTO TB_STOCK ( "
                    + "    STOCK_KEY, LOT, DONE_QC, WAIT_QC, CURRENT_QTY, SAFE_QTY, UPDATED_AT, ITEM_KEY "
                    + ") VALUES ( "
                    + "    STOCK_SEQ.NEXTVAL, ?, 0, 0, ?, "
                    + "    (SELECT SAFE_QTY FROM TB_ITEM WHERE ITEM_KEY = ?), "
                    + "    SYSDATE, ? "
                    + ")";

            ps = conn.prepareStatement(sql);
            ps.setString(1, nextLot);
            ps.setInt(2, dto.getCurrent_qty());
            ps.setInt(3, dto.getItem_key());
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
    // - 현재고 수량만 수정 가능
    // - 나머지 값은 그대로 유지
    // =========================
    public int update(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();

            String sql = ""
                    + "UPDATE TB_STOCK "
                    + "SET CURRENT_QTY = ?, "
                    + "    UPDATED_AT = SYSDATE "
                    + "WHERE STOCK_KEY = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getCurrent_qty());
            ps.setInt(2, dto.getStock_key());

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