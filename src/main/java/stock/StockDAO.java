package stock;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StockDAO {


    // 팀 프로젝트 DB 정보로 꼭 확인하세요!
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String user = "comp"; 
    private String password = "1234";




    public List<StockDTO> getStockList(int page) {


        List<StockDTO> list = new ArrayList<>();


        // 재고 목록 조회 + 페이징 쿼리
        String sql = "SELECT * FROM ("
                   + "  SELECT rownum rnum, s.* FROM ("
                   + "    SELECT * FROM tb_stock ORDER BY stock_key DESC"
                   + "  ) s"
                   + ") WHERE rnum BETWEEN ? AND ?";




        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            // 한 페이지에 10개씩 보여주는 계산식
            int startRow = (page - 1) * 10 + 1;
            int endRow = page * 10;


            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);




            try (ResultSet rs = pstmt.executeQuery()) {


                while (rs.next()) {


                    StockDTO dto = new StockDTO();


                    dto.setStockKey(rs.getInt("stock_key"));
                    dto.setLot(rs.getString("lot"));
                    dto.setInQty(rs.getInt("in_qty"));
                    dto.setOutQty(rs.getInt("out_qty"));
                    dto.setCurrentQty(rs.getInt("current_qty"));
                    dto.setSafeQty(rs.getInt("safe_qty"));
                    dto.setUpdatedAt(rs.getString("updated_at"));


                    list.add(dto);


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;


    }


}