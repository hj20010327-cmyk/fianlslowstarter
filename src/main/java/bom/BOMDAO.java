package bom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BOMDAO {

	
	private Connection getConn() {

		Connection conn = null;
		try {
			// JNDI 방식
			// context.xml 에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource :커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속
			conn = dataFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 페이징 + 리스트 나오게
	public List selectAllBOM(BOMDTO bomDTO) {

		List<BOMDTO> list = new ArrayList();

		try (Connection conn = getConn(); 
			PreparedStatement ps = new LoggableStatement(conn, "SELECT *\r\n"
					+ "FROM ( SELECT ROWNUM AS rnum, b.*\r\n"
					+ "    FROM ( SELECT b.bom_key,\r\n"
					+ "               (SELECT i.item_name FROM tb_item i WHERE i.item_key = b.product_item_key) AS product_item_key,\r\n"
					+ "               (SELECT i.item_name FROM tb_item i WHERE i.item_key = b.material_item_key) AS material_item_key, \r\n"
					+ "               b.qty,\r\n"
					+ "               b.remark\r\n"
					+ "        FROM tb_bom b\r\n"
					+ "        ORDER BY b.bom_key\r\n"
					+ "    ) b\r\n"
					+ "    WHERE ROWNUM <= ?\r\n"
					+ ")\r\n"
					+ "WHERE rnum >= ?");
					
			) {
			ps.setInt(1, bomDTO.getEnd());
			ps.setInt(2, bomDTO.getStart());
			
			System.out.println(((LoggableStatement) ps).getQueryString());

			try (ResultSet rs = ps.executeQuery();) {
				//결과 활용
				while (rs.next()) {
					BOMDTO dto = new BOMDTO();

					int bom_key = Integer.parseInt(rs.getString("bom_key"));
					dto.setBom_key(bom_key);
					
					
					dto.setProduct_item_key(rs.getString("product_item_key"));
					dto.setMaterial_item_key(rs.getString("material_item_key"));
					
							
					dto.setQTY(rs.getInt("qty"));
					
					dto.setRemark(rs.getString("remark"));

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
	public List selectdetailBOM(BOMDTO bomDTO) {

		List<BOMDTO> list = new ArrayList();
		
		try (Connection conn = getConn();
			PreparedStatement ps = new LoggableStatement(conn, " SELECT * FROM ("
					+ "    SELECT ROWNUM AS rnum, b.*"
					+ "    FROM ("
					+ "        SELECT b.bom_key,"
					+ "               (SELECT i.item_name FROM tb_item i WHERE i.item_key = b.product_item_key) AS product_item_key, "
					+ "               (SELECT i.item_name FROM tb_item i WHERE i.item_key = b.material_item_key) AS material_item_key, "
					+ "               b.qty, "
					+ "               b.remark "
					+ "        FROM tb_bom b "
					+ "    WHERE 1=1 "
				    + "    AND ( ? = 0 OR b.bom_key = ? ) " 
				    + "    AND ( ? IS NULL OR " 
				    + "        (SELECT i.item_name FROM tb_item i WHERE i.item_key = b.product_item_key) LIKE '%' || ? || '%' " 
				    + "    OR (SELECT i.item_name FROM tb_item i WHERE i.item_key = b.material_item_key) LIKE '%' || ? || '%' " 
				    + " ) "
					+ "        ORDER BY b.bom_key "
					+ "    ) b "
					+ "    WHERE ROWNUM <= ? "
					+ ") "
					+ " WHERE rnum >= ?");
			) {
			
			ps.setInt(1, bomDTO.getKeycode());
			ps.setInt(2, bomDTO.getKeycode());
			ps.setString(3, bomDTO.getKeyword());
			ps.setString(4, bomDTO.getKeyword());
			ps.setString(5, bomDTO.getKeyword());
			ps.setInt(6, bomDTO.getEnd());
			ps.setInt(7, bomDTO.getStart());

			try (ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					BOMDTO dto = new BOMDTO();

					int bom_key = Integer.parseInt(rs.getString("bom_key"));
					dto.setBom_key(bom_key);
					
					
					dto.setProduct_item_key(rs.getString("product_item_key"));
					dto.setMaterial_item_key(rs.getString("material_item_key"));
					
							
					dto.setQTY(rs.getInt("qty"));
					
					dto.setRemark(rs.getString("remark"));

					list.add(dto);
				}
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 삽입
	public int insertBOM(BOMDTO bomDTO) {
		
		int result = -1;

		try (Connection conn = getConn();
			PreparedStatement ps = new LoggableStatement(conn,
						" insert into tb_bom " + " values(seq_bom.nextval, , , ?, ?); ");
		) {
			ps.setInt(1, bomDTO.getQTY());
			ps.setString(2, bomDTO.getRemark());

			System.out.println(((LoggableStatement)ps).getQueryString());

			result = ps.executeUpdate();
			System.out.println("insert된 열 : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 수정
	public int updateBOM(BOMDTO bomDTO) {
		
		int result = -1 ;
		
		try (Connection conn = getConn();
			 PreparedStatement ps = new LoggableStatement(conn, " update tb_bom "
						+ " set item_code = ?, "
						+ " item_count = ?, "
						+ " status = ?, "
						+ " code_id = ? "
						+ " where bom_key = ? ");
		) {
			ps.setString(1, bomDTO.getItem_code());
			
			
			System.out.println(((LoggableStatement) ps).getQueryString());
			
			result = ps.executeUpdate();
			System.out.println("update된 열 : " + result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 삭제
	public int deleteBOM (BOMDTO bomDTO) {
		
		int result = -1;
		
		try( Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement( conn , " delete tb_bom " + " where bom_key = ?");
				
		){
			ps.setInt(1, bomDTO.getBom_key());
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate();
			System.out.println("delete된 행 : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 페이징 용 전체 개수
	
	public int BOMTotal() {
		
		int totalCount=0;
		
		try( Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, "Select count(*) cnt From tb_bom");
		){
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			try(ResultSet rs = ps.executeQuery();){
				
				if(rs.next()) {
					totalCount = rs.getInt("cnt");
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}

	public int SearchTotal(BOMDTO dto) {
		
		int totalCount = 0;
		
		String keyword = dto.getKeyword();
		int keycode = dto.getKeycode();
		
		if(keyword == null) keyword = "";
		
		try( Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, "SELECT COUNT(*) cnt "
						+ " FROM tb_bom b "
						+ " JOIN tb_item p ON p.item_key = b.product_item_key "
						+ " JOIN tb_item m ON m.item_key = b.material_item_key "
						+ " WHERE  "
						+ "    p.item_name LIKE '%' || ? || '%' "
						+ " OR m.item_name LIKE '%' || ? || '%' ");
		){
			ps.setString(1, keyword);
			ps.setString(2, keyword);
			
			
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			try(ResultSet rs = ps.executeQuery();){
				if(rs.next()) {
					totalCount = rs.getInt("cnt");
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}

}
