package quality;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [최종 수정] 
 * 1. 주소 맵핑: /qualityList (브라우저 주소창에 .jsp 없이 입력)
 * 2. 한글 처리: UTF-8 설정 추가
 * 3. 서비스 호출: page, keyword, status 3개의 인자를 정확히 전달
 */
@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // [수정] 한글 깨짐 방지를 위한 인코딩 설정 (가장 상단에 위치)
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        	System.out.println("/qualityList");
        // [추가] JSP 화면에서 전달된 검색어(keyword), 상태(status), 페이지번호(p) 수집
        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");
        String p_ = request.getParameter("p");
        
        // [수정] 페이지 번호 기본값 설정 및 숫자 변환
        int page = 1; 
        if (p_ != null && !p_.equals("")) {
            page = Integer.parseInt(p_);
        }

        // [확인용] 이클립스 콘솔에 현재 어떤 값이 들어오는지 찍어보기
        System.out.println("컨트롤러 작동 중 -> 검색어: " + keyword + ", 상태: " + status + ", 페이지: " + page);

        // [핵심] 서비스 객체 생성 및 데이터 호출
        QualityService service = new QualityService();
        
        /**
         * [중요] Service의 getList가 (int, String, String) 형식을 받는지 반드시 확인하세요.
         * 만약 Service에서 빨간줄이 뜨면, Service 파일도 제가 이전에 드린 3인자 버전으로 수정해야 합니다.
         */
        List<QualityDTO> list = service.getList(page, keyword, status);

        // [결과 전달] DB에서 가져온 리스트를 'list'라는 이름으로 담아서 JSP로 보냄
        request.setAttribute("list", list);
        
        System.out.println("jsp보내기 이전");
        // [화면 이동] webapp 폴더 바로 아래에 있는 quality.jsp로 화면 전환
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}