package quality;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quality/add")
public class QualityAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("/quality/add doPost 실행");
		
		// 1. 인코딩 설정
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		try {
			// 2. 파라미터 확보 (JSP의 input name 태그와 일치해야 함)
			String quality_code = request.getParameter("quality_code");
			int inspect_qty = Integer.parseInt(request.getParameter("inspect_qty"));
			int good_qty = Integer.parseInt(request.getParameter("good_qty"));
			int defect_qty = Integer.parseInt(request.getParameter("defect_qty"));
			String defect_reason = request.getParameter("defect_reason");
			String qc_status = request.getParameter("qc_status");
			int prod_key = Integer.parseInt(request.getParameter("prod_key"));
			
			// 날짜 처리 (yyyy-mm-dd 형식)
			Date inspect_date = Date.valueOf(request.getParameter("inspect_date"));

			// 3. DTO에 데이터 담기
			QualityDTO dto = new QualityDTO();
			dto.setQuality_code(quality_code);
			dto.setInspect_qty(inspect_qty);
			dto.setGood_qty(good_qty);
			dto.setDefect_qty(defect_qty);
			dto.setDefect_reason(defect_reason);
			dto.setQc_status(qc_status);
			dto.setProd_key(prod_key);
			dto.setInspect_date(inspect_date);

			// 4. 서비스 호출 (메서드 이름을 getaddquality로 통일)
			QualityService service = new QualityService();
			int result = service.getaddquality(dto);

			if (result > 0) {
				System.out.println("등록 성공");
				// 등록 후 리스트 페이지로 이동 (QualityListController가 없으므로 jsp로 직접 이동)
				response.sendRedirect(request.getContextPath() + "/quality.jsp");
			} else {
				System.out.println("등록 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("파라미터 변환 또는 DB 등록 중 에러 발생");
		}
	}
}