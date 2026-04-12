package product;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/productList") // 브라우저 접속 주소: /slowstarter/productList
public class ProductController extends HttpServlet {


    private ProductService service = new ProductService();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {


        // 1. 서비스에서 완제품 목록 가져오기
        List<ProductDTO> productList = service.getProductList();


        // 2. JSP로 데이터 전달
        request.setAttribute("productList", productList);


        // 3. product.jsp 화면으로 이동
        request.getRequestDispatcher("/WEB-INF/views/product/product.jsp").forward(request, response);
    }
}