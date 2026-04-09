package quality;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class QualityService {
    private QualityDAO dao = new QualityDAO();

    // 1. 기존 전체 리스트 가져오기 (기존 유지)
    public List<QualityDTO> getList() {
        return dao.selectAll();
    }

    /* 2. [추가] 검색어와 페이징 범위를 받아서 DAO에 전달하는 메서드 */
    public List<QualityDTO> getSearchList(String searchCode, String searchName, int startRow, int endRow) {
        // 이전에 DAO에 추가했던 searchQualityList 메서드를 호출합니다.
        return dao.searchQualityList(searchCode, searchName, startRow, endRow);
    }

    /* 3. [기존 추가] 삭제 요청을 DAO로 전달하는 메서드 */
    public int delete(String quality_key) {
        // Controller에서 발생한 에러를 해결하기 위해 DAO의 delete 메서드를 호출합니다.
        return dao.delete(quality_key);
    }

    /**
     * 하단 페이지네이션 번호 계산 메서드
     *
     */
    public Map<String, Object> getPageInfo(int curPage, int size) {
        // 전체 데이터 개수를 DAO에서 가져옵니다.
        int total = dao.getTotalCount(); 
        Map<String, Object> pageMap = new HashMap<>();

        // 1. 끝 페이지 번호 계산 (화면에 5개씩 번호를 보여준다고 가정: 1~5, 6~10)
        int endPage = (int) (Math.ceil(curPage / 5.0)) * 5;
        
        // 2. 시작 페이지 번호 계산
        int startPage = endPage - 4;

        // 3. 실제 마지막 페이지 번호 계산 (데이터가 부족할 때 끝번호 보정)
        int realEnd = (int) (Math.ceil((total * 1.0) / size));

        // 4. 끝 페이지 보정
        if (realEnd < endPage) {
            endPage = realEnd;
        }

        // 5. 이전(prev)/다음(next) 버튼 표시 여부
        boolean prev = startPage > 1;
        boolean next = endPage < realEnd;

        // JSP에서 쉽게 꺼내 쓸 수 있도록 Map에 저장
        pageMap.put("startPage", startPage);
        pageMap.put("endPage", endPage);
        pageMap.put("prev", prev);
        pageMap.put("next", next);
        pageMap.put("curPage", curPage);
        pageMap.put("total", total);

        return pageMap;
    }

    // ============================================================
    // [새로 추가] 기존 코드를 건드리지 않고 수정 기능을 위해 추가된 메서드
    // ============================================================

    /**
     * 4. 수정 페이지로 이동할 때, 기존에 입력된 데이터를 한 건 가져오는 메서드
     */
    public QualityDTO getOne(String quality_key) {
        return dao.selectOne(quality_key);
    }

    /**
     * 5. 사용자가 수정한 데이터를 DB에 최종 반영하는 메서드
     */
    public int update(QualityDTO dto) {
        return dao.update(dto);
    }
}