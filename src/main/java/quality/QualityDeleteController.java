package quality;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quality/delete")
public class QualityDeleteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String[] codes = request.getParameterValues("quality_code");
        if (codes != null) {
            QualityService service = new QualityService();
            for (String code : codes) {
                service.getdeletequality(code); // String 전송
            }
        }
        response.sendRedirect(request.getContextPath() + "/quality");
    }
}