package quality;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quality/update")
public class QualityUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        System.out.println("/quality/update doPost 실행");

        // 1. 한글 깨짐 방지 설정
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        try {
            // 2. 파라미터 받기 (JSP의 input name과 일치해야 합니다)
            String quality_code = request.getParameter("quality_code");
            String item_name = request.getParameter("item_name");
            int inspect_qty = Integer.parseInt(request.getParameter("inspect_qty"));
            String qc_status = request.getParameter("qc_status");
            
            // 날짜 파라미터 처리
            String dateStr = request.getParameter("inspect_date");
            Date inspect_date = (dateStr != null && !dateStr.isEmpty()) ? Date.valueOf(dateStr) : null;

            // 3. DTO 객체 생성 및 데이터 세팅
            QualityDTO dto = new QualityDTO();
            dto.setQuality_code(quality_code);
            dto.setItem_name(item_name);
            dto.setInspect_qty(inspect_qty);
            dto.setQc_status(qc_status);
            dto.setInspect_date(inspect_date);

            // 4. 서비스 객체 생성 및 업데이트 메서드 호출
            QualityService service = new QualityService();
            int result = service.getupdatquality(dto);

            System.out.println("업데이트 결과: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

       
        response.sendRedirect(request.getContextPath() + "/quality");
    }
}