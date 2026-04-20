package process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProcessDAO {

	private Connection getConn() {

		Connection conn = null;

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 페이징 + 리스트 나오게
	public List selectAllProcess(ProcessDTO processDTO) {

		List<ProcessDTO> list = new ArrayList();

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn, " SELECT * FROM (SELECT rownum AS rnum, p.*  "
						+ "	FROM( SELECT p.process_key,p.process_code,p.process_name,p.sequence_no,p.process_desc,p.status,p.item_key,  "
						+ "			( SELECT i.item_name FROM tb_item i WHERE i.item_key = p.item_key) AS item_name  "
						+ "				FROM tb_process p ORDER BY p.process_key ) p "
						+ "					WHERE rownum <= ? ) WHERE rnum >= ? ");) {
			ps.setInt(1, processDTO.getEnd());
			ps.setInt(2, processDTO.getStart());

			System.out.println(((LoggableStatement) ps).getQueryString());

			try (ResultSet rs = ps.executeQuery();) {
				// 결과 활용
				while (rs.next()) {
					ProcessDTO dto = new ProcessDTO();

					dto.setProcess_key(rs.getInt("process_key"));
					dto.setProcess_code(rs.getString("process_code"));
					dto.setProcess_name(rs.getString("process_name"));
					dto.setSequence_no(rs.getInt("sequence_no"));
					dto.setProcess_desc(rs.getString("process_desc"));
					dto.setStatus(rs.getString("status"));
					dto.setItem_key(rs.getInt("Item_key"));
					dto.setItem_name(rs.getString("Item_name"));

					list.add(dto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("DAO list.size: " + list.size());
		return list;
	}

	// 조회 버튼 반영
	public List selectdetailProcess(ProcessDTO processDTO) {

		List<ProcessDTO> list = new ArrayList();

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn, "SELECT * FROM (SELECT rownum AS rnum, p.*  "
						+ "	FROM( SELECT p.process_key,p.process_code,p.process_name,p.sequence_no,p.process_desc,p.status,p.item_key, "
						+ "			i.item_name As Item_name From tb_process p  "
						+ "			left join tb_item i on p.item_key = i.item_key  "
						+ "			where 1=1 and (? is null or p.process_name like '%' || ? || '%' ) "
						+ " and (? = 0 or p.item_key = ?) "
						+ "			and (? is null or i.item_name like '%' || ? || '%' )   "
						+ "			ORDER BY p.process_key ) p  "
						+ "					WHERE rownum <= ? ) WHERE rnum >= ? ");

		) {
			ps.setString(1, processDTO.getProcess_name());
			ps.setString(2, processDTO.getProcess_name());
			ps.setInt(3, processDTO.getItem_key());
			ps.setInt(4, processDTO.getItem_key());
			
			ps.setString(5, processDTO.getKeyword());
			ps.setString(6, processDTO.getKeyword());
			ps.setInt(7, processDTO.getEnd());
			ps.setInt(8, processDTO.getStart());

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {

					ProcessDTO dto = new ProcessDTO();

					dto.setProcess_key(rs.getInt("process_key"));
					dto.setProcess_code(rs.getString("process_code"));
					dto.setProcess_name(rs.getString("process_name"));
					dto.setSequence_no(rs.getInt("sequence_no"));
					dto.setProcess_desc(rs.getString("process_desc"));
					dto.setStatus(rs.getString("status"));
					dto.setItem_key(rs.getInt("item_key"));
					dto.setItem_name(rs.getString("item_name"));
					
					list.add(dto);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 삽입
	public int insertProcess(ProcessDTO processDTO) {

		int result = -1;

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn,
						"INSERT INTO tb_process  " + "VALUES ( " + "    seq_process.nextval, "
								+ "    'PROC-' || LPAD(seq_process.currval, 3, '0'), " + "    ?, " + "    ?, "
								+ "    ?, " + "    ?, " + "    ? " + ") ");) {
			ps.setString(1, processDTO.getProcess_name());
			ps.setInt(2, processDTO.getSequence_no());
			ps.setString(3, processDTO.getProcess_desc());
			ps.setString(4, processDTO.getStatus());
			ps.setInt(5, processDTO.getItem_key());

			System.out.println(((LoggableStatement) ps).getQueryString());

			result = ps.executeUpdate();
			System.out.println("insert된 열 : " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	// 수정
	public int updateProcess(ProcessDTO processDTO) {

		int result = -1;

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn,
						"update tb_process " + " set process_code = ?, " + "  process_name = ?, "
								+ "  sequence_no = ?, " + "  process_desc = ?, " + "  status = ?, "
								+ "  item_key = ? "
								+ " where process_key = ? ");) {

			ps.setString(1, processDTO.getProcess_code());
			ps.setString(2, processDTO.getProcess_name());
			ps.setInt(3, processDTO.getSequence_no());
			ps.setString(4, processDTO.getProcess_desc());
			ps.setString(5, processDTO.getStatus());
			ps.setInt(6, processDTO.getItem_key());
			ps.setInt(7, processDTO.getProcess_key());

			System.out.println(((LoggableStatement) ps).getQueryString());

			result = ps.executeUpdate();
			System.out.println("update된 열 : " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int deleteProcess(ProcessDTO processDTO) {

		int result = -1;

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn, "delete tb_process" + " where process_key = ?");) {
			ps.setInt(1, processDTO.getProcess_key());
			System.out.println(((LoggableStatement) ps).getQueryString());

			result = ps.executeUpdate();
			System.out.println("delete된 열 : " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 페이징용 전체 개수
	public int ProcessTotal() {

		int totalCount = 0;

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn, "Select count(*) cnt From tb_process");) {
			System.out.println(((LoggableStatement) ps).getQueryString());

			try (ResultSet rs = ps.executeQuery();) {

				if (rs.next()) {
					totalCount = rs.getInt("cnt");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}

	public int SearchTotal(ProcessDTO dto) {

		int totalCount = 0;

		String keyword = dto.getKeyword();
		int keycode = dto.getKeycode();

		if (keyword == null)
			keyword = "";

		try (Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn,
						"SELECT COUNT(*) cnt " + "	FROM tb_process p " + "	LEFT JOIN tb_item i ON p.item_key = i.item_key "
								+ "	WHERE 1=1 " + "	AND ( ? = 0 OR p.process_key = ? ) "
								+ "	AND ( ? IS NULL OR i.item_name LIKE '%' || ? || '%' )");) {
			ps.setInt(1, keycode);
			ps.setInt(2, keycode);
			ps.setString(3, keyword);
			ps.setString(4, keyword);

			System.out.println(((LoggableStatement) ps).getQueryString());

			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					totalCount = rs.getInt("cnt");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}
	
	// 모달창 완제품 중복 거르기 
	public List<ProcessDTO> selectItemForProcess() {

	    List<ProcessDTO> list = new ArrayList<>();

	    String sql =
	        "SELECT DISTINCT p.item_key, i.item_name " +
	        " FROM tb_item i " +
	        " left join tb_process p " +
	        " on i.item_key = p.item_key " +   
	        " ORDER BY item_name";

	    try (Connection conn = getConn();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            ProcessDTO dto = new ProcessDTO();
	            dto.setItem_key(rs.getInt("item_key"));
	            dto.setItem_name(rs.getString("item_name"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}
