package stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    // [DB 연결] 오라클 접속 정보 (본인 환경에 맞게 수정 필요)
    private Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
     // 
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "comop", "1234");
    }

    // [조회] 페이징 처리와 검색 기능
    public List<StockDTO> selectAll(int page, String keyword) {
        List<StockDTO> list = new ArrayList<>();
        
        int start = (page - 1) * 10 + 1; // 페이지 시작 번호
        int end = page * 10;             // 페이지 끝 번호
        
        // 검색어가 있으면 LOT번호로 필터링하는 쿼리
        String sql = "SELECT * FROM ( " +
                     "  SELECT rownum rnum, a.* FROM ( " +
                     "    SELECT * FROM tb_stock WHERE lot LIKE ? ORDER BY stock_key DESC " +
                     "  ) a " +
                     ") WHERE rnum BETWEEN ? AND ?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            pstmt.setInt(2, start);
            pstmt.setInt(3, end);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setStock_key(rs.getInt("stock_key"));
                dto.setLot(rs.getString("lot"));
                dto.setIn_qty(rs.getInt("in_qty"));
                dto.setOut_qty(rs.getInt("out_qty"));
                dto.setCurrent_qty(rs.getInt("current_qty"));
                dto.setSafe_qty(rs.getInt("safe_qty"));
                dto.setUpdated_at(rs.getTimestamp("updated_at"));
                dto.setItem_key(rs.getInt("item_key"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // [등록] 신규 재고 데이터 추가
    public int insert(StockDTO dto) {
        // current_qty는 (입고 - 출고)로 자동 계산하여 입력
        String sql = "INSERT INTO tb_stock (stock_key, lot, in_qty, out_qty, current_qty, safe_qty, updated_at, item_key) " +
                     "VALUES (stock_seq.NEXTVAL, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?)";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dto.getLot());
            pstmt.setInt(2, dto.getIn_qty());
            pstmt.setInt(3, dto.getOut_qty());
            pstmt.setInt(4, dto.getIn_qty() - dto.getOut_qty()); // 현재고 계산
            pstmt.setInt(5, dto.getSafe_qty());
            pstmt.setInt(6, dto.getItem_key());
            
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // [수정] 기존 재고 데이터 정보 업데이트
    public int update(StockDTO dto) {
        String sql = "UPDATE tb_stock SET lot=?, in_qty=?, out_qty=?, current_qty=?, safe_qty=?, updated_at=CURRENT_TIMESTAMP " +
                     "WHERE stock_key=?";
        
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dto.getLot());
            pstmt.setInt(2, dto.getIn_qty());
            pstmt.setInt(3, dto.getOut_qty());
            pstmt.setInt(4, dto.getIn_qty() - dto.getOut_qty()); // 수량 재계산
            pstmt.setInt(5, dto.getSafe_qty());
            pstmt.setInt(6, dto.getStock_key());
            
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // [삭제] 선택한 항목 일괄 삭제
    public int delete(String ids) {
        String sql = "DELETE FROM tb_stock WHERE stock_key IN (" + ids + ")";
        
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            
            return stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}