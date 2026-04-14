package process;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/process")
public class ProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process doGet 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String skeycode = request.getParameter("keycode");
		String keyword = request.getParameter("keyword");
		
		// 페이징 
		int size = 5; 
		int page = 1; 
		int keycode = 0; 
		
		String sSize = request.getParameter("size");
		String sPage = request.getParameter("page");
		
		try {
			size = Integer.parseInt(sSize);
		} catch(Exception e) {}
		try {
			page = Integer.parseInt(sPage);
		} catch(Exception e) {}
		try {
			keycode = Integer.parseInt(skeycode);
		} catch(Exception e) {}
		
		ProcessDTO processDTO = new ProcessDTO();
		processDTO.setSize(size);
		processDTO.setPage(page);
		processDTO.setKeycode(keycode);
		processDTO.setKeyword(keyword);
		
		// 서비스 
		ProcessService processservice = new ProcessService(); 
		
		Map<String, Object> map = processservice.getPaging(processDTO);
		map.put("size", size);
		map.put("page", page);
		
		request.setAttribute("map",map);
		request.setAttribute("list", map.get("list"));
		
		System.out.println("sequence_no = " + request.getParameter("sequence_no"));
		
		RequestDispatcher rd = request.getRequestDispatcher("/process.jsp");
		rd.forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process doPost 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// form 요소 
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
		System.out.println("/process insert 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// 파라미터
		
		String process_code = request.getParameter("process_code");
		String process_name = request.getParameter("process_name");
		String Sequence = request.getParameter("sequence_no");
		int sequence_no = Integer.parseInt(Sequence);
		String process_desc = request.getParameter("process_desc");
		String status = request.getParameter("status");
		String Pitem_key = request.getParameter("item_key");
		int item_key = Integer.parseInt(Pitem_key);
		
		
		// DTO 실행
		ProcessDTO processDTO = new ProcessDTO(); 
		
		processDTO.setProcess_code(process_code);
		processDTO.setProcess_name(process_name);
		processDTO.setSequence_no(sequence_no);
		processDTO.setProcess_desc(process_desc);
		processDTO.setStatus(status);
		processDTO.setItem_key(item_key);
		
		// service & DTO 
		ProcessService processservice = new ProcessService(); 
		int result = processservice.insert(processDTO);
		
		System.out.println("insert된 rows : " + result);
		
		response.sendRedirect("/slowstarter/process");
		
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/process update 실행");
		
		// 파라미터
		String Process_key = request.getParameter("process_key");
		int process_key = Integer.parseInt(Process_key);
		String process_code = request.getParameter("process_code");
		String process_name = request.getParameter("process_name");
		String Sequence = request.getParameter("sequence_no");
		int sequence_no = Integer.parseInt(Sequence);
		String process_desc = request.getParameter("process_desc");
		String status = request.getParameter("status");
		String Pitem_key = request.getParameter("item_key");
		int item_key = Integer.parseInt(Pitem_key);
		
		try {
			
			ProcessDTO processDTO = new ProcessDTO(); 
			
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			processDTO.setProcess_code(process_code);
			processDTO.setProcess_name(process_name);
			processDTO.setSequence_no(sequence_no);
			processDTO.setProcess_desc(process_desc);
			processDTO.setStatus(status);
			processDTO.setItem_key(item_key);
			
			ProcessService processservice = new ProcessService(); 
			int result = processservice.update(processDTO);
			
			System.out.println("update된 열 : " + result);
			
			response.sendRedirect("");
			
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process delete 실행");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8;");
			
			String[] keys = request.getParameterValues("process_key");
			
			ProcessDTO dto = new ProcessDTO();
			
			if(keys != null) {
				
				ProcessService processservice = new ProcessService(); 
				
				for(String key : keys) {
					int process_key = Integer.parseInt(key);
					System.out.println("process_key : " + process_key);
					
					dto.setProcess_key(process_key);
					
					processservice.delete(dto);
				}
				int result = processservice.delete(dto);
			}
			response.sendRedirect("../slowstarter/process");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
