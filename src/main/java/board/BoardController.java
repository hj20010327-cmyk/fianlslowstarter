package board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comment.CommentDTO;
import comment.CommentService;
import login.LoginDTO;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	BoardService service = new BoardService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		CommentService commentService = new CommentService();

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String action = request.getParameter("action");

		if (action == null || "list".equals(action)) {
			String searchType = request.getParameter("searchType");
			String keyword = request.getParameter("keyword");
			String pageStr = request.getParameter("page");

			int page = 1;
			int pageSize = 10;

			if (pageStr != null && !pageStr.isEmpty()) {
				page = Integer.parseInt(pageStr);
			}

			int totalCount = service.getBoardCount(searchType, keyword);
			int totalPage = (int) Math.ceil((double) totalCount / pageSize);

			ArrayList<BoardDTO> list = service.getBoardList(searchType, keyword, page, pageSize);

			request.setAttribute("boardList", list);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("page", page);
			request.setAttribute("totalPage", totalPage == 0 ? 1 : totalPage);
			request.setAttribute("searchType", searchType);
			request.setAttribute("keyword", keyword);

			request.getRequestDispatcher("/board.jsp").forward(request, response);
			return;
		}

		if ("detail".equals(action)) {
			int boardKey = Integer.parseInt(request.getParameter("board_key"));
		    BoardDTO dto = service.getBoardOne(boardKey);

		    ArrayList<CommentDTO> commentList = commentService.getCommentList(boardKey);

		    request.setAttribute("board", dto);
		    request.setAttribute("commentList", commentList);
		    request.getRequestDispatcher("/board_detail.jsp").forward(request, response);
		    return;
		}

		if ("writeForm".equals(action)) {
			request.getRequestDispatcher("/board_write.jsp").forward(request, response);
			return;
		}

		if ("editForm".equals(action)) {
			int boardKey = Integer.parseInt(request.getParameter("board_key"));
			BoardDTO dto = service.getBoardOneForEdit(boardKey);
			request.setAttribute("board", dto);
			request.getRequestDispatcher("/board_edit.jsp").forward(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/board?action=list");
	}

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
			BoardDTO dto = new BoardDTO();
			dto.setBoard_type(request.getParameter("board_type"));
			dto.setTitle(request.getParameter("title"));
			dto.setContent(request.getParameter("content"));
			dto.setUser_key(loginUser.getUser_key());

			int result = service.writeBoard(dto);

			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/board?action=list");
			} else {
				response.sendRedirect(request.getContextPath() + "/board?action=writeForm");
			}
			return;
		}

		if ("edit".equals(action)) {
			BoardDTO dto = new BoardDTO();
			dto.setBoard_key(Integer.parseInt(request.getParameter("board_key")));
			dto.setBoard_type(request.getParameter("board_type"));
			dto.setTitle(request.getParameter("title"));
			dto.setContent(request.getParameter("content"));

			int result = service.modifyBoard(dto);

			if (result > 0) {
				response.sendRedirect(
						request.getContextPath() + "/board?action=detail&board_key=" + dto.getBoard_key());
			} else {
				response.sendRedirect(
						request.getContextPath() + "/board?action=editForm&board_key=" + dto.getBoard_key());
			}
			return;
		}

		if ("delete".equals(action)) {
			int boardKey = Integer.parseInt(request.getParameter("board_key"));
			int result = service.removeBoard(boardKey);

			if (result > 0) {
				response.sendRedirect(request.getContextPath() + "/board?action=list");
			} else {
				response.sendRedirect(request.getContextPath() + "/board?action=detail&board_key=" + boardKey);
			}
			return;
		}

		response.sendRedirect(request.getContextPath() + "/board?action=list");
	}

}
