package dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import login.LoginDTO;

public class DashboardDAO {

	private DataSource getDataSource() throws Exception {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
    }

    public DashboardDTO selectDashboard(LoginDTO loginUser) {
        DashboardDTO dto = new DashboardDTO();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean isWorker = "작업자".equals(loginUser.getUser_role());

        try {
            conn = getDataSource().getConnection();

            // 오늘 생산량
            ps = conn.prepareStatement(
            	    "SELECT NVL(SUM(GOOD_QTY), 0) AS QTY " +
            	    "FROM TB_PRODUCTION " +
            	    "WHERE TRUNC(PROD_DATE)=TRUNC(SYSDATE)"
            	);

            rs = ps.executeQuery();
            if (rs.next()) dto.setTodayProdQty(rs.getInt("QTY"));
            rs.close(); ps.close();

            // 오늘 작업지시 수
            if (isWorker) {
                ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS CNT " +
                    "FROM TB_WORK_ORDER " +
                    "WHERE TRUNC(WORK_DATE)=TRUNC(SYSDATE) " +
                    "AND WORK_USER_KEY = ?"
                );
                ps.setInt(1, loginUser.getUser_key());
            } else {
                ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS CNT " +
                    "FROM TB_WORK_ORDER " +
                    "WHERE TRUNC(WORK_DATE)=TRUNC(SYSDATE)"
                );
            }

            rs = ps.executeQuery();
            if (rs.next()) dto.setTodayWorkorderCnt(rs.getInt("CNT"));
            rs.close(); ps.close();

            // 오늘 불량
            ps = conn.prepareStatement(
            	    "SELECT NVL(SUM(DEFECT_QTY), 0) AS QTY " +
            	    "FROM TB_QUALITY " +
            	    "WHERE TRUNC(INSPECT_DATE)=TRUNC(SYSDATE)"
            	);

            rs = ps.executeQuery();
            if (rs.next()) dto.setTodayDefectQty(rs.getInt("QTY"));
            rs.close(); ps.close();

            // 재고 부족
            ps = conn.prepareStatement(
                "SELECT COUNT(*) AS CNT " +
                "FROM TB_STOCK " +
                "WHERE CURRENT_QTY <= SAFE_QTY"
            );
            rs = ps.executeQuery();
            if (rs.next()) dto.setLowStockCnt(rs.getInt("CNT"));
            rs.close(); ps.close();

            // 최근 7일 생산량
            ps = conn.prepareStatement(
            	    "SELECT TO_CHAR(DT, 'MM/DD') AS DAY_LABEL, " +
            	    "NVL((SELECT SUM(GOOD_QTY) " +
            	    "     FROM TB_PRODUCTION " +
            	    "     WHERE TRUNC(PROD_DATE)=DT), 0) AS PROD_QTY " +
            	    "FROM (SELECT TRUNC(SYSDATE)-6+LEVEL-1 AS DT FROM DUAL CONNECT BY LEVEL<=7) " +
            	    "ORDER BY DT"
            	);

            rs = ps.executeQuery();
            while (rs.next()) {
                dto.getProdLabels().add(rs.getString("DAY_LABEL"));
                dto.getProdData().add(rs.getInt("PROD_QTY"));
            }
            rs.close(); ps.close();

            // 최근 7일 불량
            ps = conn.prepareStatement(
            	    "SELECT TO_CHAR(DT, 'MM/DD') AS DAY_LABEL, " +
            	    "NVL((SELECT SUM(DEFECT_QTY) " +
            	    "     FROM TB_QUALITY " +
            	    "     WHERE TRUNC(INSPECT_DATE)=DT), 0) AS DEFECT_QTY " +
            	    "FROM (SELECT TRUNC(SYSDATE)-6+LEVEL-1 AS DT FROM DUAL CONNECT BY LEVEL<=7) " +
            	    "ORDER BY DT"
            	);

