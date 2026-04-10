package workorder;

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

public class WorkOrderDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public List<WorkOrderDTO> selectAll() {
		List<WorkOrderDTO> list = new ArrayList<>();

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			String query = "SELECT * FROM tb_work_order where rownum<=4";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				WorkOrderDTO dto = new WorkOrderDTO();

				dto.setWork_order_key(rs.getInt("work_order_key"));
				dto.setWork_order_code(rs.getString("work_order_code"));
				dto.setPlan_key(rs.getInt("plan_key"));
				dto.setItem_key(rs.getInt("item_key"));
				dto.setProcess_key(rs.getInt("process_key"));
				dto.setWork_date(rs.getDate("work_date"));
				dto.setOrder_qty(rs.getInt("order_qty"));
				dto.setOrder_status(rs.getString("order_status"));
				dto.setUser_key(rs.getInt("user_key"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setMachine_key(rs.getInt("machine_key"));

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

	public int insertWorkOrder(WorkOrderDTO dto) {

		int result = -1;

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			String query = "INSERT INTO tb_work_order (" +
					"work_order_key, work_order_code, plan_key, item_key, process_key, " +
					"work_date, order_qty, order_status, user_key, created_at, machine_key" +
					") VALUES (" +
					"seq_work_order.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?" +
					")";

			ps = conn.prepareStatement(query);

			ps.setString(1, dto.getWork_order_code());
			ps.setInt(2, dto.getPlan_key());
			ps.setInt(3, dto.getItem_key());
			ps.setInt(4, dto.getProcess_key());
			ps.setDate(5, dto.getWork_date());
			ps.setInt(6, dto.getOrder_qty());
			ps.setString(7, dto.getOrder_status());
			ps.setInt(8, dto.getUser_key());
			ps.setInt(9, dto.getMachine_key());

			result = ps.executeUpdate();

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

	public int updateWorkOrder(WorkOrderDTO dto) {

		int result = -1;

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			String query = "UPDATE tb_work_order SET " +
					"work_order_code = ?, " +
					"plan_key = ?, " +
					"item_key = ?, " +
					"process_key = ?, " +
					"work_date = ?, " +
					"order_qty = ?, " +
					"order_status = ?, " +
					"user_key = ?, " +
					"machine_key = ? " +
					"WHERE work_order_key = ?";

			ps = conn.prepareStatement(query);

			ps.setString(1, dto.getWork_order_code());
			ps.setInt(2, dto.getPlan_key());
			ps.setInt(3, dto.getItem_key());
			ps.setInt(4, dto.getProcess_key());
			ps.setDate(5, dto.getWork_date());
			ps.setInt(6, dto.getOrder_qty());
			ps.setString(7, dto.getOrder_status());
			ps.setInt(8, dto.getUser_key());
			ps.setInt(9, dto.getMachine_key());
			ps.setInt(10, dto.getWork_order_key());

			result = ps.executeUpdate();

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

	public int deleteWorkOrder(int workorderkey) {

		int result = -1;

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			String query = "DELETE FROM tb_work_order WHERE work_order_key = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, workorderkey);

			result = ps.executeUpdate();

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
