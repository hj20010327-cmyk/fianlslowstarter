package user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserService service = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        LoginDTO loginUser = getLoginUser(request);
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!isAdmin(loginUser)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String action = request.getParameter("action");
        String userKeyStr = request.getParameter("user_key");

        if ("detail".equals(action) && userKeyStr != null && !userKeyStr.trim().isEmpty()) {
            try {
                int userKey = Integer.parseInt(userKeyStr);
                UserDTO dto = service.getUserOne(userKey);
                request.setAttribute("userOne", dto);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/user?msg=fail");
                return;
            }
        }

        List<UserDTO> list = service.getUserList();
        request.setAttribute("userList", list);
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        LoginDTO loginUser = getLoginUser(request);
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!isAdmin(loginUser)) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String action = request.getParameter("action");
        String userKeyStr = request.getParameter("user_key");

        if (userKeyStr == null || userKeyStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user?msg=fail");
            return;
        }

        int userKey;
        try {
            userKey = Integer.parseInt(userKeyStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/user?msg=fail");
            return;
        }

        if ("update".equals(action)) {
            UserDTO dto = new UserDTO();
            dto.setUser_key(userKey);
            dto.setUser_name(request.getParameter("user_name"));
            dto.setUser_role(request.getParameter("user_role"));
            dto.setUser_phone(request.getParameter("user_phone"));
            dto.setUser_email(request.getParameter("user_email"));
            dto.setStatus(request.getParameter("status"));

            int result = service.modifyUser(dto);
            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/user?msg=updated&action=detail&user_key=" + userKey);
            } else {
                response.sendRedirect(request.getContextPath() + "/user?msg=fail");
            }
            return;
        }

        if ("delete".equals(action)) {
            int result = service.removeUser(userKey);
            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/user?msg=deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/user?msg=fail");
            }
            return;
        }

        response.sendRedirect(request.getContextPath() + "/user");
    }

    private LoginDTO getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (LoginDTO) session.getAttribute("dto");
    }

    private boolean isAdmin(LoginDTO loginUser) {
        return "관리자".equals(loginUser.getUser_role()) || "슈퍼바이저".equals(loginUser.getUser_role());
    }

}
