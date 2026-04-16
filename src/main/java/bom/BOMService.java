package bom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BOMService {
	
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
		
		List list;
		int totalCount;

		if(dto.getParent_item_key() != 0) {
		    list = bomDAO.selectdetailBOM(dto);
		    totalCount = bomDAO.SearchTotal(dto);
		}  else { list = bomDAO.selectAllBOM(dto);
		    totalCount = bomDAO.BOMTotal();
		}	
		
		
		Map map = new HashMap(); 
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		
		return map;
		
	}
}
