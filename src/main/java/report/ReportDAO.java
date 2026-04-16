package report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ReportDAO {
	
	private DataSource getDataSource() throws Exception {
		Context ctx = new InitialContext();
		return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	}

	public ReportSummaryDTO getSummary(String startDate, String endDate) {
		ReportSummaryDTO dto = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = ""
				    + "SELECT                                                                 "
				    + "    NVL(SUM(P.PLAN_QTY), 0) AS TOTAL_PLAN_QTY,                        "
				    + "    NVL(SUM(W.ORDER_QTY), 0) AS TOTAL_ORDER_QTY,                      "
				    + "    NVL(SUM(PR.GOOD_QTY), 0) AS TOTAL_GOOD_QTY,                       "
				    + "    NVL(SUM(PR.DEFECT_QTY), 0) AS TOTAL_DEFECT_QTY,                   "
				    + "    ROUND(CASE                                                         "
				    + "        WHEN NVL(SUM(P.PLAN_QTY), 0) = 0 THEN 0                       "
				    + "        ELSE SUM(PR.GOOD_QTY) / SUM(P.PLAN_QTY) * 100                 "
				    + "    END, 2) AS ACHIEVEMENT_RATE,                                      "
				    + "    ROUND(CASE                                                         "
				    + "        WHEN NVL(SUM(PR.GOOD_QTY + PR.DEFECT_QTY), 0) = 0 THEN 0      "
				    + "        ELSE SUM(PR.GOOD_QTY) / SUM(PR.GOOD_QTY + PR.DEFECT_QTY) * 100 "
				    + "    END, 2) AS YIELD_RATE,                                            "
				    + "    ROUND(CASE                                                         "
				    + "        WHEN NVL(SUM(PR.GOOD_QTY + PR.DEFECT_QTY), 0) = 0 THEN 0      "
				    + "        ELSE SUM(PR.DEFECT_QTY) / SUM(PR.GOOD_QTY + PR.DEFECT_QTY) * 100 "
				    + "    END, 2) AS DEFECT_RATE                                            "
				    + "FROM TB_PLAN P                                                        "
				    + "LEFT JOIN TB_WORK_ORDER W ON P.PLAN_KEY = W.PLAN_KEY                 "
				    + "LEFT JOIN TB_PRODUCTION PR ON W.WORK_ORDER_KEY = PR.WORK_ORDER_KEY   "
				    + "WHERE P.PLAN_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD')                   "
				    + "AND TO_DATE(?, 'YYYY-MM-DD') + 0.99999";                               

			ps = conn.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new ReportSummaryDTO();
				dto.setTotalPlanQty(rs.getInt("TOTAL_PLAN_QTY"));
				dto.setTotalOrderQty(rs.getInt("TOTAL_ORDER_QTY"));
				dto.setTotalGoodQty(rs.getInt("TOTAL_GOOD_QTY"));
				dto.setTotalDefectQty(rs.getInt("TOTAL_DEFECT_QTY"));
				dto.setAchievementRate(rs.getDouble("ACHIEVEMENT_RATE"));
				dto.setYieldRate(rs.getDouble("YIELD_RATE"));
				dto.setDefectRate(rs.getDouble("DEFECT_RATE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return dto;
	}

	public List<DailyReportDTO> getDailyReport(String startDate, String endDate) {
		List<DailyReportDTO> list = new ArrayList<DailyReportDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = ""
					+ "SELECT                                                                  "
					+ "    TO_CHAR(P.PLAN_DATE, 'YYYY-MM-DD') AS WORK_DAY,                    "
					+ "    SUM(P.PLAN_QTY) AS PLAN_QTY,                                       "
					+ "    NVL(SUM(W.ORDER_QTY), 0) AS ORDER_QTY,                             "
					+ "    NVL(SUM(PR.GOOD_QTY), 0) AS GOOD_QTY,                              "
					+ "    NVL(SUM(PR.DEFECT_QTY), 0) AS DEFECT_QTY,                          "
					+ "    ROUND(CASE                                                          "
					+ "        WHEN SUM(P.PLAN_QTY) = 0 THEN 0                                "
					+ "        ELSE SUM(PR.GOOD_QTY) / SUM(P.PLAN_QTY) * 100                  "
					+ "    END, 2) AS ACH_RATE                                                 "
					+ "FROM TB_PLAN P                                                          "
					+ "LEFT JOIN TB_WORK_ORDER W ON P.PLAN_KEY = W.PLAN_KEY                   "
					+ "LEFT JOIN TB_PRODUCTION PR ON W.WORK_ORDER_KEY = PR.WORK_ORDER_KEY     "
					+ "WHERE P.PLAN_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD')                     "
					+ "AND TO_DATE(?, 'YYYY-MM-DD') + 0.99999                                 "
					+ "GROUP BY TO_CHAR(P.PLAN_DATE, 'YYYY-MM-DD')                            "
					+ "ORDER BY WORK_DAY                                                       ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			rs = ps.executeQuery();

			while (rs.next()) {
				DailyReportDTO dto = new DailyReportDTO();
				dto.setWorkDay(rs.getString("WORK_DAY"));
				dto.setPlanQty(rs.getInt("PLAN_QTY"));
				dto.setOrderQty(rs.getInt("ORDER_QTY"));
				dto.setGoodQty(rs.getInt("GOOD_QTY"));
				dto.setDefectQty(rs.getInt("DEFECT_QTY"));
				dto.setAchievementRate(rs.getDouble("ACH_RATE"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return list;
	}

	public List<DefectReasonDTO> getDefectReasonReport(String startDate, String endDate) {
		List<DefectReasonDTO> list = new ArrayList<DefectReasonDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = ""
					+ "SELECT                                                          "
					+ "    Q.DEFECT_REASON,                                           "
					+ "    COUNT(*) AS DEFECT_COUNT,                                  "
					+ "    NVL(SUM(Q.DEFECT_QTY), 0) AS TOTAL_DEFECT_QTY              "
					+ "FROM TB_QUALITY Q                                              "
					+ "WHERE Q.INSPECT_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD')          "
					+ "AND TO_DATE(?, 'YYYY-MM-DD') + 0.99999                         "
					+ "GROUP BY Q.DEFECT_REASON                                       "
					+ "ORDER BY TOTAL_DEFECT_QTY DESC                                 ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			rs = ps.executeQuery();

			while (rs.next()) {
				DefectReasonDTO dto = new DefectReasonDTO();
				dto.setDefectReason(rs.getString("DEFECT_REASON"));
				dto.setDefectCount(rs.getInt("DEFECT_COUNT"));
				dto.setTotalDefectQty(rs.getInt("TOTAL_DEFECT_QTY"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return list;
	}

	public List<ItemPerformanceDTO> getItemPerformance(String startDate, String endDate) {
		List<ItemPerformanceDTO> list = new ArrayList<ItemPerformanceDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = ""
					+ "SELECT                                                                  "
					+ "    I.ITEM_CODE,                                                       "
					+ "    I.ITEM_NAME,                                                       "
					+ "    SUM(P.PLAN_QTY) AS PLAN_QTY,                                       "
					+ "    NVL(SUM(W.ORDER_QTY), 0) AS ORDER_QTY,                             "
					+ "    NVL(SUM(PR.GOOD_QTY), 0) AS GOOD_QTY,                              "
					+ "    NVL(SUM(PR.DEFECT_QTY), 0) AS DEFECT_QTY,                          "
					+ "    ROUND(CASE                                                          "
					+ "        WHEN SUM(P.PLAN_QTY) = 0 THEN 0                                "
					+ "        ELSE SUM(PR.GOOD_QTY) / SUM(P.PLAN_QTY) * 100                  "
					+ "    END, 2) AS ACH_RATE                                                 "
					+ "FROM TB_PLAN P                                                          "
					+ "JOIN TB_ITEM I ON P.ITEM_KEY = I.ITEM_KEY                              "
					+ "LEFT JOIN TB_WORK_ORDER W ON P.PLAN_KEY = W.PLAN_KEY                   "
					+ "LEFT JOIN TB_PRODUCTION PR ON W.WORK_ORDER_KEY = PR.WORK_ORDER_KEY     "
					+ "WHERE P.PLAN_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD')                     "
					+ "AND TO_DATE(?, 'YYYY-MM-DD') + 0.99999                                 "
					+ "GROUP BY I.ITEM_CODE, I.ITEM_NAME                                      "
					+ "ORDER BY I.ITEM_CODE                                                    ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);

			rs = ps.executeQuery();

			while (rs.next()) {
				ItemPerformanceDTO dto = new ItemPerformanceDTO();
				dto.setItemCode(rs.getString("ITEM_CODE"));
				dto.setItemName(rs.getString("ITEM_NAME"));
				dto.setPlanQty(rs.getInt("PLAN_QTY"));
				dto.setOrderQty(rs.getInt("ORDER_QTY"));
				dto.setGoodQty(rs.getInt("GOOD_QTY"));
				dto.setDefectQty(rs.getInt("DEFECT_QTY"));
				dto.setAchievementRate(rs.getDouble("ACH_RATE"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return list;
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
}
