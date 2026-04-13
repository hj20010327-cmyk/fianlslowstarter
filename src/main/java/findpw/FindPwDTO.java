package findpw;

public class FindPwDTO {

	private String user_id;
	private String user_email;
	private String user_pw;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	@Override
	public String toString() {
		return "FindPwDTO [user_id=" + user_id + ", user_email=" + user_email + ", user_pw=" + user_pw + "]";
	}
	
	
	
}
