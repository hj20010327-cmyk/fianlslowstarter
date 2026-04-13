package process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessService {
	
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
	
	public Map getPaging(ProcessDTO dto) {
		
		ProcessDAO processDAO = new ProcessDAO(); 
		
		int size = dto.getSize(); 
		int page = dto.getPage();
		
		int start = 0, end = 0; 
		
		end = size * page; 
		start = end - (size - 1);
		
		dto.setEnd(end);
		dto.setStart(start);
		
		List list; 
		int totalCount; 
		
		if(dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
			list = processDAO.selectdetailProcess(dto);
			totalCount = processDAO.SearchTotal(dto);
		} else if (dto.getKeycode() != 0) {
			list = processDAO.selectdetailProcess(dto);
			totalCount = processDAO.SearchTotal(dto);
		} else { list = processDAO.selectAllProcess(dto);
			totalCount = processDAO.ProcessTotal();
		}
		
		Map map = new HashMap(); 
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}

}
