package mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/mypage")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	MypageService service = new MypageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUserId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userId = (String) session.getAttribute("loginUserId");
        MypageDTO dto = service.getUserByUserId(userId);

        request.setAttribute("dto", dto);
        request.getRequestDispatcher("/mypage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUserId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userId = (String) session.getAttribute("loginUserId");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String pw = request.getParameter("pw");
        String npw = request.getParameter("npw");
        String cnpw = request.getParameter("cnpw");

        MypageDTO dto = new MypageDTO();
        dto.setUser_id(userId);
        dto.setUser_name(name);
        dto.setUser_email(email);
        dto.setUser_phone(phone);

        if (pw == null || pw.trim().isEmpty()) {
            response.sendRedirect("mypage?msg=emptyPw");
            return;
        }

        boolean pwOk = service.checkPassword(userId, pw);
        if (!pwOk) {
            response.sendRedirect("mypage?msg=wrongPw");
            return;
        }

        int result = 0;

        boolean hasNewPw = npw != null && !npw.trim().isEmpty();
        boolean hasConfirmPw = cnpw != null && !cnpw.trim().isEmpty();

        if (hasNewPw || hasConfirmPw) {
            if (!npw.equals(cnpw)) {
                response.sendRedirect("mypage?msg=pwMismatch");
                return;
            }

            if (npw.length() < 8) {
                response.sendRedirect("mypage?msg=pwShort");
                return;
            }

            result = service.updateInfoAndPassword(dto, npw);
        } else {
            result = service.updateOnlyInfo(dto);
        }

        if (result > 0) {
            response.sendRedirect("mypage?msg=success");
        } else {
            response.sendRedirect("mypage?msg=fail");
        }
    }

}
