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
        	System.out.println("stock doGet 실행");
        request.setCharacterEncoding("utf-8");

        // 1. 페이지 및 검색어 처리
        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

     // StockController의 doGet 내부
        int page = 1;
        if (p_ != null && !p_.isEmpty()) {
            page = Integer.parseInt(p_);
        }

        // 품질관리와 동일한 페이징 계산 로직 추가
        int startRow = (page - 1) * 10 + 1;
        int endRow = page * 10;

        StockService service = new StockService();
        // 여기서 에러가 났던 것임! 인자를 3개로 맞춰줍니다.
        List<StockDTO> list = service.getList(startRow, endRow, keyword);

        request.setAttribute("list", list); 

        request.getRequestDispatcher("stock.jsp").forward(request, response);
    }
}