package signup;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		request.getRequestDispatcher("signup.jsp").forward(request, response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String pwCheck = request.getParameter("pwCheck");
		String email = request.getParameter("email");
		
		System.out.println(id);
		
		// 공백 제거
		name = safeTrim(name);
		role = safeTrim(role);
		phone1 = safeTrim(phone1);
		phone2 = safeTrim(phone2);
		phone3 = safeTrim(phone3);
		id = safeTrim(id);
		pw = safeTrim(pw);
		pwCheck = safeTrim(pwCheck);
		email = safeTrim(email);
		
		// 기존 입력값 유지
		request.setAttribute("name", name);
		request.setAttribute("role", role);
		request.setAttribute("phone1", phone1);
		request.setAttribute("phone2", phone2);
		request.setAttribute("phone3", phone3);
		request.setAttribute("id", id);
		request.setAttribute("email", email);
		
		// 비어있는거 방지
		if (name.isEmpty() || role.isEmpty() || phone1.isEmpty() || phone2.isEmpty() || phone3.isEmpty()
				|| id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || email.isEmpty() ) {
			
			request.setAttribute("error", "모든 항목을 입력해주세요");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return;
		}
		
		// 역할 제대로 됐는지 검사
		if (!(role.equals("작업자") || role.equals("관리자") || role.equals("슈퍼바이저"))) {
			request.setAttribute("error", "올바르지 않은 권한입니다");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return;
		}
		
		String phone = phone1 + "-" + phone2+ "-" + phone3;
		
		// 이메일 형식 검사 (정규식)
		if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			request.setAttribute("error", "이메일 형식이 올바르지 않습니다");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return;
		}
		
		// 비밀번호 일치 검사
		if(!pw.equals(pwCheck)) {
			request.setAttribute("error", "비밀번호가 일치하지 않습니다");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return;
		}
		
		SignupService service = new SignupService();
		
		int checkId = service.idcheck(id);
		
		if(checkId > 0) {
			request.setAttribute("error", "이미 사용 중인 아이디입니다");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			return;
		}
		
		SignupDTO dto = new SignupDTO();
		dto.setUser_name(name);
		dto.setUser_role(role);
		dto.setUser_phone(phone);
		dto.setUser_id(id);
		
		String encryptedPw = PasswordUtil.sha256(pw);
		dto.setUser_pw(encryptedPw);
		dto.setUser_email(email);
		
		int result = service.signup(dto);
		
		if (result > 0) {
			response.sendRedirect("login.html");
		} else {
			request.setAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요");
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
		
		
	}

	private String safeTrim(String str) {
		return str == null ? "" : str.trim();
	}
	
}
