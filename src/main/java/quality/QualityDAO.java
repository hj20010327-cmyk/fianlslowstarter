package quality;

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

public class QualityDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // DB 연결
    private void getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        conn = dataFactory.getConnection();
    }

    // 자원 닫기
    private void closeAll() {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    // =========================
    // 🔥 수정 부분 핵심 설명
    // 품목명은 TB_ITEM 공통 데이터이기 때문에
    // 하나 수정하면 전체가 바뀜
    // 그래서 품목명 수정 기능 제거함
    // =========================

    // 등록
    public int addquality(QualityDTO dto) {
        int result = 0;

        PreparedStatement psInsert = null;
        PreparedStatement psUpdateStock = null;

        try {
            getConnection();
            conn.setAutoCommit(false);

            // 품질 데이터 저장
            String insertSql = ""
                    + "INSERT INTO TB_QUALITY ( "
                    + "    QUALITY_KEY, QUALITY_CODE, INSPECT_DATE, INSPECT_QTY, "
                    + "    GOOD_QTY, DEFECT_QTY, DEFECT_REASON, QC_STATUS, "
                    + "    CREATED_AT, USER_KEY, WORK_ORDER_KEY "
                    + ") VALUES ( "
                    + "    SEQ_TB_QUALITY.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ? "
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

            result = psInsert.executeUpdate();

            // 🔥 재고 WAIT_QC 증가
            String updateStockSql = ""
                    + "UPDATE TB_STOCK "
                    + "SET WAIT_QC = WAIT_QC + ?, "
                    + "    UPDATED_AT = SYSDATE "
                    + "WHERE ITEM_KEY = ( "
                    + "    SELECT plan.ITEM_KEY "
                    + "    FROM TB_WORK_ORDER w "
                    + "    LEFT OUTER JOIN TB_PLAN plan "
                    + "        ON w.PLAN_KEY = plan.PLAN_KEY "
                    + "    WHERE w.WORK_ORDER_KEY = ? "
                    + ")";

            psUpdateStock = conn.prepareStatement(updateStockSql);
            psUpdateStock.setInt(1, dto.getInspect_qty());
            psUpdateStock.setInt(2, dto.getProd_key());
            psUpdateStock.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            result = 0;
        } finally {
            try { if (psInsert != null) psInsert.close(); } catch (Exception e) {}
            try { if (psUpdateStock != null) psUpdateStock.close(); } catch (Exception e) {}
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return result;
    }

    // =========================
    // 🔥 수정 (핵심 수정 완료 버전)
    // =========================
    public int updatequality(QualityDTO dto) {
        int result = 0;

        PreparedStatement psQuality = null;

        try {
            getConnection();
            conn.setAutoCommit(false);

            // ✔ TB_QUALITY만 수정
            String qualitySql = ""
                    + "UPDATE TB_QUALITY "
                    + "SET INSPECT_DATE = ?, "
                    + "    INSPECT_QTY = ?, "
                    + "    GOOD_QTY = ?, "
                    + "    DEFECT_QTY = ?, "
                    + "    DEFECT_REASON = ?, "
                    + "    QC_STATUS = ?, "
                    + "    USER_KEY = ? "
                    + "WHERE QUALITY_KEY = ?";

            psQuality = conn.prepareStatement(qualitySql);
            psQuality.setDate(1, dto.getInspect_date());
            psQuality.setInt(2, dto.getInspect_qty());
            psQuality.setInt(3, dto.getGood_qty());
            psQuality.setInt(4, dto.getDefect_qty());
            psQuality.setString(5, dto.getDefect_reason());
            psQuality.setString(6, dto.getQc_status());
            psQuality.setInt(7, dto.getUser_key());
            psQuality.setInt(8, dto.getQuality_key());

            result = psQuality.executeUpdate();

            // ❌ 중요: TB_ITEM 수정 제거
            // 이유: 품목명은 공통 데이터라 전체 바뀌는 문제 발생

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            result = 0;
        } finally {
            try { if (psQuality != null) psQuality.close(); } catch (Exception e) {}
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return result;
    }
}