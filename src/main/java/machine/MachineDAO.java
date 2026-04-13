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
	 	Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	public List<MachineDTO> selectAll() {
//		List list = new ArrayList();
		List<MachineDTO> list = new ArrayList<MachineDTO>();

		try {
			// DB 연결 
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
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
		}  finally {
			closeAll();
		}
		System.out.println("list.size(): " + list.size());
		return list;
	}
	
	public List<MachineDTO> searchList(String machineName, String machineStatus) {
	    List<MachineDTO> list = new ArrayList<MachineDTO>();

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
			closeAll();
		}

	    return list;
	}

	public int insertmachine(MachineDTO machineDTO) {
		
		int result = -1;

		try {
			// DB연결
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
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
			System.out.println("insert의 결과:" + result);

			// 결과 활용
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			closeAll();
		}

		return result;

	}

	public int updatemachine(MachineDTO machineDTO) {
		
		int result = -1;

		try {
			// DB연결
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			// 2. SQL 준비
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
			System.out.println("update의 결과:" + result);

			// 결과 활용

		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			closeAll();
		}

		return result;

	}

	public int deletemachine(MachineDTO machineDTO) {
		
		int result = -1;

		try {
			// 1. DB 연결
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			// 2. SQL 준비
			String query = " delete from tb_machine";
			query += " where machine_key = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, machineDTO.getMachineKey());

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();
			System.out.println("delete의 결과:" + result);

			// 결과 활용

		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			closeAll();
		}

		return result;
	}
	 	// 페이징
	public List<MachineDTO> selectPage(int startRow, int endRow) {
	    List<MachineDTO> list = new ArrayList<>();

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
	    }  finally {
			closeAll();
		}

	    return list;
	}
	
	public int getTotalCount() {
	   
	    int count = 0;

	    try {
	        Context ctx = new InitialContext();
	        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	        conn = dataFactory.getConnection();

	        String query = "SELECT COUNT(*) FROM tb_machine";
	        ps = conn.prepareStatement(query);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }  finally {
			closeAll();
		}

	    return count;
	}
	// finally 의 close가 너무 반복되서 함수로 빼버림 
		private void closeAll() {
			if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
			if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	
}