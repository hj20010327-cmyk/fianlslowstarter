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

	    String prodKeyStr = request.getParameter("prod_key");
	    String prodCode = request.getParameter("prod_code");
	    String prodDate = request.getParameter("prod_date");
	    String workOrderKeyStr = request.getParameter("work_order_key");
	    String qualityKeyStr = request.getParameter("quality_key");

	    if (prodDate == null || prodDate.trim().isEmpty()
	            || workOrderKeyStr == null || workOrderKeyStr.trim().isEmpty()
	            || qualityKeyStr == null || qualityKeyStr.trim().isEmpty()) {
	        response.sendRedirect(request.getContextPath() + "/production?msg=fail");
	        return;
	    }

	    ProductionDTO dto = new ProductionDTO();
	    dto.setProd_key(parseIntSafe(prodKeyStr));
	    dto.setProd_code(prodCode);
	    dto.setProd_date(prodDate);
	    dto.setWork_order_key(parseIntSafe(workOrderKeyStr));
	    dto.setQuality_key(parseIntSafe(qualityKeyStr));

	    if ("update".equals(cmd)) {
	        service.updateProduction(dto);
	    } else {
	        service.insertProduction(dto);
	    }

	    response.sendRedirect(request.getContextPath() + "/production");
	}

	private int parseIntSafe(String str) {
	    return Integer.parseInt(str.trim());
	}

}
