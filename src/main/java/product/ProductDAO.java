package product;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {


    // 1. DB 연결 정보 (직접 설정)
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String user = "comp";      // 본인의 오라클 아이디
    private String password = "1234";  // 본인의 오라클 비밀번호




    // 2. DB 연결 메서드
    private Connection getConnection() throws Exception {


        Class.forName("oracle.jdbc.driver.OracleDriver");


        return DriverManager.getConnection(url, user, password);
    }




    // 3. 완제품 목록 조회 (ITEM_CODE가 CP-A로 시작)
    public List<ProductDTO> selectProductList() {


        List<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TB_ITEM WHERE ITEM_CODE LIKE 'CP-A%' ORDER BY ITEM_KEY DESC";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {


            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setItemKey(rs.getInt("ITEM_KEY"));
                dto.setItemCode(rs.getString("ITEM_CODE"));
                dto.setItemName(rs.getString("ITEM_NAME"));
                dto.setSpec(rs.getString("ITEM_SPEC"));
                dto.setUnit(rs.getString("ITEM_UNIT"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setSafeQty(rs.getInt("SAFE_QTY"));
                dto.setStatus(rs.getString("STATUS"));
                list.add(dto);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }




    // 4. 제품 삭제 (DeleteController에서 에러 나던 부분)
    public int deleteProduct(int itemKey) {


        String sql = "DELETE FROM TB_ITEM WHERE ITEM_KEY = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setInt(1, itemKey);


            return pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }




    // 5. 신규 제품 등록
    public int insertProduct(ProductDTO dto) {


        String sql = "INSERT INTO TB_ITEM (ITEM_KEY, ITEM_CODE, ITEM_NAME, ITEM_SPEC, ITEM_UNIT, PRICE, SAFE_QTY, STATUS) " +
                     "VALUES (SEQ_ITEM.NEXTVAL, ?, ?, ?, ?, ?, ?, 'Y')";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, dto.getItemCode());
            pstmt.setString(2, dto.getItemName());
            pstmt.setString(3, dto.getSpec());
            pstmt.setString(4, dto.getUnit());
            pstmt.setInt(5, dto.getPrice());
            pstmt.setInt(6, dto.getSafeQty());


            return pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }




    // 6. 제품 정보 수정
    public int updateProduct(ProductDTO dto) {


        String sql = "UPDATE TB_ITEM SET ITEM_NAME = ?, ITEM_SPEC = ?, PRICE = ?, SAFE_QTY = ?, STATUS = ? WHERE ITEM_KEY = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, dto.getItemName());
            pstmt.setString(2, dto.getSpec());
            pstmt.setInt(3, dto.getPrice());
            pstmt.setInt(4, dto.getSafeQty());
            pstmt.setString(5, dto.getStatus());
            pstmt.setInt(6, dto.getItemKey());


            return pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}