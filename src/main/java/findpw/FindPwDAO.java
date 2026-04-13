package findpw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FindPwDAO {

	private DataSource getDataSource() throws Exception {
		Context ctx = new InitialContext();
		return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	}

	public boolean checkUser(String userId, String email) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			conn = getDataSource().getConnection();

			String sql = "SELECT COUNT(*) cnt " + "FROM tb_user " + "WHERE user_id = ? AND user_email = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, email);

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
	
	public int updateTempPassword(String userId, String encryptedTempPw) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            conn = getDataSource().getConnection();

            String sql = "UPDATE tb_user SET user_pw = ? WHERE user_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, encryptedTempPw);
            ps.setString(2, userId);

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }

        return result;
    }
	
	private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

}
