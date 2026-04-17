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

    // 목록 조회용 DTO 세팅
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

    // 담당자 목록 (테스트 계정 제외)
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

    // 작업지시 목록 조회
    // TB_WORK_ORDER / TB_PLAN은 조회만 사용
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

    // 검색용 검사번호 목록
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

    // 검색용 품목명 목록
    public List<String> getItemNameList() {
        List<String> list = new ArrayList<String>();

        String query = ""
                + "SELECT DISTINCT i.ITEM_NAME "
                + "FROM TB_QUALITY q "
                + "LEFT OUTER JOIN TB_WORK_ORDER w "
                + "    ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY "
                + "LEFT OUTER JOIN TB_PLAN plan "
                + "    ON w.PLAN_KEY = plan.PLAN_KEY "
                + "LEFT OUTER JOIN TB_ITEM i "
                + "    ON plan.ITEM_KEY = i.ITEM_KEY "
                + "ORDER BY i.ITEM_NAME ASC";

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

    // 다음 검사번호 생성
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

    // 목록 페이징 조회
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
                + "           w.WORK_ORDER_CODE, "
                + "           q.USER_KEY, "
                + "           i.ITEM_KEY, "
                + "           i.ITEM_NAME, "
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

    // 전체 개수
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

    // 검색 페이징 조회
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
        sql.append("           w.WORK_ORDER_CODE, ");
        sql.append("           q.USER_KEY, ");
        sql.append("           i.ITEM_KEY, ");
        sql.append("           i.ITEM_NAME, ");
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

        if (qualityCode != null && !qualityCode.trim().equals("")) {
            sql.append(" AND q.QUALITY_CODE = ? ");
        }

        if (itemName != null && !itemName.trim().equals("")) {
            sql.append(" AND i.ITEM_NAME = ? ");
        }

        if (status != null && !status.trim().equals("")) {
            sql.append(" AND q.QC_STATUS = ? ");
        }

        if (inspectDate != null && !inspectDate.trim().equals("")) {
            sql.append(" AND q.INSPECT_DATE = ? ");
        }

        sql.append(") ");
        sql.append("WHERE rnum BETWEEN ? AND ?");

        try {
            getConnection();
            ps = conn.prepareStatement(sql.toString());

            int idx = 1;

            if (qualityCode != null && !qualityCode.trim().equals("")) {
                ps.setString(idx++, qualityCode);
            }
            if (itemName != null && !itemName.trim().equals("")) {
                ps.setString(idx++, itemName);
            }
            if (status != null && !status.trim().equals("")) {
                ps.setString(idx++, status);
            }
            if (inspectDate != null && !inspectDate.trim().equals("")) {
                ps.setDate(idx++, Date.valueOf(inspectDate));
            }

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

    // 검색 개수
    public int getSearchCount(String qualityCode, String itemName, String status, String inspectDate) {
        int count = 0;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) cnt ");
        sql.append("FROM TB_QUALITY q ");
        sql.append("LEFT OUTER JOIN TB_WORK_ORDER w ");
        sql.append("    ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY ");
        sql.append("LEFT OUTER JOIN TB_PLAN plan ");
        sql.append("    ON w.PLAN_KEY = plan.PLAN_KEY ");
        sql.append("LEFT OUTER JOIN TB_ITEM i ");
        sql.append("    ON plan.ITEM_KEY = i.ITEM_KEY ");
        sql.append("WHERE 1=1 ");

        if (qualityCode != null && !qualityCode.trim().equals("")) {
            sql.append(" AND q.QUALITY_CODE = ? ");
        }

        if (itemName != null && !itemName.trim().equals("")) {
            sql.append(" AND i.ITEM_NAME = ? ");
        }

        if (status != null && !status.trim().equals("")) {
            sql.append(" AND q.QC_STATUS = ? ");
        }

        if (inspectDate != null && !inspectDate.trim().equals("")) {
            sql.append(" AND q.INSPECT_DATE = ? ");
        }

        try {
            getConnection();
            ps = conn.prepareStatement(sql.toString());

            int idx = 1;

            if (qualityCode != null && !qualityCode.trim().equals("")) {
                ps.setString(idx++, qualityCode);
            }
            if (itemName != null && !itemName.trim().equals("")) {
                ps.setString(idx++, itemName);
            }
            if (status != null && !status.trim().equals("")) {
                ps.setString(idx++, status);
            }
            if (inspectDate != null && !inspectDate.trim().equals("")) {
                ps.setDate(idx++, Date.valueOf(inspectDate));
            }

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

    // 등록
    // 1. TB_QUALITY 저장
    // 2. TB_STOCK WAIT_QC 증가
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

            // 등록 성공하면 WAIT_QC 증가
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
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
    // 1. TB_QUALITY 수정
    // 2. 품목명 바뀌었으면 TB_ITEM.ITEM_NAME 수정
    public int updatequality(QualityDTO dto) {
        int result = 0;

        PreparedStatement psQuality = null;
        PreparedStatement psItem = null;

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

            // 품목명 수정
            if (dto.getItem_name() != null && !dto.getItem_name().trim().equals("")) {
                String itemSql = ""
                        + "UPDATE TB_ITEM "
                        + "SET ITEM_NAME = ? "
                        + "WHERE ITEM_KEY = ( "
                        + "    SELECT plan.ITEM_KEY "
                        + "    FROM TB_QUALITY q "
                        + "    LEFT OUTER JOIN TB_WORK_ORDER w "
                        + "        ON q.WORK_ORDER_KEY = w.WORK_ORDER_KEY "
                        + "    LEFT OUTER JOIN TB_PLAN plan "
                        + "        ON w.PLAN_KEY = plan.PLAN_KEY "
                        + "    WHERE q.QUALITY_KEY = ? "
                        + ")";

                psItem = conn.prepareStatement(itemSql);
                psItem.setString(1, dto.getItem_name());
                psItem.setInt(2, dto.getQuality_key());
                psItem.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            result = 0;
        } finally {
            try { if (psQuality != null) psQuality.close(); } catch (Exception e) {}
            try { if (psItem != null) psItem.close(); } catch (Exception e) {}
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception e) {}
            closeAll();
        }

        return result;
    }

    // 삭제
    public int deleteQuality(String[] qualityKeys) {
        int result = 0;

        String query = "DELETE FROM TB_QUALITY WHERE QUALITY_KEY = ?";

        try {
            getConnection();
            ps = conn.prepareStatement(query);

            for (int i = 0; i < qualityKeys.length; i++) {
                ps.setInt(1, Integer.parseInt(qualityKeys[i]));
                result += ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return result;
    }
}