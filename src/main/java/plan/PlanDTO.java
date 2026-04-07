package plan;

import java.sql.Date;

public class PlanDTO {
	private String plan_key; // plan001
	private String p_name; // 계획 명
	private int priority; // 우선순위
	private Date plan_date; // 계획일
	private String code_id; // 코드 id
	private int deptno; // 부서번호 
	private int quantity; // 수량 
	public String getPlan_key() {
		return plan_key;
	}
	public void setPlan_key(String plan_key) {
		this.plan_key = plan_key;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Date getPlan_date() {
		return plan_date;
	}
	public void setPlan_date(Date plan_date) {
		this.plan_date = plan_date;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "PlanDTO [plan_key=" + plan_key + ", p_name=" + p_name + ", priority=" + priority + ", plan_date="
				+ plan_date + ", code_id=" + code_id + ", deptno=" + deptno + ", quantity=" + quantity + "]";
	}
}