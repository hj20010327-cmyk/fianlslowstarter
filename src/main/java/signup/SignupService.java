package signup;

public class SignupService {
	SignupDAO dao = new SignupDAO();

	public int signup (SignupDTO dto) {
		
		return dao.signUp(dto);
		
	}
	
	public int idcheck(String id) {

		return dao.checkId(id);
		
	}
	
}
