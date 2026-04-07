package process;

import java.util.List;

public class ProcessService {
	
	public List getList() {
		
		ProcessDAO processDAO = new ProcessDAO(); 
		return processDAO.selectAllProcess(); 
		
	}
	
	public ProcessDTO getProcess(String process_key) {
		
		ProcessDAO processDAO = new ProcessDAO();
		ProcessDTO processDTO = processDAO.selectOneProcess(process_key);
		return processDTO;
		
	}
	
	public int insert(ProcessDTO dto) {
		
		ProcessDAO processDAO = new ProcessDAO(); 
		return processDAO.insertProcess(dto);
	}
	
	public int update(ProcessDTO dto) {
		
		ProcessDAO processDAO = new ProcessDAO(); 
		return processDAO.updateProcess(dto);
	}
	
	public int delete(ProcessDTO dto) {
		
		ProcessDAO processDAO = new ProcessDAO(); 
		return processDAO.deleteProcess(dto);
	}

}
