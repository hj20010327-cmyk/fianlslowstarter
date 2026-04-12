package stock;

import java.sql.Timestamp;

public class StockDTO {

    private int stock_key;      // 재고 고유 번호 (PK)
    private String lot;         // LOT 번호 (상품 식별 번호)
    private int in_qty;         // 입고 수량
    private int out_qty;        // 출고 수량
    private int current_qty;    // 현재 재고량 (입고 - 출고)
    private int safe_qty;       // 안전 재고량 (최소 유지 수량)
    private Timestamp updated_at; // 데이터 수정 일시
    private int item_key;       // 품목 고유 번호 (FK)

    // --- Getter / Setter (데이터를 주고받기 위한 메소드) ---

    public int getStock_key() { return stock_key; }
    public void setStock_key(int stock_key) { this.stock_key = stock_key; }

    public String getLot() { return lot; }
    public void setLot(String lot) { this.lot = lot; }

    public int getIn_qty() { return in_qty; }
    public void setIn_qty(int in_qty) { this.in_qty = in_qty; }

    public int getOut_qty() { return out_qty; }
    public void setOut_qty(int out_qty) { this.out_qty = out_qty; }

    public int getCurrent_qty() { return current_qty; }
    public void setCurrent_qty(int current_qty) { this.current_qty = current_qty; }

    public int getSafe_qty() { return safe_qty; }
    public void setSafe_qty(int safe_qty) { this.safe_qty = safe_qty; }

    public Timestamp getUpdated_at() { return updated_at; }
    public void setUpdated_at(Timestamp updated_at) { this.updated_at = updated_at; }

    public int getItem_key() { return item_key; }
    public void setItem_key(int item_key) { this.item_key = item_key; }
}