package quality;

import java.util.List;

public class QualityService {
    private QualityDAO dao = new QualityDAO();

    // [수정] 컨트롤러에서 보낸 page 번호를 인자로 받습니다.
    public List<QualityDTO> getList(int page) {
        // 한 페이지에 보여줄 데이터 개수 (5개)
        int size = 5; 
        
        // DB 쿼리에 들어갈 시작 번호와 끝 번호 계산
        // 1페이지: 1~5, 2페이지: 6~10
        int start = 1 + (page - 1) * size;
        int end = page * size;
        
        // [수정] DAO의 메서드도 start와 end를 받도록 호출합니다.
        // 기존 dao.selectAll() 대신 페이징용 메서드를 호출해야 합니다.
        return dao.selectAll(start, end); 
    }

    public int getaddquality(QualityDTO dto) {
        return dao.insertQuality(dto);
    }

    public int getdeletequality(QualityDTO dto) {
        return dao.deleteQuality(dto);
    }

    public int getupdatquality(QualityDTO dto) {
        return dao.updateQuality(dto);
    }
}