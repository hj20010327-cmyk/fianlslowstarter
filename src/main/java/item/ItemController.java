package item;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/item")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ItemService itemService = new ItemService();

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");
        String itemType = request.getParameter("item_type");
        String pageStr = request.getParameter("page");

        if (keyword == null) keyword = "";
        if (status == null) status = "";
        if (itemType == null || itemType.trim().isEmpty()) {
            itemType = "";
        }

        int currentPage = 1;
        int pageSize = 5;

        try {
            if (pageStr != null && !pageStr.trim().isEmpty()) {
                currentPage = Integer.parseInt(pageStr);
            }
        } catch (Exception e) {
            currentPage = 1;
        }

        int startRow = (currentPage - 1) * pageSize + 1;
        int endRow = currentPage * pageSize;

        try {
            int totalCount = itemService.getItemCount(keyword, status, itemType);
            List<ItemDTO> itemList = itemService.getItemList(keyword, status, itemType, startRow, endRow);

            int totalPage = (int) Math.ceil((double) totalCount / pageSize);

            int activeCount = itemService.getItemCountByCondition(keyword, "", itemType, "", "Y");
            int inactiveCount = itemService.getItemCountByCondition(keyword, "", itemType, "", "N");

            int productCount = itemService.getItemCountByCondition(keyword, status, itemType, "완제품", "");
            int semiCount = itemService.getItemCountByCondition(keyword, status, itemType, "반제품", "");
            int materialCount = itemService.getItemCountByCondition(keyword, status, itemType, "자재", "");

            request.setAttribute("itemList", itemList);
            request.setAttribute("activeCount", activeCount);
            request.setAttribute("inactiveCount", inactiveCount);
            request.setAttribute("productCount", productCount);
            request.setAttribute("semiCount", semiCount);
            request.setAttribute("materialCount", materialCount);

            request.setAttribute("keyword", keyword);
            request.setAttribute("status", status);
            request.setAttribute("itemType", itemType);

            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalCount", totalCount);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "품목 목록 조회 중 오류가 발생했습니다.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/item.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
