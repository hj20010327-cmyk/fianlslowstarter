package machine;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/machine/delete")
public class MachineDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/machine delete ½ÇÇà");	
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String systemkey = (request.getParameter("systemkey"));
		
		MachineDTO machinedto = new MachineDTO();
		machinedto.setSystemKey(systemkey);

        MachineService 	machineService = new MachineService();
        machineService.getdeletemachine(machinedto);

//        response.sendRedirect("");
	
	}

}
