package product;

public class ProductDTO {
    private int product_key;
    private String item_code;
    private String product_name;
    private String spec;
    private String unit;
    private int price;

    // Getter & Setter
    public int getProduct_key() { return product_key; }
    public void setProduct_key(int product_key) { this.product_key = product_key; }
    public String getItem_code() { return item_code; }
    public void setItem_code(String item_code) { this.item_code = item_code; }
    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }
    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}