package Commoncode;

import java.util.List;

public class CommoncodeService {
	
	public List getList() {
		
		CommoncodeDAO commoncodeDAO = new CommoncodeDAO(); 
		return commoncodeDAO.selectAllCommoncode();
		
	}
	
	public CommoncodeDTO getcommoncode(String code_key) {
		
		CommoncodeDAO commoncodeDAO = new CommoncodeDAO(); 
		CommoncodeDTO commoncodeDTO = commoncodeDAO.selectOneCommoncode(code_key);
		return commoncodeDTO;
		
	}
	
	public int insert(CommoncodeDTO dto) {
		
		CommoncodeDAO commoncodeDAO = new CommoncodeDAO();
		return commoncodeDAO.insertCommoncode(dto);
	}
	
	public int update(CommoncodeDTO dto) {
		
		CommoncodeDAO commoncodeDAO = new CommoncodeDAO();
		return commoncodeDAO.updateCommoncode(dto);
	}
	
	public int delete(CommoncodeDTO dto) {
		
		CommoncodeDAO commoncodeDAO = new CommoncodeDAO(); 
		return commoncodeDAO.deleteCommoncode(dto);
	}

}
