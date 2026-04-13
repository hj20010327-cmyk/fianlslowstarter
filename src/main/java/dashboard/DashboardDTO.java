package dashboard;

import java.util.List;

public class DashboardDTO {

	private int todayProductionQty;
	private int todayDefectQty;
	private int lowStockCount;
	private int workorderTotal;
	private int workorderInProgress;
	private int workorderWaiting;
	
	private double qualityPassRate;
	private double equipmentRunRate;
	
	private List<String> weekLabels;
	private List<Integer> weekProductionQtys;
	public int getTodayProductionQty() {
		return todayProductionQty;
	}
	public void setTodayProductionQty(int todayProductionQty) {
		this.todayProductionQty = todayProductionQty;
	}
	public int getTodayDefectQty() {
		return todayDefectQty;
	}
	public void setTodayDefectQty(int todayDefectQty) {
		this.todayDefectQty = todayDefectQty;
	}
	public int getLowStockCount() {
		return lowStockCount;
	}
	public void setLowStockCount(int lowStockCount) {
		this.lowStockCount = lowStockCount;
	}
	public int getWorkorderTotal() {
		return workorderTotal;
	}
	public void setWorkorderTotal(int workorderTotal) {
		this.workorderTotal = workorderTotal;
	}
	public int getWorkorderInProgress() {
		return workorderInProgress;
	}
	public void setWorkorderInProgress(int workorderInProgress) {
		this.workorderInProgress = workorderInProgress;
	}
	public int getWorkorderWaiting() {
		return workorderWaiting;
	}
	public void setWorkorderWaiting(int workorderWaiting) {
		this.workorderWaiting = workorderWaiting;
	}
	public double getQualityPassRate() {
		return qualityPassRate;
	}
	public void setQualityPassRate(double qualityPassRate) {
		this.qualityPassRate = qualityPassRate;
	}
	public double getEquipmentRunRate() {
		return equipmentRunRate;
	}
	public void setEquipmentRunRate(double equipmentRunRate) {
		this.equipmentRunRate = equipmentRunRate;
	}
	public List<String> getWeekLabels() {
		return weekLabels;
	}
	public void setWeekLabels(List<String> weekLabels) {
		this.weekLabels = weekLabels;
	}
	public List<Integer> getWeekProductionQtys() {
		return weekProductionQtys;
	}
	public void setWeekProductionQtys(List<Integer> weekProductionQtys) {
		this.weekProductionQtys = weekProductionQtys;
	}
	
	
	
	
}
