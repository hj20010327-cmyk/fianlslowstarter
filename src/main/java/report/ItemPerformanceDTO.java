package report;

public class ItemPerformanceDTO {

	 private String itemCode;
	    private String itemName;
	    private int planQty;
	    private int orderQty;
	    private int goodQty;
	    private int defectQty;
	    private double achievementRate;

	    public String getItemCode() {
	        return itemCode;
	    }

	    public void setItemCode(String itemCode) {
	        this.itemCode = itemCode;
	    }

	    public String getItemName() {
	        return itemName;
	    }

	    public void setItemName(String itemName) {
	        this.itemName = itemName;
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
