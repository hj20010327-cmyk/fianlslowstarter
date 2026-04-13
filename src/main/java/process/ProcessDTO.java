package process;

public class ProcessDTO {
	
	
	private int process_key; 
	private String process_code; 
	private String process_name;
	private int sequence_no; 
	private String process_desc;
	private String status; 
	private String process_item_key; 
	
	
	int size = 5; 
	int page = 1; 
	
	int start = 0; 
	int end; 
	
	int keycode; 
	String keyword; 
	
	
	@Override
	public String toString() {
		return "ProcessDTO [process_key=" + process_key + ", process_code=" + process_code + ", process_name="
				+ process_name + ", sequence_no=" + sequence_no + ", process_desc=" + process_desc + ", status="
				+ status + ", process_item_key=" + process_item_key + ", size=" + size + ", page=" + page + ", start="
				+ start + ", end=" + end + ", keycode=" + keycode + ", keyword=" + keyword + ", item_key=" + item_key
				+ ", item_code=" + item_code + ", item_name=" + item_name + ", spec=" + spec + ", unit=" + unit
				+ ", price=" + price + ", safe_qty=" + safe_qty + "]";
	}
	public int getProcess_key() {
		return process_key;
	}
	public void setProcess_key(int process_key) {
		this.process_key = process_key;
	}
	public String getProcess_code() {
		return process_code;
	}
	public void setProcess_code(String process_code) {
		this.process_code = process_code;
	}
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	public int getSequence_no() {
		return sequence_no;
	}
	public void setSequence_no(int sequence_no) {
		this.sequence_no = sequence_no;
	}
	public String getProcess_desc() {
		return process_desc;
	}
	public void setProcess_desc(String process_desc) {
		this.process_desc = process_desc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcess_item_key() {
		return process_item_key;
	}
	public void setProcess_item_key(String process_item_key) {
		this.process_item_key = process_item_key;
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
	
	private int item_key;
	private String item_code; 
	private String item_name; 
	private String spec;
	private String unit; 
	private int price; 
	private int safe_qty;
	
	
	
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
