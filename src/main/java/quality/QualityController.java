package quality;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        // 1. 페이지 번호 및 검색어 받기
        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword"); 
        if (keyword == null) keyword = ""; 

        int page = 1;
        if (p_ != null && !p_.isEmpty()) {
            page = Integer.parseInt(p_);
        }

        int pageSize = 5;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        // 2. 서비스 호출 (인자 3개: start, end, keyword)
        QualityService service = new QualityService();
        List<QualityDTO> list = service.selectPage(startRow, endRow, keyword);

        // 3. 데이터 설정
        request.setAttribute("list", list);
        
        // 4. quality.jsp로 이동
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}