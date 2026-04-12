package product;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/productDelete")
public class ProductDeleteController extends HttpServlet {


    private ProductDAO dao = new ProductDAO();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {


        // 1. 삭제할 제품의 Key값 가져오기
        int itemKey = Integer.parseInt(request.getParameter("itemKey"));


        // 2. DB 삭제 로직 수행
        dao.deleteProduct(itemKey);


        // 3. 삭제 후 목록으로 복귀
        response.sendRedirect(request.getContextPath() + "/productList");
    }
}