package workorder;
import java.sql.Date;

public class WorkOrderDTO {
	private String work_key;  // PK 작업번호
	private String product_name; // 제품명
	private String quantity; // 수량
	private Date duedate; 	// 작업일
	private String status;	// 상태
	private Date created_at; //생성일
	private String Plan_key; // Fk
	private String user_key; // fk
	public String getWork_key() {
		return work_key;
	}
	public void setWork_key(String work_key) {
		this.work_key = work_key;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public String getPlan_key() {
		return Plan_key;
	}
	public void setPlan_key(String plan_key) {
		Plan_key = plan_key;
	}
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	@Override
	public String toString() {
		return "WorkOrderDTO [work_key=" + work_key + ", product_name=" + product_name + ", quantity=" + quantity
				+ ", duedate=" + duedate + ", status=" + status + ", created_at=" + created_at + ", Plan_key="
				+ Plan_key + ", user_key=" + user_key + "]";
	}
	
	
	
}
