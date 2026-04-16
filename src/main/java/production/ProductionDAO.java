package production;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProductionDAO {

	private DataSource getDataSource() throws Exception {
		Context ctx = new InitialContext();
		return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	}

	public List<ProductionDTO> selectProductionList(String startDate, String endDate, String keyword, String status) {
		List<ProductionDTO> list = new ArrayList<ProductionDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "" + "SELECT                                                  "
					+ "    PR.PROD_KEY,                                       "
					+ "    PR.PROD_CODE,                                      "
					+ "    TO_CHAR(PR.PROD_DATE, 'YYYY-MM-DD') AS PROD_DATE,  "
					+ "    PR.GOOD_QTY,                                       "
					+ "    PR.DEFECT_QTY,                                     "
					+ "    W.WORK_ORDER_KEY,                                  "
					+ "    W.WORK_ORDER_CODE,                                 "
					+ "    W.ORDER_QTY,                                       "
					+ "    P.PLAN_KEY,                                        "
					+ "    P.PLAN_CODE,                                       "
					+ "    P.PLAN_QTY,                                        "
					+ "    I.ITEM_KEY,                                        "
					+ "    I.ITEM_CODE,                                       "
					+ "    I.ITEM_NAME,                                       "
					+ "    U.USER_KEY AS WORK_USER_KEY,                       "
					+ "    U.USER_NAME AS WORK_USER_NAME,                     "
					+ "    Q.QUALITY_KEY,                                     "
					+ "    Q.INSPECT_QTY,                                     "
					+ "    Q.DEFECT_REASON,                                   "
					+ "    Q.QC_STATUS,                                       "
					+ "    ROUND(CASE                                         "
					+ "        WHEN NVL(P.PLAN_QTY, 0) = 0 THEN 0            "
					+ "        ELSE PR.GOOD_QTY / P.PLAN_QTY * 100           "
					+ "    END, 2) AS ACHIEVEMENT_RATE                        "
					+ "FROM TB_PRODUCTION PR                                  "
					+ "JOIN TB_WORK_ORDER W ON PR.WORK_ORDER_KEY = W.WORK_ORDER_KEY "
					+ "JOIN TB_PLAN P ON W.PLAN_KEY = P.PLAN_KEY              "
					+ "JOIN TB_ITEM I ON P.ITEM_KEY = I.ITEM_KEY              "
					+ "LEFT JOIN TB_USER U ON PR.WORK_USER_KEY = U.USER_KEY   "
					+ "LEFT JOIN TB_QUALITY Q ON PR.PROD_KEY = Q.PROD_KEY     "
					+ "WHERE PR.PROD_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD')    "
					+ "AND TO_DATE(?, 'YYYY-MM-DD') + 0.99999                 "
					+ "AND (I.ITEM_CODE LIKE ? OR I.ITEM_NAME LIKE ? OR PR.PROD_CODE LIKE ?) ";

			if ("정상".equals(status)) {
				sql += "AND PR.DEFECT_QTY = 0 ";
			} else if ("불량발생".equals(status)) {
				sql += "AND PR.DEFECT_QTY > 0 ";
			}

			sql += "ORDER BY PR.PROD_DATE DESC, PR.PROD_KEY DESC";

			ps = conn.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, "%" + keyword + "%");
			ps.setString(5, "%" + keyword + "%");

			rs = ps.executeQuery();

			while (rs.next()) {
				ProductionDTO dto = new ProductionDTO();

				dto.setProd_key(rs.getInt("PROD_KEY"));
				dto.setProd_code(rs.getString("PROD_CODE"));
				dto.setProd_date(rs.getString("PROD_DATE"));
				dto.setGood_qty(rs.getInt("GOOD_QTY"));
				dto.setDefect_qty(rs.getInt("DEFECT_QTY"));

				dto.setWork_order_key(rs.getInt("WORK_ORDER_KEY"));
				dto.setWork_order_code(rs.getString("WORK_ORDER_CODE"));
				dto.setOrder_qty(rs.getInt("ORDER_QTY"));

				dto.setPlan_key(rs.getInt("PLAN_KEY"));
				dto.setPlan_code(rs.getString("PLAN_CODE"));
				dto.setPlan_qty(rs.getInt("PLAN_QTY"));

				dto.setItem_key(rs.getInt("ITEM_KEY"));
				dto.setItem_code(rs.getString("ITEM_CODE"));
				dto.setItem_name(rs.getString("ITEM_NAME"));

				dto.setWork_user_key(rs.getInt("WORK_USER_KEY"));
				dto.setWork_user_name(rs.getString("WORK_USER_NAME"));

				dto.setQuality_key(rs.getInt("QUALITY_KEY"));
				dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
				dto.setDefect_reason(rs.getString("DEFECT_REASON"));
				dto.setQc_status(rs.getString("QC_STATUS"));

				dto.setAchievement_rate(rs.getDouble("ACHIEVEMENT_RATE"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return list;
	}

	public ProductionSummaryDTO selectProductionSummary(String startDate, String endDate, String keyword,
			String status) {
		ProductionSummaryDTO dto = new ProductionSummaryDTO();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "" + "SELECT                                                  "
					+ "    NVL(SUM(P.PLAN_QTY), 0) AS TOTAL_PLAN_QTY,          "
					+ "    NVL(SUM(W.ORDER_QTY), 0) AS TOTAL_ORDER_QTY,        "
					+ "    NVL(SUM(PR.GOOD_QTY), 0) AS TOTAL_GOOD_QTY,         "
					+ "    NVL(SUM(PR.DEFECT_QTY), 0) AS TOTAL_DEFECT_QTY,     "
					+ "    ROUND(AVG(CASE                                      "
					+ "        WHEN NVL(P.PLAN_QTY, 0) = 0 THEN 0             "
					+ "        ELSE PR.GOOD_QTY / P.PLAN_QTY * 100            "
					+ "    END), 2) AS AVG_ACHIEVEMENT_RATE                    "
					+ "FROM TB_PRODUCTION PR                                   "
					+ "JOIN TB_WORK_ORDER W ON PR.WORK_ORDER_KEY = W.WORK_ORDER_KEY "
					+ "JOIN TB_PLAN P ON W.PLAN_KEY = P.PLAN_KEY               "
					+ "JOIN TB_ITEM I ON P.ITEM_KEY = I.ITEM_KEY               "
					+ "WHERE PR.PROD_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD')     "
					+ "AND TO_DATE(?, 'YYYY-MM-DD') + 0.99999                  "
					+ "AND (I.ITEM_CODE LIKE ? OR I.ITEM_NAME LIKE ? OR PR.PROD_CODE LIKE ?) ";

			if ("정상".equals(status)) {
				sql += "AND PR.DEFECT_QTY = 0 ";
			} else if ("불량발생".equals(status)) {
				sql += "AND PR.DEFECT_QTY > 0 ";
			}

			ps = conn.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, "%" + keyword + "%");
			ps.setString(5, "%" + keyword + "%");

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setTotal_plan_qty(rs.getInt("TOTAL_PLAN_QTY"));
				dto.setTotal_order_qty(rs.getInt("TOTAL_ORDER_QTY"));
				dto.setTotal_good_qty(rs.getInt("TOTAL_GOOD_QTY"));
				dto.setTotal_defect_qty(rs.getInt("TOTAL_DEFECT_QTY"));
				dto.setAvg_achievement_rate(rs.getDouble("AVG_ACHIEVEMENT_RATE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		setBestAndLowInfo(dto, startDate, endDate, keyword, status);

		return dto;
	}

	private void setBestAndLowInfo(ProductionSummaryDTO dto, String startDate, String endDate, String keyword,
			String status) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String baseSql = "" + "SELECT * FROM (                                          "
					+ "    SELECT                                              "
					+ "        I.ITEM_NAME,                                    "
					+ "        ROUND(CASE                                      "
					+ "            WHEN NVL(P.PLAN_QTY, 0) = 0 THEN 0         "
					+ "            ELSE PR.GOOD_QTY / P.PLAN_QTY * 100        "
					+ "        END, 2) AS ACH_RATE                             "
					+ "    FROM TB_PRODUCTION PR                               "
					+ "    JOIN TB_WORK_ORDER W ON PR.WORK_ORDER_KEY = W.WORK_ORDER_KEY "
					+ "    JOIN TB_PLAN P ON W.PLAN_KEY = P.PLAN_KEY           "
					+ "    JOIN TB_ITEM I ON P.ITEM_KEY = I.ITEM_KEY           "
					+ "    WHERE PR.PROD_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD') "
					+ "    AND TO_DATE(?, 'YYYY-MM-DD') + 0.99999              "
					+ "    AND (I.ITEM_CODE LIKE ? OR I.ITEM_NAME LIKE ? OR PR.PROD_CODE LIKE ?) ";

			if ("정상".equals(status)) {
				baseSql += "AND PR.DEFECT_QTY = 0 ";
			} else if ("불량발생".equals(status)) {
				baseSql += "AND PR.DEFECT_QTY > 0 ";
			}

			String bestSql = baseSql + "ORDER BY ACH_RATE DESC) WHERE ROWNUM = 1";

			ps = conn.prepareStatement(bestSql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, "%" + keyword + "%");
			ps.setString(5, "%" + keyword + "%");

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setBest_item_name(rs.getString("ITEM_NAME"));
				dto.setBest_rate(rs.getDouble("ACH_RATE"));
			}

			close(rs, ps, null);

			String lowSql = baseSql + "ORDER BY ACH_RATE ASC) WHERE ROWNUM = 1";

			ps = conn.prepareStatement(lowSql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			ps.setString(3, "%" + keyword + "%");
			ps.setString(4, "%" + keyword + "%");
			ps.setString(5, "%" + keyword + "%");

			rs = ps.executeQuery();

			if (rs.next()) {
				dto.setLow_item_name(rs.getString("ITEM_NAME"));
				dto.setLow_rate(rs.getDouble("ACH_RATE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
	}

	private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insertProduction(ProductionDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "" + "INSERT INTO TB_PRODUCTION ( "
					+ "    PROD_KEY, PROD_CODE, PROD_DATE, GOOD_QTY, DEFECT_QTY, WORK_ORDER_KEY, WORK_USER_KEY "
					+ ") VALUES ( " + "    SEQ_PRODUCTION.NEXTVAL, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ? " + ")";

			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getProd_code());
			ps.setString(2, dto.getProd_date());
			ps.setInt(3, dto.getGood_qty());
			ps.setInt(4, dto.getDefect_qty());
			ps.setInt(5, dto.getWork_order_key());
			ps.setInt(6, dto.getWork_user_key());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}

		return result;
	}

	public int updateProduction(ProductionDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "" + "UPDATE TB_PRODUCTION SET " + "    PROD_DATE = TO_DATE(?, 'YYYY-MM-DD'), "
					+ "    GOOD_QTY = ?, " + "    DEFECT_QTY = ?, " + "    WORK_ORDER_KEY = ?, "
					+ "    WORK_USER_KEY = ? " + "WHERE PROD_KEY = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getProd_date());
			ps.setInt(2, dto.getGood_qty());
			ps.setInt(3, dto.getDefect_qty());
			ps.setInt(4, dto.getWork_order_key());
			ps.setInt(5, dto.getWork_user_key());
			ps.setInt(6, dto.getProd_key());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}

		return result;
	}
	public List<ProductionOptionDTO> selectProductionOptions() {
	    List<ProductionOptionDTO> list = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = getDataSource().getConnection();

	        String sql =
	                "SELECT w.WORK_ORDER_KEY, w.WORK_ORDER_CODE, "
	              + "       p.PLAN_KEY, p.PLAN_CODE, p.PLAN_QTY, "
	              + "       i.ITEM_NAME, "
	              + "       wu.USER_KEY AS WORK_USER_KEY, wu.USER_NAME AS WORK_USER_NAME, "
	              + "       q.QUALITY_KEY, q.GOOD_QTY, q.DEFECT_QTY "
	              + "FROM TB_WORK_ORDER w "
	              + "JOIN TB_PLAN p ON w.PLAN_KEY = p.PLAN_KEY "
	              + "JOIN TB_ITEM i ON p.ITEM_KEY = i.ITEM_KEY "
	              + "LEFT JOIN TB_USER wu ON w.WORK_USER_KEY = wu.USER_KEY "
	              + "LEFT JOIN TB_QUALITY q ON w.WORK_ORDER_KEY = q.WORK_ORDER_KEY "
	              + "ORDER BY w.WORK_ORDER_KEY DESC";

	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            ProductionOptionDTO dto = new ProductionOptionDTO();

	            dto.setWork_order_key(rs.getInt("WORK_ORDER_KEY"));
	            dto.setWork_order_code(rs.getString("WORK_ORDER_CODE"));
	            dto.setPlan_key(rs.getInt("PLAN_KEY"));
	            dto.setPlan_code(rs.getString("PLAN_CODE"));
	            dto.setPlan_qty(rs.getInt("PLAN_QTY"));
	            dto.setItem_name(rs.getString("ITEM_NAME"));
	            dto.setWork_user_key(rs.getInt("WORK_USER_KEY"));
	            dto.setWork_user_name(rs.getString("WORK_USER_NAME"));
	            dto.setQuality_key(rs.getInt("QUALITY_KEY"));
	            dto.setGood_qty(rs.getInt("GOOD_QTY"));
	            dto.setDefect_qty(rs.getInt("DEFECT_QTY"));

	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(rs, ps, conn);
	    }

	    return list;
	}

}