            rs = ps.executeQuery();
            while (rs.next()) {
                dto.getDefectLabels().add(rs.getString("DAY_LABEL"));
                dto.getDefectData().add(rs.getInt("DEFECT_QTY"));
            }
            rs.close(); ps.close();

            // 작업지시 목록 (품목명 + 사용자 이름 포함)
            String sql =
                "SELECT w.WORK_ORDER_CODE, w.ORDER_QTY, w.WORK_DATE, " +
                "       ou.USER_NAME AS ORDER_USER_NAME, " +
                "       wu.USER_NAME AS WORK_USER_NAME, " +
                "       i.ITEM_NAME " +
                "FROM TB_WORK_ORDER w " +
                "JOIN TB_PLAN p ON w.PLAN_KEY = p.PLAN_KEY " +
                "JOIN TB_ITEM i ON p.ITEM_KEY = i.ITEM_KEY " +
                "JOIN TB_USER ou ON w.ORDER_USER_KEY = ou.USER_KEY " +
                "JOIN TB_USER wu ON w.WORK_USER_KEY = wu.USER_KEY " +
                "WHERE TRUNC(w.WORK_DATE)=TRUNC(SYSDATE) ";

            if (isWorker) {
                sql += "AND w.WORK_USER_KEY = ? ";
            }

            sql += "ORDER BY w.WORK_ORDER_KEY DESC";

            ps = conn.prepareStatement(sql);

            if (isWorker) {
                ps.setInt(1, loginUser.getUser_key());
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                DashboardWorkorderDTO w = new DashboardWorkorderDTO();

                w.setWork_order_code(rs.getString("WORK_ORDER_CODE"));
                w.setOrder_qty(rs.getInt("ORDER_QTY"));
                w.setWork_date(rs.getDate("WORK_DATE"));

                w.setOrder_user_name(rs.getString("ORDER_USER_NAME"));
                w.setWork_user_name(rs.getString("WORK_USER_NAME"));

                w.setItem_name(rs.getString("ITEM_NAME"));

                dto.getWorkorderList().add(w);
            }
            rs.close(); ps.close();

            // 부족 재고 목록
            ps = conn.prepareStatement(
                "SELECT I.ITEM_CODE, I.ITEM_NAME, S.CURRENT_QTY, S.SAFE_QTY " +
                "FROM TB_STOCK S " +
                "JOIN TB_ITEM I ON S.ITEM_KEY = I.ITEM_KEY " +
                "WHERE S.CURRENT_QTY <= S.SAFE_QTY " +
                "ORDER BY I.ITEM_KEY"
            );
            rs = ps.executeQuery();
            while (rs.next()) {
                DashboardStockDTO s = new DashboardStockDTO();
                s.setItem_code(rs.getString("ITEM_CODE"));
                s.setItem_name(rs.getString("ITEM_NAME"));
                s.setCurrent_qty(rs.getInt("CURRENT_QTY"));
                s.setSafe_qty(rs.getInt("SAFE_QTY"));
                dto.getLowStockList().add(s);
            }
            rs.close(); ps.close();

            // 최근 공지
            ps = conn.prepareStatement(
                "SELECT BOARD_KEY, TITLE, CREATED_AT, VIEW_COUNT " +
                "FROM ( " +
                "    SELECT BOARD_KEY, TITLE, CREATED_AT, VIEW_COUNT " +
                "    FROM TB_BOARD " +
                "    WHERE STATUS = 'Y' AND BOARD_TYPE = '공지' " +
                "    ORDER BY BOARD_KEY DESC " +
                ") WHERE ROWNUM <= 3"
            );

            rs = ps.executeQuery();
            while (rs.next()) {
                DashboardNoticeDTO n = new DashboardNoticeDTO();
                n.setBoard_key(rs.getInt("BOARD_KEY"));
                n.setTitle(rs.getString("TITLE"));
                n.setCreated_at(rs.getDate("CREATED_AT"));
                n.setView_count(rs.getInt("VIEW_COUNT"));
                dto.getNoticeList().add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return dto;
    }
}