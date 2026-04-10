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
}