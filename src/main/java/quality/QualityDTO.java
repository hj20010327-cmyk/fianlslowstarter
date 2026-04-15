package quality;

import java.sql.Date;

public class QualityDTO {
    private int quality_key;
    private String quality_code;
    private int item_key;      // DB 외래키
    private String item_code;  // 조인용
    private String item_name;  // 조인용
    private int quality_qty;   // DB의 INSPECT_QTY와 매칭 (변수명은 유지하되 DAO에서 매핑)
    private String qc_status;
    private Date inspect_date;
    private String remarks;    // DB의 DEFECT_REASON과 매칭
    
    // 추가로 DB 이미지에 있는 키값들 (필요시 사용)
    private int prod_key;      
    private int user_key;

    // Getter & Setter
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

    public int getQuality_qty() {
        return quality_qty;
    }

    // 이 메서드명이 서블릿/JSP의 파라미터 수집과 일치해야 합니다.
    public void setQuality_qty(int quality_qty) {
        this.quality_qty = quality_qty;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}