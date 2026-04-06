package bom;

public class BOMDTO {
	
	private String bom_key;
	private String item_code;
	private int item_count;
	private int status;
	private String code_id;
	
	

	@Override
	public String toString() {
		return "BOMDTO [bom_key=" + bom_key + ", item_code=" + item_code + ", item_count=" + item_count + ", status="
				+ status + ", code_id=" + code_id + "]";
	}
	public String getBom_key() {
		return bom_key;
	}
	public void setBom_key(String bom_key) {
		this.bom_key = bom_key;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public int getItem_count() {
		return item_count;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	
	
	
}
