package process;

import java.io.IOException;
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
		
		String cmd = request.getParameter("cmd");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process doPost 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String cmd = request.getParameter("cmd");
	}
	
	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process insert 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// 파라미터 확보
		String process_key = request.getParameter("process_key");
		System.out.println("process_key: " + process_key);
		
		// DTO 넣기 
		ProcessDTO processDTO = new ProcessDTO(); 
		processDTO.setProcess_key(process_key);
		
		// 서비스로 DTO를 보냄 
		ProcessService processservice = new ProcessService(); 
		int result = processservice.insert(processDTO);
		
		System.out.println("insert된 rows : " + result);
		
		response.sendRedirect("");
		
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/process update 실행");
		
		// 파라미터 확보 
		String process_key = request.getParameter("process_key");
		String sequence_no = request.getParameter("sequence_no");
		String work_desc = request.getParameter("work-desc");
		String process_note = request.getParameter("process_note");
		String code_id = request.getParameter("code_id");
		String system_key = request.getParameter("system_key");
		
		try {
			
			ProcessDTO processDTO = new ProcessDTO(); 
			
			processDTO.setProcess_key(process_key);
			processDTO.setSequence_no(sequence_no);
			processDTO.setWork_desc(work_desc);
			processDTO.setProcess_note(process_note);
			processDTO.setSystem_key(system_key);
			
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process delete 실행");
	}
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process list 실행");
	}
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process detail 실행");
	}

}
