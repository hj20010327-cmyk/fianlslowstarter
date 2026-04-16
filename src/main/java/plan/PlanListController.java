package plan;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/plan")
public class PlanListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/plan doGet 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String planCode = request.getParameter("planCode");
		String status = request.getParameter("status");
		String dueDate = request.getParameter("dueDate");

		String pageStr = request.getParameter("page");
		int page = 1;
		if (pageStr != null) {
			page = Integer.parseInt(pageStr);
		}

		int pageSize = 10;
		int startRow = (page - 1) * pageSize + 1;
		int endRow = page * pageSize;

		PlanService service = new PlanService();
		List<PlanDTO> list;
		int totalCount = 0;

		if ((planCode == null || planCode.isEmpty())
		        && (status == null || status.isEmpty())
		        && (dueDate == null || dueDate.isEmpty())) {
		    list = service.selectPage(startRow, endRow);
		    totalCount = service.getTotalCount();
		} else {
		    list = service.searchPage(planCode, status,dueDate, startRow, endRow);
		    totalCount = service.getSearchCount(planCode, status, dueDate);
		}
		
		
	    int totalPage = (int) Math.ceil((double) totalCount / pageSize);
	    
	    List<PlanDTO> itemList = service.selectItemList();
	    
	    request.setAttribute("itemList", itemList);
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("planCode", planCode);
		request.setAttribute("status", status);
		request.setAttribute("dueDate", dueDate);
		request.setAttribute("totalPage", totalPage);
		

		request.getRequestDispatcher("/plan.jsp").forward(request, response);
	}
}