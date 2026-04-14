package production;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductionSaveController
 */
@WebServlet("/productionsave")
public class ProductionSaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ProductionService service = new ProductionService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String cmd = request.getParameter("cmd");

        ProductionDTO dto = new ProductionDTO();

        dto.setProd_key(parseInt(request.getParameter("prod_key")));
        dto.setProd_code(request.getParameter("prod_code"));
        dto.setProd_date(request.getParameter("prod_date"));

        dto.setInput_qty(parseInt(request.getParameter("input_qty")));
        dto.setGood_qty(parseInt(request.getParameter("good_qty")));
        dto.setDefect_qty(parseInt(request.getParameter("defect_qty")));

        dto.setWork_order_key(parseInt(request.getParameter("work_order_key")));
        dto.setWork_user_key(parseInt(request.getParameter("work_user_key")));
        dto.setPlan_key(parseInt(request.getParameter("plan_key")));

        if ("update".equals(cmd)) {
            service.updateProduction(dto);
        } else {
            service.insertProduction(dto);
        }

        response.sendRedirect(request.getContextPath() + "/production");
    }

    private int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

}
