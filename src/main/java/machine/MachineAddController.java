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


@WebServlet("/machine/add")
public class MachineAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MachineAddController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/machine/add doPost ����");
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
//		int machineKey = Integer.parseInt(request.getParameter("machineKey"));
		String machineCode = request.getParameter("machineCode");
		String machineName = request.getParameter("machineName");
		int processKey = Integer.parseInt(request.getParameter("processKey"));
		String machineStatus = request.getParameter("machineStatus");
		String remark = request.getParameter("remark");
		Date buyDate = Date.valueOf(request.getParameter("buyDate"));
		Date lastCheckDate = Date.valueOf(request.getParameter("lastCheckDate"));
//		Date Create_at = Date.valueOf(request.getParameter("Create_at")); 
		System.out.println("machineCode :" + machineCode);

		// DTO에 담기
		MachineDTO dto = new MachineDTO();
//		  	dto.setMachineKey(machineKey);
		dto.setMachineCode(machineCode);
		dto.setMachineName(machineName);
		dto.setProcessKey(processKey);
		dto.setRemark(remark);
		dto.setMachineStatus(machineStatus);
		dto.setBuyDate(buyDate);
		dto.setLastCheckDate(lastCheckDate);
//		    dto.setCreatedAt(Create_at);

		// service 호출
		MachineService machineService = new MachineService();
		int result = machineService.getaddmachine(dto);
		System.out.println("result : " + result);

		// 목록 페이지 이동
		response.sendRedirect("/slowstarter/machine");
	}

}
