package stock;


public class StockDTO {


    private int stockKey;      // STOCK_KEY
    private String lot;        // LOT
    private int inQty;         // IN_QTY
    private int outQty;        // OUT_QTY
    private int currentQty;    // CURRENT_QTY
    private int safeQty;       // SAFE_QTY
    private String updatedAt;  // UPDATED_AT
    private int itemKey;       // ITEM_KEY




    // 기본 생성자
    public StockDTO() {
    }




    // Getter & Setter (하나하나 구분해서 엔터 넣었습니다)
    public int getStockKey() {
        return stockKey;
    }

    public void setStockKey(int stockKey) {
        this.stockKey = stockKey;
    }


    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }


    public int getInQty() {
        return inQty;
    }

    public void setInQty(int inQty) {
        this.inQty = inQty;
    }


    public int getOutQty() {
        return outQty;
    }

    public void setOutQty(int outQty) {
        this.outQty = outQty;
    }


    public int getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(int currentQty) {
        this.currentQty = currentQty;
    }


    public int getSafeQty() {
        return safeQty;
    }

    public void setSafeQty(int safeQty) {
        this.safeQty = safeQty;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public int getItemKey() {
        return itemKey;
    }

    public void setItemKey(int itemKey) {
        this.itemKey = itemKey;
    }


}