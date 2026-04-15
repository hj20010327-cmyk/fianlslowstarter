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

        request.setCharacterEncoding("utf-8");

        try {
            QualityDTO dto = new QualityDTO();
            
            // 1. 파라미터 수집 및 DTO 세팅
            dto.setQuality_code(request.getParameter("quality_code"));
            
            // 숫자로 변환할 때 값이 비어있으면 에러가 날 수 있으므로 체크
            String qtyStr = request.getParameter("inspect_qty");
            if (qtyStr != null && !qtyStr.isEmpty()) {
                dto.setQuality_qty(Integer.parseInt(qtyStr));
            }

            dto.setQc_status(request.getParameter("qc_status"));
            
            String dateStr = request.getParameter("inspect_date");
            if(dateStr != null && !dateStr.isEmpty()) {
                dto.setInspect_date(Date.valueOf(dateStr));
            }
            
            // JSP의 textarea 혹은 input name이 "remarks"인지 꼭 확인하세요!
            dto.setRemarks(request.getParameter("remarks"));

            // 2. 서비스 호출
            QualityService service = new QualityService();
            int result = service.getaddquality(dto); // 성공 여부를 숫자로 받으면 좋습니다.

            // 3. 목록으로 이동 (경로 주의!)
            // 보통 목록 페이지 주소가 "/quality"라면 아래처럼 수정해야 할 수도 있습니다.
            // 작성하신 코드의 /qualityList가 실제 서블릿 주소와 맞는지 확인하세요.
            response.sendRedirect(request.getContextPath() + "/quality");

        } catch (Exception e) {
            e.printStackTrace();
            // 에러 발생 시 사용자에게 알림을 주거나 에러 페이지로 이동
            response.sendRedirect(request.getContextPath() + "/quality/add?error=true");
        }
    }
}