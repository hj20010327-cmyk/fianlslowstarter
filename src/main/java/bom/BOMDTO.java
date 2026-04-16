package bom;

import java.sql.Date;

public class BOMDTO {
	
	private int bom_key;
	private String bom_code;
	private int qty;
	private String remark;
	private int item_key;
	private int parent_item_key;
	private String item_name;
	private String parent_item_name;
	
	
	int size = 10; 
	int page = 1; 
	
	int start = 0; 
	int end; 
	
	int keycode;
	String keyword;
	
	
	
	
	@Override
	public String toString() {
		return "BOMDTO [bom_key=" + bom_key + ", bom_code=" + bom_code + ", qty=" + qty + ", remark=" + remark
				+ ", item_key=" + item_key + ", parent_item_key=" + parent_item_key + ", item_name=" + item_name
				+ ", parent_item_name=" + parent_item_name + ", size=" + size + ", page=" + page + ", start=" + start
				+ ", end=" + end + ", keycode=" + keycode + ", keyword=" + keyword + "]";
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
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getItem_key() {
		return item_key;
	}
	public void setItem_key(int item_key) {
		this.item_key = item_key;
	}
	public int getParent_item_key() {
		return parent_item_key;
	}
	public void setParent_item_key(int parent_item_key) {
		this.parent_item_key = parent_item_key;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getParent_item_name() {
		return parent_item_name;
	}
	public void setParent_item_name(String parent_item_name) {
		this.parent_item_name = parent_item_name;
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
	
	
	
	
}
