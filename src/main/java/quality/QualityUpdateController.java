package quality;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quality/update")
public class QualityUpdateController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		int quality_key = Integer.parseInt(request.getParameter("quality_key"));
		String qc_status = request.getParameter("qc_status");

		QualityDTO dto = new QualityDTO();
		dto.setQuality_key(quality_key);
		dto.setQc_status(qc_status);

		QualityService service = new QualityService();
		service.getupdatquality(dto);

		response.sendRedirect(request.getContextPath() + "/quality.jsp");
	}

}