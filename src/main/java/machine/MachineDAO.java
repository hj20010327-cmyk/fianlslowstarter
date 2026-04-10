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
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "select * from tb_machine where rownum<=4 order by machine_key";
			ps = conn.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

//			print (rs,response);

			// 결과 활용
			while (rs.next()) { // ������ ������ �ݺ� ��!

				int machineKey = rs.getInt("machine_key");
				String machineCode = rs.getString("machine_code");
				String machineName = rs.getString("machine_name");
				int processKey = rs.getInt("process_key");
				String machineStatus = rs.getString("machine_status");
				Date buyDate = rs.getDate("buy_date");
				Date lastCheckDate = rs.getDate("last_check_date");
				String remark = rs.getString("remark");
				String status = rs.getString("status");
				Date createdAt = rs.getDate("created_at");

				MachineDTO dto = new MachineDTO();
				dto.setMachineKey(machineKey);
				dto.setMachineCode(machineCode);
				dto.setMachineName(machineName);
				dto.setProcessKey(processKey);
				dto.setMachineStatus(machineStatus);
				dto.setBuyDate(buyDate);
				dto.setLastCheckDate(lastCheckDate);
				dto.setRemark(remark);
				dto.setStatus(status);
				dto.setCreatedAt(createdAt);

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
		System.out.println("list.size(): " + list.size());
		return list;
	}
	
	public List<MachineDTO> searchList(String machineName, String machineStatus) {
	    List<MachineDTO> list = new ArrayList<MachineDTO>();

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	    	// DB 연결 
	        Context ctx = new InitialContext();
	        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	        conn = dataFactory.getConnection();
	        
	        // SQL 문 (where 1=1 은 조건 붙이기 위해서 씀)
	        String query = "select * from tb_machine where 1=1";

	        // 설비명 조건이 있으면 추가 
	        if (machineName != null && !machineName.trim().equals("")) {
	            query += " and machine_name like ?";
	        }
	        // 상태 조건이 있으면 추가 
	        if (machineStatus != null && !machineStatus.trim().equals("")) {
	            query += " and machine_status = ?";
	        }
	        // 정렬
	        query += " order by machine_key";
	        
	        // SQL 준비 
	        ps = conn.prepareStatement(query);
	        
	        // ?에 값을 넣기 위함
	        int idx = 1;
	        
	        // 설비명 조회에서 부분검색 되기 위함 
	        if (machineName != null && !machineName.trim().equals("")) {
	            ps.setString(idx++, "%" + machineName.trim() + "%");
	        }
	        
	        // 상태 값 세팅  
	        if (machineStatus != null && !machineStatus.trim().equals("")) {
	            ps.setString(idx++, machineStatus);
	        }

	        rs = ps.executeQuery();

	        // 결과를 DTO에 담아서 리스트에 추가함 
	        while (rs.next()) {
	            MachineDTO dto = new MachineDTO();

	            dto.setMachineKey(rs.getInt("machine_key"));
	            dto.setMachineCode(rs.getString("machine_code"));
	            dto.setMachineName(rs.getString("machine_name"));
	            dto.setProcessKey(rs.getInt("process_key"));
	            dto.setMachineStatus(rs.getString("machine_status"));
	            dto.setBuyDate(rs.getDate("buy_date"));
	            dto.setLastCheckDate(rs.getDate("last_check_date"));
	            dto.setRemark(rs.getString("remark"));
	            dto.setStatus(rs.getString("status"));
	            dto.setCreatedAt(rs.getDate("created_at"));

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

	public int insertmachine(MachineDTO machineDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;

		try {
			// 1. DB ����
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			 // DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// 2. SQL 준비
			String query = "INSERT INTO tb_machine(" +
		               "machine_key, machine_code, machine_name, process_key, machine_status, " +
		               "buy_date, last_check_date, remark, status, created_at) " +
		               "VALUES(seq_machine.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 'Y', SYSDATE)";

			
			ps = conn.prepareStatement(query);
			
//			MachineDTO dto = new MachineDTO();
			ps.setString(1,  machineDTO.getMachineCode());
			ps.setString(2,  machineDTO.getMachineName());
			ps.setInt(3,  machineDTO.getProcessKey());
			ps.setString(4,  machineDTO.getMachineStatus());
			ps.setDate(5,  machineDTO.getBuyDate());
			ps.setDate(6,  machineDTO.getLastCheckDate());
			ps.setString(7,  machineDTO.getRemark());

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();
			System.out.println("insert ���:" + result);

			// 결과 활용

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
			// 1. DB ����
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// 2. SQL �غ�
			String query = "UPDATE tb_machine SET " +
		               "machine_code = ?, " +
		               "machine_name = ?, " +
		               "process_key = ?, " +
		               "machine_status = ?, " +
		               "buy_date = ?, " +
		               "last_check_date = ?, " +
		               "remark = ? " +
		               "WHERE machine_key = ?";
			ps = conn.prepareStatement(query);
//			MachineDTO dto = new MachineDTO(); �̰� �ϸ� �ȵ� 
			ps.setString(1, machineDTO.getMachineCode());
			ps.setString(2, machineDTO.getMachineName());
			ps.setInt(3, machineDTO.getProcessKey());
			ps.setString(4, machineDTO.getMachineStatus());
			ps.setDate(5, machineDTO.getBuyDate());
			ps.setDate(6, machineDTO.getLastCheckDate());
			ps.setString(7, machineDTO.getRemark());
			ps.setInt(8, machineDTO.getMachineKey());

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();
			System.out.println("update ���:" + result);

			// 결과 활용

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
			// 1. DB ����
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : Ŀ�ؼ� Ǯ ������
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// 2. SQL �غ�
			String query = " delete from tb_machine";
			query += " where machine_key = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, machineDTO.getMachineKey());

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();
			System.out.println("delete ���:" + result);

			// 결과 활용

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
	 	// 페이징
	public List<MachineDTO> selectPage(int startRow, int endRow) {
	    List<MachineDTO> list = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        Context ctx = new InitialContext();
	        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	        conn = dataFactory.getConnection();

	        String query =
	            "SELECT * FROM ( " +
	            "  SELECT ROWNUM rnum, A.* FROM ( " +
	            "    SELECT * FROM tb_machine ORDER BY machine_key " +
	            "  ) A WHERE ROWNUM <= ? " +
	            ") WHERE rnum >= ?";

	        ps = conn.prepareStatement(query);
	        ps.setInt(1, endRow);
	        ps.setInt(2, startRow);

	        rs = ps.executeQuery();

	        while(rs.next()) {
	            MachineDTO dto = new MachineDTO();
	            dto.setMachineKey(rs.getInt("machine_key"));
	            dto.setMachineName(rs.getString("machine_name"));
	            dto.setMachineCode(rs.getString("machine_code"));
	            dto.setMachineStatus(rs.getString("machine_status"));
	            dto.setProcessKey(rs.getInt("process_key"));
	            list.add(dto);
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
}