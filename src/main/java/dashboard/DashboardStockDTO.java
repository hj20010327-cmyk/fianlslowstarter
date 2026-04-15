package dashboard;

public class DashboardStockDTO {

	private String item_code;
	private String item_name;
	private int current_qty;
	private int safe_qty;

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

	public int getCurrent_qty() {
		return current_qty;
	}

	public void setCurrent_qty(int current_qty) {
		this.current_qty = current_qty;
	}

	public int getSafe_qty() {
		return safe_qty;
	}

	public void setSafe_qty(int safe_qty) {
		this.safe_qty = safe_qty;
	}

	@Override
	public String toString() {
		return "DashboardStockDTO [item_code=" + item_code + ", item_name=" + item_name + ", current_qty=" + current_qty
				+ ", safe_qty=" + safe_qty + "]";
	}
	
	

}
