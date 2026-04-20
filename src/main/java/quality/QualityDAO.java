package quality;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QualityDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // =========================
    // DB 연결
    // =========================
    private void getConnection() throws Exception {
        javax.naming.Context ctx = new javax.naming.InitialContext();
        javax.sql.DataSource dataFactory = (javax.sql.DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        conn = dataFactory.getConnection();
    }

    // =========================
    // 자원 닫기
    // =========================
    private void closeAll() {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    // =========================
    // 목록 조회용 DTO 세팅
    // =========================
    private QualityDTO makeDto(ResultSet rs) throws Exception {
        QualityDTO dto = new QualityDTO();

        dto.setQuality_key(rs.getInt("QUALITY_KEY"));
        dto.setQuality_code(rs.getString("QUALITY_CODE"));
        dto.setInspect_date(rs.getDate("INSPECT_DATE"));
        dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
        dto.setGood_qty(rs.getInt("GOOD_QTY"));
        dto.setDefect_qty(rs.getInt("DEFECT_QTY"));
        dto.setDefect_reason(rs.getString("DEFECT_REASON"));
        dto.setQc_status(rs.getString("QC_STATUS"));
        dto.setCreated_at(rs.getDate("CREATED_AT"));

        dto.setProd_key(rs.getInt("WORK_ORDER_KEY"));
        dto.setUser_key(rs.getInt("USER_KEY"));
        dto.setItem_key(rs.getInt("ITEM_KEY"));
        dto.setUser_name(rs.getString("USER_NAME"));
        dto.setDue_date(rs.getDate("DUE_DATE"));
        dto.setItem_name(rs.getString("ITEM_NAME"));
        dto.setProd_name(rs.getString("WORK_ORDER_CODE"));

        return dto;
    }

    public List<QualityDTO> getUserList() {
        List<QualityDTO> list = new ArrayList<QualityDTO>();

        String query = ""
                + "SELECT USER_KEY, USER_NAME "
                + "FROM TB_USER "
                + "WHERE USER_NAME NOT LIKE '%테스트%' "
                + "ORDER BY USER_NAME ASC";

        try {
            getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                dto.setUser_key(rs.getInt("USER_KEY"));
                dto.setUser_name(rs.getString("USER_NAME"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return list;
    }

    public List<QualityDTO> getWorkOrderList() {
        List<QualityDTO> list = new ArrayList<QualityDTO>();

        String query = ""
                + "SELECT w.WORK_ORDER_KEY, "
                + "       w.WORK_ORDER_CODE, "
                + "       i.ITEM_NAME, "
                + "       NVL(s.CURRENT_QTY, 0) AS STOCK_QTY "
                + "FROM TB_WORK_ORDER w "
                + "LEFT OUTER JOIN TB_PLAN plan "
                + "    ON w.PLAN_KEY = plan.PLAN_KEY "
                + "LEFT OUTER JOIN TB_ITEM i "
                + "    ON plan.ITEM_KEY = i.ITEM_KEY "
                + "LEFT OUTER JOIN TB_STOCK s "
                + "    ON i.ITEM_KEY = s.ITEM_KEY "
                + "ORDER BY w.WORK_ORDER_KEY ASC";

        try {
            getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                QualityDTO dto = new QualityDTO();
                dto.setProd_key(rs.getInt("WORK_ORDER_KEY"));
                dto.setProd_name(rs.getString("WORK_ORDER_CODE"));
                dto.setItem_name(rs.getString("ITEM_NAME"));
                dto.setStock_qty(rs.getInt("STOCK_QTY"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return list;
    }

    public List<String> getQualityCodeList() {
        List<String> list = new ArrayList<String>();

        String query = "SELECT DISTINCT QUALITY_CODE FROM TB_QUALITY ORDER BY QUALITY_CODE ASC";

        try {
            getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("QUALITY_CODE"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return list;
    }

    public List<String> getItemNameList() {
        List<String> list = new ArrayList<String>();

        String query = ""
                + "SELECT DISTINCT ITEM_NAME "
                + "FROM TB_QUALITY "
                + "WHERE ITEM_NAME IS NOT NULL "
                + "ORDER BY ITEM_NAME ASC";

        try {
            getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("ITEM_NAME"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return list;
    }

    public String getNextQualityCode() {
        String nextCode = "Q-001";

        String query = ""
                + "SELECT NVL(MAX(TO_NUMBER(SUBSTR(QUALITY_CODE, 3))), 0) + 1 AS NEXT_NO "
                + "FROM TB_QUALITY "
                + "WHERE QUALITY_CODE LIKE 'Q-%'";

        try {
            getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                int nextNo = rs.getInt("NEXT_NO");
                nextCode = String.format("Q-%03d", nextNo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return nextCode;
    }

    public List<QualityDTO> selectPage(int startRow, int endRow) {
        List<QualityDTO> list = new ArrayList<QualityDTO>();

        String query = ""
                + "SELECT * "
                + "FROM ( "
                + "    SELECT ROW_NUMBER() OVER (ORDER BY q.QUALITY_KEY ASC) rnum, "
                + "           q.QUALITY_KEY, "
                + "           q.QUALITY_CODE, "
                + "           q.INSPECT_DATE, "
                + "           q.INSPECT_QTY, "
                + "           q.GOOD_QTY, "
                + "           q.DEFECT_QTY, "
                + "           q.DEFECT_REASON, "
                + "           q.QC_STATUS, "
                + "           q.CREATED_AT, "
                + "           q.WORK_ORDER_KEY, "
                + "           q.ITEM_NAME, "
                + "           w.WORK_ORDER_CODE, "
                + "           q.USER_KEY, "
                + "           i.ITEM_KEY, "
                + "           u.USER_NAME, "
                + "           plan.DUE_DATE "
                + "    FROM TB_QUALITY q "
                + "    LEFT OUTER JOIN TB_WORK_ORDER w "
                + "        ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY "
                + "    LEFT OUTER JOIN TB_PLAN plan "
                + "        ON w.PLAN_KEY = plan.PLAN_KEY "
                + "    LEFT OUTER JOIN TB_ITEM i "
                + "        ON plan.ITEM_KEY = i.ITEM_KEY "
                + "    LEFT OUTER JOIN TB_USER u "
                + "        ON q.USER_KEY = u.USER_KEY "
                + ") "
                + "WHERE rnum BETWEEN ? AND ?";

        try {
            getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, startRow);
            ps.setInt(2, endRow);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(makeDto(rs));
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

        String query = "SELECT COUNT(*) cnt FROM TB_QUALITY";

        try {
            getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("cnt");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return count;
    }

    public List<QualityDTO> searchPage(String qualityCode, String itemName, String status, String inspectDate, int startRow, int endRow) {
        List<QualityDTO> list = new ArrayList<QualityDTO>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * ");
        sql.append("FROM ( ");
        sql.append("    SELECT ROW_NUMBER() OVER (ORDER BY q.QUALITY_KEY ASC) rnum, ");
        sql.append("           q.QUALITY_KEY, ");
        sql.append("           q.QUALITY_CODE, ");
        sql.append("           q.INSPECT_DATE, ");
        sql.append("           q.INSPECT_QTY, ");
        sql.append("           q.GOOD_QTY, ");
        sql.append("           q.DEFECT_QTY, ");
        sql.append("           q.DEFECT_REASON, ");
        sql.append("           q.QC_STATUS, ");
        sql.append("           q.CREATED_AT, ");
        sql.append("           q.WORK_ORDER_KEY, ");
        sql.append("           q.ITEM_NAME, ");
        sql.append("           w.WORK_ORDER_CODE, ");
        sql.append("           q.USER_KEY, ");
        sql.append("           i.ITEM_KEY, ");
        sql.append("           u.USER_NAME, ");
        sql.append("           plan.DUE_DATE ");
        sql.append("    FROM TB_QUALITY q ");
        sql.append("    LEFT OUTER JOIN TB_WORK_ORDER w ");
        sql.append("        ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY ");
        sql.append("    LEFT OUTER JOIN TB_PLAN plan ");
        sql.append("        ON w.PLAN_KEY = plan.PLAN_KEY ");
        sql.append("    LEFT OUTER JOIN TB_ITEM i ");
        sql.append("        ON plan.ITEM_KEY = i.ITEM_KEY ");
        sql.append("    LEFT OUTER JOIN TB_USER u ");
        sql.append("        ON q.USER_KEY = u.USER_KEY ");
        sql.append("    WHERE 1=1 ");

        if (qualityCode != null && !qualityCode.trim().equals("")) sql.append(" AND q.QUALITY_CODE = ? ");
        if (itemName != null && !itemName.trim().equals("")) sql.append(" AND q.ITEM_NAME = ? ");
        if (status != null && !status.trim().equals("")) sql.append(" AND q.QC_STATUS = ? ");
        if (inspectDate != null && !inspectDate.trim().equals("")) sql.append(" AND q.INSPECT_DATE = ? ");

        sql.append(") ");
        sql.append("WHERE rnum BETWEEN ? AND ?");

        try {
            getConnection();
            ps = conn.prepareStatement(sql.toString());

            int idx = 1;
            if (qualityCode != null && !qualityCode.trim().equals("")) ps.setString(idx++, qualityCode);
            if (itemName != null && !itemName.trim().equals("")) ps.setString(idx++, itemName);
            if (status != null && !status.trim().equals("")) ps.setString(idx++, status);
            if (inspectDate != null && !inspectDate.trim().equals("")) ps.setDate(idx++, Date.valueOf(inspectDate));
            ps.setInt(idx++, startRow);
            ps.setInt(idx++, endRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(makeDto(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return list;
    }

    public int getSearchCount(String qualityCode, String itemName, String status, String inspectDate) {
        int count = 0;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) cnt ");
        sql.append("FROM TB_QUALITY q ");
        sql.append("LEFT OUTER JOIN TB_WORK_ORDER w ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY ");
        sql.append("LEFT OUTER JOIN TB_PLAN plan ON w.PLAN_KEY = plan.PLAN_KEY ");
        sql.append("LEFT OUTER JOIN TB_ITEM i ON plan.ITEM_KEY = i.ITEM_KEY ");
        sql.append("WHERE 1=1 ");

        if (qualityCode != null && !qualityCode.trim().equals("")) sql.append(" AND q.QUALITY_CODE = ? ");
        if (itemName != null && !itemName.trim().equals("")) sql.append(" AND q.ITEM_NAME = ? ");
        if (status != null && !status.trim().equals("")) sql.append(" AND q.QC_STATUS = ? ");
        if (inspectDate != null && !inspectDate.trim().equals("")) sql.append(" AND q.INSPECT_DATE = ? ");

        try {
            getConnection();
            ps = conn.prepareStatement(sql.toString());

            int idx = 1;
            if (qualityCode != null && !qualityCode.trim().equals("")) ps.setString(idx++, qualityCode);
            if (itemName != null && !itemName.trim().equals("")) ps.setString(idx++, itemName);
            if (status != null && !status.trim().equals("")) ps.setString(idx++, status);
            if (inspectDate != null && !inspectDate.trim().equals("")) ps.setDate(idx++, Date.valueOf(inspectDate));

            rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt("cnt");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return count;
    }

    // 등록
    public int addquality(QualityDTO dto) {
        int result = 0;

        PreparedStatement psInsert = null;
        PreparedStatement psUpdateStock = null;

        try {
            getConnection();
            conn.setAutoCommit(false);

            String insertSql = ""
                    + "INSERT INTO TB_QUALITY ( "
                    + "    QUALITY_KEY, QUALITY_CODE, INSPECT_DATE, INSPECT_QTY, "
                    + "    GOOD_QTY, DEFECT_QTY, DEFECT_REASON, QC_STATUS, "
                    + "    CREATED_AT, USER_KEY, WORK_ORDER_KEY, ITEM_NAME "
                    + ") VALUES ( "
                    + "    SEQ_TB_QUALITY.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?, ? "
                    + ")";

            psInsert = conn.prepareStatement(insertSql);
            psInsert.setString(1, dto.getQuality_code());
            psInsert.setDate(2, dto.getInspect_date());
            psInsert.setInt(3, dto.getInspect_qty());
            psInsert.setInt(4, dto.getGood_qty());
            psInsert.setInt(5, dto.getDefect_qty());
            psInsert.setString(6, dto.getDefect_reason());
            psInsert.setString(7, dto.getQc_status());
            psInsert.setInt(8, dto.getUser_key());
            psInsert.setInt(9, dto.getProd_key());
            psInsert.setString(10, dto.getItem_name());

            result = psInsert.executeUpdate();

            String updateStockSql = ""
                    + "UPDATE TB_STOCK "
                    + "SET WAIT_QC = WAIT_QC + ?, UPDATED_AT = SYSDATE "
                    + "WHERE ITEM_KEY = ( "
                    + "    SELECT plan.ITEM_KEY "
                    + "    FROM TB_WORK_ORDER w "
                    + "    LEFT OUTER JOIN TB_PLAN plan ON w.PLAN_KEY = plan.PLAN_KEY "
                    + "    WHERE w.WORK_ORDER_KEY = ? "
                    + ")";

            psUpdateStock = conn.prepareStatement(updateStockSql);
            psUpdateStock.setInt(1, dto.getInspect_qty());
            psUpdateStock.setInt(2, dto.getProd_key());
            psUpdateStock.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            result = 0;
        } finally {
            try { if (psInsert != null) psInsert.close(); } catch (Exception e) {}
            try { if (psUpdateStock != null) psUpdateStock.close(); } catch (Exception e) {}
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return result;
    }

    // 수정
    public int updatequality(QualityDTO dto) {
        int result = 0;
        PreparedStatement psQuality = null;

        try {
            getConnection();
            conn.setAutoCommit(false);

            String qualitySql = ""
                    + "UPDATE TB_QUALITY "
                    + "SET INSPECT_DATE = ?, "
                    + "    INSPECT_QTY = ?, "
                    + "    GOOD_QTY = ?, "
                    + "    DEFECT_QTY = ?, "
                    + "    DEFECT_REASON = ?, "
                    + "    QC_STATUS = ?, "
                    + "    USER_KEY = ?, "
                    + "    ITEM_NAME = ? "
                    + "WHERE QUALITY_KEY = ?";

            psQuality = conn.prepareStatement(qualitySql);
            psQuality.setDate(1, dto.getInspect_date());
            psQuality.setInt(2, dto.getInspect_qty());
            psQuality.setInt(3, dto.getGood_qty());
            psQuality.setInt(4, dto.getDefect_qty());
            psQuality.setString(5, dto.getDefect_reason());
            psQuality.setString(6, dto.getQc_status());
            psQuality.setInt(7, dto.getUser_key());
            psQuality.setString(8, dto.getItem_name());
            psQuality.setInt(9, dto.getQuality_key());

            result = psQuality.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            result = 0;
        } finally {
            try { if (psQuality != null) psQuality.close(); } catch (Exception e) {}
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return result;
    }

    // =========================
    // 삭제
    // 완료 안 된 건: WAIT_QC 원복 후 삭제
    // 완료 된 건: 생산실적 삭제 + 재고 원복 후 삭제
    // =========================
    public int deleteQuality(String[] qualityKeys) {
        int result = 0;

        PreparedStatement psSelect = null;
        PreparedStatement psDeleteProduction = null;
        PreparedStatement psUpdateStock = null;
        PreparedStatement psDeleteQuality = null;
        ResultSet rsLocal = null;

        try {
            getConnection();
            conn.setAutoCommit(false);

            for (String key : qualityKeys) {
                int qualityKey = Integer.parseInt(key);

                String selectSql = ""
                        + "SELECT q.QUALITY_KEY, q.QC_STATUS, q.INSPECT_QTY, plan.ITEM_KEY "
                        + "FROM TB_QUALITY q "
                        + "LEFT OUTER JOIN TB_WORK_ORDER w ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY "
                        + "LEFT OUTER JOIN TB_PLAN plan ON w.PLAN_KEY = plan.PLAN_KEY "
                        + "WHERE q.QUALITY_KEY = ?";

                psSelect = conn.prepareStatement(selectSql);
                psSelect.setInt(1, qualityKey);
                rsLocal = psSelect.executeQuery();

                if (!rsLocal.next()) {
                    try { rsLocal.close(); } catch (Exception e) {}
                    try { psSelect.close(); } catch (Exception e) {}
                    continue;
                }

                String qcStatus = rsLocal.getString("QC_STATUS");
                int inspectQty = rsLocal.getInt("INSPECT_QTY");
                int itemKey = rsLocal.getInt("ITEM_KEY");

                try { rsLocal.close(); } catch (Exception e) {}
                try { psSelect.close(); } catch (Exception e) {}

                if ("완료".equals(qcStatus)) {
                    String deleteProdSql = "DELETE FROM TB_PRODUCTION WHERE QUALITY_KEY = ?";
                    psDeleteProduction = conn.prepareStatement(deleteProdSql);
                    psDeleteProduction.setInt(1, qualityKey);
                    psDeleteProduction.executeUpdate();
                    try { psDeleteProduction.close(); } catch (Exception e) {}

                    String updateStockSql = ""
                            + "UPDATE TB_STOCK "
                            + "SET WAIT_QC = WAIT_QC + ?, "
                            + "    DONE_QC = CASE WHEN DONE_QC - ? < 0 THEN 0 ELSE DONE_QC - ? END, "
                            + "    CURRENT_QTY = CASE WHEN CURRENT_QTY - ? < 0 THEN 0 ELSE CURRENT_QTY - ? END, "
                            + "    UPDATED_AT = SYSDATE "
                            + "WHERE ITEM_KEY = ?";

                    psUpdateStock = conn.prepareStatement(updateStockSql);
                    psUpdateStock.setInt(1, inspectQty);
                    psUpdateStock.setInt(2, inspectQty);
                    psUpdateStock.setInt(3, inspectQty);
                    psUpdateStock.setInt(4, inspectQty);
                    psUpdateStock.setInt(5, inspectQty);
                    psUpdateStock.setInt(6, itemKey);
                    psUpdateStock.executeUpdate();
                    try { psUpdateStock.close(); } catch (Exception e) {}
                } else {
                    String updateStockSql = ""
                            + "UPDATE TB_STOCK "
                            + "SET WAIT_QC = CASE WHEN WAIT_QC - ? < 0 THEN 0 ELSE WAIT_QC - ? END, "
                            + "    UPDATED_AT = SYSDATE "
                            + "WHERE ITEM_KEY = ?";

                    psUpdateStock = conn.prepareStatement(updateStockSql);
                    psUpdateStock.setInt(1, inspectQty);
                    psUpdateStock.setInt(2, inspectQty);
                    psUpdateStock.setInt(3, itemKey);
                    psUpdateStock.executeUpdate();
                    try { psUpdateStock.close(); } catch (Exception e) {}
                }

                String deleteQualitySql = "DELETE FROM TB_QUALITY WHERE QUALITY_KEY = ?";
                psDeleteQuality = conn.prepareStatement(deleteQualitySql);
                psDeleteQuality.setInt(1, qualityKey);
                result += psDeleteQuality.executeUpdate();
                try { psDeleteQuality.close(); } catch (Exception e) {}
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            result = 0;
        } finally {
            try { if (rsLocal != null) rsLocal.close(); } catch (Exception e) {}
            try { if (psSelect != null) psSelect.close(); } catch (Exception e) {}
            try { if (psDeleteProduction != null) psDeleteProduction.close(); } catch (Exception e) {}
            try { if (psUpdateStock != null) psUpdateStock.close(); } catch (Exception e) {}
            try { if (psDeleteQuality != null) psDeleteQuality.close(); } catch (Exception e) {}
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return result;
    }

    // 완료 처리
    public QualityCompleteResult completeQuality(String[] qualityKeys) {
        QualityCompleteResult completeResult = new QualityCompleteResult();

        if (qualityKeys == null || qualityKeys.length == 0) return completeResult;

        try {
            getConnection();
            conn.setAutoCommit(false);

            for (String key : qualityKeys) {
                if (key == null || key.trim().equals("")) continue;

                int qualityKey = Integer.parseInt(key);

                QualityCompleteDTO dto = getCompleteTarget(conn, qualityKey);
                if (dto == null) {
                    completeResult.setFailCount(completeResult.getFailCount() + 1);
                    continue;
                }

                if (existsProductionByQualityKey(conn, qualityKey)) {
                    completeResult.setAlreadyCompletedCount(
                            completeResult.getAlreadyCompletedCount() + 1);
                    continue;
                }

                int stockResult = updateStockAfterComplete(conn, dto);
                if (stockResult <= 0) {
                    completeResult.setFailCount(completeResult.getFailCount() + 1);
                    continue;
                }

                int prodResult = insertProductionAfterComplete(conn, dto);
                if (prodResult <= 0) {
                    completeResult.setFailCount(completeResult.getFailCount() + 1);
                    continue;
                }

                updateQualityStatusToComplete(conn, qualityKey);
                completeResult.setSuccessCount(completeResult.getSuccessCount() + 1);
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return completeResult;
    }

    private static class QualityCompleteDTO {
        private int qualityKey;
        private int workOrderKey;
        private int inspectQty;
        private int goodQty;
        private int defectQty;
        private int itemKey;
        private int workUserKey;
    }

    private QualityCompleteDTO getCompleteTarget(Connection conn, int qualityKey) throws Exception {
        PreparedStatement psLocal = null;
        ResultSet rsLocal = null;

        try {
            String sql = ""
                    + "SELECT q.QUALITY_KEY, q.WORK_ORDER_KEY, q.INSPECT_QTY, q.GOOD_QTY, "
                    + "       q.DEFECT_QTY, plan.ITEM_KEY, w.WORK_USER_KEY "
                    + "FROM TB_QUALITY q "
                    + "LEFT OUTER JOIN TB_WORK_ORDER w ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY "
                    + "LEFT OUTER JOIN TB_PLAN plan ON w.PLAN_KEY = plan.PLAN_KEY "
                    + "WHERE q.QUALITY_KEY = ?";

            psLocal = conn.prepareStatement(sql);
            psLocal.setInt(1, qualityKey);
            rsLocal = psLocal.executeQuery();

            if (rsLocal.next()) {
                QualityCompleteDTO dto = new QualityCompleteDTO();
                dto.qualityKey = rsLocal.getInt("QUALITY_KEY");
                dto.workOrderKey = rsLocal.getInt("WORK_ORDER_KEY");
                dto.inspectQty = rsLocal.getInt("INSPECT_QTY");
                dto.goodQty = rsLocal.getInt("GOOD_QTY");
                dto.defectQty = rsLocal.getInt("DEFECT_QTY");
                dto.itemKey = rsLocal.getInt("ITEM_KEY");
                dto.workUserKey = rsLocal.getInt("WORK_USER_KEY");
                return dto;
            }

        } finally {
            try { if (rsLocal != null) rsLocal.close(); } catch (Exception e) {}
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }

        return null;
    }

    private boolean existsProductionByQualityKey(Connection conn, int qualityKey) throws Exception {
        PreparedStatement psLocal = null;
        ResultSet rsLocal = null;

        try {
            String sql = "SELECT COUNT(*) CNT FROM TB_PRODUCTION WHERE QUALITY_KEY = ?";
            psLocal = conn.prepareStatement(sql);
            psLocal.setInt(1, qualityKey);
            rsLocal = psLocal.executeQuery();

            if (rsLocal.next()) return rsLocal.getInt("CNT") > 0;

        } finally {
            try { if (rsLocal != null) rsLocal.close(); } catch (Exception e) {}
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }

        return false;
    }

    private int updateStockAfterComplete(Connection conn, QualityCompleteDTO dto) throws Exception {
        PreparedStatement psLocal = null;

        try {
            String sql = ""
                    + "UPDATE TB_STOCK "
                    + "SET WAIT_QC = CASE WHEN WAIT_QC - ? < 0 THEN 0 ELSE WAIT_QC - ? END, "
                    + "    DONE_QC = DONE_QC + ?, "
                    + "    CURRENT_QTY = CURRENT_QTY + ?, "
                    + "    UPDATED_AT = SYSDATE "
                    + "WHERE ITEM_KEY = ?";

            psLocal = conn.prepareStatement(sql);
            psLocal.setInt(1, dto.inspectQty);
            psLocal.setInt(2, dto.inspectQty);
            psLocal.setInt(3, dto.inspectQty);
            psLocal.setInt(4, dto.inspectQty);
            psLocal.setInt(5, dto.itemKey);

            return psLocal.executeUpdate();

        } finally {
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }
    }

    private int insertProductionAfterComplete(Connection conn, QualityCompleteDTO dto) throws Exception {
        PreparedStatement psLocal = null;

        try {
            String prodCode = makeProductionCode(conn);

            String sql = ""
                    + "INSERT INTO TB_PRODUCTION ( "
                    + "    PROD_KEY, PROD_CODE, PROD_DATE, GOOD_QTY, DEFECT_QTY, "
                    + "    CREATED_AT, WORK_USER_KEY, QUALITY_KEY, WORK_ORDER_KEY "
                    + ") VALUES ( "
                    + "    SEQ_PRODUCTION.NEXTVAL, ?, SYSDATE, ?, ?, SYSDATE, ?, ?, ? "
                    + ")";

            psLocal = conn.prepareStatement(sql);
            psLocal.setString(1, prodCode);
            psLocal.setInt(2, dto.goodQty);
            psLocal.setInt(3, dto.defectQty);
            psLocal.setInt(4, dto.workUserKey);
            psLocal.setInt(5, dto.qualityKey);
            psLocal.setInt(6, dto.workOrderKey);

            return psLocal.executeUpdate();

        } finally {
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }
    }

    private String makeProductionCode(Connection conn) throws Exception {
        PreparedStatement psLocal = null;
        ResultSet rsLocal = null;

        try {
            String sql = ""
                    + "SELECT 'PROD-' || LPAD(NVL(MAX(TO_NUMBER(SUBSTR(PROD_CODE, 6))), 0) + 1, 3, '0') AS NEXT_CODE "
                    + "FROM TB_PRODUCTION "
                    + "WHERE PROD_CODE LIKE 'PROD-%'";

            psLocal = conn.prepareStatement(sql);
            rsLocal = psLocal.executeQuery();

            if (rsLocal.next()) return rsLocal.getString("NEXT_CODE");

        } finally {
            try { if (rsLocal != null) rsLocal.close(); } catch (Exception e) {}
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }

        return "PROD-001";
    }

    private int updateQualityStatusToComplete(Connection conn, int qualityKey) throws Exception {
        PreparedStatement psLocal = null;

        try {
            String sql = "UPDATE TB_QUALITY SET QC_STATUS = '완료' WHERE QUALITY_KEY = ?";
            psLocal = conn.prepareStatement(sql);
            psLocal.setInt(1, qualityKey);
            return psLocal.executeUpdate();

        } finally {
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }
    }
}