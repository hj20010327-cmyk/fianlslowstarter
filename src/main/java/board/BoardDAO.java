package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	private DataSource getDataSource() throws Exception {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
    }

    public int selectBoardCount(String searchType, String keyword) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) cnt ");
            sql.append("FROM TB_BOARD b ");
            sql.append("JOIN TB_USER u ON b.USER_KEY = u.USER_KEY ");
            sql.append("WHERE b.STATUS = 'Y' ");

            if (keyword != null && !keyword.trim().isEmpty()) {
                if ("title".equals(searchType)) {
                    sql.append("AND b.TITLE LIKE ? ");
                } else if ("content".equals(searchType)) {
                    sql.append("AND b.CONTENT LIKE ? ");
                } else if ("writer".equals(searchType)) {
                    sql.append("AND u.USER_NAME LIKE ? ");
                }
            }

            ps = conn.prepareStatement(sql.toString());

            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("cnt");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }

        return count;
    }

    public ArrayList<BoardDTO> selectBoardList(String searchType, String keyword, int page, int pageSize) {
        ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        try {
            conn = getDataSource().getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * ");
            sql.append("FROM ( ");
            sql.append("    SELECT ROWNUM rnum, a.* ");
            sql.append("    FROM ( ");
            sql.append("        SELECT b.BOARD_KEY, b.BOARD_TYPE, b.TITLE, b.CONTENT, ");
            sql.append("               b.USER_KEY, u.USER_NAME, b.VIEW_COUNT, ");
            sql.append("               b.CREATED_AT, b.UPDATED_AT, b.STATUS ");
            sql.append("        FROM TB_BOARD b ");
            sql.append("        JOIN TB_USER u ON b.USER_KEY = u.USER_KEY ");
            sql.append("        WHERE b.STATUS = 'Y' ");

            if (keyword != null && !keyword.trim().isEmpty()) {
                if ("title".equals(searchType)) {
                    sql.append("AND b.TITLE LIKE ? ");
                } else if ("content".equals(searchType)) {
                    sql.append("AND b.CONTENT LIKE ? ");
                } else if ("writer".equals(searchType)) {
                    sql.append("AND u.USER_NAME LIKE ? ");
                }
            }

            sql.append("        ORDER BY CASE WHEN b.BOARD_TYPE = '공지' THEN 0 ELSE 1 END, b.BOARD_KEY DESC ");
            sql.append("    ) a ");
            sql.append("    WHERE ROWNUM <= ? ");
            sql.append(") ");
            sql.append("WHERE rnum >= ?");

            ps = conn.prepareStatement(sql.toString());

            int idx = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(idx++, "%" + keyword + "%");
            }
            ps.setInt(idx++, endRow);
            ps.setInt(idx, startRow);

            rs = ps.executeQuery();

            while (rs.next()) {
                BoardDTO dto = new BoardDTO();
                dto.setBoard_key(rs.getInt("BOARD_KEY"));
                dto.setBoard_type(rs.getString("BOARD_TYPE"));
                dto.setTitle(rs.getString("TITLE"));
                dto.setContent(rs.getString("CONTENT"));
                dto.setUser_key(rs.getInt("USER_KEY"));
                dto.setUser_name(rs.getString("USER_NAME"));
                dto.setView_count(rs.getInt("VIEW_COUNT"));
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

    public BoardDTO selectBoardOne(int boardKey) {
        BoardDTO dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "SELECT b.BOARD_KEY, b.BOARD_TYPE, b.TITLE, b.CONTENT, "
                       + "b.USER_KEY, u.USER_NAME, b.VIEW_COUNT, b.CREATED_AT, b.UPDATED_AT, b.STATUS "
                       + "FROM TB_BOARD b "
                       + "JOIN TB_USER u ON b.USER_KEY = u.USER_KEY "
                       + "WHERE b.BOARD_KEY = ? "
                       + "AND b.STATUS = 'Y'";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardKey);

            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new BoardDTO();
                dto.setBoard_key(rs.getInt("BOARD_KEY"));
                dto.setBoard_type(rs.getString("BOARD_TYPE"));
                dto.setTitle(rs.getString("TITLE"));
                dto.setContent(rs.getString("CONTENT"));
                dto.setUser_key(rs.getInt("USER_KEY"));
                dto.setUser_name(rs.getString("USER_NAME"));
                dto.setView_count(rs.getInt("VIEW_COUNT"));
                dto.setCreated_at(rs.getDate("CREATED_AT"));
                dto.setUpdated_at(rs.getDate("UPDATED_AT"));
                dto.setStatus(rs.getString("STATUS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }

        return dto;
    }

    public void increaseViewCount(int boardKey) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "UPDATE TB_BOARD "
                       + "SET VIEW_COUNT = VIEW_COUNT + 1 "
                       + "WHERE BOARD_KEY = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardKey);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }
    }

    public int insertBoard(BoardDTO dto) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "INSERT INTO TB_BOARD "
                       + "(BOARD_KEY, BOARD_TYPE, TITLE, CONTENT, USER_KEY, VIEW_COUNT, CREATED_AT, UPDATED_AT, STATUS) "
                       + "VALUES (SEQ_BOARD.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE, SYSDATE, 'Y')";

            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getBoard_type());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setInt(4, dto.getUser_key());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }

        return result;
    }

    public int updateBoard(BoardDTO dto) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "UPDATE TB_BOARD "
                       + "SET BOARD_TYPE = ?, TITLE = ?, CONTENT = ?, UPDATED_AT = SYSDATE "
                       + "WHERE BOARD_KEY = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getBoard_type());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setInt(4, dto.getBoard_key());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }

        return result;
    }

    public int deleteBoard(int boardKey) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getDataSource().getConnection();

            String sql = "UPDATE TB_BOARD "
                       + "SET STATUS = 'N', UPDATED_AT = SYSDATE "
                       + "WHERE BOARD_KEY = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardKey);

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

