package quality;

public class QualityDTO {

    private String quality_key;    
    private String quality_code;   
    private String inspect_date;   
    
    private int inspect_qty;       
    private int good_qty;          
    private int defect_qty;        
    
    private String defect_reason;  
    private String qc_status;      
    
    private String prod_key;       
    private String item_name;      

    // --- Getter & Setter ---

    public String getQuality_key() { return quality_key; }
    public void setQuality_key(String quality_key) { this.quality_key = quality_key; }

    public String getQuality_code() { return quality_code; }
    public void setQuality_code(String quality_code) { this.quality_code = quality_code; }

    public String getInspect_date() { return inspect_date; }
    public void setInspect_date(String inspect_date) { this.inspect_date = inspect_date; }

    public int getInspect_qty() { return inspect_qty; }
    public void setInspect_qty(int inspect_qty) { this.inspect_qty = inspect_qty; }

    public int getGood_qty() { return good_qty; }
    public void setGood_qty(int good_qty) { this.good_qty = good_qty; }

    public int getDefect_qty() { return defect_qty; }
    public void setDefect_qty(int defect_qty) { this.defect_qty = defect_qty; }

    public String getDefect_reason() { return defect_reason; }
    public void setDefect_reason(String defect_reason) { this.defect_reason = defect_reason; }

    public String getQc_status() { return qc_status; }
    public void setQc_status(String qc_status) { this.qc_status = qc_status; }

    public String getProd_key() { return prod_key; }
    public void setProd_key(String prod_key) { this.prod_key = prod_key; }

    public String getItem_name() { return item_name; }
    public void setItem_name(String item_name) { this.item_name = item_name; }

}