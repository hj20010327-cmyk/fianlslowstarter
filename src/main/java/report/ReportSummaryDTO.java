package report;

public class ReportSummaryDTO {

	private int totalPlanQty;
	private int totalOrderQty;
	private int totalInputQty;
	private int totalGoodQty;
	private int totalDefectQty;
	private double achievementRate;
	private double yieldRate;
	private double defectRate;

	public int getTotalPlanQty() {
		return totalPlanQty;
	}

	public void setTotalPlanQty(int totalPlanQty) {
		this.totalPlanQty = totalPlanQty;
	}

	public int getTotalOrderQty() {
		return totalOrderQty;
	}

	public void setTotalOrderQty(int totalOrderQty) {
		this.totalOrderQty = totalOrderQty;
	}

	public int getTotalInputQty() {
		return totalInputQty;
	}

	public void setTotalInputQty(int totalInputQty) {
		this.totalInputQty = totalInputQty;
	}

	public int getTotalGoodQty() {
		return totalGoodQty;
	}

	public void setTotalGoodQty(int totalGoodQty) {
		this.totalGoodQty = totalGoodQty;
	}

	public int getTotalDefectQty() {
		return totalDefectQty;
	}

	public void setTotalDefectQty(int totalDefectQty) {
		this.totalDefectQty = totalDefectQty;
	}

	public double getAchievementRate() {
		return achievementRate;
	}

	public void setAchievementRate(double achievementRate) {
		this.achievementRate = achievementRate;
	}

	public double getYieldRate() {
		return yieldRate;
	}

	public void setYieldRate(double yieldRate) {
		this.yieldRate = yieldRate;
	}

	public double getDefectRate() {
		return defectRate;
	}

	public void setDefectRate(double defectRate) {
		this.defectRate = defectRate;
	}

}
