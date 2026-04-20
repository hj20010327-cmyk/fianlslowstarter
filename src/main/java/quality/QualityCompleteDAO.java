package quality;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QualityCompleteDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

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
    // 완료 처리용 내부 DTO
    // =========================
    private static class CompleteDTO {
        private int qualityKey;      // 품질 PK
        private int workOrderKey;    // 작업지시 PK
        private int inspectQty;      // 검사수량
        private int goodQty;         // 양품수량
        private int defectQty;       // 불량수량
        private int itemKey;         // 품목 PK
        private int workUserKey;     // 작업자 PK
    }

    // =========================
    // 완료 처리 메인
    // - 품질 완료
    // - 재고 반영
    // - 생산실적 등록
    // - 품질 상태 완료 변경
    // =========================
    public QualityCompleteResult completeQuality(String[] qualityKeys) {
        QualityCompleteResult result = new QualityCompleteResult();

        if (qualityKeys == null || qualityKeys.length == 0) {
            return result;
        }

        try {
            getConnection();
            conn.setAutoCommit(false);

            for (int i = 0; i < qualityKeys.length; i++) {
                if (qualityKeys[i] == null || qualityKeys[i].trim().equals("")) {
                    continue;
                }

                int qualityKey = Integer.parseInt(qualityKeys[i]);

                // 1. 완료 대상 조회
                CompleteDTO dto = getCompleteTarget(conn, qualityKey);
                if (dto == null) {
                    result.setFailCount(result.getFailCount() + 1);
                    continue;
                }

                // 2. 이미 생산실적이 있으면 이미 완료 처리
                if (existsProductionByQualityKey(conn, qualityKey)) {
                    result.setAlreadyCompletedCount(result.getAlreadyCompletedCount() + 1);
                    continue;
                }

                // 3. 재고 반영
                //    검사전 수량 - 검사수량
                //    검사후 수량 + 검사수량
                //    현재고 + 검사수량
                int stockResult = updateStockAfterComplete(conn, dto);
                if (stockResult <= 0) {
                    result.setFailCount(result.getFailCount() + 1);
                    continue;
                }

                // 4. 생산실적 등록
                int productionResult = insertProductionAfterComplete(conn, dto);
                if (productionResult <= 0) {
                    result.setFailCount(result.getFailCount() + 1);
                    continue;
                }

                // 5. 품질 상태 완료 변경
                int statusResult = updateQualityStatusToComplete(conn, qualityKey);
                if (statusResult <= 0) {
                    result.setFailCount(result.getFailCount() + 1);
                    continue;
                }

                // 6. 성공
                result.setSuccessCount(result.getSuccessCount() + 1);
            }

            // 전체 완료 커밋
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return result;
    }

    // =========================
    // 완료 대상 조회
    // - 품질 / 작업지시 / 계획 / 품목 / 작업자 정보 조회
    // =========================
    private CompleteDTO getCompleteTarget(Connection conn, int qualityKey) throws Exception {
        PreparedStatement psLocal = null;
        ResultSet rsLocal = null;

        try {
            String sql = ""
                    + "SELECT q.QUALITY_KEY, "
                    + "       q.WORK_ORDER_KEY, "
                    + "       q.INSPECT_QTY, "
                    + "       q.GOOD_QTY, "
                    + "       q.DEFECT_QTY, "
                    + "       plan.ITEM_KEY, "
                    + "       w.WORK_USER_KEY "
                    + "FROM TB_QUALITY q "
                    + "LEFT OUTER JOIN TB_WORK_ORDER w "
                    + "    ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY "
                    + "LEFT OUTER JOIN TB_PLAN plan "
                    + "    ON w.PLAN_KEY = plan.PLAN_KEY "
                    + "WHERE q.QUALITY_KEY = ?";

            psLocal = conn.prepareStatement(sql);
            psLocal.setInt(1, qualityKey);
            rsLocal = psLocal.executeQuery();

            if (rsLocal.next()) {
                CompleteDTO dto = new CompleteDTO();
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

    // =========================
    // 이미 생산실적이 등록되었는지 확인
    // =========================
    private boolean existsProductionByQualityKey(Connection conn, int qualityKey) throws Exception {
        PreparedStatement psLocal = null;
        ResultSet rsLocal = null;

        try {
            String sql = "SELECT COUNT(*) CNT FROM TB_PRODUCTION WHERE QUALITY_KEY = ?";
            psLocal = conn.prepareStatement(sql);
            psLocal.setInt(1, qualityKey);
            rsLocal = psLocal.executeQuery();

            if (rsLocal.next()) {
                return rsLocal.getInt("CNT") > 0;
            }

        } finally {
            try { if (rsLocal != null) rsLocal.close(); } catch (Exception e) {}
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }

        return false;
    }

    // =========================
    // 완료 시 재고 반영
    // - 검사전 수량(WAIT_QC)  : 검사수량만큼 차감
    // - 검사후 수량(DONE_QC)  : 검사수량만큼 증가
    // - 현재고(CURRENT_QTY)   : 검사수량만큼 증가
    // =========================
    private int updateStockAfterComplete(Connection conn, CompleteDTO dto) throws Exception {
        PreparedStatement psLocal = null;

        try {
            String sql = ""
                    + "UPDATE TB_STOCK "
                    + "SET WAIT_QC = CASE "
                    + "                  WHEN WAIT_QC - ? < 0 THEN 0 "
                    + "                  ELSE WAIT_QC - ? "
                    + "              END, "
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

    // =========================
    // 완료 시 생산실적 등록
    // - PROD_CODE 는 LPAD 방식 자동 생성
    // =========================
    private int insertProductionAfterComplete(Connection conn, CompleteDTO dto) throws Exception {
        PreparedStatement psLocal = null;

        try {
            String prodCode = makeProductionCode(conn);

            String sql = ""
                    + "INSERT INTO TB_PRODUCTION ( "
                    + "    PROD_KEY, "
                    + "    PROD_CODE, "
                    + "    PROD_DATE, "
                    + "    GOOD_QTY, "
                    + "    DEFECT_QTY, "
                    + "    CREATED_AT, "
                    + "    WORK_USER_KEY, "
                    + "    QUALITY_KEY, "
                    + "    WORK_ORDER_KEY "
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

    // =========================
    // 생산실적 코드 자동 생성
    // - PROD-001, PROD-002 ...
    // - LPAD 사용
    // =========================
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

            if (rsLocal.next()) {
                return rsLocal.getString("NEXT_CODE");
            }

        } finally {
            try { if (rsLocal != null) rsLocal.close(); } catch (Exception e) {}
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }

        return "PROD-001";
    }

    // =========================
    // 품질 상태 완료 변경
    // =========================
    private int updateQualityStatusToComplete(Connection conn, int qualityKey) throws Exception {
        PreparedStatement psLocal = null;

        try {
            String sql = ""
                    + "UPDATE TB_QUALITY "
                    + "SET QC_STATUS = '완료' "
                    + "WHERE QUALITY_KEY = ?";

            psLocal = conn.prepareStatement(sql);
            psLocal.setInt(1, qualityKey);

            return psLocal.executeUpdate();

        } finally {
            try { if (psLocal != null) psLocal.close(); } catch (Exception e) {}
        }
    }
}