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
		String machineKeyStr = request.getParameter("machineKey");
		String machineCode = request.getParameter("machineCode");
		String machineName = request.getParameter("machineName");
		String processKeyStr = request.getParameter("processKey");
		String machineStatus = request.getParameter("machineStatus");
		System.out.println("수정 요청 machineStatus" + machineStatus);
		String buyDateStr = request.getParameter("buyDate");
		String lastCheckDateStr = request.getParameter("lastCheckDate");
		String remark = request.getParameter("remark");

		if (machineKeyStr == null || machineKeyStr.trim().equals("")) {
		    response.sendRedirect(request.getContextPath() + "/machine");
		    return;
		}

		if (machineCode == null || machineCode.trim().equals("")) {
		    response.sendRedirect(request.getContextPath() + "/machine");
		    return;
		}

		if (machineName == null || machineName.trim().equals("")) {
		    response.sendRedirect(request.getContextPath() + "/machine");
		    return;
		}

		if (processKeyStr == null || processKeyStr.trim().equals("")) {
		    response.sendRedirect(request.getContextPath() + "/machine");
		    return;
		}

		int machineKey = 0;
		int processKey = 0;
		Date buyDate = null;
		Date lastCheckDate = null;

		try {
		    machineKey = Integer.parseInt(machineKeyStr);
		    processKey = Integer.parseInt(processKeyStr);

		    if (buyDateStr != null && !buyDateStr.trim().equals("")) {
		        buyDate = Date.valueOf(buyDateStr);
		    }

		    if (lastCheckDateStr != null && !lastCheckDateStr.trim().equals("")) {
		        lastCheckDate = Date.valueOf(lastCheckDateStr);
		    }
		} catch (Exception e) {
		    response.sendRedirect(request.getContextPath() + "/machine");
		    return;
		}

		// DTO에 담기
		MachineDTO dto = new MachineDTO();
		dto.setMachineKey(machineKey);
		dto.setMachineCode(machineCode);
		dto.setMachineName(machineName);
		dto.setMachineStatus(machineStatus);
		dto.setBuyDate(buyDate);
		dto.setLastCheckDate(lastCheckDate);
		dto.setRemark(remark);
		dto.setProcessKey(processKey);

		// Service 호출
		MachineService machineservice = new MachineService();
		int count = machineservice.getupdatemachine(dto);
		System.out.println("count: " + count);

		// 목록으로 이동
		response.sendRedirect(request.getContextPath() + "/machine");

	}

}