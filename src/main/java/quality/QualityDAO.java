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

    // 1. 목록 조회 (페이징 유지 + 5중 조인 적용)
    public List<QualityDTO> selectPage(int startRow, int endRow, String keyword) {
        List<QualityDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // TB_QUALITY에 없는 ITEM_NAME을 가져오기 위해 조인 경로를 수정했습니다.
        String sql = "SELECT * FROM ( "
                   + "  SELECT ROWNUM rnum, A.* FROM ( "
                   + "    SELECT Q.QUALITY_CODE, I.ITEM_CODE, I.ITEM_NAME, "
                   + "           Q.INSPECT_QTY AS QUALITY_QTY, Q.QC_STATUS, Q.INSPECT_DATE, Q.QUALITY_KEY, "
                   + "           Q.DEFECT_REASON " 
                   + "    FROM TB_QUALITY Q "
                   + "    LEFT OUTER JOIN TB_PRODUCTION P ON Q.PROD_KEY = P.PROD_KEY "
                   + "    LEFT OUTER JOIN TB_WORK_ORDER W ON P.WORK_ORDER_KEY = W.WORK_ORDER_KEY "
                   + "    LEFT OUTER JOIN TB_PLAN PLAN ON W.PLAN_KEY = PLAN.PLAN_KEY "
                   + "    LEFT OUTER JOIN TB_ITEM I ON PLAN.ITEM_KEY = I.ITEM_KEY " 
                   + "    WHERE Q.QUALITY_CODE LIKE ? OR I.ITEM_NAME LIKE ? "
                   + "    ORDER BY Q.QUALITY_KEY DESC "
                   + "  ) A WHERE ROWNUM <= ? "
                   + ") WHERE rnum >= ?";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            String search = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setInt(3, endRow);
            ps.setInt(4, startRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                dto.setQuality_code(rs.getString("QUALITY_CODE"));
                dto.setItem_code(rs.getString("ITEM_CODE"));
                dto.setItem_name(rs.getString("ITEM_NAME"));
                dto.setQuality_qty(rs.getInt("QUALITY_QTY")); 
                dto.setQc_status(rs.getString("QC_STATUS"));
                dto.setInspect_date(rs.getDate("INSPECT_DATE"));
                dto.setQuality_key(rs.getInt("QUALITY_KEY"));
                dto.setRemarks(rs.getString("DEFECT_REASON"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return list;
    }

    // 2. 전체 개수 조회 (조인 경로 동일하게 수정)
    public int getTotalCount(String keyword) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT COUNT(*) FROM TB_QUALITY Q "
                   + "LEFT OUTER JOIN TB_PRODUCTION P ON Q.PROD_KEY = P.PROD_KEY "
                   + "LEFT OUTER JOIN TB_WORK_ORDER W ON P.WORK_ORDER_KEY = W.WORK_ORDER_KEY "
                   + "LEFT OUTER JOIN TB_PLAN PLAN ON W.PLAN_KEY = PLAN.PLAN_KEY "
                   + "LEFT OUTER JOIN TB_ITEM I ON PLAN.ITEM_KEY = I.ITEM_KEY "
                   + "WHERE Q.QUALITY_CODE LIKE ? OR I.ITEM_NAME LIKE ?";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            String search = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return 0;
    }

    // 3. 데이터 등록 (PROD_KEY 기준)
    public int insertquality(QualityDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO TB_QUALITY (QUALITY_KEY, QUALITY_CODE, INSPECT_QTY, QC_STATUS, INSPECT_DATE, DEFECT_REASON, PROD_KEY) "
                   + "VALUES (seq_quality.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getQuality_code());
            ps.setInt(2, dto.getQuality_qty());
            ps.setString(3, dto.getQc_status());
            ps.setDate(4, dto.getInspect_date());
            ps.setString(5, dto.getRemarks());
            ps.setInt(6, dto.getProd_key()); 
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(conn, ps, null);
        }
    }

    // 4. 데이터 수정
    public int updatequality(QualityDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE TB_QUALITY SET INSPECT_QTY=?, QC_STATUS=?, INSPECT_DATE=?, DEFECT_REASON=? "
                   + "WHERE QUALITY_CODE=?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getQuality_qty());
            ps.setString(2, dto.getQc_status());
            ps.setDate(3, dto.getInspect_date());
            ps.setString(4, dto.getRemarks());
            ps.setString(5, dto.getQuality_code());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(conn, ps, null);
        }
    }

    // 5. 데이터 삭제
    public int deletequality(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM TB_QUALITY WHERE QUALITY_CODE = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close(conn, ps, null);
        }
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
}