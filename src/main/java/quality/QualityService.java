package quality;

import java.util.*;

public class QualityService {

    private QualityDAO dao = new QualityDAO();

    // 페이징 리스트 조회
    public List<QualityDTO> getSearchList(String searchCode, int startRow, int endRow) {
    
        return dao.selectQualityList(searchCode, startRow, endRow);
        
    }

    // 페이징 정보 계산 (예전 로직 그대로)
    public Map<String, Object> getPageInfo(int curPage, int size, String searchCode) {
    
        int total = dao.getTotalCount(searchCode);
        int totalPage = (int) Math.ceil((double) total / size);
        
        int startPage = ((curPage - 1) / 5) * 5 + 1;
        int endPage = startPage + 4;
        
        if (endPage > totalPage) endPage = totalPage;

        Map<String, Object> pageInfo = new HashMap<>();
        
        pageInfo.put("curPage", curPage);
        pageInfo.put("totalPage", totalPage);
        pageInfo.put("startPage", startPage);
        pageInfo.put("endPage", endPage);
        
        return pageInfo;
    }
}