package production;

public class ProductionOptionDTO {

	 private int work_order_key;
	    private String work_order_code;

	    private int plan_key;
	    private String plan_code;
	    private int plan_qty;

	    private String item_name;

	    private int work_user_key;
	    private String work_user_name;

	    private int quality_key;
	    private int good_qty;
	    private int defect_qty;

	    public int getWork_order_key() {
	        return work_order_key;
	    }

	    public void setWork_order_key(int work_order_key) {
	        this.work_order_key = work_order_key;
	    }

	    public String getWork_order_code() {
	        return work_order_code;
	    }

	    public void setWork_order_code(String work_order_code) {
	        this.work_order_code = work_order_code;
	    }

	    public int getPlan_key() {
	        return plan_key;
	    }

	    public void setPlan_key(int plan_key) {
	        this.plan_key = plan_key;
	    }

	    public String getPlan_code() {
	        return plan_code;
	    }

	    public void setPlan_code(String plan_code) {
	        this.plan_code = plan_code;
	    }

	    public int getPlan_qty() {
	        return plan_qty;
	    }

	    public void setPlan_qty(int plan_qty) {
	        this.plan_qty = plan_qty;
	    }

	    public String getItem_name() {
	        return item_name;
	    }

	    public void setItem_name(String item_name) {
	        this.item_name = item_name;
	    }

	    public int getWork_user_key() {
	        return work_user_key;
	    }

	    public void setWork_user_key(int work_user_key) {
	        this.work_user_key = work_user_key;
	    }

	    public String getWork_user_name() {
	        return work_user_name;
	    }

	    public void setWork_user_name(String work_user_name) {
	        this.work_user_name = work_user_name;
	    }

	    public int getQuality_key() {
	        return quality_key;
	    }

	    public void setQuality_key(int quality_key) {
	        this.quality_key = quality_key;
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

		@Override
		public String toString() {
			return "ProductionOptionDTO [work_order_key=" + work_order_key + ", work_order_code=" + work_order_code
					+ ", plan_key=" + plan_key + ", plan_code=" + plan_code + ", plan_qty=" + plan_qty + ", item_name="
					+ item_name + ", work_user_key=" + work_user_key + ", work_user_name=" + work_user_name
					+ ", quality_key=" + quality_key + ", good_qty=" + good_qty + ", defect_qty=" + defect_qty + "]";
		}
	    
	
}
