package report;

public class DailyReportDTO {

	private String workDay;
    private int planQty;
    private int orderQty;
    private int inputQty;
    private int goodQty;
    private int defectQty;
    private double achievementRate;

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getInputQty() {
        return inputQty;
    }

    public void setInputQty(int inputQty) {
        this.inputQty = inputQty;
    }

    public int getGoodQty() {
        return goodQty;
    }

    public void setGoodQty(int goodQty) {
        this.goodQty = goodQty;
    }

    public int getDefectQty() {
        return defectQty;
    }

    public void setDefectQty(int defectQty) {
        this.defectQty = defectQty;
    }

    public double getAchievementRate() {
        return achievementRate;
    }

    public void setAchievementRate(double achievementRate) {
        this.achievementRate = achievementRate;
    }
	
}
