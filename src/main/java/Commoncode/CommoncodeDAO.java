package Commoncode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommoncodeDAO {
	
	private Connection getConn() {
		
		Connection conn = null; 
		try {
			Context ctx = new InitialContext(); 
			DataSource dataFactory = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
			
			conn = dataFactory.getConnection();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	} 
	
	public List selectAllCommoncode() {
		
		List<CommoncodeDTO> list = new ArrayList();
		try(Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, " select * from tb_commoncode");
				){
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			try(ResultSet rs = ps.executeQuery();){
				while (rs.next()) {
					CommoncodeDTO dto = new CommoncodeDTO();
					
					String code_key = rs.getString("code_key");
					dto.setCode_key(code_key);
					
					dto.setCode_group(rs.getString("code_group"));
					dto.setCode(rs.getString("code"));
					dto.setCode_name(rs.getString("code_name"));
					dto.setStatus(rs.getInt("status"));
					dto.setCreated_at(rs.getDate("created_at"));
					dto.setUpdated_at(rs.getDate("updated_at"));
					dto.setUser_key(rs.getString("user_key"));
					
					list.add(dto);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("DAO list.size: " + list.size());
		return list;
	}
	
	public CommoncodeDTO selectOneCommoncode(String code_key) {
		
		CommoncodeDTO dto = null; 
		
		try(Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, "select * from tb_commoncode where code_key = ? ");
				){
				ps.setString(1, code_key);
				
				try(ResultSet rs = ps.executeQuery();) {
					
					while (rs.next()) {
						
						dto = new CommoncodeDTO();
						
						dto.setCode_key(rs.getString("code_key"));
						dto.setCode_group(rs.getString("code_group"));
						dto.setCode(rs.getString("code"));
						dto.setCode_name(rs.getString("code_name"));
						dto.setCode_desc(rs.getString("code_desc"));
						dto.setStatus(rs.getInt("status"));
						dto.setCreated_at(rs.getDate("created_at"));
						dto.setUpdated_at(rs.getDate("updated_at"));
						
					}
				}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return dto;	
	}
	
	public int insertCommoncode(CommoncodeDTO commoncodeDTO) {
		
		int result = -1;
		
		try ( Connection conn = getConn(); 
			  PreparedStatement ps = new LoggableStatement(conn, "insert into tb_commoncode"
					  	+ " values(null, null, null, null, null, ?, ?, ?, ?, ?);");
		){
			ps.setInt(1, commoncodeDTO.getStatus());
			ps.setDate(2, commoncodeDTO.getCreated_at());
			ps.setDate(3, commoncodeDTO.getUpdated_at());
			ps.setString(4, commoncodeDTO.getUser_key());
			ps.setString(5, commoncodeDTO.getUser_key2());
			
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate(); 
			System.out.println("insert 결과 : " + result);
		
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
		return result;
	}
	
	public int updateCommoncode(CommoncodeDTO commoncodeDTO) {
		
		int result = -1; 
		
		try ( Connection conn = getConn(); 
			 PreparedStatement ps = new LoggableStatement(conn, "update tb_commoncode "
					 + " set status = ?, "
					 + " created_at = ?, "
					 + " updated_at = ?, "
					 + " user_key = ?, "
					 + " user_key2 = ? "
					 + " where code_key = ? ");
		){
			ps.setInt(1, commoncodeDTO.getStatus());
			ps.setDate(2, commoncodeDTO.getCreated_at());
			ps.setDate(3, commoncodeDTO.getUpdated_at());
			ps.setString(4, commoncodeDTO.getUser_key());
			ps.setString(5, commoncodeDTO.getUser_key2());
			ps.setString(6, commoncodeDTO.getCode_key());
			
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate();
			System.out.println("update 결과 : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteCommoncode(CommoncodeDTO commoncodeDTO) {
		
		int result = -1; 
		
		try ( Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, "delete tb_commoncode " 
							+ "  where code_key = ?"); 
		){
			ps.setString(1, commoncodeDTO.getCode_key());
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate(); 
			System.out.println("delete 결과 : " + result);
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		return result; 
	}

}
