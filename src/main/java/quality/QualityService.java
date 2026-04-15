package quality;

import java.util.List;

public class QualityService {
    private QualityDAO dao = new QualityDAO();

    public List<QualityDTO> selectPage(int startRow, int endRow, String keyword) {
        return dao.selectPage(startRow, endRow, keyword);
    }

    public int getTotalCount(String keyword) {
        return dao.getTotalCount(keyword);
    }

    public int getaddquality(QualityDTO dto) {
        return dao.insertquality(dto);
    }

    public int getupdatquality(QualityDTO dto) {
        return dao.updatequality(dto);
    }

    public int getdeletequality(String quality_code) {
        return dao.deletequality(quality_code);
    }
}