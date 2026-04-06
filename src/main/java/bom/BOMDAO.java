package bom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BOMDAO {

	private Connection getConn() {

		Connection conn = null;
		try {
			// JNDI 방식
			// context.xml 에 있는 DB 정보로 커넥션 풀 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(커넥션 풀로)
			conn = dataFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List selectAllBOM() {

		List<BOMDTO> list = new ArrayList();

		try (Connection conn = getConn(); PreparedStatement ps = new LoggableStatement(conn, "select * from tb_bom");) {
			System.out.println(((LoggableStatement) ps).getQueryString());

			try (ResultSet rs = ps.executeQuery();) {
				// 결과 활용
				while (rs.next()) {
					BOMDTO dto = new BOMDTO();

					String bom_key = rs.getString("bom_key");

					dto.setBom_key(bom_key);

					dto.setItem_code(rs.getString("item_code"));
					dto.setItem_count(rs.getInt("item_count"));
					dto.setStatus(rs.getInt("status"));
					dto.setCode_id(rs.getString("code_id"));

					list.add(dto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("DAO list.size: " + list.size());
		return list;
	}

	public List selectOneBOM(String bom_key) {
		List<BOMDTO> list = new ArrayList();

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn, "select * from tb_bom where bom_key = ? ");) {
			ps.setString(1, bom_key);

			try (ResultSet rs = ps.executeQuery();) {

				while (rs.next()) {
					String key = rs.getString("bom_key");
					String item_code = rs.getString("item_code");
					int item_count = rs.getInt("item_count");
					int status = rs.getInt("status");
					String code_id = rs.getString("code_id");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
