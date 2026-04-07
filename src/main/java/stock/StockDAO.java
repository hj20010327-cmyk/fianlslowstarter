package stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *  Stock DB CRUD 연결 클래스
 * 
 */
public class StockDAO {

    // DB 연결 메서드
    public Connection getConnection() {
        Connection conn = null;
        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
            conn = DriverManager.getConnection(url, "comp", "1234"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 목록 조회 및 검색 메서드 
    public List<StockDTO> selectSearch(String searchCode, String searchName, String searchType) {
        List<StockDTO> list = new ArrayList<>();
        
       
        String sql = "SELECT * FROM (SELECT * FROM tb_stock WHERE 1=1";
        
        if (searchCode != null && !searchCode.isEmpty()) sql += " AND LOT_KEY LIKE ?";
        if (searchName != null && !searchName.isEmpty()) sql += " AND NAME LIKE ?";
        if (searchType != null && !searchType.isEmpty()) sql += " AND TYPE = ?";

        sql += " ORDER BY LOT_KEY ASC) WHERE ROWNUM <= 50"; 

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            int idx = 1;
           
            if (searchCode != null && !searchCode.isEmpty()) pstmt.setString(idx++, "%" + searchCode + "%");
            if (searchName != null && !searchName.isEmpty()) pstmt.setString(idx++, "%" + searchName + "%");
            if (searchType != null && !searchType.isEmpty()) pstmt.setString(idx++, searchType);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StockDTO dto = new StockDTO();
                    
                    
                    dto.setLot_key(rs.getString("LOT_KEY"));   // 로트번호
                    dto.setName(rs.getString("NAME"));         // 품목명
                    dto.setType(rs.getString("TYPE"));         // 구분(원자재/부자재 등)
                    dto.setRemain(rs.getInt("REMAIN"));       // 재고수량
                    dto.setVender(rs.getString("VENDER"));     // 협력사
                    
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 삭제 메서드
    public int deleteStock(String lot_key) {
        int result = 0;
        String sql = "DELETE FROM tb_stock WHERE LOT_KEY = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lot_key);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}