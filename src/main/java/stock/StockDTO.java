package stock;

import java.sql.Timestamp;

public class StockDTO {

    // =========================
    // TB_STOCK 컬럼
    // =========================
    private int stock_key;          // 재고 PK
    private String lot;             // LOT 번호
    private int done_qc;            // 검사후 수량
    private int wait_qc;            // 검사전 수량
    private int current_qty;        // 현재고
    private int safe_qty;           // 안전재고
    private Timestamp updated_at;   // 수정일시
    private int item_key;           // 품목 KEY

    // =========================
    // TB_ITEM 조인용 컬럼
    // =========================
    private String item_code;       // 품목코드
    private String item_name;       // 품목명
    private String item_type;       // 완제품 / 자재 / 재고
    private String spec;            // 규격
    private String unit;            // 단위
    private int price;              // 가격

    // =========================
    // getter / setter
    // =========================
    public int getStock_key() {
        return stock_key;
    }

    public void setStock_key(int stock_key) {
        this.stock_key = stock_key;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public int getDone_qc() {
        return done_qc;
    }

    public void setDone_qc(int done_qc) {
        this.done_qc = done_qc;
    }

    public int getWait_qc() {
        return wait_qc;
    }

    public void setWait_qc(int wait_qc) {
        this.wait_qc = wait_qc;
    }

    public int getCurrent_qty() {
        return current_qty;
    }

    public void setCurrent_qty(int current_qty) {
        this.current_qty = current_qty;
    }

    public int getSafe_qty() {
        return safe_qty;
    }

    public void setSafe_qty(int safe_qty) {
        this.safe_qty = safe_qty;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public int getItem_key() {
        return item_key;
    }

    public void setItem_key(int item_key) {
        this.item_key = item_key;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}