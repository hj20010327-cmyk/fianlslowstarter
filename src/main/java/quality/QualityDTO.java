package quality;

import java.util.Date;

public class QualityDTO {
    // DB 컬럼 순서 및 이름에 맞춘 필드 구성
    private String quality_key;    // QUALITY_KEY (PK)
    private String prod_key;       // PROD_KEY
    private String item_key;       // ITEM_KEY
    private Date inspect_date;     // INSPECT_DATE
    private int inspect_qty;       // INSPECT_QTY (검사 수량)
    private int good_qty;          // GOOD_QTY (양품 수량)
    private int defect_qty;        // DEFECT_QTY (불량 수량)
    private String defect_reason;  // DEFECT_REASON (불량 사유)
    private String qc_status;      // QC_STATUS (상태: PASS/FAIL)
    private String user_key;       // USER_KEY (담당자)
    private Date created_at;       // CREATED_AT (등록 일시)

    // =========================================================
    // [추가 사항] 조인을 통해 가져온 품목명을 저장하기 위한 필드입니다.
    // =========================================================
    private String item_name;      // ITEM_NAME (예: 컴프레셔 A)

    // 기본 생성자
    public QualityDTO() {}

    // Getter 및 Setter 메소드
    public String getQuality_key() {
        return quality_key;
    }

    public void setQuality_key(String quality_key) {
        this.quality_key = quality_key;
    }

    public String getProd_key() {
        return prod_key;
    }

    public void setProd_key(String prod_key) {
        this.prod_key = prod_key;
    }

    public String getItem_key() {
        return item_key;
    }

    public void setItem_key(String item_key) {
        this.item_key = item_key;
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

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

   
    //  품목명(item_name)을 위한 Getter와 Setter입니다.
   
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}