package comment;

import java.util.ArrayList;

public class CommentService {

	 CommentDAO dao = new CommentDAO();

	    public ArrayList<CommentDTO> getCommentList(int boardKey) {
	        return dao.selectCommentList(boardKey);
	    }

	    public int writeComment(CommentDTO dto) {
	        return dao.insertComment(dto);
	    }

	    public int removeComment(int commentKey) {
	        return dao.deleteComment(commentKey);
	    }
	
}
