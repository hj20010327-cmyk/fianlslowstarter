package stock;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class StockDAO {

    // [DB 연결] 품질관리와 동일하게 DBCP 방식 사용
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        return dataFactory.getConnection();
    }

    // [조회] 1번부터 나오도록 ASC 정렬 및 DB 사진 컬럼 매칭
    public List<StockDTO> selectAll(int startRow, int endRow, String keyword) {
        List<StockDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            
            // 정렬을 STOCK_KEY ASC로 변경하여 1번부터 나오게 수정했습니다.
            String sql = "SELECT * FROM ( "
                       + "  SELECT ROWNUM rnum, A.* FROM ( "
                       + "    SELECT * FROM TB_STOCK WHERE UPPER(LOT) LIKE UPPER(?) "
                       + "    ORDER BY STOCK_KEY ASC " 
                       + "  ) A WHERE ROWNUM <= ? "
                       + ") WHERE rnum >= ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            ps.setInt(2, endRow);
            ps.setInt(3, startRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                StockDTO dto = new StockDTO();
                // DB 사진(TB_STOCK) 컬럼 순서와 이름에 맞게 세팅
                dto.setStock_key(rs.getInt("STOCK_KEY"));
                dto.setLot(rs.getString("LOT"));
                dto.setIn_qty(rs.getInt("IN_QTY"));
                dto.setOut_qty(rs.getInt("OUT_QTY"));
                dto.setCurrent_qty(rs.getInt("CURRENT_QTY"));
                dto.setSafe_qty(rs.getInt("SAFE_QTY"));
                dto.setUpdated_at(rs.getTimestamp("UPDATED_AT"));
                dto.setItem_key(rs.getInt("ITEM_KEY"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return list;
    }

    // [개수 조회] 페이징 처리를 위해 전체 데이터 개수를 가져오는 메소드 (추가됨)
    public int getTotalCount(String keyword) {
        int total = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            String sql = "SELECT COUNT(*) FROM TB_STOCK WHERE UPPER(LOT) LIKE UPPER(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            
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

    // [등록] 현재고(입고-출고) 자동 계산 로직 포함
    public int insert(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String query = "INSERT INTO TB_STOCK (STOCK_KEY, LOT, IN_QTY, OUT_QTY, CURRENT_QTY, SAFE_QTY, UPDATED_AT, ITEM_KEY) "
                         + "VALUES (STOCK_SEQ.NEXTVAL, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, dto.getLot());
            ps.setInt(2, dto.getIn_qty());
            ps.setInt(3, dto.getOut_qty());
            ps.setInt(4, dto.getIn_qty() - dto.getOut_qty()); // 현재고 계산
            ps.setInt(5, dto.getSafe_qty());
            ps.setInt(6, dto.getItem_key());
            return ps.executeUpdate();
        } catch (Exception e) { 
            e.printStackTrace(); 
            return 0; 
        } finally { 
            close(conn, ps, null); 
        }
    }
    
    // [수정] 서비스에서 호출하는 이름과 일치 (update)
    public int update(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            // 수정 시에도 현재고가 다시 계산되도록 처리
            String query = "UPDATE TB_STOCK SET LOT=?, IN_QTY=?, OUT_QTY=?, SAFE_QTY=?, CURRENT_QTY=?, UPDATED_AT=CURRENT_TIMESTAMP WHERE STOCK_KEY=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, dto.getLot());
            ps.setInt(2, dto.getIn_qty());
            ps.setInt(3, dto.getOut_qty());
            ps.setInt(4, dto.getSafe_qty());
            ps.setInt(5, dto.getIn_qty() - dto.getOut_qty()); // 현재고 갱신
            ps.setInt(6, dto.getStock_key());
            
            return ps.executeUpdate();
        } catch (Exception e) { 
            e.printStackTrace(); 
            return 0; 
        } finally { 
            close(conn, ps, null); 
        }
    }

    // [삭제]
    public int delete(String ids) {
        if (ids == null || ids.isEmpty()) return 0;
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
            try { if(stmt!=null)stmt.close(); if(conn!=null)conn.close(); } catch(Exception e){}
        }
    }

    // 자원 해제
    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if(rs!=null)rs.close(); if(ps!=null)ps.close(); if(conn!=null)conn.close(); } catch(Exception e){}
    }
}