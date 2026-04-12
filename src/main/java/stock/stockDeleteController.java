package stock;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 선택된 재고 데이터를 삭제하는 컨트롤러입니다.
 */
@WebServlet("/stockDelete")
public class stockDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 체크박스에서 선택된 ID 값들을 콤마(,)로 연결해서 받음 (예: "1,3,5")
        String codes = request.getParameter("codes");

        if (codes != null && !codes.isEmpty()) {
            StockService service = new StockService();
            service.remove(codes);
        }

        // 삭제 후 목록으로 리다이렉트
        response.sendRedirect("stockList");
    }
}