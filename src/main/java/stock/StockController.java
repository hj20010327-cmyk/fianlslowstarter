package stock;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stock") // 브라우저 접속 주소
public class StockController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 페이지 번호 파라미터 처리 (품질관리와 동일 로직)
        int page = 1;
        String pStr = request.getParameter("p");
        if (pStr != null && !pStr.isEmpty()) {
            page = Integer.parseInt(pStr);
        }
        
        // 2. 검색어 처리
        String keyword = request.getParameter("keyword");

        // 3. 재고 서비스 호출 (품질관리 대신 StockService 사용)
        StockService service = new StockService();
        List<StockDTO> list = service.getList(page, keyword);

        // 4. JSP에 데이터 전달
        request.setAttribute("list", list);
        request.setAttribute("currentPage", page);

        // 5. stock.jsp 화면으로 포워딩
        request.getRequestDispatcher("stock.jsp").forward(request, response);
    }
}