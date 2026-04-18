package stock;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stock")
public class StockController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword");
        String itemType = request.getParameter("itemType");

        if (keyword == null) keyword = "";
        if (itemType == null || itemType.trim().equals("")) itemType = "all";

        int page = 1;
        if (p_ != null && !p_.isEmpty()) {
            page = Integer.parseInt(p_);
        }

        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        StockService service = new StockService();

        // 목록 / 개수 / 품목목록 조회
        List<StockDTO> list = service.getList(startRow, endRow, keyword, itemType);
        int totalCount = service.getTotalCount(keyword, itemType);
        List<StockDTO> itemList = service.getItemList();

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("list", list);
        request.setAttribute("itemList", itemList);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("keyword", keyword);
        request.setAttribute("itemType", itemType);

        request.getRequestDispatcher("stock.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        String cmd = request.getParameter("cmd");
        StockService service = new StockService();

        try {
            if ("delete".equals(cmd)) {
                String[] stockKeys = request.getParameterValues("codes");

                if (stockKeys != null && stockKeys.length > 0) {
                    String ids = String.join(",", stockKeys);
                    service.remove(ids);
                }

            } else {
                String stockKeyStr = request.getParameter("stock_key");
                String lot = request.getParameter("lot");
                String currentQtyStr = request.getParameter("current_qty");
                String safeQtyStr = request.getParameter("safe_qty");
                String itemKeyStr = request.getParameter("item_key");

                int stock_key = (stockKeyStr != null && !stockKeyStr.isEmpty()) ? Integer.parseInt(stockKeyStr.trim()) : 0;
                int current_qty = (currentQtyStr != null && !currentQtyStr.isEmpty()) ? Integer.parseInt(currentQtyStr.trim()) : 0;
                int safe_qty = (safeQtyStr != null && !safeQtyStr.isEmpty()) ? Integer.parseInt(safeQtyStr.trim()) : 0;
                int item_key = (itemKeyStr != null && !itemKeyStr.isEmpty()) ? Integer.parseInt(itemKeyStr.trim()) : 0;

                StockDTO dto = new StockDTO();
                dto.setStock_key(stock_key);
                dto.setLot(lot);
                dto.setCurrent_qty(current_qty);
                dto.setSafe_qty(safe_qty);
                dto.setItem_key(item_key);

                if ("update".equals(cmd)) {
                    service.update(dto);
                } else {
                    service.register(dto);
                }
            }

            response.sendRedirect(request.getContextPath() + "/stock");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/stock?error=1");
        }
    }
}