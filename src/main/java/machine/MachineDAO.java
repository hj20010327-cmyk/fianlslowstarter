package machine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MachineDAO {
	public List<MachineDTO> selectAll() {
//		List list = new ArrayList();
		List<MachineDTO> list = new ArrayList<MachineDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// JNDI 방식
			// context.xml에 있는 DB정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			// DB 접속 (그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "select * from tb_machine where rownum<=4";
			ps = conn.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

//			print (rs,response);

			// 결과 활용
			while (rs.next()) { // 다음거 없으면 반복 끝!

				String system_key = rs.getString("system_key");
				int system_code = rs.getInt("system_code");
				String system_name = rs.getString("system_name");
				String system_status = rs.getString("system_status");
				MachineDTO machineDTO = new MachineDTO();
				machineDTO.setSystemKey(system_key);
				machineDTO.setSystemCode(system_code);
				machineDTO.setSystemName(system_name);
				machineDTO.setSystemStatus(system_status);
				list.add(machineDTO);

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
		System.out.println("list.size(): " + list.size());
		return list;
	}

	public int insertmachine(MachineDTO machineDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;

		try {
			// 1. DB 접속
			// context.xml에 있는 DB정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속 (그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// 2. SQL 준비
//					String query = "select * from todo where todo_id="+ todo_id; 
			String query = "INSERT INTO tb_machine(system_key, system_code, system_name, system_status) ";
			query += "VALUES('CODE' || LPAD(seq_code.NEXTVAL, 4, '0'), ?, ?, ?)";

			ps = conn.prepareStatement(query);

			ps.setInt(1, machineDTO.getSystemCode());
			ps.setString(2, machineDTO.getSystemName());
			ps.setString(3, machineDTO.getSystemStatus());

			// 3. 실행 및 결과 확보
			result = ps.executeUpdate();
			System.out.println("insert 결과:" + result);

			// 4. 결과 활용

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

		return result;

	}

	public int updatemachine(MachineDTO machineDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;

		try {
			// 1. DB 접속
			// context.xml에 있는 DB정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속 (그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// 2. SQL 준비
//					String query = "select * from todo where todo_id="+ todo_id; 
			String query = " update tb_machine";
			query += " set system_code = ? , system_name = ? , system_status = ?";
			query += " where system_key = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, machineDTO.getSystemCode());
			ps.setString(2, machineDTO.getSystemName());
			ps.setString(3, machineDTO.getSystemStatus());
			ps.setString(4, machineDTO.getSystemKey());

			// 3. 실행 및 결과 확보
			result = ps.executeUpdate();
			System.out.println("update 결과:" + result);

			// 4. 결과 활용

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

		return result;

	}

	public int deletemachine(MachineDTO machineDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;

		try {
			// 1. DB 접속
			// context.xml에 있는 DB정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속 (그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// 2. SQL 준비
			String query = " delete from tb_machine";
			query += " where system_key = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, machineDTO.getSystemKey());

			// 3. 실행 및 결과 확보
			result = ps.executeUpdate();
			System.out.println("delete 결과:" + result);

			// 4. 결과 활용

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

		return result;
	}
}