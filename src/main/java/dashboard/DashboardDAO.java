package dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DashboardDAO {

    private DataSource getDataSource() throws Exception {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
    }

    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        dto.setTodayProductionQty(getTodayProductionQty());
        dto.setTodayDefectQty(getTodayDefectQty());
        dto.setLowStockCount(getLowStockCount());

        dto.setWorkorderTotal(getWorkorderTotal());
        dto.setWorkorderInProgress(getWorkorderInProgress());
        dto.setWorkorderWaiting(getWorkorderWaiting());

        dto.setQualityPassRate(getQualityPassRate());
        dto.setEquipmentRunRate(getEquipmentRunRate());

        dto.setWeekLabels(getWeekLabels());
        dto.setWeekProductionQtys(getWeekProductionQtys());

        return dto;
    }

    // 오늘 생산량: 양품 기준
    public int getTodayProductionQty() {
        String sql = "SELECT NVL(SUM(GOOD_QTY), 0) AS qty "
                   + "FROM TB_PRODUCTION "
                   + "WHERE TRUNC(PROD_DATE) = TRUNC(SYSDATE)";
        return getIntValue(sql, "qty");
    }

    // 오늘 불량 수량: 품질 테이블 기준
    public int getTodayDefectQty() {
        String sql = "SELECT NVL(SUM(DEFECT_QTY), 0) AS defect_qty "
                   + "FROM TB_QUALITY "
                   + "WHERE TRUNC(INSPECT_DATE) = TRUNC(SYSDATE)";
        return getIntValue(sql, "defect_qty");
    }

    // 재고 부족 품목 수: 현재재고 <= 안전재고
    public int getLowStockCount() {
        String sql = "SELECT COUNT(*) AS cnt "
                   + "FROM TB_STOCK "
                   + "WHERE CURRENT_QTY <= SAFE_QTY";
        return getIntValue(sql, "cnt");
    }

    // 오늘 작업지시 총 건수
    public int getWorkorderTotal() {
        String sql = "SELECT COUNT(*) AS cnt "
                   + "FROM TB_WORK_ORDER "
                   + "WHERE TRUNC(WORK_DATE) = TRUNC(SYSDATE)";
        return getIntValue(sql, "cnt");
    }

    // 오늘 작업지시 중 생산실적이 하나라도 연결된 건 -> 진행중
    public int getWorkorderInProgress() {
        String sql = "SELECT COUNT(DISTINCT w.WORK_ORDER_KEY) AS cnt "
                   + "FROM TB_WORK_ORDER w "
                   + "JOIN TB_PRODUCTION p ON w.WORK_ORDER_KEY = p.WORK_ORDER_KEY "
                   + "WHERE TRUNC(w.WORK_DATE) = TRUNC(SYSDATE)";
        return getIntValue(sql, "cnt");
    }

    // 대기 = 총 건수 - 진행중
    public int getWorkorderWaiting() {
        int total = getWorkorderTotal();
        int inProgress = getWorkorderInProgress();
        int waiting = total - inProgress;
        return waiting < 0 ? 0 : waiting;
    }

    // 품질 합격률 = (검사수량 - 불량수량) / 검사수량 * 100
    public double getQualityPassRate() {
        String sql = "SELECT CASE "
                   + "         WHEN NVL(SUM(INSPECT_QTY), 0) = 0 THEN 0 "
                   + "         ELSE ROUND(((SUM(INSPECT_QTY) - NVL(SUM(DEFECT_QTY), 0)) / SUM(INSPECT_QTY)) * 100, 1) "
                   + "       END AS rate "
                   + "FROM TB_QUALITY "
                   + "WHERE TRUNC(INSPECT_DATE) = TRUNC(SYSDATE)";
        return getDoubleValue(sql, "rate");
    }

    // 설비 가동률 = MACHINE_STATUS가 '가동중'인 비율
    public double getEquipmentRunRate() {
        String sql = "SELECT CASE "
                   + "         WHEN COUNT(*) = 0 THEN 0 "
                   + "         ELSE ROUND((SUM(CASE WHEN MACHINE_STATUS = '가동중' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 1) "
                   + "       END AS rate "
                   + "FROM TB_MACHINE";
        return getDoubleValue(sql, "rate");
    }

    public List<String> getWeekLabels() {
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "SELECT TO_CHAR(dt, 'MM/DD') AS day_label "
                       + "FROM ( "
                       + "    SELECT TRUNC(SYSDATE) - 6 + LEVEL - 1 AS dt "
                       + "    FROM dual "
                       + "    CONNECT BY LEVEL <= 7 "
                       + ") "
                       + "ORDER BY dt";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("day_label"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }

        return list;
    }

    // 최근 7일 양품 생산량
    public List<Integer> getWeekProductionQtys() {
        List<Integer> list = new ArrayList<Integer>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "SELECT qty "
                       + "FROM ( "
                       + "    SELECT dt, "
                       + "           NVL((SELECT SUM(GOOD_QTY) "
                       + "                FROM TB_PRODUCTION "
                       + "                WHERE TRUNC(PROD_DATE) = dt), 0) AS qty "
                       + "    FROM ( "
                       + "        SELECT TRUNC(SYSDATE) - 6 + LEVEL - 1 AS dt "
                       + "        FROM dual "
                       + "        CONNECT BY LEVEL <= 7 "
                       + "    ) "
                       + ") "
                       + "ORDER BY dt";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("qty"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }

        return list;
    }

    private int getIntValue(String sql, String columnName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int value = 0;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                value = rs.getInt(columnName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }

        return value;
    }

    private double getDoubleValue(String sql, String columnName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        double value = 0;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                value = rs.getDouble(columnName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }

        return value;
    }

    private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}