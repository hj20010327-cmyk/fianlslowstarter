package quality;

import java.sql.*;
import java.util.*;

public class QualityDAO {

    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String user = "mes_admin";
    private String password = "password";

    // [개수 조회] 페이징을 위한 전체 카운트
    public int getTotalCount(String searchCode) {
    
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tb_quality WHERE quality_code LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + (searchCode == null ? "" : searchCode) + "%");
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            
        } catch (Exception e) { e.printStackTrace(); }
        
        return count;
    }

    // [목록 조회] 페이징 + 검색 + 조인
    public List<QualityDTO> selectQualityList(String searchCode, int startRow, int endRow) {
    
        List<QualityDTO> list = new ArrayList<>();
        
        String sql = "SELECT * FROM ( "
                   + "  SELECT rownum rnum, a.* FROM ( "
                   + "    SELECT q.*, i.item_name "
                   + "    FROM tb_quality q "
                   + "    JOIN tb_item i ON q.prod_key = i.item_key "
                   + "    WHERE q.quality_code LIKE ? "
                   + "    ORDER BY q.quality_key ASC "
                   + "  ) a "
                   + ") WHERE rnum BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + (searchCode == null ? "" : searchCode) + "%");
            pstmt.setInt(2, startRow);
            pstmt.setInt(3, endRow);

            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                
                dto.setQuality_key(rs.getString("quality_key"));
                dto.setQuality_code(rs.getString("quality_code"));
                dto.setInspect_qty(rs.getInt("inspect_qty"));
                dto.setGood_qty(rs.getInt("good_qty"));
                dto.setDefect_qty(rs.getInt("defect_qty"));
                dto.setQc_status(rs.getString("qc_status"));
                dto.setItem_name(rs.getString("item_name"));
                
                list.add(dto);
            }
            
        } catch (Exception e) { e.printStackTrace(); }
        
        return list;
    }
}