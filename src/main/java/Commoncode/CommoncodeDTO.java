package Commoncode;

import java.sql.Date;

public class CommoncodeDTO {
	
	private String code_key; 
	private String code_group;
	private String code; 
	private String code_name;
	private String code_desc;
	private int status;
	private Date created_at;
	private String user_key;
	private String user_key2;
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "CommoncodeDTO [code_key=" + code_key + ", code_group=" + code_group + ", code=" + code + ", code_name="
				+ code_name + ", code_desc=" + code_desc + ", status=" + status + ", created_at=" + created_at
				+ ", user_key=" + user_key + ", user_key2=" + user_key2 + "]";
	}
	public String getCode_key() {
		return code_key;
	}
	public void setCode_key(String code_key) {
		this.code_key = code_key;
	}
	public String getCode_group() {
		return code_group;
	}
	public void setCode_group(String code_group) {
		this.code_group = code_group;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getCode_desc() {
		return code_desc;
	}
	public void setCode_desc(String code_desc) {
		this.code_desc = code_desc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	public String getUser_key2() {
		return user_key2;
	}
	public void setUser_key2(String user_key2) {
		this.user_key2 = user_key2;
	}
	
	
	

}
