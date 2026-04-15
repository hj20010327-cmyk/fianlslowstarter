package dashboard;

import java.sql.Date;

public class DashboardWorkorderDTO {

	private String work_order_code;
	private int order_qty;
	private Date work_date;
	private int order_user_key;
	private int work_user_key;
	private String item_name;
	private String order_user_name;
	private String work_user_name;

	public String getOrder_user_name() {
	    return order_user_name;
	}

	public void setOrder_user_name(String order_user_name) {
	    this.order_user_name = order_user_name;
	}

	public String getWork_user_name() {
	    return work_user_name;
	}

	public void setWork_user_name(String work_user_name) {
	    this.work_user_name = work_user_name;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
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

	public Date getWork_date() {
		return work_date;
	}

	public void setWork_date(Date work_date) {
		this.work_date = work_date;
	}

	public int getOrder_user_key() {
		return order_user_key;
	}

	public void setOrder_user_key(int order_user_key) {
		this.order_user_key = order_user_key;
	}

	public int getWork_user_key() {
		return work_user_key;
	}

	public void setWork_user_key(int work_user_key) {
		this.work_user_key = work_user_key;
	}

	@Override
	public String toString() {
		return "DashboardWorkorderDTO [work_order_code=" + work_order_code + ", order_qty=" + order_qty + ", work_date="
				+ work_date + ", order_user_key=" + order_user_key + ", work_user_key=" + work_user_key + ", item_name="
				+ item_name + ", order_user_name=" + order_user_name + ", work_user_name=" + work_user_name + "]";
	}



}
