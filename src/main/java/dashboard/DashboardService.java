package dashboard;

public class DashboardService {

	private DashboardDAO dao = new DashboardDAO();

	public DashboardDTO getDashboardData() {
		return dao.getDashboardData();
	}

}
