package login;

import java.util.List;

public class LoginService {
	
	LoginDAO dao = new LoginDAO();
	
	public List CheckLogin() {
		
		return dao.loginCheck();
		
	}
	
	
	
}
