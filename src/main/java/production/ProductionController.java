package production;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/production")
public class ProductionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ProductionService productionService = new ProductionService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");

        if (startDate == null || "".equals(startDate.trim())) {
            startDate = "2026-04-14";
        }
        if (endDate == null || "".equals(endDate.trim())) {
            endDate = "2026-04-20";
        }
        if (keyword == null) {
            keyword = "";
        }
        if (status == null || "".equals(status.trim())) {
            status = "전체";
        }

        try {
            List<ProductionDTO> list = productionService.getProductionList(startDate, endDate, keyword, status);
            ProductionSummaryDTO summary = productionService.getProductionSummary(startDate, endDate, keyword, status);
            List<ProductionOptionDTO> optionList = productionService.getProductionOptions();
            
            request.setAttribute("list", list);
            request.setAttribute("summary", summary);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("keyword", keyword);
            request.setAttribute("status", status);
            request.setAttribute("optionList", optionList);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "생산실적 조회 중 오류가 발생했습니다.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/production.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
