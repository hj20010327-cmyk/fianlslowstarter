package stock;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stockAdd")
public class stockAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        try {
            // 1. 파라미터 받기 (JSP의 input name과 똑같아야 함)
            String lot = request.getParameter("lot");
            int in_qty = Integer.parseInt(request.getParameter("in_qty"));
            int out_qty = Integer.parseInt(request.getParameter("out_qty"));
            int safe_qty = Integer.parseInt(request.getParameter("safe_qty"));
            int item_key = Integer.parseInt(request.getParameter("item_key"));

            // 2. DTO에 담기
            StockDTO dto = new StockDTO();
            dto.setLot(lot);
            dto.setIn_qty(in_qty);
            dto.setOut_qty(out_qty);
            dto.setSafe_qty(safe_qty);
            dto.setItem_key(item_key);

            // 3. 서비스 호출
            StockService service = new StockService();
            service.register(dto); // 여기서 에러가 났다면 위 Service 코드를 수정하면 해결됩니다.

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. 목록으로 이동 (품질관리와 동일한 리다이렉트 방식)
        response.sendRedirect(request.getContextPath() + "/stock");
    }
}