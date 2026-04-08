package stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String user = "comp";      
    private String password = "1234";  

    public List<StockDTO> getStockList(String searchCode) {
        
        System.out.println("DAO: getStockList 실행 시작");
        
        List<StockDTO> list = new ArrayList<>();
        
        // 1. 테이블 이름이 실제 DB와 똑같은지 반드시 확인하세요! (예: TB_STOCK)
        String sql = " SELECT STOCK_KEY, ITEM_KEY, IN_QTY, OUT_QTY, CURRENT_QTY, SAFE_QTY "
                   + " FROM TB_stock " 
                   + " WHERE 1=1 ";
        
        if (searchCode != null && !searchCode.isEmpty()) {
            sql += " AND ITEM_KEY LIKE ? ";
        }

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (searchCode != null && !searchCode.isEmpty()) {
                ps.setString(1, "%" + searchCode + "%");
            }

            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                StockDTO dto = new StockDTO();
                
                // 2. DB 컬럼명(STOCK_KEY 등)과 DTO 변수명이 정확히 매칭되어야 합니다.
                dto.setStock_key(rs.getInt("STOCK_KEY"));
                dto.setItem_key(rs.getString("ITEM_KEY"));
                dto.setIn_qty(rs.getInt("IN_QTY"));
                dto.setOut_qty(rs.getInt("OUT_QTY"));
                dto.setCurrent_qty(rs.getInt("CURRENT_QTY"));
                dto.setSafe_qty(rs.getInt("SAFE_QTY"));
                
                list.add(dto);
            }
            
            // 콘솔창에 이 숫자가 찍히는지 확인하세요!
            System.out.println("DAO: DB에서 가져온 행 개수 = " + list.size());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }
}