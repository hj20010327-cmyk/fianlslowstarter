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
	public List selectAllBOM() {

		List<BOMDTO> list = new ArrayList();

		try (Connection conn = getConn(); 
			PreparedStatement ps = new LoggableStatement(conn, "select * from tb_bom");
			) {
			System.out.println(((LoggableStatement) ps).getQueryString());

			try (ResultSet rs = ps.executeQuery();) {
				// 결과 활용
				while (rs.next()) {
					BOMDTO dto = new BOMDTO();

					String bom_key = rs.getString("bom_key");
					dto.setBom_key(bom_key);

					dto.setItem_code(rs.getString("item_code"));
					dto.setItem_count(rs.getInt("item_count"));
					dto.setStatus(rs.getInt("status"));
					dto.setCode_id(rs.getString("code_id"));

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
	public BOMDTO selectOneBOM(String bom_key) {
		
		BOMDTO dto = null;

		try (Connection conn = getConn();
			PreparedStatement ps = new LoggableStatement(conn, "select * from tb_bom where bom_key = ? ");
			) {
			ps.setString(1, bom_key);

			try (ResultSet rs = ps.executeQuery();) {

				while (rs.next()) {
					dto = new BOMDTO();
					
					dto.setBom_key(rs.getString("bom_key"));
					dto.setItem_code(rs.getString("item_code"));
					dto.setItem_count(rs.getInt("item_count"));
					dto.setStatus(rs.getInt("status"));
					dto.setCode_id(rs.getString("code_id"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 데이터 삽입
	public int insertBOM(BOMDTO bomDTO) {
		
		int result = -1;

		try (Connection conn = getConn();
			PreparedStatement ps = new LoggableStatement(conn,
						" insert into tb_bom " + " values(('BOM'||lpad(seq_bom.nextval,4,'0'), null, ?, ?, null); ");
		) {
			ps.setInt(1, bomDTO.getItem_count());
			ps.setInt(2, bomDTO.getStatus());

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
			ps.setInt(2, bomDTO.getItem_count());
			ps.setInt(3, bomDTO.getStatus());
			ps.setString(4, bomDTO.getCode_id());
			ps.setString(5, bomDTO.getBom_key());
			
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
			ps.setString(1, bomDTO.getBom_key());
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate();
			System.out.println("delete 결과 : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
