package stock;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stock")
public class StockController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // =========================
    // 목록 조회
    // =========================
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        String p_ = request.getParameter("p");
        String lotKeyword = request.getParameter("lotKeyword");
        String itemType = request.getParameter("itemType");
        String itemCodeKeyword = request.getParameter("itemCodeKeyword");
        String itemNameKeyword = request.getParameter("itemNameKeyword");
        String errorMsg = request.getParameter("errorMsg");

        if (lotKeyword == null) lotKeyword = "";
        if (itemType == null || itemType.trim().equals("")) itemType = "all";
        if (itemCodeKeyword == null) itemCodeKeyword = "";
        if (itemNameKeyword == null) itemNameKeyword = "";
        if (errorMsg == null) errorMsg = "";

        int page = 1;
        if (p_ != null && !p_.isEmpty()) {
            page = Integer.parseInt(p_);
        }

        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        StockService service = new StockService();

        // 목록 / 개수
        List<StockDTO> list = service.getList(startRow, endRow, lotKeyword, itemType, itemCodeKeyword, itemNameKeyword);
        int totalCount = service.getTotalCount(lotKeyword, itemType, itemCodeKeyword, itemNameKeyword);

        // 드롭다운용 데이터
        List<StockDTO> itemList = service.getItemList();
        List<String> lotList = service.getLotList();

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("list", list);
        request.setAttribute("itemList", itemList);
        request.setAttribute("lotList", lotList);

        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);

        request.setAttribute("lotKeyword", lotKeyword);
        request.setAttribute("itemType", itemType);
        request.setAttribute("itemCodeKeyword", itemCodeKeyword);
        request.setAttribute("itemNameKeyword", itemNameKeyword);
        request.setAttribute("errorMsg", errorMsg);

        request.getRequestDispatcher("stock.jsp").forward(request, response);
    }

    // =========================
    // 등록 / 수정 / 삭제
    // =========================
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        String cmd = request.getParameter("cmd");
        StockService service = new StockService();

        try {
            // =========================
            // 삭제
            // =========================
            if ("delete".equals(cmd)) {
                String[] stockKeys = request.getParameterValues("codes");

                if (stockKeys != null && stockKeys.length > 0) {
                    String ids = String.join(",", stockKeys);
                    service.remove(ids);
                }

            } else {
                // =========================
                // 등록 / 수정 공통 파라미터
                // [수정] LOT 입력 제거
                // [수정] 재고수량 사용
                // =========================
                String stockKeyStr = request.getParameter("stock_key");
                String currentQtyStr = request.getParameter("current_qty");
                String safeQtyStr = request.getParameter("safe_qty");
                String itemKeyStr = request.getParameter("item_key");

                int stock_key = (stockKeyStr != null && !stockKeyStr.isEmpty()) ? Integer.parseInt(stockKeyStr.trim()) : 0;
                int current_qty = (currentQtyStr != null && !currentQtyStr.isEmpty()) ? Integer.parseInt(currentQtyStr.trim()) : 0;
                int safe_qty = (safeQtyStr != null && !safeQtyStr.isEmpty()) ? Integer.parseInt(safeQtyStr.trim()) : 0;
                int item_key = (itemKeyStr != null && !itemKeyStr.isEmpty()) ? Integer.parseInt(itemKeyStr.trim()) : 0;

                // 품목 선택 검사
                if (item_key <= 0) {
                    String msg = URLEncoder.encode("품목을 선택해주세요.", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
                }

                // 수량 검사
                if (current_qty < 0 || safe_qty < 0) {
                    String msg = URLEncoder.encode("수량은 0 이상만 입력할 수 있습니다.", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
                }

                StockDTO dto = new StockDTO();
                dto.setStock_key(stock_key);
                dto.setCurrent_qty(current_qty);
                dto.setSafe_qty(safe_qty);
                dto.setItem_key(item_key);

                int result = 0;

                // =========================
                // 수정
                // =========================
                if ("update".equals(cmd)) {
                    result = service.update(dto);
                    if (result <= 0) {
                        String msg = URLEncoder.encode("재고 수정 실패", "UTF-8");
                        response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                        return;
                    }
                } else {
                    // =========================
                    // 등록
                    // =========================
                    result = service.register(dto);
                    if (result <= 0) {
                        String msg = URLEncoder.encode("재고 등록 실패", "UTF-8");
                        response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                        return;
                    }
                }
            }

            response.sendRedirect(request.getContextPath() + "/stock");

        } catch (Exception e) {
            e.printStackTrace();
            String msg = URLEncoder.encode("재고 처리 중 오류가 발생했습니다.", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
        }
    }
}