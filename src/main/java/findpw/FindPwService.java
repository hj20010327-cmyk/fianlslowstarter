package findpw;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindPwService {

	private final FindPwDAO dao = new FindPwDAO();
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$";
    private static final SecureRandom random = new SecureRandom();

    public boolean checkUser(String userId, String email) {
        return dao.checkUser(userId, email);
    }

    public String makeTempPassword() {
    	
    	String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String number = "0123456789";
        String special = "!@#$%^&*";
        
        String all = upper + lower + number + special;
    	
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        
        sb.append(upper.charAt(random.nextInt(upper.length())));
        sb.append(lower.charAt(random.nextInt(lower.length())));
        sb.append(number.charAt(random.nextInt(number.length())));
        sb.append(special.charAt(random.nextInt(special.length())));
        
        for (int i = 0; i < 10; i++) {
            sb.append(all.charAt(random.nextInt(all.length())));
        }
        return shuffleString(sb.toString());
    }

    public int resetPassword(String userId, String tempPw) {
        String encrypted = PasswordUtil.sha256(tempPw);
        return dao.updateTempPassword(userId, encrypted);
    }
	
    private String shuffleString(String input) {
        List<Character> list = new ArrayList<>();
        for (char c : input.toCharArray()) {
            list.add(c);
        }

        Collections.shuffle(list);

        StringBuilder sb = new StringBuilder();
        for (char c : list) {
            sb.append(c);
        }

        return sb.toString();
    }
    
}
