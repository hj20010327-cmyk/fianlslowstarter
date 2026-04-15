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
		String machineName = request.getParameter("machineName");
		String buyDateStr = request.getParameter("buyDate");
		String remark = request.getParameter("remark");

		if (machineName == null || machineName.trim().equals("")) {
		    response.sendRedirect("/slowstarter/machine");
		    return;
		}

		Date buyDate = null;
		if (buyDateStr != null && !buyDateStr.trim().equals("")) {
		    buyDate = Date.valueOf(buyDateStr);
		}


		// DTO에 담기
		MachineDTO dto = new MachineDTO();
		dto.setMachineName(machineName);
		dto.setBuyDate(buyDate);
		dto.setRemark(remark);

		// 등록 시 기본값
		dto.setMachineStatus("점검중");
		dto.setLastCheckDate(null);

		// service 호출
		MachineService machineService = new MachineService();
		int result = machineService.getaddmachine(dto);
		System.out.println("result : " + result);

		// 목록 페이지 이동
		response.sendRedirect("/slowstarter/machine");
	}

}
