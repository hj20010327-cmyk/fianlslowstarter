package workorder;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import login.LoginDTO;

@WebServlet("/workorder")
public class WorkOrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/workorder doGet 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String workOrderCode = request.getParameter("workOrderCode");
		String planKey = request.getParameter("planKey");
		String workDate = request.getParameter("workDate");
		String itemName = request.getParameter("itemName");
		String workUserKey = request.getParameter("workUserKey");

		String pageStr = request.getParameter("page");
		
		
		int page = 1;
		if (pageStr != null) {
			page = Integer.parseInt(pageStr);
		}

		int pageSize = 10;
		int startRow = (page - 1) * pageSize + 1;
		int endRow = page * pageSize;
		
		HttpSession session = request.getSession();
		LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");
	

		WorkOrderService service = new WorkOrderService();
		List<WorkOrderDTO> list;
		int totalCount = 0;

		if (loginUser != null && "작업자".equals(loginUser.getUser_role())) {
			list = service.selectPageByWorker(loginUser.getUser_key(), startRow, endRow);
		} else {
			if ((workOrderCode != null && !workOrderCode.isEmpty()) ||
				    (itemName != null && !itemName.isEmpty()) ||
				    (workDate != null && !workDate.isEmpty()) ||
				    (workUserKey != null && !workUserKey.isEmpty())) {
				list = service.searchPage(workOrderCode, itemName, workDate,workUserKey, startRow, endRow);
				totalCount = service.getSearchCount(workOrderCode, itemName, workDate,workUserKey);
			} else {
				list = service.selectPage(startRow, endRow);
				totalCount = service.getTotalCount();
			}
		}
		
		
		int totalPage = (int) Math.ceil((double) totalCount / pageSize);
		
		List<WorkOrderDTO> userList = service.selectWorkerList();
		List<WorkOrderDTO> planList = service.selectPlanList();
		List<WorkOrderDTO> itemList = service.selectItemList();
		List editPlanList = service.selectAllPlanList(); // 수정용
		
		request.setAttribute("editPlanList", editPlanList);
		request.setAttribute("itemList", itemList);
		request.setAttribute("planList", planList);
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("userList", userList);
		request.setAttribute("workOrderCode", workOrderCode);
		request.setAttribute("planKey", planKey);
		request.setAttribute("itemName", itemName);
		request.setAttribute("workDate", workDate);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("workUserKey", workUserKey);
		
	

		request.getRequestDispatcher("/workorder.jsp").forward(request, response);
	}
}