package quality;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class QualityDAO {
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        return dataFactory.getConnection();
    }

    public List<QualityDTO> selectPage(int startRow, int endRow, String keyword) {
        List<QualityDTO> list = new ArrayList<>();
        Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            conn = getConnection();
            // TB_QUALITY에 있는 컬럼만 조회하도록 쿼리 수정
            String sql = "SELECT * FROM ( SELECT ROWNUM rnum, A.* FROM ( "
                       + "SELECT * FROM TB_QUALITY WHERE QUALITY_CODE LIKE ? ORDER BY QUALITY_KEY DESC "
                       + ") A WHERE ROWNUM <= ? ) WHERE rnum >= ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            ps.setInt(2, endRow); ps.setInt(3, startRow);
            rs = ps.executeQuery();
            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                dto.setQuality_key(rs.getInt("QUALITY_KEY"));
                dto.setQuality_code(rs.getString("QUALITY_CODE"));
               
                dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
                dto.setQc_status(rs.getString("QC_STATUS"));
                dto.setInspector(rs.getString("INSPECTOR"));
                dto.setInspect_date(rs.getDate("INSPECT_DATE"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(conn, ps, rs); }
        return list;
    }

    public int getTotalCount(String keyword) {
        int count = 0;
        Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM TB_QUALITY WHERE QUALITY_CODE LIKE ?");
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%" );
            rs = ps.executeQuery();
            if(rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(conn, ps, rs); }
        return count;
    }

    // Service에서 에러 안 나도록 메서드 이름 확인 (insertquality)
    public int insertquality(QualityDTO dto) { return 1; } // 우선 틀만 만듦
    public int updatequality(QualityDTO dto) { return 1; }
    public int deletequality(String code) { return 1; }

    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if(rs!=null) rs.close(); if(ps!=null) ps.close(); if(conn!=null) conn.close(); } catch(Exception e){}
    }
}