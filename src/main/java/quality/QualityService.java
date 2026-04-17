package quality;

import java.util.List;

public class QualityService {

    QualityDAO dao = new QualityDAO();

    public List<QualityDTO> selectPage(int startRow, int endRow) {
        return dao.selectPage(startRow, endRow);
    }

    public int getTotalCount() {
        return dao.getTotalCount();
    }

    public List<QualityDTO> searchPage(String qualityCode, String itemName, String status, String inspectDate, int startRow, int endRow) {
        return dao.searchPage(qualityCode, itemName, status, inspectDate, startRow, endRow);
    }

    public int getSearchCount(String qualityCode, String itemName, String status, String inspectDate) {
        return dao.getSearchCount(qualityCode, itemName, status, inspectDate);
    }

    public int addquality(QualityDTO dto) {
        return dao.addquality(dto);
    }

    public int updatequality(QualityDTO dto) {
        return dao.updatequality(dto);
    }

    public int deleteQuality(String[] qualityKeys) {
        return dao.deleteQuality(qualityKeys);
    }

    public List<QualityDTO> getUserList() {
        return dao.getUserList();
    }

    public List<QualityDTO> getWorkOrderList() {
        return dao.getWorkOrderList();
    }

    public List<String> getQualityCodeList() {
        return dao.getQualityCodeList();
    }

    public List<String> getItemNameList() {
        return dao.getItemNameList();
    }

    public String getNextQualityCode() {
        return dao.getNextQualityCode();
    }
}