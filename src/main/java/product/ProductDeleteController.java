package product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/productDelete")
public class ProductDeleteController extends HttpServlet { // P 대문자로 수정
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        // jsp에서 체크박스의 name이 "codes"인지 확인 필요
        String codes = request.getParameter("codes");

        if (codes != null && !codes.isEmpty()) {
            ProductService service = new ProductService();
            service.remove(codes);
        }

        response.sendRedirect(request.getContextPath() + "/product");
    }
}