package quality;

import java.util.List;

public class QualityService {

    QualityDAO dao = new QualityDAO();

    // 목록 조회
    public List<QualityDTO> selectPage(int startRow, int endRow) {
        return dao.selectPage(startRow, endRow);
    }

    // 전체 개수
    public int getTotalCount() {
        return dao.getTotalCount();
    }

    // 검색
    public List<QualityDTO> searchPage(String qualityCode, String status, String inspectDate, int startRow, int endRow) {
        return dao.searchPage(qualityCode, status, inspectDate, startRow, endRow);
    }

    // 검색 개수
    public int getSearchCount(String qualityCode, String status, String inspectDate) {
        return dao.getSearchCount(qualityCode, status, inspectDate);
    }

    // 등록
    public int addquality(QualityDTO dto) {
        return dao.addquality(dto);
    }

    // 수정
    public int updatequality(QualityDTO dto) {
        return dao.updatequality(dto);
    }

    // 삭제
    public int deleteQuality(String[] qualityKeys) {
        return dao.deleteQuality(qualityKeys);
    }

    // ⭐ 추가: 담당자 목록
    public List<QualityDTO> getUserList() {
        return dao.getUserList();
    }
}