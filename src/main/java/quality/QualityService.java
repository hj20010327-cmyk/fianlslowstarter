package quality;

import java.util.List;

public class QualityService {

    private QualityDAO dao = new QualityDAO();

    // 인자 3개를 받아 DAO로 전달합니다.
    public List<QualityDTO> selectPage(int startRow, int endRow, String keyword) {
        return dao.selectPage(startRow, endRow, keyword);
    }

    public int getaddquality(QualityDTO dto) {
        return dao.insertquality(dto);
    }

    public int getupdatquality(QualityDTO dto) {
        return dao.updatequality(dto);
    }

    public int getdeletequality(QualityDTO dto) {
        return dao.deletequality(dto);
    }
}