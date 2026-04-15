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

     // doPost 내부
        QualityDTO dto = new QualityDTO();
        dto.setQuality_code(request.getParameter("quality_code"));
        dto.setItem_code(request.getParameter("item_code")); // 추가
        dto.setItem_name(request.getParameter("item_name"));
        dto.setInspect_type(request.getParameter("inspect_type")); // 추가
        dto.setInspect_qty(Integer.parseInt(request.getParameter("inspect_qty")));
        dto.setQc_status(request.getParameter("qc_status"));
        dto.setInspector(request.getParameter("inspector")); // 추가
        dto.setInspect_date(Date.valueOf(request.getParameter("inspect_date")));
        dto.setRemarks(request.getParameter("remarks")); // 추가

        QualityService service = new QualityService();
        service.getaddquality(dto);

        response.sendRedirect(request.getContextPath() + "/quality");
    }
}