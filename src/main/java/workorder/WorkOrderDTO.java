package workorder;
import java.sql.Date;

public class WorkOrderDTO {
	private int work_order_key; // �۾����� Ű PK
	private int order_user_key; // FK
	private int work_user_key; // FK
	private String work_order_code; // �۾����� �ڵ�
	private int order_qty; // ���� ���� 
	private Date work_date; //
	private Date created_at; // ������ 
	private int plan_key; // �����ȹ Ű FK
	private String plan_code;
	private String order_user_name;
	private String work_user_name;
	private int plan_qty;
	private Date plan_date;
	private String item_name;
	public int getWork_order_key() {
		return work_order_key;
	}
	public void setWork_order_key(int work_order_key) {
		this.work_order_key = work_order_key;
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
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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
	
	public int getPlan_qty() {
		return plan_qty;
	}
	public void setPlan_qty(int plan_qty) {
		this.plan_qty = plan_qty;
	}
	public Date getPlan_date() {
		return plan_date;
	}
	public void setPlan_date(Date plan_date) {
		this.plan_date = plan_date;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	@Override
	public String toString() {
		return "WorkOrderDTO [work_order_key=" + work_order_key + ", order_user_key=" + order_user_key
				+ ", work_user_key=" + work_user_key + ", work_order_code=" + work_order_code + ", order_qty="
				+ order_qty + ", work_date=" + work_date + ", created_at=" + created_at + ", plan_key=" + plan_key
				+ ", plan_code=" + plan_code + ", order_user_name=" + order_user_name + ", work_user_name="
				+ work_user_name + ", plan_qty=" + plan_qty + ", plan_date=" + plan_date + ", item_name=" + item_name
				+ "]";
	}
	
	
	

	
	
	
	
	
	
}
