package quality;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class QualityDAO {
    private Connection getConn() {
        try {
            Context ctx = new InitialContext();
            DataSource df = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
            return df.getConnection();
        } catch(Exception e) { e.printStackTrace(); return null; }
    }

    // 전체 목록 조회 (컬럼명 대폭 수정)
    public List<QualityDTO> selectAll() {
        List<QualityDTO> list = new ArrayList<>();
        // 이미지 순서에 맞춰 정렬하거나 필요한 컬럼을 명시하는 것이 좋습니다.
        String sql = "SELECT * FROM TB_QUALITY where rownum<=4 ORDER BY QUALITY_KEY DESC ";
        
        try(
            Connection conn = getConn(); 
            PreparedStatement ps = conn.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()) {
                QualityDTO dto = new QualityDTO();
                // DB 컬럼명과 DTO 세터 매칭 (DTO 필드명이 아래와 같다고 가정합니다)
                dto.setQuality_key(rs.getString("QUALITY_KEY"));
                dto.setProd_key(rs.getString("PROD_KEY"));
                dto.setItem_key(rs.getString("ITEM_KEY"));
                dto.setInspect_date(rs.getTimestamp("INSPECT_DATE")); // DATE 타입
                dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
                dto.setGood_qty(rs.getInt("GOOD_QTY"));
                dto.setDefect_qty(rs.getInt("DEFECT_QTY"));
                dto.setDefect_reason(rs.getString("DEFECT_REASON"));
                dto.setQc_status(rs.getString("QC_STATUS"));
                dto.setUser_key(rs.getString("USER_KEY"));
                dto.setCreated_at(rs.getTimestamp("CREATED_AT"));
                
                list.add(dto);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    // 데이터 삽입 (추가된 컬럼 모두 반영)
    public int insert(QualityDTO dto) {
        // 컬럼을 명시적으로 적어주는 것이 유지보수에 훨씬 유리합니다.
        String sql = "INSERT INTO TB_QUALITY (QUALITY_KEY, PROD_KEY, ITEM_KEY, INSPECT_DATE, INSPECT_QTY, "
                   + "GOOD_QTY, DEFECT_QTY, DEFECT_REASON, QC_STATUS, USER_KEY, CREATED_AT) "
                   + "VALUES (SEQ_QUALITY.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, SYSDATE)";
        
        try(Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getProd_key());
            ps.setString(2, dto.getItem_key());
            ps.setInt(3, dto.getInspect_qty());
            ps.setInt(4, dto.getGood_qty());
            ps.setInt(5, dto.getDefect_qty());
            ps.setString(6, dto.getDefect_reason());
            ps.setString(7, dto.getQc_status());
            ps.setString(8, dto.getUser_key());
            
            return ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); return 0; }
    }

    // 삭제는 기존과 동일 (PK 기준)
    public int delete(String quality_key) {
        String sql = "DELETE FROM TB_QUALITY WHERE QUALITY_KEY = ?";
        try(Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, quality_key);
            return ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); return 0; }
    }
}