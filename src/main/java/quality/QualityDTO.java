package quality;

import java.sql.Date;

public class QualityDTO {

    private String quality_code;
    private String item_name;
    private int inspect_qty;
    private String qc_status;
    private Date inspect_date;

    // Getter & Setter
    public String getQuality_code() {
        return quality_code;
    }

    public void setQuality_code(String quality_code) {
        this.quality_code = quality_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getInspect_qty() {
        return inspect_qty;
    }

    public void setInspect_qty(int inspect_qty) {
        this.inspect_qty = inspect_qty;
    }

    public String getQc_status() {
        return qc_status;
    }

    public void setQc_status(String qc_status) {
        this.qc_status = qc_status;
    }

    public Date getInspect_date() {
        return inspect_date;
    }

    public void setInspect_date(Date inspect_date) {
        this.inspect_date = inspect_date;
    }
}