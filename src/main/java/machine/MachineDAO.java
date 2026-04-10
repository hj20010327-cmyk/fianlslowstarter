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
			// JNDI ���
			// context.xml�� �ִ� DB������ Ŀ�ؼ� Ǯ�� �����´�
			Context ctx = new InitialContext();
			// DataSource : Ŀ�ؼ� Ǯ ������
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			// DB ���� (�׷��� ���� Ŀ�ؼ� Ǯ��)
			conn = dataFactory.getConnection();

			// SQL �غ�
			String query = "select * from tb_machine where rownum<=4 order by machine_key";
			ps = conn.prepareStatement(query);

			// SQL ���� �� ��� Ȯ��
			rs = ps.executeQuery();

//			print (rs,response);

			// ��� Ȱ��
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

	public int insertmachine(MachineDTO machineDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;

		try {
			// 1. DB ����
			// context.xml�� �ִ� DB������ Ŀ�ؼ� Ǯ�� �����´�
			Context ctx = new InitialContext();
			// DataSource : Ŀ�ؼ� Ǯ ������
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB ���� (�׷��� ���� Ŀ�ؼ� Ǯ��)
			conn = dataFactory.getConnection();

			// 2. SQL �غ�
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

			// 3. ���� �� ��� Ȯ��
			result = ps.executeUpdate();
			System.out.println("insert ���:" + result);

			// 4. ��� Ȱ��

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
			// context.xml�� �ִ� DB������ Ŀ�ؼ� Ǯ�� �����´�
			Context ctx = new InitialContext();
			// DataSource : Ŀ�ؼ� Ǯ ������
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB ���� (�׷��� ���� Ŀ�ؼ� Ǯ��)
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

			// 3. ���� �� ��� Ȯ��
			result = ps.executeUpdate();
			System.out.println("update ���:" + result);

			// 4. ��� Ȱ��

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
			// context.xml�� �ִ� DB������ Ŀ�ؼ� Ǯ�� �����´�
			Context ctx = new InitialContext();
			// DataSource : Ŀ�ؼ� Ǯ ������
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB ���� (�׷��� ���� Ŀ�ؼ� Ǯ��)
			conn = dataFactory.getConnection();

			// 2. SQL �غ�
			String query = " delete from tb_machine";
			query += " where machine_key = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, machineDTO.getMachineKey());

			// 3. ���� �� ��� Ȯ��
			result = ps.executeUpdate();
			System.out.println("delete ���:" + result);

			// 4. ��� Ȱ��

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