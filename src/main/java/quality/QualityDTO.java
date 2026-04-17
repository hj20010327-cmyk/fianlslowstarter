package quality;

import java.sql.Date;

public class QualityDTO {

    // =========================
    // 기본 품질 정보
    // =========================

    private int quality_key;
    private String quality_code;
    private Date inspect_date;
    private int inspect_qty;
    private int good_qty;
    private int defect_qty;
    private String defect_reason;
    private String qc_status;
    private Date created_at;

    // =========================
    // KEY 정보
    // =========================

    private int prod_key;
    private int user_key;
    private int item_key;

    // =========================
    // 추가 정보 (조인용)
    // =========================

    private String user_name;
    private String item_name;
    private String prod_name;
    private Date due_date;

    // 추가 필드가 있어도 데이터 출력에는 영향 없음
    private int stock_qty;

    public int getQuality_key() {
        return quality_key;
    }

    public void setQuality_key(int quality_key) {
        this.quality_key = quality_key;
    }

    public String getQuality_code() {
        return quality_code;
    }

    public void setQuality_code(String quality_code) {
        this.quality_code = quality_code;
    }

    public Date getInspect_date() {
        return inspect_date;
    }

    public void setInspect_date(Date inspect_date) {
        this.inspect_date = inspect_date;
    }

    public int getInspect_qty() {
        return inspect_qty;
    }

    public void setInspect_qty(int inspect_qty) {
        this.inspect_qty = inspect_qty;
    }

    public int getGood_qty() {
        return good_qty;
    }

    public void setGood_qty(int good_qty) {
        this.good_qty = good_qty;
    }

    public int getDefect_qty() {
        return defect_qty;
    }

    public void setDefect_qty(int defect_qty) {
        this.defect_qty = defect_qty;
    }

    public String getDefect_reason() {
        return defect_reason;
    }

    public void setDefect_reason(String defect_reason) {
        this.defect_reason = defect_reason;
    }

    public String getQc_status() {
        return qc_status;
    }

    public void setQc_status(String qc_status) {
        this.qc_status = qc_status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getProd_key() {
        return prod_key;
    }

    public void setProd_key(int prod_key) {
        this.prod_key = prod_key;
    }

    public int getUser_key() {
        return user_key;
    }

    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }

    public int getItem_key() {
        return item_key;
    }

    public void setItem_key(int item_key) {
        this.item_key = item_key;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public int getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(int stock_qty) {
        this.stock_qty = stock_qty;
    }
}