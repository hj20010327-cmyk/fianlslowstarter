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

	// 커넥션 메소드 만듦
	private Connection getConn() {

		Connection conn = null;
		try {
			// JNDI 방식
			// context.xml 에 있는 DB 정보로 커넥션 풀 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(커넥션 풀로)=
			conn = dataFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 목록 전체 보기 리턴 리스트() - bom_key 가져오기
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
				// 결과 활용
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

	// 리스트 목록 하나만 가져오기 (나중에 상세보기용 - 수정,삭제 포함)
	public List<BOMDTO> selectOneBOM(int bom_key) {
		
		List<BOMDTO> list = new ArrayList<BOMDTO>();
		
		

		try (Connection conn = getConn();
			PreparedStatement ps = new LoggableStatement(conn, "select * from tb_bom where bom_key = ? ");
			) {
			ps.setInt(1, bom_key);

			try (ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					int product_item_key = rs.getInt("product_item_key");
					int material_item_key = rs.getInt("material_item_key");
					int QTY = rs.getInt("QTY");
					String remark = rs.getString("remark");
				}
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 데이터 삽입  - 0408 다시 해야함 
	public int insertBOM(BOMDTO bomDTO) {
		
		int result = -1;

		try (Connection conn = getConn();
			PreparedStatement ps = new LoggableStatement(conn,
						" insert into tb_bom " + " values(seq_bom.nextval, , , ?, ?); ");
		) {
			ps.setInt(1, bomDTO.getQTY());
			ps.setString(2, bomDTO.getRemark());
			

			System.out.println(((LoggableStatement) ps).getQueryString());

			result = ps.executeUpdate();
			System.out.println("insert 결과 : " + result);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 데이터 수정 
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
			System.out.println("update 결과 : " + result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 데이터 삭제 
	public int deleteBOM (BOMDTO bomDTO) {
		
		int result = -1;
		
		try( Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement( conn , " delete tb_bom " + " where bom_key = ?");
				
		){
			ps.setInt(1, bomDTO.getBom_key());
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate();
			System.out.println("delete 결과 : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 페이징 
	
	public int BOMTotal() {
		
		int totalCount=0;
		
		try( Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, "Select count(*) cnt From tb_bom");
		){
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			try(ResultSet rs = ps.executeQuery();){
				// 결과 활용
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
