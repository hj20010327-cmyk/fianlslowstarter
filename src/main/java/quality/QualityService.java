package quality;

import java.util.List;

public class QualityService {
    private QualityDAO dao = new QualityDAO();

    public List<QualityDTO> getList() {
        return dao.selectAll();
    }

    public int getaddquality(QualityDTO dto) {
        return dao.insertQuality(dto); // DAO의 메서드명과 일치
    }

    public int getdeletequality(QualityDTO dto) {
        return dao.deleteQuality(dto); // deletemachine -> deleteQuality로 수정
    }

    public int getupdatquality(QualityDTO dto) {
        return dao.updateQuality(dto); // DAO의 메서드명과 일치
    }
}