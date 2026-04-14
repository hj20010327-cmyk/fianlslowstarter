package product;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        System.out.println("product doGet 실행");
        // 1. 파라미터 수신
        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        // 2. 페이징 계산
        int page = 1;
        if (p_ != null && !p_.isEmpty()) page = Integer.parseInt(p_);

        int startRow = (page - 1) * 10 + 1;
        int endRow = page * 10;

        // 3. 데이터 로드 (ProductService와 연결)
        ProductService service = new ProductService();
        List<ProductDTO> list = service.getList(startRow, endRow, keyword);
        int totalCount = service.getTotalCount(keyword);
        
        // 4. JSP 데이터 전달 (이름 "list"로 고정)
        request.setAttribute("list", list);
        request.setAttribute("totalCount", totalCount);
        
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // 삭제/등록/수정 처리 로직 (생략 - 기존 로직 유지)
        doGet(request, response);
    }
}