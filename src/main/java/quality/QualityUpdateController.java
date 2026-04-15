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

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        try {
            QualityDTO dto = new QualityDTO();
            dto.setQuality_code(request.getParameter("quality_code"));
            
            // setInspect_qty -> setQuality_qty로 수정
            dto.setQuality_qty(Integer.parseInt(request.getParameter("inspect_qty")));
            dto.setQc_status(request.getParameter("qc_status"));
            
            String dateStr = request.getParameter("inspect_date");
            if (dateStr != null && !dateStr.isEmpty()) {
                dto.setInspect_date(Date.valueOf(dateStr));
            }
            dto.setRemarks(request.getParameter("remarks"));

            QualityService service = new QualityService();
            int result = service.getupdatquality(dto);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/qualityList");
            } else {
                response.getWriter().println("<script>alert('수정 실패'); history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('에러 발생'); history.back();</script>");
        }
    }
}