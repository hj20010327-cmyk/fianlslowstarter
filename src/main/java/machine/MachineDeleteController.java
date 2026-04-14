package machine;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;
import user.UserDTO;

@WebServlet("/machine/delete")
public class MachineDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/machine delete 실행");	
		// 한글 깨짐 방지 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		HttpSession session = request.getSession();
		LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");
		
		System.out.println("loginUser = " + loginUser);

		if (loginUser == null
				|| (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
			// 이거는 주소로 들어오는사람 막는용도
			response.sendRedirect(request.getContextPath() + "/machine");
			return;
		}
		
		// 체크된 key 배열 받기 
		String[] machineKeys = request.getParameterValues("machineKey");

		// 삭제 처리 
		if (machineKeys != null) {
			MachineService machineService = new MachineService();

			for (String key : machineKeys) {
				MachineDTO dto = new MachineDTO();
				dto.setMachineKey(Integer.parseInt(key));
				machineService.getdeletemachine(dto);
			}
		}
		// 목록으로 이동 
        response.sendRedirect("/slowstarter/machine");
	
	}

}
