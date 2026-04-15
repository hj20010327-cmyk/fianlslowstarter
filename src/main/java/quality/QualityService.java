package quality;

import java.util.List;

public class QualityService {
    private QualityDAO dao = new QualityDAO();

    // 1. 목록 조회 (페이징)
    public List<QualityDTO> selectPage(int startRow, int endRow, String keyword) {
        return dao.selectPage(startRow, endRow, keyword);
    }

    // 2. 전체 데이터 개수 조회
    public int getTotalCount(String keyword) {
        return dao.getTotalCount(keyword);
    }

    // 3. 품질 데이터 등록 (메서드명 확인: insertquality)
    public int getaddquality(QualityDTO dto) {
        // DAO의 메서드명이 insertquality인지 확인하세요.
        return dao.insertquality(dto);
    }

    // 4. 품질 데이터 수정 (메서드명 확인: updatequality)
    public int getupdatquality(QualityDTO dto) {
        // DAO의 메서드명이 updatequality인지 확인하세요.
        return dao.updatequality(dto);
    }

    // 5. 품질 데이터 삭제 (메서드명 확인: deletequality)
    public int getdeletequality(String quality_code) {
        return dao.deletequality(quality_code);
    }
}