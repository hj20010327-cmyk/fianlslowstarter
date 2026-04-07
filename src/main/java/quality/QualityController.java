package quality;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 배운 내용: 브라우저에서 호출할 가상 주소 설정
@WebServlet("/qualityList")
public class QualityController extends HttpServlet {

    private QualityService service = new QualityService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 1. JSP 화면에서 보낸 검색어 파라미터 받기
        String searchCode = request.getParameter("searchCode");
        String searchName = request.getParameter("searchName");
        String searchResult = request.getParameter("searchResult");

        // 2. 서비스 객체에 일을 시켜서 DB 데이터(list) 가져오기
        List<QualityDTO> list = service.getQualityList(searchCode, searchName, searchResult);

        // 3. JSP 화면에서 쓸 수 있도록 request에 저장하기
        request.setAttribute("list", list);

        // 4. 품질관리 JSP 화면으로 데이터와 함께 이동하기 (포워드)
        request.getRequestDispatcher("/quality.jsp").forward(request, response);

    }
}