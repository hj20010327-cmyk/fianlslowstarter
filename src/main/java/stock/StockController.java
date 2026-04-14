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
        System.out.println("stock doGet 실행");
        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        int page = 1;
        if (p_ != null && !p_.isEmpty()) {
            page = Integer.parseInt(p_);
        }

        int startRow = (page - 1) * 10 + 1;
        int endRow = page * 10;

        StockService service = new StockService();
        List<StockDTO> list = service.getList(startRow, endRow, keyword);
        int totalCount = service.getTotalCount(keyword); 
        
        request.setAttribute("totalCount", totalCount); 
        request.setAttribute("list", list); 
        request.getRequestDispatcher("stock.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        
        String cmd = request.getParameter("cmd"); // action 대신 cmd 파라미터 사용
        StockService service = new StockService();

        try {
            if ("delete".equals(cmd)) {
                String[] stockKeys = request.getParameterValues("codes");
                if (stockKeys != null && stockKeys.length > 0) {
                    // ID들을 "1,2,3" 형태로 합쳐서 DAO의 IN 절에 전달
                    String ids = String.join(",", stockKeys);
                    service.remove(ids); 
                }
            } else {
                // [오타 수정 완료] 변수명을 선언부와 일치시켰습니다.
                String stockKeyStr = request.getParameter("stock_key");
                String lot = request.getParameter("lot");
                String inQtyStr = request.getParameter("in_qty");
                String outQtyStr = request.getParameter("out_qty");
                String safeQtyStr = request.getParameter("safe_qty");
                String itemKeyStr = request.getParameter("item_key");
                
                // 변수명 일치 확인: stockKeyStr.trim() 등
                int stock_key = (stockKeyStr != null && !stockKeyStr.isEmpty()) ? Integer.parseInt(stockKeyStr.trim()) : 0;
                int in_qty = (inQtyStr != null && !inQtyStr.isEmpty()) ? Integer.parseInt(inQtyStr.trim()) : 0;
                int out_qty = (outQtyStr != null && !outQtyStr.isEmpty()) ? Integer.parseInt(outQtyStr.trim()) : 0;
                int safe_qty = (safeQtyStr != null && !safeQtyStr.isEmpty()) ? Integer.parseInt(safeQtyStr.trim()) : 0;
                int item_key = (itemKeyStr != null && !itemKeyStr.isEmpty()) ? Integer.parseInt(itemKeyStr.trim()) : 0;

                StockDTO dto = new StockDTO();
                dto.setStock_key(stock_key);
                dto.setLot(lot);
                dto.setIn_qty(in_qty);
                dto.setOut_qty(out_qty);
                dto.setSafe_qty(safe_qty);
                dto.setItem_key(item_key);
                dto.setCurrent_qty(in_qty - out_qty);

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