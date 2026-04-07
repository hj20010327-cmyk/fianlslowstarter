package machine;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/machine/add")
public class MachineAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MachineAddController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/todo/add doPost 실행");
		// 요청의 한글 깨짐 방지
		request.setCharacterEncoding("utf-8");
		// 응답의 한글 깨짐 방지
		response.setContentType("text/html; charset=utf-8;");
		
		// 파라메터 확보
		int systemcode = Integer.parseInt(request.getParameter("systemcode"));
		String systemkey = request.getParameter("systemkey");
		String systemname = request.getParameter("systemname");
		String systemstatus = request.getParameter("systemstatus");
		System.out.println("systemstatus: "+ systemstatus);
		
		// DTO에 담기 
		MachineDTO machineDTO = new MachineDTO();
		machineDTO.setSystemCode(systemcode);
		machineDTO.setSystemKey(systemkey);
		machineDTO.setSystemName(systemname);
		machineDTO.setSystemStatus(systemstatus);
		
		
		// service로 DTO를 보냄
		MachineService machineService = new MachineService();
		int result = machineService.getaddmachine(machineDTO);
		System.out.println("result : "+ result);
	}

}
