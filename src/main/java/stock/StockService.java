package stock;


import java.util.List;


public class StockService {


    private StockDAO dao = new StockDAO();




    public List<StockDTO> getList(int page) {


        // 나중에 여기서 완제품/자재 필터링 로직을 넣을 수 있습니다.
        return dao.getStockList(page);


    }


}