package production;

public class ProductionSummaryDTO {

	private int total_plan_qty;
    private int total_order_qty;
    private int total_input_qty;
    private int total_good_qty;
    private int total_defect_qty;
    private double avg_achievement_rate;

    private String best_item_name;
    private double best_rate;

    private String low_item_name;
    private double low_rate;

    public int getTotal_plan_qty() {
        return total_plan_qty;
    }

    public void setTotal_plan_qty(int total_plan_qty) {
        this.total_plan_qty = total_plan_qty;
    }

    public int getTotal_order_qty() {
        return total_order_qty;
    }

    public void setTotal_order_qty(int total_order_qty) {
        this.total_order_qty = total_order_qty;
    }

    public int getTotal_input_qty() {
        return total_input_qty;
    }

    public void setTotal_input_qty(int total_input_qty) {
        this.total_input_qty = total_input_qty;
    }

    public int getTotal_good_qty() {
        return total_good_qty;
    }

    public void setTotal_good_qty(int total_good_qty) {
        this.total_good_qty = total_good_qty;
    }

    public int getTotal_defect_qty() {
        return total_defect_qty;
    }

    public void setTotal_defect_qty(int total_defect_qty) {
        this.total_defect_qty = total_defect_qty;
    }

    public double getAvg_achievement_rate() {
        return avg_achievement_rate;
    }

    public void setAvg_achievement_rate(double avg_achievement_rate) {
        this.avg_achievement_rate = avg_achievement_rate;
    }

    public String getBest_item_name() {
        return best_item_name;
    }

    public void setBest_item_name(String best_item_name) {
        this.best_item_name = best_item_name;
    }

    public double getBest_rate() {
        return best_rate;
    }

    public void setBest_rate(double best_rate) {
        this.best_rate = best_rate;
    }

    public String getLow_item_name() {
        return low_item_name;
    }

    public void setLow_item_name(String low_item_name) {
        this.low_item_name = low_item_name;
    }

    public double getLow_rate() {
        return low_rate;
    }

    public void setLow_rate(double low_rate) {
        this.low_rate = low_rate;
    }

	@Override
	public String toString() {
		return "ProductionSummaryDTO [total_plan_qty=" + total_plan_qty + ", total_order_qty=" + total_order_qty
				+ ", total_input_qty=" + total_input_qty + ", total_good_qty=" + total_good_qty + ", total_defect_qty="
				+ total_defect_qty + ", avg_achievement_rate=" + avg_achievement_rate + ", best_item_name="
				+ best_item_name + ", best_rate=" + best_rate + ", low_item_name=" + low_item_name + ", low_rate="
				+ low_rate + "]";
	}
	
}
