package comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	CommentService service = new CommentService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("dto") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");
        String action = request.getParameter("action");

        if ("write".equals(action)) {
            CommentDTO dto = new CommentDTO();
            dto.setBoard_key(Integer.parseInt(request.getParameter("board_key")));
            dto.setContent(request.getParameter("content"));
            dto.setUser_key(loginUser.getUser_key());

            int result = service.writeComment(dto);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/board?action=detail&board_key=" + dto.getBoard_key());
            } else {
                response.sendRedirect(request.getContextPath() + "/board?action=detail&board_key=" + dto.getBoard_key());
            }
            return;
        }

        if ("delete".equals(action)) {
            int commentKey = Integer.parseInt(request.getParameter("comment_key"));
            int boardKey = Integer.parseInt(request.getParameter("board_key"));

            int result = service.removeComment(commentKey);

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/board?action=detail&board_key=" + boardKey);
            } else {
                response.sendRedirect(request.getContextPath() + "/board?action=detail&board_key=" + boardKey);
            }
            return;
        }

        response.sendRedirect(request.getContextPath() + "/board?action=list");
    }

}
