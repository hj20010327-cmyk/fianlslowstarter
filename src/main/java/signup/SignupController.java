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
		String phone1 = request.getParameter("phone1");
		String phone2 = request.getParameter("phone2");
		String phone3 = request.getParameter("phone3");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		
		System.out.println(id);
		
		SignupService service = new SignupService();
		int check = service.idcheck(id);
		
		
		if(check == 0) {			
			response.sendRedirect("signup.jsp?name="+name+
					"&role="+role
					+"phone2="+phone2
					+"phone3="+phone3
					+"&id="+id
					+"&email="+email);
		} else if(check == 1) {
			SignupDTO dto = new SignupDTO();
			dto.setUser_name(name);
			dto.setUser_role(role);
			dto.setUser_phone(phone1+"-"+phone2+"-"+phone3);
			dto.setUser_id(id);
			dto.setUser_pw(pw);
			dto.setUser_email(email);
			
			int result = service.signup(dto);
			response.sendRedirect("login.html");
		}
		
		
		
		
	
	}

}
