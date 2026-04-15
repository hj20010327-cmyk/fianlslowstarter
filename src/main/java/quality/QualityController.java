package quality;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 404 에러 방지를 위해 호출 경로를 명확히 합니다.
@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        System.out.println("/quality do get 실행");

        // 1. 파라미터 수집
        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        // 2. 페이징 계산
        int page = 1;
        try {
            if (p_ != null && !p_.isEmpty()) page = Integer.parseInt(p_);
        } catch (NumberFormatException e) {
            page = 1;
        }
        
        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        // 3. 데이터 조회 (DB에서 TB_QUALITY 데이터를 가져옴)
        QualityService service = new QualityService();
        List<QualityDTO> list = service.selectPage(startRow, endRow, keyword);
        int totalCount = service.getTotalCount(keyword);
        System.out.println("totalCount" + totalCount);
        int totalPage = (int)Math.ceil((double)totalCount / pageSize);

        // [중요] JSP에서 사용할 데이터를 request 객체에 담습니다.
        // 여기서 "list"라는 이름이 quality.jsp의 <c:forEach items="${list}">와 같아야 합니다.
        request.setAttribute("list", list); 
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("keyword", keyword);
        
        // 4. JSP로 데이터와 함께 화면 전환
        // 경로에 /slowstarter가 포함되어 있다면 컨텍스트 경로를 확인해야 합니다.
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}