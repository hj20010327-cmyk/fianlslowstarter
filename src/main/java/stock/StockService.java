package stock;

import java.util.List;

public class StockService {
    private StockDAO dao = new StockDAO();

    // 컨트롤러가 부르는 이름
    public List<StockDTO> getStockList(String searchCode, String searchName, String searchType) {
        // DAO의 selectSearch 메서드에 3개 값을 넘깁니다.
        return dao.selectSearch(searchCode, searchName, searchType);
    }
}