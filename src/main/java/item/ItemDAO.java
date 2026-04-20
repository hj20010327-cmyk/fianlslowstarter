package item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ItemDAO {

	private DataSource getDataSource() throws Exception {
		Context ctx = new InitialContext();
		return (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
	}

	public List<ItemDTO> selectItemList(String keyword, String status, String itemType, int startRow, int endRow) {
	    List<ItemDTO> list = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = getDataSource().getConnection();

	        String innerSql = ""
	                + "SELECT ITEM_KEY, ITEM_CODE, ITEM_NAME, ITEM_TYPE, SPEC, UNIT, "
	                + "       PRICE, SAFE_QTY, STATUS, "
	                + "       TO_CHAR(CREATED_AT, 'YYYY-MM-DD HH24:MI:SS') AS CREATED_AT "
	                + "FROM TB_ITEM "
	                + "WHERE 1=1 ";

	        if (keyword != null && !keyword.trim().isEmpty()) {
	            innerSql += "AND (ITEM_CODE LIKE ? OR ITEM_NAME LIKE ? OR SPEC LIKE ?) ";
	        }

	        if (status != null && !status.trim().isEmpty()) {
	            innerSql += "AND STATUS = ? ";
	        }

	        if (itemType != null && !itemType.trim().isEmpty()) {
	            innerSql += "AND ITEM_TYPE = ? ";
	        }

	        innerSql += "ORDER BY ITEM_KEY DESC";

	        String sql = ""
	                + "SELECT * FROM ( "
	                + "    SELECT ROWNUM rnum, A.* FROM ( " + innerSql + " ) A "
	                + "    WHERE ROWNUM <= ? "
	                + ") "
	                + "WHERE rnum >= ?";

	        ps = conn.prepareStatement(sql);

	        int idx = 1;

	        if (keyword != null && !keyword.trim().isEmpty()) {
	            ps.setString(idx++, "%" + keyword + "%");
	            ps.setString(idx++, "%" + keyword + "%");
	            ps.setString(idx++, "%" + keyword + "%");
	        }

	        if (status != null && !status.trim().isEmpty()) {
	            ps.setString(idx++, status);
	        }

	        if (itemType != null && !itemType.trim().isEmpty()) {
	            ps.setString(idx++, itemType);
	        }

	        ps.setInt(idx++, endRow);
	        ps.setInt(idx++, startRow);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            ItemDTO dto = new ItemDTO();
	            dto.setItem_key(rs.getInt("ITEM_KEY"));
	            dto.setItem_code(rs.getString("ITEM_CODE"));
	            dto.setItem_name(rs.getString("ITEM_NAME"));
	            dto.setItem_type(rs.getString("ITEM_TYPE"));
	            dto.setSpec(rs.getString("SPEC"));
	            dto.setUnit(rs.getString("UNIT"));
	            dto.setPrice(rs.getInt("PRICE"));
	            dto.setSafe_qty(rs.getInt("SAFE_QTY"));
	            dto.setStatus(rs.getString("STATUS"));
	            dto.setCreated_at(rs.getString("CREATED_AT"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(rs, ps, conn);
	    }

	    return list;
	}

	public ItemDTO selectItemOne(int itemKey) {
		ItemDTO dto = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "" + "SELECT ITEM_KEY, ITEM_CODE, ITEM_NAME, ITEM_TYPE, SPEC, UNIT, "
					+ "       PRICE, SAFE_QTY, STATUS, "
					+ "       TO_CHAR(CREATED_AT, 'YYYY-MM-DD HH24:MI:SS') AS CREATED_AT " + "FROM TB_ITEM "
					+ "WHERE ITEM_KEY = ?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, itemKey);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new ItemDTO();
				dto.setItem_key(rs.getInt("ITEM_KEY"));
				dto.setItem_code(rs.getString("ITEM_CODE"));
				dto.setItem_name(rs.getString("ITEM_NAME"));
				dto.setItem_type(rs.getString("ITEM_TYPE"));
				dto.setSpec(rs.getString("SPEC"));
				dto.setUnit(rs.getString("UNIT"));
				dto.setPrice(rs.getInt("PRICE"));
				dto.setSafe_qty(rs.getInt("SAFE_QTY"));
				dto.setStatus(rs.getString("STATUS"));
				dto.setCreated_at(rs.getString("CREATED_AT"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return dto;
	}

	public int insertItem(ItemDTO dto) {
		int result = 0;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getDataSource().getConnection();

			// 🔥 여기 핵심
			String itemCode = createItemCode(conn, dto.getItem_type());
			dto.setItem_code(itemCode);

			String sql = "" + "INSERT INTO TB_ITEM ( " + "ITEM_KEY, ITEM_CODE, ITEM_NAME, ITEM_TYPE, SPEC, UNIT, "
					+ "PRICE, SAFE_QTY, STATUS, CREATED_AT " + ") VALUES ( "
					+ "SEQ_ITEM.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE )";

			ps = conn.prepareStatement(sql);

			ps.setString(1, dto.getItem_code());
			ps.setString(2, dto.getItem_name());
			ps.setString(3, dto.getItem_type());
			ps.setString(4, dto.getSpec());
			ps.setString(5, dto.getUnit());
			ps.setInt(6, dto.getPrice());
			ps.setInt(7, dto.getSafe_qty());
			ps.setString(8, dto.getStatus());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int updateItem(ItemDTO dto) {
		int result = 0;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getDataSource().getConnection();

			String sql = "" + "UPDATE TB_ITEM SET " + "    ITEM_CODE = ?, " + "    ITEM_NAME = ?, "
					+ "    ITEM_TYPE = ?, " + "    SPEC = ?, " + "    UNIT = ?, " + "    PRICE = ?, "
					+ "    SAFE_QTY = ?, " + "    STATUS = ? " + "WHERE ITEM_KEY = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getItem_code());
			ps.setString(2, dto.getItem_name());
			ps.setString(3, dto.getItem_type());
			ps.setString(4, dto.getSpec());
			ps.setString(5, dto.getUnit());
			ps.setInt(6, dto.getPrice());
			ps.setInt(7, dto.getSafe_qty());
			ps.setString(8, dto.getStatus());
			ps.setInt(9, dto.getItem_key());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, ps, conn);
		}

		return result;
	}

	public String createItemCode(Connection conn, String itemType) throws Exception {

		String prefix = "";

		if ("완제품".equals(itemType)) {
			prefix = "CP-A-";
		} else if ("반제품".equals(itemType)) {
			prefix = "CP-P-";
		} else {
			prefix = "CP-M-";
		}

		String sql = "" + "SELECT NVL(MAX(TO_NUMBER(SUBSTR(ITEM_CODE, 6))), 0) + 1 AS NEXT_NO " + "FROM TB_ITEM "
				+ "WHERE ITEM_TYPE = ?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, itemType);

		ResultSet rs = ps.executeQuery();

		int nextNo = 1;
		if (rs.next()) {
			nextNo = rs.getInt("NEXT_NO");
		}

		rs.close();
		ps.close();

		return prefix + String.format("%03d", nextNo);
	}

	public int selectItemCount(String keyword, String status, String itemType) {
	    int count = 0;

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = getDataSource().getConnection();

	        String sql = "SELECT COUNT(*) FROM TB_ITEM WHERE 1=1 ";

	        if (keyword != null && !keyword.trim().isEmpty()) {
	            sql += "AND (ITEM_CODE LIKE ? OR ITEM_NAME LIKE ? OR SPEC LIKE ?) ";
	        }

	        if (status != null && !status.trim().isEmpty()) {
	            sql += "AND STATUS = ? ";
	        }

	        if (itemType != null && !itemType.trim().isEmpty()) {
	            sql += "AND ITEM_TYPE = ? ";
	        }

	        ps = conn.prepareStatement(sql);

	        int idx = 1;

	        if (keyword != null && !keyword.trim().isEmpty()) {
	            ps.setString(idx++, "%" + keyword + "%");
	            ps.setString(idx++, "%" + keyword + "%");
	            ps.setString(idx++, "%" + keyword + "%");
	        }

	        if (status != null && !status.trim().isEmpty()) {
	            ps.setString(idx++, status);
	        }

	        if (itemType != null && !itemType.trim().isEmpty()) {
	            ps.setString(idx++, itemType);
	        }

	        rs = ps.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(rs, ps, conn);
	    }

	    return count;
	}

	public int selectItemCountByCondition(String keyword, String status, String itemType, String targetType, String targetStatus) {
	    int count = 0;

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = getDataSource().getConnection();

	        String sql = "SELECT COUNT(*) FROM TB_ITEM WHERE 1=1 ";

	        if (keyword != null && !keyword.trim().isEmpty()) {
	            sql += "AND (ITEM_CODE LIKE ? OR ITEM_NAME LIKE ? OR SPEC LIKE ?) ";
	        }

	        if (status != null && !status.trim().isEmpty()) {
	            sql += "AND STATUS = ? ";
	        }

	        if (itemType != null && !itemType.trim().isEmpty()) {
	            sql += "AND ITEM_TYPE = ? ";
	        }

	        if (targetType != null && !targetType.trim().isEmpty()) {
	            sql += "AND ITEM_TYPE = ? ";
	        }

	        if (targetStatus != null && !targetStatus.trim().isEmpty()) {
	            sql += "AND STATUS = ? ";
	        }

	        ps = conn.prepareStatement(sql);

	        int idx = 1;

	        if (keyword != null && !keyword.trim().isEmpty()) {
	            ps.setString(idx++, "%" + keyword + "%");
	            ps.setString(idx++, "%" + keyword + "%");
	            ps.setString(idx++, "%" + keyword + "%");
	        }

	        if (status != null && !status.trim().isEmpty()) {
	            ps.setString(idx++, status);
	        }

	        if (itemType != null && !itemType.trim().isEmpty()) {
	            ps.setString(idx++, itemType);
	        }

	        if (targetType != null && !targetType.trim().isEmpty()) {
	            ps.setString(idx++, targetType);
	        }

	        if (targetStatus != null && !targetStatus.trim().isEmpty()) {
	            ps.setString(idx++, targetStatus);
	        }

	        rs = ps.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(rs, ps, conn);
	    }

	    return count;
	}

	private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
