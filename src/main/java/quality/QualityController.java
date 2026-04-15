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
        // 디버깅용: 콘솔에서 현재 페이지 번호와 검색어를 확인합니다.
        System.out.println("===> QualityController 실행");

        // 1. 파라미터 수집
        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        // 2. 페이징 계산
        int page = 1;
        try {
            if (p_ != null && !p_.isEmpty()) {
                page = Integer.parseInt(p_);
            }
        } catch (NumberFormatException e) {
            page = 1;
        }
        
        int pageSize = 10;
        // Oracle ROWNUM 기반 페이징 계산
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        // 3. 데이터 조회
        QualityService service = new QualityService();
        
        // [중요] 여기서 예외 발생 시 에러 메시지를 확인해야 합니다.
        List<QualityDTO> list = service.selectPage(startRow, endRow, keyword);
        int totalCount = service.getTotalCount(keyword);
        
        System.out.println("조회된 데이터 수: " + (list != null ? list.size() : 0));
        System.out.println("전체 게시글 수(totalCount): " + totalCount);

        int totalPage = (int)Math.ceil((double)totalCount / pageSize);

        // 4. JSP 전달 데이터 설정
        // quality.jsp의 ${list}와 이름이 정확히 일치해야 합니다.
        request.setAttribute("list", list); 
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("keyword", keyword);
        
        // 5. JSP로 포워딩
        // 컨텍스트 루트가 설정되어 있다면 경로에 주의하세요. 
        // 보통 "/WEB-INF/views/quality.jsp" 형식을 쓰기도 하지만, 
        // 현재 구조에 맞춰 "/quality.jsp"로 유지합니다.
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}