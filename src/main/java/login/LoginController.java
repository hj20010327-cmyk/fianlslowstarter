package login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import signup.PasswordUtil;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String encryptedPw = PasswordUtil.sha256(pw);
		
		LoginService loginService = new LoginService();
		LoginDTO dto = loginService.checkLogin(id, encryptedPw);
		HttpSession session = request.getSession();
		
		if(dto == null) {
			request.setAttribute("error","아이디 혹은 비밀번호가 틀렸습니다" );
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {			
			session.setAttribute("dto", dto);
			session.setAttribute("login", "Y");
			
			session.setAttribute("loginUserId", dto.getUser_id());
			response.sendRedirect("index");
		}
		
		
		
		
	
	}

}
