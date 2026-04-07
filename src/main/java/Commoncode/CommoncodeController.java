package Commoncode;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/commoncode")
public class CommoncodeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/commoncode doGet 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String cmd = request.getParameter("cmd");
		
		if(cmd.equals("list")) {
			list(request,response);
		} else if(cmd.equals("detail")) {
			detail(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/commoncode doPost 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String cmd = request.getParameter("cmd");
		
		if(cmd.equals("insert")) {
			insert(request,response);
		} else if(cmd.equals("update")) {
			update(request, response);
		} else if(cmd.equals("delete")) {
			delete(request, response);
		}
	}
	
	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/commoncode insert 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// 파라미터 확보
		String code_key = request.getParameter("code_key");
		System.out.println("code_key: " + code_key);
		
		// DTO 넣기 
		CommoncodeDTO commoncodeDTO = new CommoncodeDTO();
		commoncodeDTO.setCode_key(code_key);
		
		// 서비스로 DTO를 보냄
		CommoncodeService commoncodeservice = new CommoncodeService(); 
		int result = commoncodeservice.insert(commoncodeDTO);
		
		System.out.println("insert된 rows : " + result);
		
		response.sendRedirect("");
		
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/commoncode update 실행");
		
		// 파라미터 확보
		String code_key = request.getParameter("code_key");
		String code_group = request.getParameter("code_group");
		String code = request.getParameter("code");
		String code_name = request.getParameter("code_name");
		String code_desc = request.getParameter("code_desc");
		String Status = request.getParameter("status");
		
		String Created_at = request.getParameter("created_at");
		String Updated_at = request.getParameter("updated_at");
		String user_key = request.getParameter("user_key");
		String user_key2 = request.getParameter("user_key2");
		
		try {
			CommoncodeDTO commoncodeDTO = new CommoncodeDTO();
			
			commoncodeDTO.setCode_key(code_key);
			commoncodeDTO.setCode_group(code_group);
			commoncodeDTO.setCode(code);
			commoncodeDTO.setCode_name(code_name);
			commoncodeDTO.setCode_desc(code_desc);
			
			int status = Integer.parseInt(Status);
			commoncodeDTO.setStatus(status);
			
			Date created_at = Date.valueOf(Created_at);
			commoncodeDTO.setCreated_at(created_at);
			
			Date updated_at = Date.valueOf(Updated_at);
			commoncodeDTO.setUpdated_at(updated_at);
			
			commoncodeDTO.setUser_key(user_key);
			commoncodeDTO.setUser_key2(user_key2);
			
			CommoncodeService commoncodeservice = new CommoncodeService(); 
			int result = commoncodeservice.update(commoncodeDTO);
			
			System.out.println("update 결과 : " + result);
			
			response.sendRedirect("");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/commoncode delete 실행");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8;");
			
			CommoncodeDTO commoncodeDTO = new CommoncodeDTO();
			String code_key = request.getParameter("code_key");
			commoncodeDTO.setCode_key(code_key);
			
			CommoncodeService commoncodeservice = new CommoncodeService(); 
			int result = commoncodeservice.delete(commoncodeDTO);
			
			response.sendRedirect("");
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/commoncode list 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// 서비스 연결 
		CommoncodeService commoncodeservice = new CommoncodeService(); 
		
		List<CommoncodeDTO> list = commoncodeservice.getList(); 
		request.setAttribute("list",list);
		
		System.out.println(list.size());
		
		request.getRequestDispatcher("").forward(request, response);
	}
	
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/commoncode detail 실행");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8;");
			
			String code_key = request.getParameter("code_key");
			System.out.println("code_key: " + code_key);
			
			// code_key를 service랑 DAO  보내기 
			CommoncodeService commoncodeservice = new CommoncodeService(); 
			CommoncodeDTO commoncodeDTO = commoncodeservice.getcommoncode(code_key);
			
			request.setAttribute("commoncodeDTO", commoncodeDTO);
			
			System.out.println(commoncodeDTO);
			
			request.getRequestDispatcher("").forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
