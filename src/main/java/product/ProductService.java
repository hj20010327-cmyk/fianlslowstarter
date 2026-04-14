package product;

import java.util.List;

public class ProductService {
    private ProductDAO dao = new ProductDAO();

    public List<ProductDTO> getList(int startRow, int endRow, String keyword) {
        return dao.selectAll(startRow, endRow, keyword);
    }

    public int getTotalCount(String keyword) {
        return dao.getTotalCount(keyword);
    }

    public int register(ProductDTO dto) { return dao.insert(dto); }
    public int update(ProductDTO dto) { return dao.update(dto); }
    public int remove(String ids) { return dao.delete(ids); }
}