package product;


import java.util.List;


public class ProductService {


    private ProductDAO dao = new ProductDAO();


    public List<ProductDTO> getProductList() {
        return dao.selectProductList();
    }
}