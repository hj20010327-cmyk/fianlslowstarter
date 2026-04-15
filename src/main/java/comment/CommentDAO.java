package comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommentDAO {

	private DataSource getDataSource() throws Exception {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
    }

    public ArrayList<CommentDTO> selectCommentList(int boardKey) {
        ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "SELECT c.COMMENT_KEY, c.CONTENT, c.BOARD_KEY, c.USER_KEY, "
                       + "u.USER_NAME, c.CREATED_AT, c.UPDATED_AT, c.STATUS "
                       + "FROM TB_COMMENT c "
                       + "JOIN TB_USER u ON c.USER_KEY = u.USER_KEY "
                       + "WHERE c.BOARD_KEY = ? "
                       + "AND c.STATUS = 'Y' "
                       + "ORDER BY c.COMMENT_KEY ASC";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardKey);
            rs = ps.executeQuery();

            while (rs.next()) {
                CommentDTO dto = new CommentDTO();
                dto.setComment_key(rs.getInt("COMMENT_KEY"));
                dto.setContent(rs.getString("CONTENT"));
                dto.setBoard_key(rs.getInt("BOARD_KEY"));
                dto.setUser_key(rs.getInt("USER_KEY"));
                dto.setUser_name(rs.getString("USER_NAME"));
                dto.setCreated_at(rs.getDate("CREATED_AT"));
                dto.setUpdated_at(rs.getDate("UPDATED_AT"));
                dto.setStatus(rs.getString("STATUS"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }

        return list;
    }

    public int insertComment(CommentDTO dto) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "INSERT INTO TB_COMMENT "
                       + "(COMMENT_KEY, CONTENT, BOARD_KEY, USER_KEY, CREATED_AT, UPDATED_AT, STATUS) "
                       + "VALUES (SEQ_COMMENT.NEXTVAL, ?, ?, ?, SYSDATE, SYSDATE, 'Y')";

            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getContent());
            ps.setInt(2, dto.getBoard_key());
            ps.setInt(3, dto.getUser_key());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }

        return result;
    }

    public int deleteComment(int commentKey) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "UPDATE TB_COMMENT "
                       + "SET STATUS = 'N', UPDATED_AT = SYSDATE "
                       + "WHERE COMMENT_KEY = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, commentKey);

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }

        return result;
    }

    private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
	
}
