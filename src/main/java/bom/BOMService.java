package bom;

import java.util.List;

public class BOMService {
	
	public List getList() {
		
		BOMDAO bomDAO = new BOMDAO();
		return bomDAO.selectAllBOM();
	
	}
	
	public BOMDTO getBOM(String bom_key) {
	
		BOMDAO bomDAO = new BOMDAO(); 
		BOMDTO bomDTO = bomDAO.selectOneBOM(bom_key);
		return bomDTO;
	
	}
	
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
}
