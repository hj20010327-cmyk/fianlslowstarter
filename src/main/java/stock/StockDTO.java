package stock;

public class StockDTO {
    private String lot_key;
    private String name;
    private String type;
    private int remain;
    private String vender;
    private String status; 

    // 기본 생성자
    public StockDTO() {}

    // Getter 및 Setter 메서드
    public String getLot_key() { return lot_key; }
    public void setLot_key(String lot_key) { this.lot_key = lot_key; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getRemain() { return remain; }
    public void setRemain(int remain) { this.remain = remain; }

    public String getVender() { return vender; }
    public void setVender(String vender) { this.vender = vender; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}