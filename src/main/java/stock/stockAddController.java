package stock;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 재고 신규 등록을 처리하는 컨트롤러입니다.
 * URL: http://localhost:8080/프로젝트명/stockAdd
 */
@WebServlet("/stockAdd")
public class stockAddController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 한글 깨짐 방지 설정
        request.setCharacterEncoding("UTF-8");

        // 2. JSP 모달창 폼에서 넘어온 파라미터 받기
        String lot = request.getParameter("lot");
        int inQty = Integer.parseInt(request.getParameter("in_qty"));
        int outQty = Integer.parseInt(request.getParameter("out_qty"));
        int safeQty = Integer.parseInt(request.getParameter("safe_qty"));
        int itemKey = Integer.parseInt(request.getParameter("item_key"));

        // 3. DTO 객체에 데이터 담기
        StockDTO dto = new StockDTO();
        dto.setLot(lot);
        dto.setIn_qty(inQty);
        dto.setOut_qty(outQty);
        dto.setSafe_qty(safeQty);
        dto.setItem_key(itemKey);

        // 4. 서비스 호출하여 DB에 등록 실행
        StockService service = new StockService();
        service.register(dto);

        // 5. 등록 완료 후 다시 목록 페이지(stockList)로 이동
        response.sendRedirect("stockList");
    }
}