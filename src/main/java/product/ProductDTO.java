package product;

public class ProductDTO {
    private int product_key;     // 번호 (ITEM_KEY)
    private String item_code;    // 품목 코드 (추가)
    private String product_name; // 제품명 (ITEM_NAME)
    private String spec;         // 규격
    private String unit;         // 단위
    private int price;           // 가격 (추가)
    private String remarks;      // 비고

    public ProductDTO() {}

    // Getter & Setter
    public int getProduct_key() { return product_key; }
    public void setProduct_key(int product_key) { this.product_key = product_key; }

    public String getItem_code() { return item_code; } // 추가
    public void setItem_code(String item_code) { this.item_code = item_code; } // 추가

    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }

    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getPrice() { return price; } // 추가
    public void setPrice(int price) { this.price = price; } // 추가

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}