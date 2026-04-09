package plan;

import java.util.List;

public class PlanService {
	public List getList() {
		PlanDAO plandao = new PlanDAO();
		List list = plandao.selectAll();
		return list;
	}

	public int addplan(PlanDTO dto) {
		PlanDAO plandaoDAO = new PlanDAO();
		int a = plandaoDAO.insertPlan(dto);
		return a;
	}
	
	public int updateplan(PlanDTO dto) {
		PlanDAO plandaoDAO = new PlanDAO();
		int a = plandaoDAO.updatePlan(dto);
		return a;
	}
	
	public int deleteplan(int plankey) {
		PlanDAO plandaoDAO = new PlanDAO();
		int a = plandaoDAO.deletePlan(plankey);
		return a;
	}
}
