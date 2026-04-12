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
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            
            // 1번부터 나오게 정렬을 ASC(오름차순)로 변경했습니다.
            String sql = "SELECT * FROM ( "
                       + "  SELECT ROWNUM rnum, A.* FROM ( "
                       + "    SELECT * FROM TB_QUALITY WHERE QUALITY_CODE LIKE ? "
                       + "    ORDER BY QUALITY_KEY ASC " 
                       + "  ) A WHERE ROWNUM <= ? "
                       + ") WHERE rnum >= ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, endRow);
            ps.setInt(3, startRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                // 품목명 로직 제외, DB 캡처의 대문자 컬럼명 적용
                dto.setQuality_code(rs.getString("QUALITY_CODE"));
                dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
                dto.setQc_status(rs.getString("QC_STATUS"));
                dto.setInspect_date(rs.getDate("INSPECT_DATE"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return list;
    }

    // Insert/Update/Delete (TB_QUALITY 테이블 기준)
    public int insertquality(QualityDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String query = "INSERT INTO TB_QUALITY (QUALITY_CODE, INSPECT_QTY, QC_STATUS, INSPECT_DATE) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, dto.getQuality_code());
            ps.setInt(2, dto.getInspect_qty());
            ps.setString(3, dto.getQc_status());
            ps.setDate(4, dto.getInspect_date());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { close(conn, ps, null); }
    }

    public int updatequality(QualityDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String query = "UPDATE TB_QUALITY SET QUALITY_CODE=?, INSPECT_QTY=?, QC_STATUS=?, INSPECT_DATE=? WHERE QUALITY_CODE=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, dto.getQuality_code());
            ps.setInt(2, dto.getInspect_qty());
            ps.setString(3, dto.getQc_status());
            ps.setDate(4, dto.getInspect_date());
            ps.setString(5, dto.getQuality_code());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { close(conn, ps, null); }
    }

    public int deletequality(QualityDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String query = "DELETE FROM TB_QUALITY WHERE QUALITY_CODE = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, dto.getQuality_code());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
        finally { close(conn, ps, null); }
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if(rs!=null)rs.close(); if(ps!=null)ps.close(); if(conn!=null)conn.close(); } catch(Exception e){}
    }
}