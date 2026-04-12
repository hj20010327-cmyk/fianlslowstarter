package product;


public class ProductDTO {


    private int itemKey;      // 아이템 고유 키
    private String itemCode;  // 제품 코드 (예: CP-A-001)
    private String itemName;  // 제품명
    private String spec;      // 규격
    private String unit;      // 단위 (EA)
    private int price;        // 단가
    private int safeQty;     // 안전 재고
    private String status;    // 사용 여부 (Y/N)


    // Getter 및 Setter (생략 가능하나 가독성을 위해 생성 권장)
    public int getItemKey() { return itemKey; }
    public void setItemKey(int itemKey) { this.itemKey = itemKey; }


    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }


    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }


    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }


    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }


    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }


    public int getSafeQty() { return safeQty; }
    public void setSafeQty(int safeQty) { this.safeQty = safeQty; }


    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}