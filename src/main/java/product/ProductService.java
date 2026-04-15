package product;

import java.util.List;

public class ProductService {
    private ProductDAO dao = new ProductDAO();

    // 목록 조회
    public List<ProductDTO> getList(int startRow, int endRow, String keyword) {
        return dao.selectAll(startRow, endRow, keyword);
    }

    // 전체 개수 조회
    public int getTotalCount(String keyword) {
        return dao.getTotalCount(keyword);
    }

    // [중요] ProductAddController 대응: register 이름 추가
    public int register(ProductDTO dto) { 
        return dao.insert(dto); 
    }
    
    public int insert(ProductDTO dto) { 
        return dao.insert(dto); 
    }

    // 수정
    public int update(ProductDTO dto) { 
        return dao.update(dto); 
    }

    // [중요] ProductDeleteController 대응: remove 이름 추가
    public int remove(String ids) { 
        return dao.delete(ids); 
    }

    public int delete(String ids) { 
        return dao.delete(ids); 
    }
}