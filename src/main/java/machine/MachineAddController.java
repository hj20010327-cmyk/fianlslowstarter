package machine;

import java.io.IOException;
import java.sql.Date;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/machine/add doPost ����");
		// ��û�� �ѱ� ���� ����
		request.setCharacterEncoding("utf-8");
		// ������ �ѱ� ���� ����
		response.setContentType("text/html; charset=utf-8;");

		// �Ķ���� Ȯ��
		int machineKey = Integer.parseInt(request.getParameter("machineKey"));
		String machineCode = request.getParameter("machineCode");
		String machineName = request.getParameter("machineName");
		int processKey = Integer.parseInt(request.getParameter("processKey"));
		String machineStatus = request.getParameter("machineStatus");
		String remark = request.getParameter("remark");
		Date buyDate = Date.valueOf(request.getParameter("buyDate")); 
		Date lastCheckDate = Date.valueOf(request.getParameter("lastCheckDate")); 
		Date Create_at = Date.valueOf(request.getParameter("Create_at")); 
		System.out.println("machineKey :" + machineKey);
		

		// DTO�� ���
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

		// service�� DTO�� ����
		MachineService machineService = new MachineService();
		int result = machineService.getaddmachine(dto);
		System.out.println("result : " + result);
		response.sendRedirect("/machine.jsp");
	}

}
