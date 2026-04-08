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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		String[] phonenum = request.getParameterValues("phone");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		
		int phone1 = Integer.parseInt(phonenum[0]);
		int phone2 = Integer.parseInt(phonenum[1]);
		int phone3 = Integer.parseInt(phonenum[2]);
		
		SignupDTO dto = new SignupDTO();
		dto.setUser_name(name);
		dto.setUser_role(role);
		dto.setUser_phone(phone1+"-"+phone2+"-"+phone3);
		dto.setUser_id(id);
		dto.setUser_pw(pw);
		dto.setUser_email(email);
		
		SignupService service = new SignupService();
		int result = service.signup(dto);
		int check = service.idcheck(id);
		
		if(check == 0) {			
			response.sendRedirect("signup.html?name="+name+
					"&role="+role
					+"&id="+id
					+"&email="+email);
		} else if(check == 1) {
			response.sendRedirect("login.html");
		}
		
		
		
		
	
	}

}
