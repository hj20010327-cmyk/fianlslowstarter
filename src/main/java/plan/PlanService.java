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
	
	public List searchList(String planCode, String status) {
	    PlanDAO dao = new PlanDAO();
	    return dao.searchList(planCode, status);
	}

	public List selectPage(int startRow, int endRow) {
	    PlanDAO dao = new PlanDAO();
	    return dao.selectPage(startRow, endRow);
	}
	public int getTotalCount() {
		PlanDAO dao = new PlanDAO();
	    return dao.getTotalCount();
	}
	
	public List<PlanDTO> searchPage(String planCode, String status, int startRow, int endRow) {
	    PlanDAO dao = new PlanDAO();
	    return dao.searchPage(planCode, status, startRow, endRow);
	}

	public int getSearchCount(String planCode, String status) {
	    PlanDAO dao = new PlanDAO();
	    return dao.getSearchCount(planCode, status);
	}
	
	
}
