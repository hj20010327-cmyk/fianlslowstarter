package quality;

import java.util.List;

public class QualityService {

    // DAO 객체 생성
    QualityDAO dao = new QualityDAO();

    // 전체 목록 조회
    // 페이징 처리된 목록을 가져옴
    public List<QualityDTO> selectPage(int startRow, int endRow) {
        return dao.selectPage(startRow, endRow);
    }

    // 전체 데이터 개수 조회
    // 전체 페이지 수 계산할 때 사용
    public int getTotalCount() {
        return dao.getTotalCount();
    }

    // 검색 목록 조회
    // 검사번호, 상태, 검사일자로 검색하면서 페이징 처리
    public List<QualityDTO> searchPage(String qualityCode, String status, String inspectDate, int startRow, int endRow) {
        return dao.searchPage(qualityCode, status, inspectDate, startRow, endRow);
    }

    // 검색 결과 개수 조회
    // 검색했을 때 전체 몇 건인지 계산할 때 사용
    public int getSearchCount(String qualityCode, String status, String inspectDate) {
        return dao.getSearchCount(qualityCode, status, inspectDate);
    }

    // 신규 등록
    // 품질 데이터를 DB에 저장
    public int addquality(QualityDTO dto) {
        return dao.addquality(dto);
    }

    // 수정
    // 품질 데이터를 수정
    public int updatequality(QualityDTO dto) {
        return dao.updatequality(dto);
    }

    // 삭제
    // 체크박스로 선택한 여러 건 삭제
    public int deleteQuality(String[] qualityKeys) {
        return dao.deleteQuality(qualityKeys);
    }

    // 담당자 목록 조회
    // 등록/수정 모달에서 담당자명 select 박스에 뿌릴 때 사용
    public List<QualityDTO> getUserList() {
        return dao.getUserList();
    }
}