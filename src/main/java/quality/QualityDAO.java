package quality;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class QualityDAO {

    // DB 연결 (JNDI 방식)
    private Connection getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        return ds.getConnection();
    }


    /**
     * 1. 전체 목록 조회 (페이징 + 검색 조건 동적 추가)
     * [수정] Service로부터 start, end 외에 keyword와 status를 추가로 받습니다.
     */
    public List<QualityDTO> selectAll(int start, int end, String keyword, String status) {
        
        List<QualityDTO> list = new ArrayList<QualityDTO>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            
            // --- [동적 쿼리 생성 시작] ---
            // 기본 쿼리 베이스 (1=1은 조건 추가를 편하게 하기 위한 트릭입니다)
            String sql = "SELECT * FROM ("
                       + "  SELECT ROWNUM AS rn, t.* FROM ("
                       + "    SELECT q.*, i.item_name FROM tb_quality q "
                       + "    JOIN tb_item i ON q.prod_key = i.item_key "
                       + "    WHERE 1=1 ";

            // 검색어(keyword)가 있을 경우 조건 추가 (품목명 또는 검사번호 검색)
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql += " AND (i.item_name LIKE ? OR q.quality_code LIKE ?) ";
            }

            // 상태(status)가 있을 경우 조건 추가
            if (status != null && !status.trim().isEmpty()) {
                sql += " AND q.qc_status = ? ";
            }

            sql += "    ORDER BY q.quality_key ASC"
                 + "  ) t"
                 + ") WHERE rn BETWEEN ? AND ?";
            // --- [동적 쿼리 생성 종료] ---


            ps = conn.prepareStatement(sql);
            int idx = 1; // 파라미터 순서를 맞추기 위한 변수

            // 1. 검색어 세팅 (있을 경우에만)
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword + "%"); // 품목명 부분 검색
                ps.setString(idx++, "%" + keyword + "%"); // 검사번호 부분 검색
            }

            // 2. 상태값 세팅 (있을 경우에만)
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(idx++, status);
            }

            // 3. 페이징 파라미터 세팅 (무조건 마지막에 위치)
            ps.setInt(idx++, start);
            ps.setInt(idx++, end);

            
            // 이클립스 콘솔에서 쿼리 실행 확인용 (필요 시 주석 해제)
            // System.out.println("SQL 실행: " + sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                
                dto.setQuality_key(rs.getInt("quality_key"));
                dto.setQuality_code(rs.getString("quality_code"));
                dto.setItem_name(rs.getString("item_name"));
                dto.setInspect_qty(rs.getInt("inspect_qty"));
                dto.setQc_status(rs.getString("qc_status"));
                dto.setInspect_date(rs.getDate("inspect_date"));
                
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, ps, rs);
        }

        return list;

    }


    /**
     * 2. 품질 데이터 등록 (insertQuality)
     */
    public int insertQuality(QualityDTO dto) {

        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            conn = getConnection();

            String sql = "INSERT INTO tb_quality (quality_key, quality_code, inspect_qty, "
                       + "good_qty, defect_qty, defect_reason, qc_status, prod_key, inspect_date) "
                       + "VALUES (seq_quality.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getQuality_code());
            ps.setInt(2, dto.getInspect_qty());
            ps.setInt(3, dto.getGood_qty());
            ps.setInt(4, dto.getDefect_qty());
            ps.setString(5, dto.getDefect_reason());
            ps.setString(6, dto.getQc_status());
            ps.setInt(7, dto.getProd_key());
            ps.setDate(8, dto.getInspect_date());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, ps, null);
        }

        return result;

    }


    /**
     * 3. 품질 데이터 수정 (updateQuality)
     */
    public int updateQuality(QualityDTO dto) {

        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            conn = getConnection();

            String sql = "UPDATE tb_quality SET qc_status = ?, defect_reason = ? "
                       + "WHERE quality_key = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getQc_status());
            ps.setString(2, dto.getDefect_reason());
            ps.setInt(3, dto.getQuality_key());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, ps, null);
        }

        return result;

    }


    /**
     * 4. 품질 데이터 삭제 (deleteQuality)
     */
    public int deleteQuality(QualityDTO dto) {

        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            conn = getConnection();

            String sql = "DELETE FROM tb_quality WHERE quality_key = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getQuality_key());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, ps, null);
        }

        return result;

    }


    /**
     * 자원 해제 메서드
     */
    private void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conn != null) conn.close();
        } catch(Exception e) {}
    }

}