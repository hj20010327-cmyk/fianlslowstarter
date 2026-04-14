package production;

import java.util.List;

public class ProductionService {

	private ProductionDAO productionDAO = new ProductionDAO();

    public List<ProductionDTO> getProductionList(String startDate, String endDate, String keyword, String status) {
        return productionDAO.selectProductionList(startDate, endDate, keyword, status);
    }

    public ProductionSummaryDTO getProductionSummary(String startDate, String endDate, String keyword, String status) {
        return productionDAO.selectProductionSummary(startDate, endDate, keyword, status);
    }
    
    public int insertProduction(ProductionDTO dto) {
        return productionDAO.insertProduction(dto);
    }

    public int updateProduction(ProductionDTO dto) {
        return productionDAO.updateProduction(dto);
    }
	
}
