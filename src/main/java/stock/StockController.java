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
    // - 검색 조건 / 페이징 / 모달용 데이터 조회
    // =========================
    @Override
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

        // =========================
        // 재고 목록 / 전체 개수
        // =========================
        List<StockDTO> list = service.getList(startRow, endRow, lotKeyword, itemType, itemCodeKeyword, itemNameKeyword);
        int totalCount = service.getTotalCount(lotKeyword, itemType, itemCodeKeyword, itemNameKeyword);

        // =========================
        // 검색용 전체 품목 목록
        // =========================
        List<StockDTO> itemList = service.getAllItemList();

        // =========================
        // 신규 등록용 품목 목록
        // - 현재 재고 테이블에 없는 품목만
        // =========================
        List<StockDTO> insertItemList = service.getInsertItemList();

        // =========================
        // LOT 검색 목록
        // =========================
        List<String> lotList = service.getLotList();

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        request.setAttribute("list", list);
        request.setAttribute("itemList", itemList);
        request.setAttribute("insertItemList", insertItemList);
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
    @Override
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

            } else if ("update".equals(cmd)) {
                // =========================
                // 수정
                // - 현재고 수량만 수정 가능
                // =========================
                String stockKeyStr = request.getParameter("stock_key");
                String currentQtyStr = request.getParameter("current_qty");

                int stock_key = (stockKeyStr != null && !stockKeyStr.isEmpty())
                        ? Integer.parseInt(stockKeyStr.trim()) : 0;
                int current_qty = (currentQtyStr != null && !currentQtyStr.isEmpty())
                        ? Integer.parseInt(currentQtyStr.trim()) : 0;

                if (stock_key <= 0) {
                    String msg = URLEncoder.encode("재고 번호가 올바르지 않습니다.", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
                }

                if (current_qty < 0) {
                    String msg = URLEncoder.encode("재고수량은 0 이상만 입력할 수 있습니다.", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
                }

                StockDTO dto = new StockDTO();
                dto.setStock_key(stock_key);
                dto.setCurrent_qty(current_qty);

                int result = service.update(dto);

                if (result <= 0) {
                    String msg = URLEncoder.encode("재고 수정 실패", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
                }

            } else {
                // =========================
                // 등록
                // - 사용자가 입력하는 값: 품목, 재고수량
                // - 안전재고는 TB_ITEM에서 사용
                // - LOT는 DAO에서 자동 생성
                // =========================
                String currentQtyStr = request.getParameter("current_qty");
                String itemKeyStr = request.getParameter("item_key");

                int current_qty = (currentQtyStr != null && !currentQtyStr.isEmpty())
                        ? Integer.parseInt(currentQtyStr.trim()) : 0;
                int item_key = (itemKeyStr != null && !itemKeyStr.isEmpty())
                        ? Integer.parseInt(itemKeyStr.trim()) : 0;

                if (item_key <= 0) {
                    String msg = URLEncoder.encode("품목을 선택해주세요.", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
                }

                if (current_qty < 0) {
                    String msg = URLEncoder.encode("재고수량은 0 이상만 입력할 수 있습니다.", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
                }

                StockDTO dto = new StockDTO();
                dto.setCurrent_qty(current_qty);
                dto.setItem_key(item_key);

                int result = service.register(dto);

                if (result <= 0) {
                    String msg = URLEncoder.encode("재고 등록 실패", "UTF-8");
                    response.sendRedirect(request.getContextPath() + "/stock?errorMsg=" + msg);
                    return;
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