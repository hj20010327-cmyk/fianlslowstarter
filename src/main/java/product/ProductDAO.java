package product;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class ProductDAO {
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        return dataFactory.getConnection();
    }

    // 1. 목록 조회: ITEM_CODE와 PRICE 컬럼 추가, 정렬 순서 유지
    public List<ProductDTO> selectAll(int startRow, int endRow, String keyword) {
        List<ProductDTO> list = new ArrayList<>();
        // ITEM_CODE와 PRICE를 쿼리에 추가
        String sql = "SELECT * FROM ( "
                   + "  SELECT ROWNUM AS rn, t.* FROM ( "
                   + "    SELECT ITEM_KEY AS product_key, ITEM_CODE, ITEM_NAME AS product_name, SPEC, UNIT, PRICE "
                   + "    FROM TB_item "
                   + "    WHERE ITEM_CODE LIKE 'CP-A%' "
                   + "    AND (ITEM_NAME LIKE ? OR SPEC LIKE ?) "
                   + "    ORDER BY ITEM_KEY ASC " 
                   + "  ) t "
                   + ") WHERE rn BETWEEN ? AND ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setInt(3, startRow);
            ps.setInt(4, endRow);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProduct_key(rs.getInt("product_key"));
                dto.setItem_code(rs.getString("ITEM_CODE")); // 품목 코드 추가
                dto.setProduct_name(rs.getString("product_name"));
                dto.setSpec(rs.getString("SPEC"));
                dto.setUnit(rs.getString("UNIT"));
                dto.setPrice(rs.getInt("PRICE"));           // 가격 추가
                dto.setRemarks(""); // 비고는 빈 값 처리
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. 전체 개수 조회
    public int getTotalCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM TB_item WHERE ITEM_CODE LIKE 'CP-A%' AND (ITEM_NAME LIKE ? OR SPEC LIKE ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // 3. 등록: PRICE 추가 및 REMARKS 제외
    public int insert(ProductDTO dto) {
        String sql = "INSERT INTO TB_item (ITEM_KEY, ITEM_CODE, ITEM_NAME, SPEC, UNIT, PRICE, STATUS) "
                   + "VALUES (SEQ_ITEM.NEXTVAL, 'CP-A-'||LPAD(SEQ_ITEM.NEXTVAL, 3, '0'), ?, ?, ?, ?, 'Y')";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getProduct_name());
            ps.setString(2, dto.getSpec());
            ps.setString(3, dto.getUnit());
            ps.setInt(4, dto.getPrice()); // 가격 등록 추가
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    // 4. 수정: PRICE 수정 추가 및 REMARKS 제외
    public int update(ProductDTO dto) {
        String sql = "UPDATE TB_item SET ITEM_NAME=?, SPEC=?, UNIT=?, PRICE=? WHERE ITEM_KEY=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getProduct_name());
            ps.setString(2, dto.getSpec());
            ps.setString(3, dto.getUnit());
            ps.setInt(4, dto.getPrice()); // 가격 수정 추가
            ps.setInt(5, dto.getProduct_key());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    // 5. 삭제
    public int delete(String ids) {
        if (ids == null || ids.isEmpty()) return 0;
        String sql = "DELETE FROM TB_item WHERE ITEM_KEY IN (" + ids + ")";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
}