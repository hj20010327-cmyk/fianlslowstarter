package login;

import java.util.List;

public class LoginService {
	
	LoginDAO dao = new LoginDAO();
	
	public LoginDTO checkLogin(String id, String pw) {
		
		return dao.loginCheck(id, pw);
		
	}
	
	
	
}
