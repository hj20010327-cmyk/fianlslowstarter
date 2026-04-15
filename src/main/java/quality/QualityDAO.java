package quality;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class QualityDAO {
    
    // DB 연결 (DBCP 사용)
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        return dataFactory.getConnection();
    }

    // 1. 데이터 목록 조회 (JOIN을 사용하여 실제 품목명 추출)
    public List<QualityDTO> selectPage(int startRow, int endRow, String keyword) {
        List<QualityDTO> list = new ArrayList<>();
        Connection conn = null; 
        PreparedStatement ps = null; 
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            
            // TB_QUALITY와 TB_ITEM을 PROD_KEY로 조인하여 실제 ITEM_NAME과 ITEM_CODE를 가져옵니다.
            String sql = "SELECT * FROM ( "
                       + "  SELECT ROWNUM rnum, A.* FROM ( "
                       + "    SELECT Q.QUALITY_CODE, I.ITEM_CODE, I.ITEM_NAME, "
                       + "           Q.INSPECT_QTY, Q.QC_STATUS, Q.INSPECT_DATE, Q.QUALITY_KEY "
                       + "    FROM TB_QUALITY Q "
                       + "    LEFT JOIN TB_ITEM I ON Q.PROD_KEY = I.PROD_KEY "
                       + "    WHERE Q.QUALITY_CODE LIKE ? OR I.ITEM_NAME LIKE ? "
                       + "    ORDER BY Q.QUALITY_KEY DESC "
                       + "  ) A WHERE ROWNUM <= ? "
                       + ") WHERE rnum >= ?";
            
            ps = conn.prepareStatement(sql);
            String search = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setInt(3, endRow); 
            ps.setInt(4, startRow);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                // 요청하신 컬럼 구성: 검사번호, 품목코드, 품목명, 검사수량, 상태, 검사일자
                dto.setQuality_code(rs.getString("QUALITY_CODE"));
                dto.setItem_code(rs.getString("ITEM_CODE")); // 실제 품목코드
                dto.setItem_name(rs.getString("ITEM_NAME")); // 실제 품목명 (확인용 삭제)
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

    // 2. 전체 데이터 개수 조회 (검색어 포함)
    public int getTotalCount(String keyword) {
        int count = 0;
        Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT COUNT(*) FROM TB_QUALITY Q "
                       + "LEFT JOIN TB_ITEM I ON Q.PROD_KEY = I.PROD_KEY "
                       + "WHERE Q.QUALITY_CODE LIKE ? OR I.ITEM_NAME LIKE ?";
            ps = conn.prepareStatement(sql);
            String search = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            rs = ps.executeQuery();
            if(rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(conn, ps, rs); }
        return count;
    }

    // 3. 데이터 등록 (INSERT)
    public int insertquality(QualityDTO dto) {
        Connection conn = null; PreparedStatement ps = null;
        String sql = "INSERT INTO TB_QUALITY (QUALITY_KEY, QUALITY_CODE, INSPECT_DATE, INSPECT_QTY, QC_STATUS, PROD_KEY, USER_KEY, DEFECT_REASON) "
                   + "VALUES (SEQ_QUALITY_KEY.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection(); 
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getQuality_code()); 
            ps.setDate(2, dto.getInspect_date());
            ps.setInt(3, dto.getInspect_qty()); 
            ps.setString(4, dto.getQc_status());
            ps.setString(5, dto.getItem_code()); // PROD_KEY 매칭
            ps.setString(6, dto.getInspector()); // USER_KEY 매칭
            ps.setString(7, dto.getRemarks());   // DEFECT_REASON 매칭
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; } 
        finally { close(conn, ps, null); }
    }

    // 4. 데이터 수정 (UPDATE)
    public int updatequality(QualityDTO dto) {
        Connection conn = null; PreparedStatement ps = null;
        String sql = "UPDATE TB_QUALITY SET INSPECT_QTY=?, QC_STATUS=?, INSPECT_DATE=?, DEFECT_REASON=? "
                   + "WHERE QUALITY_CODE=?";
        try {
            conn = getConnection(); 
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getInspect_qty()); 
            ps.setString(2, dto.getQc_status());
            ps.setDate(3, dto.getInspect_date()); 
            ps.setString(4, dto.getRemarks());
            ps.setString(5, dto.getQuality_code());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; } 
        finally { close(conn, ps, null); }
    }

    // 5. 데이터 삭제 (DELETE)
    public int deletequality(String code) {
        Connection conn = null; PreparedStatement ps = null;
        try {
            conn = getConnection(); 
            ps = conn.prepareStatement("DELETE FROM TB_QUALITY WHERE QUALITY_CODE = ?");
            ps.setString(1, code); 
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; } 
        finally { close(conn, ps, null); }
    }

    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if(rs != null) rs.close(); if(ps != null) ps.close(); if(conn != null) conn.close(); } 
        catch(Exception e) { e.printStackTrace(); }
    }
}