package bom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import process.ProcessDTO;

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
			PreparedStatement ps = new LoggableStatement(conn, "SELECT * FROM ( SELECT rownum AS rnum, b.* "
					+ "    FROM (   SELECT b.bom_key, b.bom_code, b.qty, b.remark, b.item_key, b.parent_item_key, "
					+ "	 (SELECT i.item_name  FROM tb_item i  WHERE i.item_key = b.item_key) AS item_name, "
					+ "	 (SELECT i.item_name  FROM tb_item i WHERE i.item_key = b.parent_item_key) AS parent_item_name "
					+ "        FROM tb_bom b ORDER BY b.bom_key ) b "
					+ "    WHERE rownum <= ? "
					+ " ) "
					+ "WHERE rnum >= ?" );
					
			) {
			ps.setInt(1, bomDTO.getEnd());
			ps.setInt(2, bomDTO.getStart());
			
			System.out.println(((LoggableStatement) ps).getQueryString());

			try (ResultSet rs = ps.executeQuery();) {
				//결과 활용
				while (rs.next()) {
					BOMDTO dto = new BOMDTO();
					
					dto.setBom_key(rs.getInt("bom_key"));	
					dto.setBom_code(rs.getString("bom_code"));	
					dto.setQty(rs.getInt("qty"));
					dto.setRemark(rs.getString("remark"));
					dto.setItem_key(rs.getInt("item_key"));
					dto.setParent_item_key(rs.getInt("parent_item_key"));
					dto.setItem_name(rs.getString("item_name"));
					dto.setParent_item_name(rs.getString("parent_item_name"));

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
				PreparedStatement ps = new LoggableStatement(conn,
					   "SELECT * FROM ( "
					   + "    SELECT rownum AS rnum, b.* "
					   + "    FROM ( "
					   + "        SELECT b.bom_key, b.bom_code, b.qty, b.remark, "
					   + "               b.item_key, b.parent_item_key, "
					   + "               i.item_name AS item_name, "
					   + "               (SELECT i2.item_name "
					   + "                FROM tb_item i2 "
					   + "                WHERE i2.item_key = b.parent_item_key) AS parent_item_name "
					   + "        FROM tb_bom b "
					   + "        LEFT JOIN tb_item i ON b.item_key = i.item_key "
					   + "        WHERE b.parent_item_key = ? "
					   + "        ORDER BY b.bom_key "
					   + "    ) b "
					   + "    WHERE rownum <= ? "
					   + ") "
					   + "WHERE rnum >= ?"
					);
			) {
			
			
			ps.setInt(1, bomDTO.getParent_item_key());
			ps.setInt(2, bomDTO.getEnd());
			ps.setInt(3, bomDTO.getStart());

			try (ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					BOMDTO dto = new BOMDTO();

					dto.setBom_key(rs.getInt("bom_key"));	
					dto.setBom_code(rs.getString("bom_code"));	
					dto.setQty(rs.getInt("qty"));
					dto.setRemark(rs.getString("remark"));
					dto.setItem_key(rs.getInt("item_key"));
					dto.setParent_item_key(rs.getInt("parent_item_key"));
					dto.setItem_name(rs.getString("item_name"));
					dto.setParent_item_name(rs.getString("parent_item_name"));	

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
						"INSERT INTO tb_bom  "
						+ "VALUES ( "
						+ "    seq_bom.nextval, "
						+ "    'BOM-' || LPAD(seq_bom.currval, 3, '0'), "
						+ "    ?, "
						+ "    ?, "
						+ "    ?, "
						+ "    ? "
						+ ") ");
		) {
			ps.setInt(1, bomDTO.getQty());
			ps.setString(2, bomDTO.getRemark());
			ps.setInt(3, bomDTO.getItem_key());
			ps.setInt(4, bomDTO.getParent_item_key());

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
						+ " set bom_code = ?, "
						+ " qty = ?, "
						+ " remark = ?, "
						+ " item_key = ?, "
						+ " parent_item_key = ? "
						+ " where bom_key = ? ");
		) {
			ps.setString(1, bomDTO.getBom_code());
			ps.setInt(2, bomDTO.getQty());
			ps.setString(3, bomDTO.getRemark());
			ps.setInt(4, bomDTO.getItem_key());
			ps.setInt(5, bomDTO.getParent_item_key());
			ps.setInt(6, bomDTO.getBom_key());
			
		
			
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
						+ "FROM tb_bom b "
						+ "LEFT JOIN tb_item i ON b.item_key = i.item_key "
						+ "WHERE 1=1 "
						+ "  AND ( ? = 0 OR b.bom_key = ? ) "
						+ "  AND ( ? IS NULL OR i.item_name LIKE '%' || ? || '%' )");
		){
			ps.setInt(1, keycode);
			ps.setInt(2, keycode);
			ps.setString(3, keyword);
			ps.setString(4, keyword);
			
			
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
	
	
	// 모달창 완제품 중복 거르기 
		public List<BOMDTO> selectItemForBOM() {

		    List<BOMDTO> list = new ArrayList<>();

		    String sql =
		        "SELECT DISTINCT i.item_key, i.item_name " +
		        " FROM tb_item i " +
		        " left join tb_bom b " +
		        " on i.item_key = b.item_key " +   
		        " ORDER BY item_name";

		    try (Connection conn = getConn();
		         PreparedStatement ps = conn.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {

		        while (rs.next()) {
		            BOMDTO dto = new BOMDTO();
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
