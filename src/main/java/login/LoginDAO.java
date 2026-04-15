package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class LoginDAO {

	public LoginDTO loginCheck(String id, String pw) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		LoginDTO dto = null;

		try {
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// 1. DB 접속
			conn = dataFactory.getConnection();
			// 2. SQL 준비
			String query = "select * from tb_user" + " where user_id=?" + " and user_pw=?"; // 변수 방식
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, pw);

			// 3. 실행 및 결과 확보
			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new LoginDTO();
				String user_name = rs.getString("user_name");
				String user_role = rs.getString("user_role");
				String user_phone = rs.getString("user_phone");
				String user_email = rs.getString("user_email");
				String status = rs.getString("status");
				String emp_no = rs.getString("emp_no");
				int user_key = rs.getInt("user_key");
				
				dto.setUser_key(user_key);
				dto.setUser_id(id);
				dto.setUser_name(user_name);
				dto.setUser_role(user_role);
				dto.setUser_phone(user_phone);
				dto.setUser_email(user_email);
				dto.setStatus(status);
				dto.setEmp_no(emp_no);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return dto;

	}

}
