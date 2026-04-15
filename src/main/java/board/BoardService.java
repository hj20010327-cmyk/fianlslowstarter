package board;

import java.util.ArrayList;

public class BoardService {

	 BoardDAO dao = new BoardDAO();

	    public int getBoardCount(String searchType, String keyword) {
	        return dao.selectBoardCount(searchType, keyword);
	    }

	    public ArrayList<BoardDTO> getBoardList(String searchType, String keyword, int page, int pageSize) {
	        return dao.selectBoardList(searchType, keyword, page, pageSize);
	    }

	    public BoardDTO getBoardOne(int boardKey) {
	        dao.increaseViewCount(boardKey);
	        return dao.selectBoardOne(boardKey);
	    }

	    public BoardDTO getBoardOneForEdit(int boardKey) {
	        return dao.selectBoardOne(boardKey);
	    }

	    public int writeBoard(BoardDTO dto) {
	        return dao.insertBoard(dto);
	    }

	    public int modifyBoard(BoardDTO dto) {
	        return dao.updateBoard(dto);
	    }

	    public int removeBoard(int boardKey) {
	        return dao.deleteBoard(boardKey);
	    }
	
}
