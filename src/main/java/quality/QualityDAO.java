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

    // DB 연결 객체
    Connection conn = null;

    // SQL 실행 객체
    PreparedStatement ps = null;

    // 조회 결과 저장 객체
    ResultSet rs = null;

    // DB 연결 메서드
    private void getConnection() throws Exception {
        Context ctx = new InitialContext();
        DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
        conn = dataFactory.getConnection();
    }

    // 자원 반납 메서드
    private void closeAll() {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }

    // ResultSet -> DTO 변환 메서드
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
        dto.setProd_key(rs.getInt("PROD_KEY"));
        dto.setUser_key(rs.getInt("USER_KEY"));
        dto.setItem_key(rs.getInt("ITEM_KEY"));
        dto.setUser_name(rs.getString("USER_NAME"));
        dto.setDue_date(rs.getDate("DUE_DATE"));

        return dto;
    }

    // 담당자 목록
    public List<QualityDTO> getUserList() {
        List<QualityDTO> list = new ArrayList<QualityDTO>();

        String query = ""
                + "SELECT USER_KEY, USER_NAME "
                + "FROM TB_USER "
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

    // 전체 목록 페이징 조회
    public List<QualityDTO> selectPage(int startRow, int endRow) {
        List<QualityDTO> list = new ArrayList<QualityDTO>();

        String query = ""
                + "SELECT * "
                + "FROM ( "
                + "    SELECT ROW_NUMBER() OVER (ORDER BY q.QUALITY_KEY ASC) rnum, "
                + "           q.QUALITY_KEY, q.QUALITY_CODE, q.INSPECT_DATE, q.INSPECT_QTY, "
                + "           q.GOOD_QTY, q.DEFECT_QTY, q.DEFECT_REASON, q.QC_STATUS, "
                + "           q.CREATED_AT, q.PROD_KEY, q.USER_KEY, "
                + "           i.ITEM_KEY, u.USER_NAME, plan.DUE_DATE "
                + "    FROM TB_QUALITY q "
                + "    LEFT OUTER JOIN TB_PRODUCTION p ON q.PROD_KEY = p.PROD_KEY "
                + "    LEFT OUTER JOIN TB_WORK_ORDER w ON p.WORK_ORDER_KEY = w.WORK_ORDER_KEY "
                + "    LEFT OUTER JOIN TB_PLAN plan ON w.PLAN_KEY = plan.PLAN_KEY "
                + "    LEFT OUTER JOIN TB_ITEM i ON plan.ITEM_KEY = i.ITEM_KEY "
                + "    LEFT OUTER JOIN TB_USER u ON q.USER_KEY = u.USER_KEY "
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

    // 검색 목록 페이징 조회
    public List<QualityDTO> searchPage(String qualityCode, String status, String inspectDate, int startRow, int endRow) {
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
        sql.append("           q.PROD_KEY, ");
        sql.append("           q.USER_KEY, ");
        sql.append("           i.ITEM_KEY, ");
        sql.append("           u.USER_NAME, ");
        sql.append("           plan.DUE_DATE ");
        sql.append("    FROM TB_QUALITY q ");
        sql.append("    LEFT OUTER JOIN TB_PRODUCTION p ON q.PROD_KEY = p.PROD_KEY ");
        sql.append("    LEFT OUTER JOIN TB_WORK_ORDER w ON p.WORK_ORDER_KEY = w.WORK_ORDER_KEY ");
        sql.append("    LEFT OUTER JOIN TB_PLAN plan ON w.PLAN_KEY = plan.PLAN_KEY ");
        sql.append("    LEFT OUTER JOIN TB_ITEM i ON plan.ITEM_KEY = i.ITEM_KEY ");
        sql.append("    LEFT OUTER JOIN TB_USER u ON q.USER_KEY = u.USER_KEY ");
        sql.append("    WHERE 1=1 ");

        // 검사번호 검색
        if (qualityCode != null && !qualityCode.trim().equals("")) {
            sql.append(" AND q.QUALITY_CODE LIKE ? ");
        }

        // 검사상태 검색
        if (status != null && !status.trim().equals("")) {
            sql.append(" AND q.QC_STATUS = ? ");
        }

        // 검사일자 검색
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
                ps.setString(idx++, "%" + qualityCode + "%");
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

    // 검색 결과 개수 조회
    public int getSearchCount(String qualityCode, String status, String inspectDate) {
        int count = 0;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) cnt ");
        sql.append("FROM TB_QUALITY q ");
        sql.append("WHERE 1=1 ");

        // 검사번호 검색
        if (qualityCode != null && !qualityCode.trim().equals("")) {
            sql.append(" AND q.QUALITY_CODE LIKE ? ");
        }

        // 검사상태 검색
        if (status != null && !status.trim().equals("")) {
            sql.append(" AND q.QC_STATUS = ? ");
        }

        // 검사일자 검색
        if (inspectDate != null && !inspectDate.trim().equals("")) {
            sql.append(" AND q.INSPECT_DATE = ? ");
        }

        try {
            getConnection();
            ps = conn.prepareStatement(sql.toString());

            int idx = 1;

            if (qualityCode != null && !qualityCode.trim().equals("")) {
                ps.setString(idx++, "%" + qualityCode + "%");
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

    // 신규 등록
    public int addquality(QualityDTO dto) {
        int result = 0;

        String query = ""
                + "INSERT INTO TB_QUALITY ( "
                + "    QUALITY_KEY, QUALITY_CODE, INSPECT_DATE, INSPECT_QTY, "
                + "    GOOD_QTY, DEFECT_QTY, DEFECT_REASON, QC_STATUS, "
                + "    CREATED_AT, PROD_KEY, USER_KEY "
                + ") VALUES ( "
                + "    COMP.SEQ_TB_QUALITY.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ? "
                + ")";

        try {
            getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, dto.getQuality_code());
            ps.setDate(2, dto.getInspect_date());
            ps.setInt(3, dto.getInspect_qty());
            ps.setInt(4, dto.getGood_qty());
            ps.setInt(5, dto.getDefect_qty());
            ps.setString(6, dto.getDefect_reason());
            ps.setString(7, dto.getQc_status());
            ps.setInt(8, dto.getProd_key());
            ps.setInt(9, dto.getUser_key());

            result = ps.executeUpdate();

            // 확인용 로그
            System.out.println("등록 result : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }

        return result;
    }

    // 수정
    public int updatequality(QualityDTO dto) {
        int result = 0;

        String query = ""
                + "UPDATE TB_QUALITY "
                + "SET QUALITY_CODE = ?, INSPECT_DATE = ?, INSPECT_QTY = ?, "
                + "    DEFECT_REASON = ?, PROD_KEY = ?, USER_KEY = ? "
                + "WHERE QUALITY_KEY = ?";

        try {
            getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, dto.getQuality_code());
            ps.setDate(2, dto.getInspect_date());
            ps.setInt(3, dto.getInspect_qty());
            ps.setString(4, dto.getDefect_reason());
            ps.setInt(5, dto.getProd_key());
            ps.setInt(6, dto.getUser_key());
            ps.setInt(7, dto.getQuality_key());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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