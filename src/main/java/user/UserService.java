package user;

import java.util.List;

public class UserService {

	 UserDAO dao = new UserDAO();

	    public List<UserDTO> getUserList() {
	        return dao.selectUserList();
	    }

	    public UserDTO getUserOne(int userKey) {
	        return dao.selectUserOne(userKey);
	    }

	    public int modifyUser(UserDTO dto) {
	        return dao.updateUser(dto);
	    }

	    public int removeUser(int userKey) {
	        return dao.softDeleteUser(userKey);
	    }
	
}
