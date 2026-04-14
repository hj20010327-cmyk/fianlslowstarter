package stock;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stockUpdate")
public class stockUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 품질관리와 동일하게 doPost 방식으로 변경하여 보안성을 높입니다.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        System.out.println("/stockUpdate doPost 실행");

        // 1. 한글 깨짐 방지 설정
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        try {
            // 2. 파라미터 받기 (JSP의 input name과 일치해야 합니다)
            int stock_key = Integer.parseInt(request.getParameter("stock_key"));
            String lot = request.getParameter("lot");
            int in_qty = Integer.parseInt(request.getParameter("in_qty"));
            int out_qty = Integer.parseInt(request.getParameter("out_qty"));
            int safe_qty = Integer.parseInt(request.getParameter("safe_qty"));
            // 필요 시 item_key 등 추가

            // 3. DTO 객체 생성 및 데이터 세팅
            StockDTO dto = new StockDTO();
            dto.setStock_key(stock_key);
            dto.setLot(lot);
            dto.setIn_qty(in_qty);
            dto.setOut_qty(out_qty);
            dto.setSafe_qty(safe_qty);

            // 4. 서비스 객체 생성 및 업데이트 메서드 호출
            StockService service = new StockService();
            // 품질관리와 동일하게 결과를 int로 받도록 구성 가능합니다.
            int result = service.update(dto);

            System.out.println("재고 수정 결과: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. 작업 완료 후 목록 주소로 이동 (품질관리 스타일의 리다이렉트)
        response.sendRedirect(request.getContextPath() + "/stock");
    }
}