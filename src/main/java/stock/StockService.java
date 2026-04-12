package stock;

import java.util.List;

public class StockService {

    private StockDAO dao = new StockDAO();

    // 1. 목록 가져오기 로직
    public List<StockDTO> getList(int page, String keyword) {
        return dao.selectAll(page, keyword);
    }
    
    // 2. 신규 등록 로직
    public void register(StockDTO dto) {
        dao.insert(dto);
    }
    
    // 3. 수정 로직
    public void update(StockDTO dto) {
        dao.update(dto);
    }
    
    // 4. 삭제 로직
    public void remove(String ids) {
        dao.delete(ids);
    }
}