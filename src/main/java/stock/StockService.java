package stock;

import java.util.List;

public class StockService {

    private StockDAO dao = new StockDAO();

    // 목록 조회
    public List<StockDTO> getList(int startRow, int endRow, String keyword, String type) {
        return dao.selectList(startRow, endRow, keyword, type);
    }

    // 전체 개수 조회
    public int getTotalCount(String keyword, String type) {
        return dao.getTotalCount(keyword, type);
    }

    // 등록
    public int register(StockDTO dto) {
        if (dto != null) {
            return dao.insert(dto);
        }
        return 0;
    }

    // 수정
    public int update(StockDTO dto) {
        if (dto != null) {
            return dao.update(dto);
        }
        return 0;
    }

    // 삭제
    public int remove(String ids) {
        if (ids != null && !ids.isEmpty()) {
            return dao.delete(ids);
        }
        return 0;
    }
}