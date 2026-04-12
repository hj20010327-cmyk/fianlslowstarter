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

        QualityDTO dto = new QualityDTO();
        dto.setQuality_code(request.getParameter("quality_code"));
        dto.setItem_name(request.getParameter("item_name"));
        dto.setInspect_qty(Integer.parseInt(request.getParameter("inspect_qty")));
        dto.setQc_status(request.getParameter("qc_status"));
        dto.setInspect_date(Date.valueOf(request.getParameter("inspect_date")));

        QualityService service = new QualityService();
        service.getaddquality(dto);

        response.sendRedirect(request.getContextPath() + "/qualityList");
    }
}