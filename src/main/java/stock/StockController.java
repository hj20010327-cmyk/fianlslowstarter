package stock;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/stockList") // 이 경로로 브라우저 접속시 실행됨
public class StockController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 페이지와 검색어 파라미터 받기
        int page = request.getParameter("p") == null ? 1 : Integer.parseInt(request.getParameter("p"));
        String keyword = request.getParameter("keyword");

        StockService service = new StockService();
        request.setAttribute("list", service.getList(page, keyword));

        // 재고관리 페이지(stock.jsp)로 이동
        request.getRequestDispatcher("/stock.jsp").forward(request, response);
    }
}