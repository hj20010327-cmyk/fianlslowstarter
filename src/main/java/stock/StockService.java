package stock;

import java.util.List;

public class StockService {

    private StockDAO dao = new StockDAO();

    // =========================
    // 목록 조회
    // =========================
    public List<StockDTO> getList(int startRow, int endRow, String lotKeyword, String itemType,
                                  String itemCodeKeyword, String itemNameKeyword) {
        return dao.selectList(startRow, endRow, lotKeyword, itemType, itemCodeKeyword, itemNameKeyword);
    }

    // =========================
    // 전체 개수 조회
    // =========================
    public int getTotalCount(String lotKeyword, String itemType, String itemCodeKeyword, String itemNameKeyword) {
        return dao.getTotalCount(lotKeyword, itemType, itemCodeKeyword, itemNameKeyword);
    }

    // =========================
    // 검색용 전체 품목 목록
    // =========================
    public List<StockDTO> getAllItemList() {
        return dao.selectAllItemList();
    }

    // =========================
    // 신규 등록용 품목 목록
    // =========================
    public List<StockDTO> getInsertItemList() {
        return dao.selectInsertItemList();
    }

    // =========================
    // LOT 검색 목록
    // =========================
    public List<String> getLotList() {
        return dao.selectLotList();
    }

    // =========================
    // 등록
    // =========================
    public int register(StockDTO dto) {
        if (dto == null) return 0;

        if (dto.getCurrent_qty() < 0 || dto.getItem_key() <= 0) {
            return 0;
        }

        return dao.insert(dto);
    }

    // =========================
    // 수정
    // =========================
    public int update(StockDTO dto) {
        if (dto == null) return 0;
        if (dto.getStock_key() <= 0) return 0;
        if (dto.getCurrent_qty() < 0) return 0;

        return dao.update(dto);
    }

    // =========================
    // 삭제
    // =========================
    public int remove(String ids) {
        if (ids != null && !ids.isEmpty()) {
            return dao.delete(ids);
        }
        return 0;
    }
}