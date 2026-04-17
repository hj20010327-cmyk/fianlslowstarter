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
	
	public List searchList(String planCode, String status, String dueDate) {
	    PlanDAO dao = new PlanDAO();
	    return dao.searchList(planCode, status, dueDate);
	}

	public List selectPage(int startRow, int endRow) {
	    PlanDAO dao = new PlanDAO();
	    return dao.selectPage(startRow, endRow);
	}
	public int getTotalCount() {
		PlanDAO dao = new PlanDAO();
	    return dao.getTotalCount();
	}
	
	public List<PlanDTO> searchPage(int item_key, String status,String dueDate, int startRow, int endRow) {
	    PlanDAO dao = new PlanDAO();
	    return dao.searchPage(item_key, status, dueDate,startRow, endRow);
	}

	public int getSearchCount(int item_key, String status, String dueDate) {
	    PlanDAO dao = new PlanDAO();
	    return dao.getSearchCount(item_key, status, dueDate);
	}
	public List<PlanDTO> selectItemList() {
		PlanDAO dao = new PlanDAO();
		return dao.selectItemList();
	}
	
	
}
