package bom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOMService {
	
//	public List getList(BOMDTO dto) {
//		
//		BOMDAO bomDAO = new BOMDAO();
//		return bomDAO.selectAllBOM(dto);
//	
//	}
	
//	public BOMDTO getBOM(int bom_key) {
//	
//		BOMDAO bomDAO = new BOMDAO(); 
//		BOMDTO bomDTO = bomDAO.selectOneBOM(int);
//		return bomDTO;
//	
//	}
	
	public int insert(BOMDTO dto) {
		
		BOMDAO bomDAO = new BOMDAO();
		return bomDAO.insertBOM(dto);
	}

	public int update(BOMDTO dto) {
		
		BOMDAO bomDAO = new BOMDAO();
		return bomDAO.updateBOM(dto);
	}
	
	public int delete(BOMDTO dto) {
		
		BOMDAO bomDAO = new BOMDAO();
		return bomDAO.deleteBOM(dto);
	}
	
	public Map getPaging(BOMDTO dto) {
		
		BOMDAO bomDAO = new BOMDAO();
		
		int size = dto.getSize();
		int page = dto.getPage(); 
		
		int start = 0, end = 0; 
		
		end = size * page; 
		start = end - (size - 1);
		
		dto.setEnd(end);
		dto.setStart(start);
		
		List list = bomDAO.selectAllBOM(dto);
		int totalCount = bomDAO.BOMTotal();
	
		Map map = new HashMap(); 
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		
		return map;
		
	}
}
