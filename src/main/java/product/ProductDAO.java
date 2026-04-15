package product;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class ProductDAO {
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        return ds.getConnection();
    }

    // 목록 조회 (최대 5개로 제한)
    public List<ProductDTO> selectAll(int startRow, int endRow, String keyword) {
        List<ProductDTO> list = new ArrayList<>();
        // ROWNUM <= 5 조건을 추가하여 6번 이후 데이터는 차단합니다.
        String sql = "SELECT * FROM ( "
                   + "  SELECT ROWNUM rn, t.* FROM ( "
                   + "    SELECT * FROM TB_item "
                   + "    WHERE ITEM_NAME LIKE ? OR ITEM_CODE LIKE ? "
                   + "    ORDER BY ITEM_KEY ASC "
                   + "  ) t WHERE ROWNUM <= 5 " 
                   + ") WHERE rn BETWEEN ? AND ?";
        
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setInt(3, startRow);
            ps.setInt(4, endRow);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProduct_key(rs.getInt("ITEM_KEY"));
                dto.setItem_code(rs.getString("ITEM_CODE"));
                dto.setProduct_name(rs.getString("ITEM_NAME"));
                dto.setSpec(rs.getString("SPEC"));
                dto.setUnit(rs.getString("UNIT"));
                dto.setPrice(rs.getInt("PRICE"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 개수 조회도 최대 5개로 제한 (페이징 버튼 생성을 방지)
    public int getTotalCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM (SELECT * FROM TB_item WHERE (ITEM_NAME LIKE ? OR ITEM_CODE LIKE ?) AND ROWNUM <= 5)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int insert(ProductDTO dto) {
        String sql = "INSERT INTO TB_item (ITEM_KEY, ITEM_CODE, ITEM_NAME, SPEC, UNIT, PRICE) VALUES (SEQ_ITEM.NEXTVAL, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getItem_code());
            ps.setString(2, dto.getProduct_name());
            ps.setString(3, dto.getSpec());
            ps.setString(4, dto.getUnit());
            ps.setInt(5, dto.getPrice());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int update(ProductDTO dto) {
        String sql = "UPDATE TB_item SET ITEM_CODE=?, ITEM_NAME=?, SPEC=?, UNIT=?, PRICE=? WHERE ITEM_KEY=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getItem_code());
            ps.setString(2, dto.getProduct_name());
            ps.setString(3, dto.getSpec());
            ps.setString(4, dto.getUnit());
            ps.setInt(5, dto.getPrice());
            ps.setInt(6, dto.getProduct_key());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }

    public int delete(String ids) {
        String sql = "DELETE FROM TB_item WHERE ITEM_KEY IN (" + ids + ")";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
}