package quality;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class QualityDAO {
    private Connection getConn() {
        try {
            Context ctx = new InitialContext();
            DataSource df = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
            return df.getConnection();
        } catch(Exception e) { e.printStackTrace(); return null; }
    }

    // 1. 전체 조회
    public List<QualityDTO> selectAll() {
        List<QualityDTO> list = new ArrayList<>();
        String sql = "SELECT q.*, i.ITEM_NAME FROM TB_QUALITY q, TB_ITEM i WHERE q.ITEM_KEY = i.ITEM_KEY ORDER BY q.QUALITY_KEY ASC"; 
        try(Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                QualityDTO dto = new QualityDTO();
                dto.setQuality_key(rs.getString("QUALITY_KEY"));
                dto.setProd_key(rs.getString("PROD_KEY"));
                dto.setItem_key(rs.getString("ITEM_KEY"));
                dto.setItem_name(rs.getString("ITEM_NAME")); 
                dto.setInspect_date(rs.getTimestamp("INSPECT_DATE"));
                dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
                dto.setGood_qty(rs.getInt("GOOD_QTY"));
                dto.setDefect_qty(rs.getInt("DEFECT_QTY"));
                dto.setDefect_reason(rs.getString("DEFECT_REASON"));
                dto.setQc_status(rs.getString("QC_STATUS"));
                dto.setUser_key(rs.getString("USER_KEY"));
                dto.setCreated_at(rs.getTimestamp("CREATED_AT"));
                list.add(dto);
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. 데이터 삽입
    public int insert(QualityDTO dto) {
        String sql = "INSERT INTO TB_QUALITY (QUALITY_KEY, PROD_KEY, ITEM_KEY, INSPECT_DATE, INSPECT_QTY, "
                   + "GOOD_QTY, DEFECT_QTY, DEFECT_REASON, QC_STATUS, USER_KEY, CREATED_AT) "
                   + "VALUES (SEQ_QUALITY.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, SYSDATE)";
        try(Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dto.getProd_key());
            ps.setString(2, dto.getItem_key());
            ps.setInt(3, dto.getInspect_qty());
            ps.setInt(4, dto.getGood_qty());
            ps.setInt(5, dto.getDefect_qty());
            ps.setString(6, dto.getDefect_reason());
            ps.setString(7, dto.getQc_status());
            ps.setString(8, dto.getUser_key());
            return ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); return 0; }
    }

    // 3. 삭제
    public int delete(String quality_key) {
        String sql = "DELETE FROM TB_QUALITY WHERE QUALITY_KEY = ?";
        try(Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, quality_key);
            return ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); return 0; }
    }

    // 4. 전체 개수 조회
    public int getTotalCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM TB_QUALITY";
        try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    // 5. 검색 및 페이징 조회 (통합 메서드)
    public List<QualityDTO> searchQualityList(String searchCode, String searchName, int startRow, int endRow) {
        List<QualityDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ( ");
        sql.append("  SELECT ROWNUM AS rnum, q.* FROM ( ");
        sql.append("    SELECT t.quality_key, ");
        sql.append("           (SELECT i.item_name FROM tb_item i WHERE i.item_key = t.item_key) AS item_name, ");
        sql.append("           t.inspect_date, t.inspect_qty, t.qc_status, t.item_key ");
        sql.append("    FROM tb_quality t ");
        sql.append("    WHERE 1=1 "); 

        if (searchCode != null && !searchCode.trim().isEmpty()) {
            sql.append("    AND (t.quality_key LIKE ? OR t.item_key LIKE ?) ");
        }
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append("    AND (SELECT i.item_name FROM tb_item i WHERE i.item_key = t.item_key) LIKE ? ");
        }

        sql.append("    ORDER BY TO_NUMBER(t.quality_key) ASC "); // 숫자 정렬 강제
        sql.append("  ) q WHERE ROWNUM <= ? ");
        sql.append(") WHERE rnum >= ? ");

        try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (searchCode != null && !searchCode.trim().isEmpty()) {
                ps.setString(idx++, "%" + searchCode + "%");
                ps.setString(idx++, "%" + searchCode + "%");
            }
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(idx++, "%" + searchName + "%");
            }
            ps.setInt(idx++, endRow);
            ps.setInt(idx++, startRow);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QualityDTO dto = new QualityDTO();
                    dto.setQuality_key(rs.getString("QUALITY_KEY"));
                    dto.setItem_name(rs.getString("ITEM_NAME"));
                    dto.setInspect_date(rs.getTimestamp("INSPECT_DATE"));
                    dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
                    dto.setQc_status(rs.getString("QC_STATUS"));
                    list.add(dto);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // [추가] 6. 수정 페이지용 데이터 단건 조회
    public QualityDTO selectOne(String quality_key) {
        QualityDTO dto = null;
        String sql = "SELECT q.*, (SELECT i.item_name FROM tb_item i WHERE i.item_key = q.item_key) AS item_name "
                   + "FROM tb_quality q WHERE q.quality_key = ?";
        
        try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, quality_key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new QualityDTO();
                    dto.setQuality_key(rs.getString("QUALITY_KEY"));
                    dto.setProd_key(rs.getString("PROD_KEY"));
                    dto.setItem_key(rs.getString("ITEM_KEY"));
                    dto.setItem_name(rs.getString("ITEM_NAME"));
                    dto.setInspect_date(rs.getTimestamp("INSPECT_DATE"));
                    dto.setInspect_qty(rs.getInt("INSPECT_QTY"));
                    dto.setGood_qty(rs.getInt("GOOD_QTY"));
                    dto.setDefect_qty(rs.getInt("DEFECT_QTY"));
                    dto.setDefect_reason(rs.getString("DEFECT_REASON"));
                    dto.setQc_status(rs.getString("QC_STATUS"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return dto;
    }

    // [추가] 7. 데이터 수정 실행
    public int update(QualityDTO dto) {
        String sql = "UPDATE TB_QUALITY SET INSPECT_QTY=?, GOOD_QTY=?, DEFECT_QTY=?, "
                   + "DEFECT_REASON=?, QC_STATUS=? WHERE QUALITY_KEY=?";
        
        try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dto.getInspect_qty());
            ps.setInt(2, dto.getGood_qty());
            ps.setInt(3, dto.getDefect_qty());
            ps.setString(4, dto.getDefect_reason());
            ps.setString(5, dto.getQc_status());
            ps.setString(6, dto.getQuality_key());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
}