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


	// 1. 전체 목록 조회 (페이징 처리 및 JOIN 포함) - [수정됨]
	public List<QualityDTO> selectAll(int start, int end) { // Service에서 계산된 start, end를 받습니다.
		
		List<QualityDTO> list = new ArrayList<QualityDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			
			// ROWNUM을 사용하여 필요한 범위(start ~ end)만큼 데이터를 자르는 쿼리입니다.
			String sql = "SELECT * FROM ("
					   + "  SELECT ROWNUM AS rn, t.* FROM ("
					   + "    SELECT q.*, i.item_name FROM tb_quality q "
					   + "    JOIN tb_item i ON q.prod_key = i.item_key "
					   + "    ORDER BY q.quality_key ASC"
					   + "  ) t"
					   + ") WHERE rn BETWEEN ? AND ?";

			ps = conn.prepareStatement(sql);
			// 페이징 파라미터 세팅
			ps.setInt(1, start);
			ps.setInt(2, end);
			
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


	// 2. 품질 데이터 등록 (insertQuality) - [기존 유지]
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


	// 3. 품질 데이터 수정 (updateQuality) - [기존 유지]
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


	// 4. 품질 데이터 삭제 (deleteQuality) - [기존 유지]
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


	// 자원 해제 메서드
	private void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(conn != null) conn.close();
		} catch(Exception e) {}
	}
}