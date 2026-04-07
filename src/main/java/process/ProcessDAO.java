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
			DataSource dataFactory = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
			
			conn = dataFactory.getConnection();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public List selectAllProcess() {
		
		List<ProcessDTO> list = new ArrayList();
		
		try( Connection conn = getConn();
			 PreparedStatement ps = new LoggableStatement(conn, " select * from tb_process");		
		){
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			try(ResultSet rs = ps.executeQuery();) {
				while(rs.next()) {
					ProcessDTO dto = new ProcessDTO();
					
					String process_key = rs.getString("process_key");
					dto.setProcess_key(process_key);
					
					dto.setSequence_no(rs.getInt("sequence_no"));
					dto.setWork_desc(rs.getString("work_desc"));
					dto.setProcess_note(rs.getString("process_note"));
					dto.setCode_id(rs.getString("code_id"));
					dto.setSystem_key(rs.getString("system_key"));
					
					list.add(dto);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ProcessDTO selectOneProcess(String process_key) {
		
		ProcessDTO dto = null;
		
		try( Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn, "select * from tb_process where process_key = ? ");
				
				){
			ps.setString(1, process_key);
			
			try(ResultSet rs = ps.executeQuery(); ){
				while (rs.next()) {
					dto = new ProcessDTO(); 
					
					dto.setProcess_key(rs.getString("process_key"));
					dto.setSequence_no(rs.getInt("sequence_no"));
					dto.setWork_desc(rs.getString("work_desc"));
					dto.setProcess_note(rs.getString("process_note"));
					dto.setCode_id(rs.getString("code_id"));
					dto.setSystem_key(rs.getString("system_key"));
					
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		return dto;
	}
	
	public int insertProcess(ProcessDTO processDTO) {
		
		int result = -1; 
		
		try ( Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, "insert into tb_process"
						+ " values(null, seq_process.nextval, null, null, ?, ? );")
		){
			ps.setString(1, processDTO.getCode_id());
			ps.setString(2, processDTO.getSystem_key());
			
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate(); 
			System.out.println("insert 결과 : " + result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result; 
		
	}
	
	public int updateProcess(ProcessDTO processDTO) {
		
		int result = -1; 
		
		try ( Connection conn = getConn();
				PreparedStatement ps = new LoggableStatement(conn, "update tb_process "
						+ " set code_id = ?, "
						+ " system_key = ?");
				) {
			
			ps.setString(1, processDTO.getCode_id());
			ps.setString(2, processDTO.getSystem_key());
			
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate(); 
			System.out.println("update 결과 : " + result);
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		return result;
	}
	
	public int deleteProcess(ProcessDTO processDTO) {
		
		int result = -1; 
		
		try ( Connection conn = getConn(); 
				PreparedStatement ps = new LoggableStatement(conn, "delete tb_process"
						+ " where process_key = ?");
		){
			ps.setString(1, processDTO.getProcess_key());
			System.out.println(((LoggableStatement)ps).getQueryString());
			
			result = ps.executeUpdate();
			System.out.println("delete 결과 : " + result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result; 
	}

}
