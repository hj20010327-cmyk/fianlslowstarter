package quality;

import java.util.List;

public class QualityService {

    // 데이터베이스에 접근할 DAO 객체 생성
    private QualityDAO dao = new QualityDAO();

    // 컨트롤러에서 호출하는 서비스 메서드
    public List<QualityDTO> getQualityList(String searchCode, String searchName, String searchResult) {
        
        // 배운 내용: 서비스는 컨트롤러에게 받은 값을 그대로 DAO에게 전달합니다.
        // 이제 DAO가 3개의 값을 받도록 수정되었으므로 에러가 나지 않습니다.
        return dao.selectSearch(searchCode, searchName, searchResult);
        
    }
}