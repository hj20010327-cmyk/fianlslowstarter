package quality;

import java.util.List;

public class QualityService {

    private QualityDAO dao = new QualityDAO();


    /**
     * 품질 목록 조회 (페이징 + 검색 처리)
     * @param page    : 현재 페이지 번호
     * @param keyword : 검색어 (품목명 또는 검사번호)
     * @param status  : 품질 상태 (합격, 재검, 불합격)
     */
    public List<QualityDTO> getList(int page, String keyword, String status) {

        // 1. 한 페이지에 보여줄 데이터 개수 설정
        int size = 5; 

        
        // 2. DB 페이징 쿼리에 사용할 시작(start)과 끝(end) 번호 계산
        // 1페이지 -> 1~5, 2페이지 -> 6~10
        int start = 1 + (page - 1) * size;
        int end = page * size;


        // 3. [중요] 이클립스 콘솔에서 데이터 전달 확인 (디버깅용)
        // 조회가 안 될 때 이 코드가 있으면 이클립스 Console 창에서 값을 확인할 수 있습니다.
        System.out.println("--- QualityService 실행 ---");
        System.out.println("페이지: " + page + " (" + start + " ~ " + end + ")");
        System.out.println("검색어: " + keyword);
        System.out.println("상태값: " + status);


        // 4. [핵심 수정] DAO의 selectAll 메서드 호출 시 검색 인자(keyword, status)를 반드시 포함합니다.
        // 이 인자들이 넘어가야 DAO에서 WHERE절을 만들어 DB 조회를 수행합니다.
        // (주의: QualityDAO의 selectAll 메서드 정의부도 인자 4개를 받도록 수정되어야 합니다!)
        return dao.selectAll(start, end, keyword, status); 

    }


    /**
     * 신규 품질 기록 등록
     */
    public int getaddquality(QualityDTO dto) {

        System.out.println("품질 등록 요청: " + dto.getItem_name());
        return dao.insertQuality(dto);

    }


    /**
     * 품질 기록 삭제
     */
    public int getdeletequality(QualityDTO dto) {

        System.out.println("품질 삭제 요청 Key: " + dto.getQuality_key());
        return dao.deleteQuality(dto);

    }


    /**
     * 품질 기록 수정
     */
    public int getupdatquality(QualityDTO dto) {

        System.out.println("품질 수정 요청 Key: " + dto.getQuality_key());
        return dao.updateQuality(dto);

    }

}