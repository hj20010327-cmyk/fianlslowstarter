package workorder;
import java.sql.Date;

public class WorkOrderDTO {
	private int work_order_key; // 작업지시 키 PK
	private String work_order_code; // 작업지시 코드
	private int plan_key; // 생산계획 키 FK
	private int item_key; 	// 아이템 키 FK
	private int process_key;	// FK
	private Date work_date; //
	private int order_qty; // 지시 수량 
	private String order_status; // 지시 상태
	private int user_key; // FK
	private Date created_at; // 생성일 
	private int machine_key; // 설비 키 fk 
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
	public int getPlan_key() {
		return plan_key;
	}
	public void setPlan_key(int plan_key) {
		this.plan_key = plan_key;
	}
	public int getItem_key() {
		return item_key;
	}
	public void setItem_key(int item_key) {
		this.item_key = item_key;
	}
	public int getProcess_key() {
		return process_key;
	}
	public void setProcess_key(int process_key) {
		this.process_key = process_key;
	}
	public Date getWork_date() {
		return work_date;
	}
	public void setWork_date(Date work_date) {
		this.work_date = work_date;
	}
	public int getOrder_qty() {
		return order_qty;
	}
	public void setOrder_qty(int order_qty) {
		this.order_qty = order_qty;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public int getUser_key() {
		return user_key;
	}
	public void setUser_key(int user_key) {
		this.user_key = user_key;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public int getMachine_key() {
		return machine_key;
	}
	public void setMachine_key(int machine_key) {
		this.machine_key = machine_key;
	}
	@Override
	public String toString() {
		return "WorkOrderDTO [work_order_key=" + work_order_key + ", work_order_code=" + work_order_code + ", plan_key="
				+ plan_key + ", item_key=" + item_key + ", process_key=" + process_key + ", work_date=" + work_date
				+ ", order_qty=" + order_qty + ", order_status=" + order_status + ", user_key=" + user_key
				+ ", created_at=" + created_at + ", machine_key=" + machine_key + "]";
	}
	
	
	
	
}
