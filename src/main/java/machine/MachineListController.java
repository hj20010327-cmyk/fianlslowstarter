package machine;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/machine")
public class MachineListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/machine do Get 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
	    // 파라미터 받기
	    String name = request.getParameter("machineName");
	    String status = request.getParameter("machineStatus");
	    
	    String pageStr = request.getParameter("page");

		int page = 1;
		if(pageStr != null) {
		    page = Integer.parseInt(pageStr);
		}

		int pageSize = 5; // 한 페이지 5개
		int startRow = (page - 1) * pageSize + 1;
		int endRow = page * pageSize;

		
		MachineService service = new MachineService();
		List<MachineDTO> list;
		
		  // 2조건 여부에 따라 분기
	    if ((name == null || name.isEmpty()) && (status == null || status.isEmpty())) {
//	        // 검색 조건 없으면 전체조회
//	        list = service.getList();
	    	list = service.selectPage(startRow, endRow);
	    } else {
	        // 검색 조건 있으면 조건조회
	        list = service.searchList(name, status);
	    }
		
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		
		request.getRequestDispatcher("/machine.jsp").forward(request, response);
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}
