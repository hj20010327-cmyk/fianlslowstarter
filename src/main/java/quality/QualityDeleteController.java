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
    	
        request.setCharacterEncoding("utf-8");
        String[] codes = request.getParameterValues("quality_code");

        if (codes != null) {
            QualityService service = new QualityService();
            for (String code : codes) {
                QualityDTO dto = new QualityDTO();
                dto.setQuality_code(code);
                service.getdeletequality(dto);
            }
        }

        response.sendRedirect(request.getContextPath() + "/qualityList");
    }
}