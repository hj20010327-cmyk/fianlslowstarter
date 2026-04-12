package product;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/productAdd")
public class ProductAddController extends HttpServlet {


    private ProductDAO dao = new ProductDAO();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {


        // 1. 한글 깨짐 방지 설정
        request.setCharacterEncoding("UTF-8");


        // 2. 파라미터 수집
        String itemCode = request.getParameter("itemCode");
        String itemName = request.getParameter("itemName");
        String spec = request.getParameter("spec");
        int price = Integer.parseInt(request.getParameter("price"));


        // 3. DTO 객체 담기
        ProductDTO dto = new ProductDTO();
        dto.setItemCode(itemCode);
        dto.setItemName(itemName);
        dto.setSpec(spec);
        dto.setPrice(price);


        // 4. DB 저장 실행
        int result = dao.insertProduct(dto);


        // 5. 등록 후 목록 페이지로 이동
        response.sendRedirect(request.getContextPath() + "/productList");
    }
}