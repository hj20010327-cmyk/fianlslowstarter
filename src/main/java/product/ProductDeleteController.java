package product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/productDelete")
public class ProductDeleteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String[] codes = request.getParameterValues("codes");
        
        if (codes != null && codes.length > 0) {
            String ids = String.join(",", codes);
            new ProductService().remove(ids);
        }
        response.sendRedirect(request.getContextPath() + "/product");
    }
}