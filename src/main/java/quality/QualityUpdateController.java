package quality;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/qualityUpdate")
public class QualityUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private QualityService qualityService = new QualityService();

    // 수정 페이지로 이동 (기존 데이터 조회)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quality_key = request.getParameter("quality_key");
        
        // DAO에서 특정 번호의 데이터 한 건을 가져오는 로직 (Service에 추가 필요)
        QualityDTO dto = qualityService.getOne(quality_key);
        
        request.setAttribute("dto", dto);
        // 수정 화면(JSP)으로 포워딩
        request.getRequestDispatcher("/quality_update_form.jsp").forward(request, response);
    }

    // 실제 데이터 수정 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 1. 파라미터 수집 (수정할 때는 PK인 quality_key가 반드시 있어야 함)
        String quality_key = request.getParameter("quality_key");
        int inspect_qty = Integer.parseInt(request.getParameter("inspect_qty"));
        int good_qty = Integer.parseInt(request.getParameter("good_qty"));
        int defect_qty = Integer.parseInt(request.getParameter("defect_qty"));
        String defect_reason = request.getParameter("defect_reason");
        String qc_status = request.getParameter("qc_status");

        // 2. DTO 담기
        QualityDTO dto = new QualityDTO();
        dto.setQuality_key(quality_key);
        dto.setInspect_qty(inspect_qty);
        dto.setGood_qty(good_qty);
        dto.setDefect_qty(defect_qty);
        dto.setDefect_reason(defect_reason);
        dto.setQc_status(qc_status);

        // 3. Service 호출
        int result = qualityService.update(dto);

        if (result > 0) {
            // 수정 성공 시 다시 목록으로
            response.sendRedirect("qualityList");
        } else {
            // 실패 시 에러 메시지와 함께 이전으로
            response.sendRedirect("qualityUpdate?quality_key=" + quality_key + "&error=1");
        }
    }
}