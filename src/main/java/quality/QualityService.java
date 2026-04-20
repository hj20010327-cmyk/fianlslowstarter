package quality;

import java.util.List;

public class QualityService {

    // DAO 호출용 객체
    QualityDAO dao = new QualityDAO();

    // =========================
    // 목록 조회
    // =========================
    public List<QualityDTO> selectPage(int startRow, int endRow) {
        return dao.selectPage(startRow, endRow);
    }

    // =========================
    // 전체 개수
    // =========================
    public int getTotalCount() {
        return dao.getTotalCount();
    }

    // =========================
    // 검색 목록
    // =========================
    public List<QualityDTO> searchPage(String qualityCode, String itemName, String status, String inspectDate, int startRow, int endRow) {
        return dao.searchPage(qualityCode, itemName, status, inspectDate, startRow, endRow);
    }

    // =========================
    // 검색 개수
    // =========================
    public int getSearchCount(String qualityCode, String itemName, String status, String inspectDate) {
        return dao.getSearchCount(qualityCode, itemName, status, inspectDate);
    }

    // =========================
    // 등록
    // =========================
    public int addquality(QualityDTO dto) {
        return dao.addquality(dto);
    }

    // =========================
    // 수정
    // =========================
    public int updatequality(QualityDTO dto) {
        return dao.updatequality(dto);
    }

    // =========================
    // 삭제
    // =========================
    public int deleteQuality(String[] qualityKeys) {
        return dao.deleteQuality(qualityKeys);
    }

    // =========================
    // 담당자 목록
    // =========================
    public List<QualityDTO> getUserList() {
        return dao.getUserList();
    }

    // =========================
    // 작업지시 목록
    // =========================
    public List<QualityDTO> getWorkOrderList() {
        return dao.getWorkOrderList();
    }

    // =========================
    // 검사번호 목록
    // =========================
    public List<String> getQualityCodeList() {
        return dao.getQualityCodeList();
    }

    // =========================
    // 품목명 목록
    // =========================
    public List<String> getItemNameList() {
        return dao.getItemNameList();
    }

    // =========================
    // 다음 검사번호
    // =========================
    public String getNextQualityCode() {
        return dao.getNextQualityCode();
    }

    // =========================
    // 완료 처리
    // - 성공 / 이미 완료 / 실패 건수 반환
    // =========================
    public QualityCompleteResult completeQuality(String[] qualityKeys) {
        return dao.completeQuality(qualityKeys);
    }
}