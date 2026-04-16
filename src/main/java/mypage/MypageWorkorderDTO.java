package mypage;

public class MypageWorkorderDTO {

	private int work_order_key;
	private String work_order_code;
	private String work_date;
	private int order_qty;
	private String item_code;
	private String item_name;
	private String plan_code;
	private String order_user_name;

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

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}

	public int getOrder_qty() {
		return order_qty;
	}

	public void setOrder_qty(int order_qty) {
		this.order_qty = order_qty;
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

	public String getPlan_code() {
		return plan_code;
	}

	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}

	public String getOrder_user_name() {
		return order_user_name;
	}

	public void setOrder_user_name(String order_user_name) {
		this.order_user_name = order_user_name;
	}

	@Override
	public String toString() {
		return "MyWorkOrderDTO [work_order_key=" + work_order_key + ", work_order_code=" + work_order_code
				+ ", work_date=" + work_date + ", order_qty=" + order_qty + ", item_code=" + item_code + ", item_name="
				+ item_name + ", plan_code=" + plan_code + ", order_user_name=" + order_user_name + "]";
	}

}
