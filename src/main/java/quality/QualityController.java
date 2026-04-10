package quality;

import java.io.IOException;
import java.util.List;
import java.util.Map; // Map 사용을 위해 추가

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qualityList (목록조회) 실행");
		
		// 1. 한글 깨짐 방지 설정
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		// [추가] JSP 검색창에서 보낸 값 읽어오기
		String searchCode = request.getParameter("searchCode");
		String searchName = request.getParameter("searchName");
		
		/* [신규 추가] 2. 현재 페이지 번호 읽기 */
		String pStr = request.getParameter("page");
		int curPage = (pStr == null || pStr.isEmpty()) ? 1 : Integer.parseInt(pStr);
		int size = 5; // 한 페이지에 보여줄 게시물 개수

		// [수정] 페이징 범위 계산 (현재 페이지에 맞는 행 번호 계산)
		int startRow = (curPage - 1) * size + 1;
		int endRow = curPage * size;

		// 3. 서비스 객체를 생성해서 데이터 목록을 가져옵니다.
		QualityService qualityService = new QualityService();
		
		/* [기존 유지] 검색과 페이징이 가능한 메서드 호출 */
		List<QualityDTO> list = qualityService.getSearchList(searchCode, searchName, startRow, endRow);
		
		/* [신규 추가] 4. 하단 페이지네이션 번호 계산 정보 가져오기 */
		// 방금 Service에 추가한 Map<String, Object> 리턴 메서드 호출
		Map<String, Object> pageInfo = qualityService.getPageInfo(curPage, size);
		
		System.out.println("조회된 목록 개수: " + list.size());
		
		// 5. 데이터를 JSP로 전달
		request.setAttribute("list", list);
		
		/* [신규 추가] 하단 페이지 번호 출력용 데이터 전달 */
		request.setAttribute("p", pageInfo); 
		
		// [기존 유지] 검색어 보존
		request.setAttribute("searchCode", searchCode);
		request.setAttribute("searchName", searchName);
		
		// 6. 품질관리 메인 화면(JSP)으로 이동합니다.
		request.getRequestDispatcher("/quality.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}