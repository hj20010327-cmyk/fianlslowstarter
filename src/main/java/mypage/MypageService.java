package mypage;

public class MypageService {

	MypageDAO dao = new MypageDAO();

    public MypageDTO getUserByUserId(String userId) {
        return dao.selectUserByUserId(userId);
    }

    public boolean checkPassword(String userId, String rawPw) {
        String encryptedPw = PasswordUtil.sha256(rawPw);
        return dao.checkPassword(userId, encryptedPw);
    }

    public int updateOnlyInfo(MypageDTO dto) {
        return dao.updateUserInfo(dto);
    }

    public int updateInfoAndPassword(MypageDTO dto, String newRawPw) {
        dto.setUser_pw(PasswordUtil.sha256(newRawPw));
        return dao.updateUserInfoAndPassword(dto);
    }
	
}
