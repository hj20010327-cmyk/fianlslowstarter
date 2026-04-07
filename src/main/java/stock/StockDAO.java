package stock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    
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

    //  검색 및 목록 조회 메서드 
    public List<StockDTO> selectSearch(String searchCode, String searchName, String searchType) {
        List<StockDTO> list = new ArrayList<>();
        
        
        String sql = "SELECT * FROM (SELECT * FROM tb_stock WHERE 1=1";
        
        if (searchCode != null && !searchCode.isEmpty()) sql += " AND lot_key LIKE ?";
        if (searchName != null && !searchName.isEmpty()) sql += " AND name LIKE ?";
       
        if (searchType != null && !searchType.isEmpty()) sql += " AND type = ?";

        sql += " ORDER BY lot_key DESC) WHERE ROWNUM <= 3"; 

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            int idx = 1;
            if (searchCode != null && !searchCode.isEmpty()) pstmt.setString(idx++, "%" + searchCode + "%");
            if (searchName != null && !searchName.isEmpty()) pstmt.setString(idx++, "%" + searchName + "%");
            if (searchType != null && !searchType.isEmpty()) pstmt.setString(idx++, searchType);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StockDTO dto = new StockDTO();
                    dto.setLot_key(rs.getString("lot_key")); 
                    dto.setName(rs.getString("name"));
                    dto.setType(rs.getString("type"));
                    dto.setRemain(rs.getInt("remain"));
                    dto.setVender(rs.getString("vender"));
                    
                    dto.setStatus(rs.getString("status") != null ? rs.getString("status") : "정상"); 
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
        String sql = "DELETE FROM tb_stock WHERE lot_key = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lot_key);
            result = pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }
}