package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class LoginDAO {

	public List loginCheck() {
		List<LoginDTO> list = new ArrayList<LoginDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// 1. DB 접속
			conn = dataFactory.getConnection();
			// 2. SQL 준비
			String query = "select * from tb_user"; // 변수 방식
			ps = conn.prepareStatement(query);
			

			// 3. 실행 및 결과 확보
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String user_id = rs.getString("user_id");
				String user_pw = rs.getString("user_pw");
				
				LoginDTO dto = new LoginDTO();
				dto.setUser_id(user_id);
				dto.setUser_pw(user_pw);
				
				list.add(dto);
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

		return list;

	}

}
