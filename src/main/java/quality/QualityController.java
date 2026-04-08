package quality;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 브라우저에서 /qualityList 주소로 들어오면 이 클래스가 실행됩니다.
@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qualityList (목록조회) 실행");
		
		// 1. 한글 깨짐 방지 설정
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// 2. 서비스 객체를 생성해서 데이터 목록을 가져옵니다.
		QualityService qualityService = new QualityService();
		List<QualityDTO> list = qualityService.getList();
		System.out.println(list.size());
		
		// 3. 가져온 목록을 JSP에서 쓸 수 있게 "list"라는 이름으로 담습니다.
		request.setAttribute("list", list);
		
		// 4. 품질관리 메인 화면(JSP)으로 이동합니다.
		request.getRequestDispatcher("/quality.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 목록 조지는 보통 GET 방식을 쓰므로 doGet으로 넘깁니다.
		doGet(request, response);
	}
}