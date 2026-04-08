package stock;

import java.util.List;

public class StockService {

	private StockDAO dao = new StockDAO();

	public List<StockDTO> getStockList(String searchCode, String searchName) {
		
		System.out.println("Service: getStockList »£√‚");
		
		return dao.getStockList(searchCode, searchName);
	}
}