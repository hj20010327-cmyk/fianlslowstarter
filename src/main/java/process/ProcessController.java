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

@WebServlet("/process.jsp")
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
		request.setAttribute(sPage, processservice);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process doPost 실행");
		
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
		System.out.println("/process insert ����");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// �Ķ���� Ȯ��
		String process_key = request.getParameter("process_key");
		System.out.println("process_key: " + process_key);
		
		// DTO �ֱ� 
		ProcessDTO processDTO = new ProcessDTO(); 
		processDTO.setProcess_key(process_key);
		
		// ���񽺷� DTO�� ���� 
		ProcessService processservice = new ProcessService(); 
		int result = processservice.insert(processDTO);
		
		System.out.println("insert�� rows : " + result);
		
		response.sendRedirect("");
		
	}
	
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/process update ����");
		
		// �Ķ���� Ȯ�� 
		String process_key = request.getParameter("process_key");
		String Sequence_no = request.getParameter("sequence_no");
		String work_desc = request.getParameter("work-desc");
		String process_note = request.getParameter("process_note");
		String code_id = request.getParameter("code_id");
		String system_key = request.getParameter("system_key");
		
		try {
			
			ProcessDTO processDTO = new ProcessDTO(); 
			
			processDTO.setProcess_key(process_key);
			
			int sequence_no = Integer.parseInt(Sequence_no);
			processDTO.setSequence_no(sequence_no);
			
			processDTO.setWork_desc(work_desc);
			processDTO.setProcess_note(process_note);
			processDTO.setSystem_key(system_key);
			
			ProcessService processservice = new ProcessService(); 
			int result = processservice.update(processDTO);
			
			System.out.println("update ��� : " + result);
			response.sendRedirect("");
			
			
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/process delete ����");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8;");
			
			ProcessDTO processDTO = new ProcessDTO();
			String process_key = request.getParameter("process_key");
			processDTO.setProcess_key(process_key);
			
			ProcessService processservice = new ProcessService(); 
			int result = processservice.delete(processDTO);
			
			response.sendRedirect("");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/process list ����");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		ProcessService processservice = new ProcessService();
		
		List<ProcessDTO> list = processservice.getList(); 
		request.setAttribute("list", list);
		
		System.out.println(list.size());
		
		RequestDispatcher rd = request.getRequestDispatcher("/process.jsp");
		rd.forward(request, response);
		
	}
	
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/process detail ����");
		
		try { 
			
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8;");
			
			String process_key = request.getParameter("process_key");
			System.out.println("process_key: " + process_key);
			
			// process_key �� service�� DAO ������ 
			ProcessService processservice = new ProcessService(); 
			ProcessDTO processDTO = processservice.getProcess(process_key);
			
			request.setAttribute("processDTO", processDTO);
			
			System.out.println(processDTO);
			
			request.getRequestDispatcher("").forward(request, response);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
