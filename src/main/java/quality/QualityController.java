package quality;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [수정] 요청하신 대로 주소를 /qualityList로 변경했습니다.
@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 인코딩 설정
        request.setCharacterEncoding("utf-8");
        
        // [추가] JSP에서 보낸 페이지 번호(p)를 가져옵니다.
        String p_ = request.getParameter("p");
        int page = 1; // 기본값은 1페이지
        
        // 페이지 번호가 파라미터로 넘어왔을 경우에만 정수로 변환
        if (p_ != null && !p_.equals("")) {
            page = Integer.parseInt(p_);
        }
        
        QualityService service = new QualityService();
        
        // [수정] 서비스의 getList 메서드에 현재 페이지 번호를 전달합니다.
        // (주의: QualityService의 getList 메서드도 int 인자를 받도록 수정되어 있어야 합니다!)
        List<QualityDTO> list = service.getList(page);
        
        // 결과 리스트를 request에 담기
        request.setAttribute("list", list);
        
        // [유지] webapp 폴더 바로 아래의 quality.jsp로 화면 전환
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}