package report;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/report")
public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ReportService reportService = new ReportService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		if (startDate == null || "".equals(startDate.trim())) {
		    startDate = "2026-04-14";
		}

		if (endDate == null || "".equals(endDate.trim())) {
		    endDate = "2026-04-20";
		}

		try {
			ReportSummaryDTO summary = reportService.getSummary(startDate, endDate);
			List<DailyReportDTO> dailyList = reportService.getDailyReport(startDate, endDate);
			List<DefectReasonDTO> defectList = reportService.getDefectReasonReport(startDate, endDate);
			List<ItemPerformanceDTO> itemList = reportService.getItemPerformance(startDate, endDate);

			request.setAttribute("summary", summary);
			request.setAttribute("dailyList", dailyList);
			request.setAttribute("defectList", defectList);
			request.setAttribute("itemList", itemList);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "리포트 조회 중 오류가 발생했습니다.");
		}

		RequestDispatcher rd = request.getRequestDispatcher("/report.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
