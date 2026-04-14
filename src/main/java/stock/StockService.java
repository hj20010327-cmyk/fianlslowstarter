package stock;

import java.util.List;

public class StockService {
    private StockDAO dao = new StockDAO();

    // 1. 목록 조회 (인자 3개: 시작행, 끝행, 검색어)
    public List<StockDTO> getList(int startRow, int endRow, String keyword) {
        return dao.selectAll(startRow, endRow, keyword);
    }

    // 2. 신규 등록 (이 부분이 컨트롤러와 이름이 맞아야 합니다)
    public int register(StockDTO dto) {
        if (dto != null) {
            return dao.insert(dto);
        }
        return 0;
    }

    // 3. 수정
    public int update(StockDTO dto) {
        if (dto != null) {
            return dao.update(dto);
        }
        return 0;
    }
    
    // 4. 삭제
    public int remove(String ids) {
        if (ids != null && !ids.isEmpty()) {
            return dao.delete(ids);
        }
        return 0;
    }
    
    public int getTotalCount(String keyword) {
        StockDAO dao = new StockDAO();
        return dao.getTotalCount(keyword);
    }
}

