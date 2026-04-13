package findpw;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/findpw")
public class FindPwController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final FindPwService service = new FindPwService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/findpw.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String id = safeTrim(request.getParameter("id"));
		String email = safeTrim(request.getParameter("email"));

		request.setAttribute("id", id);
		request.setAttribute("email", email);

		if (id.isEmpty() || email.isEmpty()) {
			request.setAttribute("error", "아이디와 이메일을 모두 입력해주세요.");
			request.getRequestDispatcher("/findpw.jsp").forward(request, response);
			return;
		}

		boolean exists = service.checkUser(id, email);
		if (!exists) {
			request.setAttribute("error", "일치하는 회원 정보가 없습니다.");
			request.getRequestDispatcher("/findpw.jsp").forward(request, response);
			return;
		}

		String tempPw = service.makeTempPassword();
		int result = service.resetPassword(id, tempPw);

		if (result > 0) {
			request.setAttribute("success", "임시 비밀번호가 발급되었습니다. 로그인 후 꼭 변경해주세요.");
			request.setAttribute("tempPw", tempPw);
		} else {
			request.setAttribute("error", "비밀번호 재설정에 실패했습니다.");
		}

		request.getRequestDispatcher("/findpw.jsp").forward(request, response);
	}

	private String safeTrim(String str) {
        return str == null ? "" : str.trim();
    }
	
}
