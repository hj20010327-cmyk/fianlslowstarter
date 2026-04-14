package product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/productAdd")
public class ProductAddController extends HttpServlet { // P 대문자로 수정
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        try {
            ProductDTO dto = new ProductDTO();
            dto.setProduct_name(request.getParameter("product_name"));
            dto.setSpec(request.getParameter("spec"));
            dto.setUnit(request.getParameter("unit"));
            dto.setRemarks(request.getParameter("remarks"));

            ProductService service = new ProductService();
            service.register(dto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/product");
    }
}