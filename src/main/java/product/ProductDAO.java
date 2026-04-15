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

    // 1. 목록 조회: 품목코드(ITEM_CODE) 검색 조건 추가 및 5개 강제 제한 유지
    public List<ProductDTO> selectAll(int startRow, int endRow, String keyword) {
        List<ProductDTO> list = new ArrayList<>();
        // 품목명, 스펙 외에 ITEM_CODE 검색 조건을 추가했습니다.
        String sql = "SELECT * FROM ( "
                   + "  SELECT ROWNUM AS rn, t.* FROM ( "
                   + "    SELECT ITEM_KEY AS product_key, ITEM_CODE, ITEM_NAME AS product_name, SPEC, UNIT, PRICE "
                   + "    FROM TB_item "
                   + "    WHERE ITEM_KEY <= 5 " 
                   + "    AND (ITEM_NAME LIKE ? OR SPEC LIKE ? OR ITEM_CODE LIKE ?) "
                   + "    ORDER BY ITEM_KEY ASC " 
                   + "  ) t "
                   + ") WHERE rn BETWEEN ? AND ?";
        
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, search); 
            ps.setString(2, search);
            ps.setString(3, search); // 품목코드 검색 파라미터 추가
            ps.setInt(4, startRow); 
            ps.setInt(5, endRow);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductDTO dto = new ProductDTO();
                    dto.setProduct_key(rs.getInt("product_key"));
                    dto.setItem_code(rs.getString("ITEM_CODE"));
                    dto.setProduct_name(rs.getString("product_name"));
                    dto.setSpec(rs.getString("SPEC"));
                    dto.setUnit(rs.getString("UNIT"));
                    dto.setPrice(rs.getInt("PRICE"));
                    list.add(dto);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. 전체 개수 조회: 버튼 [1] 고정을 위해 5 반환 유지
    public int getTotalCount(String keyword) {
        return 5; 
    }

    // 3. 등록 (나중에 시연용)
    public int insert(ProductDTO dto) {
        int result = 0;
        long nextVal = 0;
        String sqlSeq = "SELECT SEQ_ITEM.NEXTVAL FROM DUAL";
        String sqlInsert = "INSERT INTO TB_item (ITEM_KEY, ITEM_CODE, ITEM_NAME, SPEC, UNIT, PRICE, STATUS) "
                         + "VALUES (?, ?, ?, ?, ?, ?, 'Y')"; // 코드를 직접 입력받도록 수정 가능

        try (Connection conn = getConnection()) {
            try (PreparedStatement psSeq = conn.prepareStatement(sqlSeq);
                 ResultSet rs = psSeq.executeQuery()) {
                if (rs.next()) nextVal = rs.getLong(1);
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setLong(1, nextVal);
                ps.setString(2, dto.getItem_code()); // 입력받은 품목코드 사용
                ps.setString(3, dto.getProduct_name());
                ps.setString(4, dto.getSpec());
                ps.setString(5, dto.getUnit());
                ps.setInt(6, dto.getPrice());
                result = ps.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    // 4. 수정: 품목 코드(ITEM_CODE)도 수정 가능하도록 SQL 반영
    public int update(ProductDTO dto) {
        // ITEM_CODE 수정 구문을 추가했습니다.
        String sql = "UPDATE TB_item SET ITEM_CODE=?, ITEM_NAME=?, SPEC=?, UNIT=?, PRICE=? WHERE ITEM_KEY=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getItem_code()); // 품목 코드 수정 반영
            ps.setString(2, dto.getProduct_name());
            ps.setString(3, dto.getSpec());
            ps.setString(4, dto.getUnit());
            ps.setInt(5, dto.getPrice());
            ps.setInt(6, dto.getProduct_key());
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