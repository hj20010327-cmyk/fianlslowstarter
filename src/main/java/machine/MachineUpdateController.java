package machine;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/machine/update")
public class MachineUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/machine update do post 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String systemkey = request.getParameter("systemkey");
		int systemcode = Integer.parseInt(request.getParameter("systemcode"));
		String systemname = request.getParameter("systemname");
		String systemstatus = request.getParameter("systemstatus");
		
		MachineDTO machineDTO = new MachineDTO();
		machineDTO.setSystemKey(systemkey);
		machineDTO.setSystemCode(systemcode);
		machineDTO.setSystemName(systemname);
		machineDTO.setSystemStatus(systemstatus);
		
		MachineService machineservice = new MachineService();
		int count = machineservice.getupdatemachine(machineDTO);
		System.out.println("업데이트 결과" + count);
		
//		response.sendRedirect(systemstatus) 주소 적어야함 
		
		
		
		
		
		
		
		
	}

}
