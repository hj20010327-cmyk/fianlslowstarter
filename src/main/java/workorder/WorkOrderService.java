package workorder;

import java.util.List;

public class WorkOrderService {

	public List getList() {
		WorkOrderDAO dao = new WorkOrderDAO();
		List list = dao.selectAll();
		return list;
	}

	public int addWorkOrder(WorkOrderDTO dto) {
		WorkOrderDAO dao = new WorkOrderDAO();
		return dao.insertWorkOrder(dto);
	}

	public int updateWorkOrder(WorkOrderDTO dto) {
		WorkOrderDAO dao = new WorkOrderDAO();
		return dao.updateWorkOrder(dto);
	}

	public int deleteWorkOrder(int key) {
		WorkOrderDAO dao = new WorkOrderDAO();
		return dao.deleteWorkOrder(key);
	}
	
	public List searchList(String workOrderCode, String itemName , String workDate) {
	    WorkOrderDAO dao = new WorkOrderDAO();
	    return dao.searchList(workOrderCode, itemName, workDate);
	}

	public List selectPage(int startRow, int endRow) {
	    WorkOrderDAO dao = new WorkOrderDAO();
	    return dao.selectPage(startRow, endRow);
	}
	
	public int getTotalCount() {
	    WorkOrderDAO dao = new WorkOrderDAO();
	    return dao.getTotalCount();
	}
	public List selectPageByWorker(int userKey, int startRow, int endRow) {
	    WorkOrderDAO dao = new WorkOrderDAO();
	    return dao.selectPageByWorker(userKey, startRow, endRow);
	}
	
	public List selectWorkerList() {
	    WorkOrderDAO dao = new WorkOrderDAO();
	    return dao.selectWorkerList();
	}
	
	public List selectPlanList() {
	    WorkOrderDAO dao = new WorkOrderDAO();
	    return dao.selectPlanList();
	}
	public List selectItemList() {
		WorkOrderDAO dao = new WorkOrderDAO();
		return dao.selectItemList();
	}
	
	public List<WorkOrderDTO> searchPage(String workOrderCode, String itemName, String workDate, int startRow, int endRow) {
		WorkOrderDAO dao = new WorkOrderDAO();
		return dao.searchPage(workOrderCode, itemName, workDate, startRow, endRow);
	}

	public int getSearchCount(String workOrderCode, String itemName, String workDate) {
		WorkOrderDAO dao = new WorkOrderDAO();
		return dao.getSearchCount(workOrderCode, itemName, workDate);
	}
	
}