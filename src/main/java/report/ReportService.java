package report;

import java.util.List;

public class ReportService {

	private ReportDAO reportDAO = new ReportDAO();

	public ReportSummaryDTO getSummary(String startDate, String endDate) {
		return reportDAO.getSummary(startDate, endDate);
	}

	public List<DailyReportDTO> getDailyReport(String startDate, String endDate) {
		return reportDAO.getDailyReport(startDate, endDate);
	}

	public List<DefectReasonDTO> getDefectReasonReport(String startDate, String endDate) {
		return reportDAO.getDefectReasonReport(startDate, endDate);
	}

	public List<ItemPerformanceDTO> getItemPerformance(String startDate, String endDate) {
		return reportDAO.getItemPerformance(startDate, endDate);
	}
	
}
