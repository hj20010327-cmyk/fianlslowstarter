package bom;

import java.sql.Date;

public class BOMDTO {
	
	private int bom_key;
	private String product_item_key;
	private String material_item_key;
	private int QTY;
	private String remark;
	
	int size = 5; 
	int page = 1; 
	
	int start = 0; 
	int end; 
	
	int keycode;
	String keyword;
	
	
	public int getKeycode() {
		return keycode;
	}
	public void setKeycode(int keycode) {
		this.keycode = keycode;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start=start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public int getSize() {
		return size;  
	}
	public void setSize(int size) {
		this.size = size; 
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page=page;
	}
	
	
	
	public int getBom_key() {
		return bom_key;
	}
	public void setBom_key(int bom_key) {
		this.bom_key = bom_key;
	}
	public String getProduct_item_key() {
		return product_item_key;
	}
	public void setProduct_item_key(String product_item_key) {
		this.product_item_key = product_item_key;
	}
	public String getMaterial_item_key() {
		return material_item_key;
	}
	public void setMaterial_item_key(String material_item_key) {
		this.material_item_key = material_item_key;
	}
	public int getQTY() {
		return QTY;
	}
	public void setQTY(int qTY) {
		QTY = qTY;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


	private int item_key; 
	private String item_code;
	private String item_name;
	private String item_type;
	private String spec;
	private String unit;
	private int price;
	private int safe_qty;
	private char status; 
	private Date created_at;
	
	
	
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
	public int getSafe_qty() {
		return safe_qty;
	}
	public void setSafe_qty(int safe_qty) {
		this.safe_qty = safe_qty;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	
	@Override
	public String toString() {
		return "BOMDTO [bom_key=" + bom_key + ", product_item_key=" + product_item_key + ", material_item_key="
				+ material_item_key + ", QTY=" + QTY + ", remark=" + remark + ", item_key=" + item_key + ", item_code="
				+ item_code + ", item_name=" + item_name + ", item_type=" + item_type + ", spec=" + spec + ", unit="
				+ unit + ", price=" + price + ", safe_qty=" + safe_qty + ", status=" + status + ", created_at="
				+ created_at + "]";
	}
	
	
	
}
