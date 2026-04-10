package quality;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quality/delete")
public class QualityDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("/quality/delete doPost 실행");
        
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        try {
            // 1. JSP의 삭제 버튼이나 폼에서 보낸 'quality_key'를 받음
            String paramKey = request.getParameter("quality_key");
            
            if (paramKey != null && !paramKey.isEmpty()) {
                int quality_key = Integer.parseInt(paramKey);

                // 2. DTO에 담기
                QualityDTO dto = new QualityDTO();
                dto.setQuality_key(quality_key);

                // 3. 서비스 호출 (Service에 getdeletequality가 있어야 함)
                QualityService service = new QualityService();
                service.getdeletequality(dto);
                
                System.out.println("삭제 처리 완료 키: " + quality_key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. 목록으로 이동 (QualityListController가 없으므로 jsp로 리다이렉트)
        response.sendRedirect(request.getContextPath() + "/quality.jsp");
    }
}