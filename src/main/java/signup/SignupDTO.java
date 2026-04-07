package signup;

public class SignupDTO {

	private String user_id;
	private String user_pw;
	private String user_name;
	private int phone;
	private String email;
	
	@Override
	public String toString() {
		return "SignupDTO [user_id=" + user_id + ", user_pw=" + user_pw + ", user_name=" + user_name + ", phone="
				+ phone + ", email=" + email + "]";
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
