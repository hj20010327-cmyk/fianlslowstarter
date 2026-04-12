package product;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/productUpdate")
public class ProductUpdateController extends HttpServlet {


    private ProductDAO dao = new ProductDAO();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");


        // 1. 수정을 위한 고유 키 및 변경 데이터 수집
        int itemKey = Integer.parseInt(request.getParameter("itemKey"));
        String itemName = request.getParameter("itemName");
        int price = Integer.parseInt(request.getParameter("price"));
        String status = request.getParameter("status");


        ProductDTO dto = new ProductDTO();
        dto.setItemKey(itemKey);
        dto.setItemName(itemName);
        dto.setPrice(price);
        dto.setStatus(status);


        // 2. DB 업데이트 실행
        dao.updateProduct(dto);


        // 3. 수정 완료 후 목록으로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/productList");
    }
}