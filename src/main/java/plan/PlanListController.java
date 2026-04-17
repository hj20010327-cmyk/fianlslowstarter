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

		String itemKeyStr = request.getParameter("item_key");
		int itemKey = 0;
		if (itemKeyStr != null && !itemKeyStr.trim().equals("")) {
			itemKey = Integer.parseInt(itemKeyStr);
		}

		String status = request.getParameter("status");
		String dueDate = request.getParameter("dueDate");

		String pageStr = request.getParameter("page");
		int page = 1;
		if (pageStr != null && !pageStr.trim().equals("")) {
			page = Integer.parseInt(pageStr);
		}

		int pageSize = 10;
		int startRow = (page - 1) * pageSize + 1;
		int endRow = page * pageSize;

		PlanService service = new PlanService();
		List<PlanDTO> list;
		int totalCount = 0;

		if (itemKey == 0
				&& (status == null || status.isEmpty())
				&& (dueDate == null || dueDate.isEmpty())) {
			list = service.selectPage(startRow, endRow);
			totalCount = service.getTotalCount();
		} else {
			list = service.searchPage(itemKey, status, dueDate, startRow, endRow);
			totalCount = service.getSearchCount(itemKey, status, dueDate);
		}

		int totalPage = (int) Math.ceil((double) totalCount / pageSize);

		List<PlanDTO> itemList = service.selectItemList();

		request.setAttribute("itemList", itemList);
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("item_key", itemKey);
		request.setAttribute("status", status);
		request.setAttribute("dueDate", dueDate);
		request.setAttribute("totalPage", totalPage);

		request.getRequestDispatcher("/plan.jsp").forward(request, response);
	}
}