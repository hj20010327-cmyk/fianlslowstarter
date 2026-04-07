package signup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SignupDAO {

	public int signUp (SignupDTO dto) {
		Connection conn = null;
		PreparedStatement ps = null;
		int rs = -1;
		
		try {
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			
			// 1. DB 접속
			conn = dataFactory.getConnection();
			// 2. SQL 준비
			String query = "insert into tb_user "; // 변수 방식
			ps = conn.prepareStatement(query);
			
			
			// 3. 실행 및 결과 확보
			rs = ps.executeUpdate();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		return rs;
		
		
	}
	
}
