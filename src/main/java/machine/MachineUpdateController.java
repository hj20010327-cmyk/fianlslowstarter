package machine;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/machine/update")
public class MachineUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/machine update do post 실행");
		
		// 한글 꺠짐 방지 
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

		// 파라미터 받기
		int machineKey = Integer.parseInt(request.getParameter("machineKey"));
		String machineCode = request.getParameter("machineCode");
		String machineName = request.getParameter("machineName");
		int processKey = Integer.parseInt(request.getParameter("processKey"));
		String machineStatus = request.getParameter("machineStatus");
		String remark = request.getParameter("remark");
		Date buyDate = Date.valueOf(request.getParameter("buyDate"));
		Date lastCheckDate = Date.valueOf(request.getParameter("lastCheckDate"));
//		Date Create_at = Date.valueOf(request.getParameter("Create_at")); 필요없어서 주석

		// DTO에 담기 
		MachineDTO dto = new MachineDTO();
		dto.setMachineKey(machineKey);
		dto.setMachineCode(machineCode);
		dto.setMachineName(machineName);
		dto.setProcessKey(processKey);
		dto.setRemark(remark);
		dto.setMachineStatus(machineStatus);
		dto.setBuyDate(buyDate);
		dto.setLastCheckDate(lastCheckDate);
//		dto.setCreatedAt(Create_at); 필요없음

		// Service 호출 
		MachineService machineservice = new MachineService();
		int count = machineservice.getupdatemachine(dto);
		System.out.println("count: "+ count);

		// 목록으로 이동 
		response.sendRedirect("/slowstarter/machine");

	}

}
