package production;

public class ProductionDTO {

	private int prod_key;
	private String prod_code;
	private String prod_date;

	private int good_qty;
	private int defect_qty;

	private int work_order_key;
	private String work_order_code;
	private int order_qty;

	private int plan_key;
	private String plan_code;
	private int plan_qty;

	private int item_key;
	private String item_code;
	private String item_name;

	private int work_user_key;
	private String work_user_name;

	private int quality_key;
	private int inspect_qty;
	private String defect_reason;
	private String qc_status;

	private double achievement_rate;

	public int getProd_key() {
		return prod_key;
	}

	public void setProd_key(int prod_key) {
		this.prod_key = prod_key;
	}

	public String getProd_code() {
		return prod_code;
	}

	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}

	public String getProd_date() {
		return prod_date;
	}

	public void setProd_date(String prod_date) {
		this.prod_date = prod_date;
	}

	public int getGood_qty() {
		return good_qty;
	}

	public void setGood_qty(int good_qty) {
		this.good_qty = good_qty;
	}

	public int getDefect_qty() {
		return defect_qty;
	}

	public void setDefect_qty(int defect_qty) {
		this.defect_qty = defect_qty;
	}

	public int getWork_order_key() {
		return work_order_key;
	}

	public void setWork_order_key(int work_order_key) {
		this.work_order_key = work_order_key;
	}

	public String getWork_order_code() {
		return work_order_code;
	}

	public void setWork_order_code(String work_order_code) {
		this.work_order_code = work_order_code;
	}

	public int getOrder_qty() {
		return order_qty;
	}

	public void setOrder_qty(int order_qty) {
		this.order_qty = order_qty;
	}

	public int getPlan_key() {
		return plan_key;
	}

	public void setPlan_key(int plan_key) {
		this.plan_key = plan_key;
	}

	public String getPlan_code() {
		return plan_code;
	}

	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}

	public int getPlan_qty() {
		return plan_qty;
	}

	public void setPlan_qty(int plan_qty) {
		this.plan_qty = plan_qty;
	}

	public int getItem_key() {
		return item_key;
	}

	public void setItem_key(int item_key) {
		this.item_key = item_key;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getWork_user_key() {
		return work_user_key;
	}

	public void setWork_user_key(int work_user_key) {
		this.work_user_key = work_user_key;
	}

	public String getWork_user_name() {
		return work_user_name;
	}

	public void setWork_user_name(String work_user_name) {
		this.work_user_name = work_user_name;
	}

	public int getQuality_key() {
		return quality_key;
	}

	public void setQuality_key(int quality_key) {
		this.quality_key = quality_key;
	}

	public int getInspect_qty() {
		return inspect_qty;
	}

	public void setInspect_qty(int inspect_qty) {
		this.inspect_qty = inspect_qty;
	}

	public String getDefect_reason() {
		return defect_reason;
	}

	public void setDefect_reason(String defect_reason) {
		this.defect_reason = defect_reason;
	}

	public String getQc_status() {
		return qc_status;
	}

	public void setQc_status(String qc_status) {
		this.qc_status = qc_status;
	}

	public double getAchievement_rate() {
		return achievement_rate;
	}

	public void setAchievement_rate(double achievement_rate) {
		this.achievement_rate = achievement_rate;
	}

	@Override
	public String toString() {
		return "ProductionDTO [prod_key=" + prod_key + ", prod_code=" + prod_code + ", prod_date=" + prod_date
				+ ", good_qty=" + good_qty + ", defect_qty=" + defect_qty + ", work_order_key=" + work_order_key
				+ ", work_order_code=" + work_order_code + ", order_qty=" + order_qty + ", plan_key=" + plan_key
				+ ", plan_code=" + plan_code + ", plan_qty=" + plan_qty + ", item_key=" + item_key + ", item_code="
				+ item_code + ", item_name=" + item_name + ", work_user_key=" + work_user_key + ", work_user_name="
				+ work_user_name + ", quality_key=" + quality_key + ", inspect_qty=" + inspect_qty + ", defect_reason="
				+ defect_reason + ", qc_status=" + qc_status + ", achievement_rate=" + achievement_rate + "]";
	}

}
