package quality;

import java.sql.Date;

public class QualityDTO {
    private int quality_key;
    private String quality_code;
    private int inspect_qty;
    private int good_qty;      // 추가
    private int defect_qty;    // 추가
    private String defect_reason; // 추가
    private String qc_status;
    private int prod_key;      // 추가
    private Date inspect_date;
    private String item_name;  // 조인용

    // --- Getter & Setter ---
    public int getQuality_key() { return quality_key; }
    public void setQuality_key(int quality_key) { this.quality_key = quality_key; }

    public String getQuality_code() { return quality_code; }
    public void setQuality_code(String quality_code) { this.quality_code = quality_code; }

    public int getInspect_qty() { return inspect_qty; }
    public void setInspect_qty(int inspect_qty) { this.inspect_qty = inspect_qty; }

    public int getGood_qty() { return good_qty; } // DAO 에러 해결용
    public void setGood_qty(int good_qty) { this.good_qty = good_qty; }

    public int getDefect_qty() { return defect_qty; } // DAO 에러 해결용
    public void setDefect_qty(int defect_qty) { this.defect_qty = defect_qty; }

    public String getDefect_reason() { return defect_reason; } // DAO 에러 해결용
    public void setDefect_reason(String defect_reason) { this.defect_reason = defect_reason; }

    public String getQc_status() { return qc_status; }
    public void setQc_status(String qc_status) { this.qc_status = qc_status; }

    public int getProd_key() { return prod_key; } // DAO 에러 해결용
    public void setProd_key(int prod_key) { this.prod_key = prod_key; }

    public Date getInspect_date() { return inspect_date; }
    public void setInspect_date(Date inspect_date) { this.inspect_date = inspect_date; }

    public String getItem_name() { return item_name; }
    public void setItem_name(String item_name) { this.item_name = item_name; }
}