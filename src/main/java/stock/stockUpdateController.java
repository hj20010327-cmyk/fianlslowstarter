package stock;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 기존 재고 정보를 수정하는 컨트롤러입니다.
 * URL: http://localhost:8080/프로젝트명/stockUpdate
 */
@WebServlet("/stockUpdate")
public class stockUpdateController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // 수정 시에는 어떤 데이터를 고칠지 결정하는 'stock_key'가 반드시 필요함
        int stockKey = Integer.parseInt(request.getParameter("stock_key"));
        String lot = request.getParameter("lot");
        int inQty = Integer.parseInt(request.getParameter("in_qty"));
        int outQty = Integer.parseInt(request.getParameter("out_qty"));
        int safeQty = Integer.parseInt(request.getParameter("safe_qty"));

        StockDTO dto = new StockDTO();
        dto.setStock_key(stockKey);
        dto.setLot(lot);
        dto.setIn_qty(inQty);
        dto.setOut_qty(outQty);
        dto.setSafe_qty(safeQty);

        // 서비스의 update 메소드 호출
        StockService service = new StockService();
        service.update(dto);

        // 수정 후 목록으로 리다이렉트
        response.sendRedirect("stockList");
    }
}