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
		System.out.println("selectAll 실행됨");

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			// JSP에서 등록/수정 모달을 하나로 사용하고 있음
			// 목록에서 클릭 시 수정 모달에 기존 값을 채우기 위해
			// 표시용 데이터 + 수정에 필요한 key 값들을 함께 조회
			// 따라서 SELECT 컬럼이 많아졌음
			String query = "SELECT w.work_order_key, "
		             + "w.work_order_code, "
		             + "w.order_user_key, "
		             + "w.work_user_key, "
		             + "w.order_qty, "
		             + "w.work_date, "
		             + "w.created_at, "
		             + "w.plan_key, "
		             + "p.plan_code, "
		             + "u.user_name AS order_user_name "
		             + "FROM tb_work_order w "
		             + "LEFT JOIN tb_plan p "
		             + "  ON w.plan_key = p.plan_key "
		             + "LEFT JOIN tb_user u "
		             + "  ON w.order_user_key = u.user_key "
		             + "ORDER BY w.work_order_key";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				WorkOrderDTO dto = new WorkOrderDTO();
				dto.setWork_order_key(rs.getInt("work_order_key"));
				dto.setWork_order_code(rs.getString("work_order_code"));
				dto.setOrder_user_key(rs.getInt("order_user_key"));
				dto.setWork_user_key(rs.getInt("work_user_key"));
				dto.setOrder_qty(rs.getInt("order_qty"));
				dto.setWork_date(rs.getDate("work_date"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setPlan_key(rs.getInt("plan_key"));
				dto.setPlan_code(rs.getString("plan_code"));
				dto.setOrder_user_name(rs.getString("order_user_name"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
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
					+ "order_qty, work_date, created_at, plan_key" + ") VALUES ("
					+ "seq_work_order.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE, ?" + ")";

			ps = conn.prepareStatement(query);

			ps.setInt(1, dto.getOrder_user_key());
			ps.setInt(2, dto.getWork_user_key());
			ps.setString(3, dto.getWork_order_code());
			ps.setInt(4, dto.getOrder_qty());
			ps.setDate(5, dto.getWork_date());
			ps.setInt(6, dto.getPlan_key());

			result = ps.executeUpdate();
			System.out.println("workorder insert의 결과:" + result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return result;
	}

	public int updateWorkOrder(WorkOrderDTO dto) {

		int result = -1;

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			String query = "UPDATE tb_work_order SET " + "order_user_key = ?, " + "work_user_key = ?, "
					+ "work_order_code = ?, " + "order_qty = ?, " + "work_date = ?, " + "plan_key = ? "
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
			System.out.println("workorder update의 결과:" + result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
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
			System.out.println("workorder delete의 결과:" + result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return result;
	}

	public List<WorkOrderDTO> searchList(String workOrderCode, String planKey) {
		List<WorkOrderDTO> list = new ArrayList<WorkOrderDTO>();

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			// [검색 목록 조회]
			// JSP에서 등록/수정 모달을 공용으로 사용하고 있음
			// 목록에서 항목 클릭 시 수정 모달에 기존 데이터를 세팅해야 하므로
			// 표시용 데이터 + 수정에 필요한 key 값들을 함께 조회함
			//  이로 인해 SELECT 컬럼이 많아짐
			String query = "SELECT w.work_order_key, "
					+ "w.order_user_key, "
					+ "w.work_user_key, "
					+ "w.work_order_code, "
					+ "w.order_qty, "
					+ "w.work_date, "
					+ "w.created_at, "
					+ "w.plan_key, "
					+ "p.plan_code, "
					+ "u.user_name AS order_user_name "
					+ "FROM tb_work_order w "
					+ "LEFT JOIN tb_plan p ON w.plan_key = p.plan_key "
					+ "LEFT JOIN tb_user u ON w.order_user_key = u.user_key "
					+ "WHERE 1=1 ";

			if (workOrderCode != null && !workOrderCode.trim().equals("")) {
				query += " and w.work_order_code like ?";
			}

			if (planKey != null && !planKey.trim().equals("")) {
				query += " and w.plan_key = ?";
			}

			query += " order by w.work_order_key";

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
				dto.setPlan_code(rs.getString("plan_code"));
				dto.setOrder_user_name(rs.getString("order_user_name"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return list;
	}

	public List<WorkOrderDTO> selectPage(int startRow, int endRow) {
		List<WorkOrderDTO> list = new ArrayList<WorkOrderDTO>();

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			// [페이징 목록 조회]
			// JSP에서 등록/수정 모달을 공용으로 사용하고 있음
			// 목록에서 항목 클릭 시 수정 모달에 기존 데이터를 세팅해야 하므로
			// 표시용 데이터 + 수정에 필요한 key 값들을 함께 조회함
			// → 이로 인해 SELECT 컬럼이 많아짐
			String query = "SELECT * FROM ( "
					+ "  SELECT ROWNUM rnum, A.* FROM ( "
					+ "    SELECT w.work_order_key, "
					+ "           w.order_user_key, "
					+ "           w.work_user_key, "
					+ "           w.work_order_code, "
					+ "           w.order_qty, "
					+ "           w.work_date, "
					+ "           w.created_at, "
					+ "           w.plan_key, "
					+ "           p.plan_code, "
					+ "           u.user_name AS order_user_name "
					+ "    FROM tb_work_order w "
					+ "    LEFT JOIN tb_plan p ON w.plan_key = p.plan_key "
					+ "    LEFT JOIN tb_user u ON w.order_user_key = u.user_key "
					+ "    ORDER BY w.work_order_key "
					+ "  ) A WHERE ROWNUM <= ? "
					+ ") WHERE rnum >= ?";

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
				dto.setPlan_code(rs.getString("plan_code"));
				dto.setOrder_user_name(rs.getString("order_user_name"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

			String query = "SELECT COUNT(*) FROM tb_work_order";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
