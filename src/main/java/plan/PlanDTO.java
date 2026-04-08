package plan;

import java.sql.Date;

public class PlanDTO {
	private int plan_key; // 계획 키 pk 
	private String plan_code; // 계획 명
	private int item_key; // 아이템 키 
	private Date plan_date; // 계획일
	private Date due_date; // 마감일 
	private int plan_qty; // 계획 수량  
	private String status; // 계획 상태
	private int user_key; // 유저 키 
	private Date create_at; // 생성일
	private int priority; // 우선순위
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
	public int getItem_key() {
		return item_key;
	}
	public void setItem_key(int item_key) {
		this.item_key = item_key;
	}
	public Date getPlan_date() {
		return plan_date;
	}
	public void setPlan_date(Date plan_date) {
		this.plan_date = plan_date;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public int getPlan_qty() {
		return plan_qty;
	}
	public void setPlan_qty(int plan_qty) {
		this.plan_qty = plan_qty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUser_key() {
		return user_key;
	}
	public void setUser_key(int user_key) {
		this.user_key = user_key;
	}
	public Date getCreate_at() {
		return create_at;
	}
	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
	@Override
	public String toString() {
		return "PlanDTO [plan_key=" + plan_key + ", plan_code=" + plan_code + ", item_key=" + item_key + ", plan_date="
				+ plan_date + ", due_date=" + due_date + ", plan_qty=" + plan_qty + ", status=" + status + ", user_key="
				+ user_key + ", create_at=" + create_at + ", priority=" + priority + "]";
	}
	
	
}