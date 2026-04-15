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

        // 1. 여러 개의 체크박스 값을 받기 위해 getParameterValues 사용
        String[] codes = request.getParameterValues("codes");

        // 2. 선택된 항목이 있는지 확인
        if (codes != null && codes.length > 0) {
            ProductService service = new ProductService();
            
            // 3. 서비스의 remove(String ids) 호출을 위해 배열을 "1,2,3" 형태의 문자열로 변환
            String ids = String.join(",", codes);
            
            // 4. 변환된 문자열로 삭제 실행
            service.remove(ids);
        }

        response.sendRedirect(request.getContextPath() + "/product");
    }
}