package stock;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stockDelete")
public class stockDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 품질관리와 동일하게 doPost 방식으로 통일합니다.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 1. 한글 깨짐 방지 설정
        request.setCharacterEncoding("utf-8");

        // 2. 체크박스에서 선택된 ID 값들을 받음 (예: "1,3,5")
        // 품질관리처럼 파라미터 이름을 "codes"로 맞추거나 JSP의 name과 일치시킵니다.
        String codes = request.getParameter("codes");

        if (codes != null && !codes.isEmpty()) {
            StockService service = new StockService();
            // 3. 서비스의 삭제 로직 호출
            service.remove(codes);
        }

        // 4. 삭제 완료 후 목록 페이지로 리다이렉트
        // 품질관리처럼 컨텍스트 경로를 포함한 절대 경로로 보내는 것이 안전합니다.
        response.sendRedirect(request.getContextPath() + "/stock");
    }
}