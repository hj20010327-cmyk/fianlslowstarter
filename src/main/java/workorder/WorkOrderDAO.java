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
				dto.setOrder_user_key(rs.getInt("order_user_key"));
				dto.setWork_user_key(rs.getInt("work_user_key"));
				dto.setWork_order_code(rs.getString("work_order_code"));
				dto.setOrder_qty(rs.getInt("order_qty"));
				dto.setWork_date(rs.getDate("work_date"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setPlan_key(rs.getInt("plan_key"));

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

			String query = "INSERT INTO tb_work_order ("
					+ "work_order_key, order_user_key, work_user_key, work_order_code, "
					+ "order_qty, work_date, created_at, plan_key"
					+ ") VALUES ("
					+ "seq_work_order.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE, ?"
					+ ")";

			ps = conn.prepareStatement(query);

			ps.setInt(1, dto.getOrder_user_key());
			ps.setInt(2, dto.getWork_user_key());
			ps.setString(3, dto.getWork_order_code());
			ps.setInt(4, dto.getOrder_qty());
			ps.setDate(5, dto.getWork_date());
			ps.setInt(6, dto.getPlan_key());

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

			String query = "UPDATE tb_work_order SET "
					+ "order_user_key = ?, "
					+ "work_user_key = ?, "
					+ "work_order_code = ?, "
					+ "order_qty = ?, "
					+ "work_date = ?, "
					+ "plan_key = ? "
					+ "WHERE work_order_key = ?";

			ps = conn.prepareStatement(query);

			ps.setInt(1, dto.getOrder_user_key());
			ps.setInt(2, dto.getWork_user_key());
			ps.setString(3, dto.getWork_order_code());
			ps.setInt(4, dto.getOrder_qty());
			ps.setDate(5, dto.getWork_date());
			ps.setInt(6, dto.getPlan_key());
			ps.setInt(7, dto.getWork_order_key());

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
	
	public List<WorkOrderDTO> searchList(String workOrderCode, String planKey) {
	    List<WorkOrderDTO> list = new ArrayList<WorkOrderDTO>();

	    try {
	        Context ctx = new InitialContext();
	        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	        conn = dataFactory.getConnection();

	        String query = "select * from tb_work_order where 1=1";

	        if (workOrderCode != null && !workOrderCode.trim().equals("")) {
	            query += " and work_order_code like ?";
	        }

	        if (planKey != null && !planKey.trim().equals("")) {
	            query += " and plan_key = ?";
	        }

	        query += " order by work_order_key";

	        ps = conn.prepareStatement(query);

	        int idx = 1;

	        if (workOrderCode != null && !workOrderCode.trim().equals("")) {
	            ps.setString(idx++, "%" + workOrderCode.trim() + "%");
	        }

	        if (planKey != null && !planKey.trim().equals("")) {
	            ps.setInt(idx++, Integer.parseInt(planKey));
	        }

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            WorkOrderDTO dto = new WorkOrderDTO();
	            dto.setWork_order_key(rs.getInt("work_order_key"));
	            dto.setOrder_user_key(rs.getInt("order_user_key"));
	            dto.setWork_user_key(rs.getInt("work_user_key"));
	            dto.setWork_order_code(rs.getString("work_order_code"));
	            dto.setOrder_qty(rs.getInt("order_qty"));
	            dto.setWork_date(rs.getDate("work_date"));
	            dto.setCreated_at(rs.getDate("created_at"));
	            dto.setPlan_key(rs.getInt("plan_key"));

	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }

	    return list;
	}

	public List<WorkOrderDTO> selectPage(int startRow, int endRow) {
	    List<WorkOrderDTO> list = new ArrayList<WorkOrderDTO>();

	    try {
	        Context ctx = new InitialContext();
	        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	        conn = dataFactory.getConnection();

	        String query =
	            "SELECT * FROM ( " +
	            "  SELECT ROWNUM rnum, A.* FROM ( " +
	            "    SELECT * FROM tb_work_order ORDER BY work_order_key " +
	            "  ) A WHERE ROWNUM <= ? " +
	            ") WHERE rnum >= ?";

	        ps = conn.prepareStatement(query);
	        ps.setInt(1, endRow);
	        ps.setInt(2, startRow);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            WorkOrderDTO dto = new WorkOrderDTO();
	            dto.setWork_order_key(rs.getInt("work_order_key"));
	            dto.setOrder_user_key(rs.getInt("order_user_key"));
	            dto.setWork_user_key(rs.getInt("work_user_key"));
	            dto.setWork_order_code(rs.getString("work_order_code"));
	            dto.setOrder_qty(rs.getInt("order_qty"));
	            dto.setWork_date(rs.getDate("work_date"));
	            dto.setCreated_at(rs.getDate("created_at"));
	            dto.setPlan_key(rs.getInt("plan_key"));

	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
	    }

	    return list;
	}
}
