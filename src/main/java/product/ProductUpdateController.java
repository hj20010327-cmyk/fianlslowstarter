package product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/productUpdate")
public class ProductUpdateController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        
        ProductDTO dto = new ProductDTO();
        dto.setProduct_key(Integer.parseInt(request.getParameter("product_key")));
        dto.setItem_code(request.getParameter("item_code"));
        dto.setProduct_name(request.getParameter("product_name"));
        dto.setSpec(request.getParameter("spec"));
        dto.setUnit(request.getParameter("unit"));
        dto.setPrice(Integer.parseInt(request.getParameter("price").replace(",", "")));

        new ProductService().update(dto);
        response.sendRedirect(request.getContextPath() + "/product");
    }
}