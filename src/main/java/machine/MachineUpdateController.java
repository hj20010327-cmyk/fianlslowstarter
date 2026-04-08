package machine;

import java.io.IOException;
import java.sql.Date;

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

		// 파라메터 확보
		int machineKey = Integer.parseInt(request.getParameter("machineKey"));
		String machineCode = request.getParameter("machineCode");
		String machineName = request.getParameter("machineName");
		int processKey = Integer.parseInt(request.getParameter("processKey"));
		String machineStatus = request.getParameter("machineStatus");
		String remark = request.getParameter("remark");
		Date buyDate = Date.valueOf(request.getParameter("buyDate"));
		Date lastCheckDate = Date.valueOf(request.getParameter("lastCheckDate"));
		Date Create_at = Date.valueOf(request.getParameter("Create_at"));

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
		dto.setCreatedAt(Create_at);

		MachineService machineservice = new MachineService();
		int count = machineservice.getupdatemachine(dto);
		System.out.println("업데이트 결과" + count);

//		response.sendRedirect(systemstatus) 주소 적어야함 

	}

}
