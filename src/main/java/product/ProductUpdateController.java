package product;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/productUpdate")
public class ProductUpdateController extends HttpServlet { // P 대문자로 수정
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        try {
            ProductDTO dto = new ProductDTO();
            // product.jsp의 hidden input name인 product_key와 일치해야 함
            String keyStr = request.getParameter("product_key");
            if(keyStr != null && !keyStr.isEmpty()) {
                dto.setProduct_key(Integer.parseInt(keyStr));
            }
            
            dto.setProduct_name(request.getParameter("product_name"));
            dto.setSpec(request.getParameter("spec"));
            dto.setUnit(request.getParameter("unit"));
            dto.setRemarks(request.getParameter("remarks"));

            ProductService service = new ProductService();
            service.update(dto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/product");
    }
}