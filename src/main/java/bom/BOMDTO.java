package bom;

import java.sql.Date;

public class BOMDTO {
	
	private int bom_key;
	private String bom_code;
	private int QTY;
	private String remark;
	private String bom_item_key;
	
	int size = 5; 
	int page = 1; 
	
	int start = 0; 
	int end; 
	
	int keycode;
	String keyword;
	
	
	@Override
	public String toString() {
		return "BOMDTO [bom_key=" + bom_key + ", bom_code=" + bom_code + ", QTY=" + QTY + ", remark=" + remark
				+ ", bom_item_key=" + bom_item_key + ", size=" + size + ", page=" + page + ", start=" + start + ", end="
				+ end + ", keycode=" + keycode + ", keyword=" + keyword + ", item_code=" + item_code + ", item_name="
				+ item_name + ", spec=" + spec + ", unit=" + unit + ", price=" + price + ", safe_qty=" + safe_qty + "]";
	}
	public int getBom_key() {
		return bom_key;
	}
	public void setBom_key(int bom_key) {
		this.bom_key = bom_key;
	}
	public String getBom_code() {
		return bom_code;
	}
	public void setBom_code(String bom_code) {
		this.bom_code = bom_code;
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
	public String getBom_item_key() {
		return bom_item_key;
	}
	public void setBom_item_key(String bom_item_key) {
		this.bom_item_key = bom_item_key;
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
		this.page = page;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
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
	
	
	private String item_code; 
	private String item_name; 
	private String spec;
	private String unit; 
	private int price; 
	private int safe_qty; 
	
	
	
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
	
	
}
