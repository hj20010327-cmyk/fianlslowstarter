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

    // 매개변수를 3개로 맞춰서 Controller에서의 에러를 방지합니다.
    public List<ProductDTO> selectAll(int startRow, int endRow, String keyword) {
        List<ProductDTO> list = new ArrayList<>();
        // 페이징 1페이지 고정이므로 start/endRow는 쿼리에서 일단 제외하고 검색어만 처리합니다.
        String sql = "SELECT * FROM TB_PRODUCT WHERE PRODUCT_NAME LIKE ? ORDER BY PRODUCT_KEY ASC";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProduct_key(rs.getInt("PRODUCT_KEY"));
                dto.setProduct_name(rs.getString("PRODUCT_NAME"));
                dto.setSpec(rs.getString("SPEC"));
                dto.setUnit(rs.getString("UNIT"));
                dto.setRemarks(rs.getString("REMARKS"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 전체 개수 조회 (페이징 UI를 위해 필요)
    public int getTotalCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM TB_PRODUCT WHERE PRODUCT_NAME LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int insert(ProductDTO dto) {
        String sql = "INSERT INTO TB_PRODUCT (PRODUCT_KEY, PRODUCT_NAME, SPEC, UNIT, REMARKS) VALUES (SEQ_PRODUCT.NEXTVAL, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getProduct_name());
            ps.setString(2, dto.getSpec());
            ps.setString(3, dto.getUnit());
            ps.setString(4, dto.getRemarks());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int update(ProductDTO dto) {
        String sql = "UPDATE TB_PRODUCT SET PRODUCT_NAME=?, SPEC=?, UNIT=?, REMARKS=? WHERE PRODUCT_KEY=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getProduct_name());
            ps.setString(2, dto.getSpec());
            ps.setString(3, dto.getUnit());
            ps.setString(4, dto.getRemarks());
            ps.setInt(5, dto.getProduct_key());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int delete(String ids) {
        if (ids == null || ids.isEmpty()) return 0;
        String sql = "DELETE FROM TB_PRODUCT WHERE PRODUCT_KEY IN (" + ids + ")";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
}