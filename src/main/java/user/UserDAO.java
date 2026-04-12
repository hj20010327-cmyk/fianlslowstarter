package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {

	 private DataSource getDataSource() throws Exception {
	        Context ctx = new InitialContext();
	        return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	    }

	    public List<UserDTO> selectUserList() {
	        List<UserDTO> list = new ArrayList<UserDTO>();
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            conn = getDataSource().getConnection();

	            String sql = "SELECT user_key, user_id, user_name, user_role, user_phone, user_email, emp_no, status "
	                       + "FROM tb_user "
	                       + "ORDER BY user_key ASC";

	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();

	            while (rs.next()) {
	                list.add(mapRow(rs));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            close(rs, ps, conn);
	        }

	        return list;
	    }

	    public UserDTO selectUserOne(int userKey) {
	        UserDTO dto = null;
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            conn = getDataSource().getConnection();

	            String sql = "SELECT user_key, user_id, user_name, user_role, user_phone, user_email, emp_no, status "
	                       + "FROM tb_user "
	                       + "WHERE user_key = ?";

	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, userKey);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	                dto = mapRow(rs);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            close(rs, ps, conn);
	        }

	        return dto;
	    }

	    public int updateUser(UserDTO dto) {
	        int result = 0;
	        Connection conn = null;
	        PreparedStatement ps = null;

	        try {
	            conn = getDataSource().getConnection();

	            String sql = "UPDATE tb_user SET "
	                       + "user_name = ?, "
	                       + "user_role = ?, "
	                       + "user_phone = ?, "
	                       + "user_email = ?, "
	                       + "status = ? "
	                       + "WHERE user_key = ?";

	            ps = conn.prepareStatement(sql);
	            ps.setString(1, dto.getUser_name());
	            ps.setString(2, dto.getUser_role());
	            ps.setString(3, dto.getUser_phone());
	            ps.setString(4, dto.getUser_email());
	            ps.setString(5, dto.getStatus());
	            ps.setInt(6, dto.getUser_key());

	            result = ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            close(null, ps, conn);
	        }

	        return result;
	    }

	    public int softDeleteUser(int userKey) {
	        int result = 0;
	        Connection conn = null;
	        PreparedStatement ps = null;

	        try {
	            conn = getDataSource().getConnection();

	            String sql = "UPDATE tb_user SET status = '퇴사' WHERE user_key = ?";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, userKey);

	            result = ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            close(null, ps, conn);
	        }

	        return result;
	    }

	    private UserDTO mapRow(ResultSet rs) throws SQLException {
	        UserDTO dto = new UserDTO();
	        dto.setUser_key(rs.getInt("user_key"));
	        dto.setUser_id(rs.getString("user_id"));
	        dto.setUser_name(rs.getString("user_name"));
	        dto.setUser_role(rs.getString("user_role"));
	        dto.setUser_phone(rs.getString("user_phone"));
	        dto.setUser_email(rs.getString("user_email"));
	        dto.setEmp_no(rs.getString("emp_no"));
	        dto.setStatus(rs.getString("status"));
	        return dto;
	    }

	    private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
	        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }
	
}
