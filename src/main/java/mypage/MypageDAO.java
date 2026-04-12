package mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MypageDAO {

	private DataSource getDataSource() throws Exception {
		Context ctx = new InitialContext();
		return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	}

	public MypageDTO selectUserByUserId(String userId) {
		MypageDTO dto = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "SELECT user_id, user_name, user_email, user_phone, user_pw, emp_no, user_role "
					+ "FROM tb_user " + "WHERE user_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new MypageDTO();
				dto.setUser_id(rs.getString("user_id"));
				dto.setUser_name(rs.getString("user_name"));
				dto.setUser_email(rs.getString("user_email"));
				dto.setUser_phone(rs.getString("user_phone"));
				dto.setUser_pw(rs.getString("user_pw"));
				dto.setEmp_no(rs.getString("emp_no"));
				dto.setUser_role(rs.getString("user_role"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return dto;
	}

	public boolean checkPassword(String userId, String encryptedPw) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "SELECT COUNT(*) cnt " + "FROM tb_user " + "WHERE user_id = ? AND user_pw = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, encryptedPw);

			rs = ps.executeQuery();

			if (rs.next() && rs.getInt("cnt") > 0) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return result;
	}

	public int updateUserInfo(MypageDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "UPDATE tb_user SET " + "user_name = ?, " + "user_email = ?, " + "user_phone = ? "
					+ "WHERE user_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getUser_name());
			ps.setString(2, dto.getUser_email());
			ps.setString(3, dto.getUser_phone());
			ps.setString(4, dto.getUser_id());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}

		return result;
	}

	public int updateUserInfoAndPassword(MypageDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "UPDATE tb_user SET " + "user_name = ?, " + "user_email = ?, " + "user_phone = ?, "
					+ "user_pw = ? " + "WHERE user_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getUser_name());
			ps.setString(2, dto.getUser_email());
			ps.setString(3, dto.getUser_phone());
			ps.setString(4, dto.getUser_pw());
			ps.setString(5, dto.getUser_id());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}

		return result;
	}

	private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
