package login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordUtil {

	 public static String sha256(String text) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hashBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));

	            StringBuilder sb = new StringBuilder();
	            for (byte b : hashBytes) {
	                sb.append(String.format("%02x", b));
	            }

	            return sb.toString();

	        } catch (Exception e) {
	            throw new RuntimeException("SHA-256 암호화 중 오류 발생", e);
	        }
	    }
	
}
