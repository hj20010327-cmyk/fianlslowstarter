package dashboard;

import login.LoginDTO;

public class DashboardService {

	DashboardDAO dao = new DashboardDAO();

    public DashboardDTO getDashboard(LoginDTO loginUser) {
        return dao.selectDashboard(loginUser);
    }
}
