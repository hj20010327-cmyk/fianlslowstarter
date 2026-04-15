package process;

public class ProcessDTO {
	
	
	private int process_key; 
	private String process_code; 
	private String process_name;
	private int sequence_no; 
	private String process_desc;
	private String status; 
	private int item_key;
	private String item_name;
	
	
	int size = 10; 
	int page = 1; 
	
	int start = 0; 
	int end; 
	
	int keycode; 
	String keyword;
	
	
	
	
	@Override
	public String toString() {
		return "ProcessDTO [process_key=" + process_key + ", process_code=" + process_code + ", process_name="
				+ process_name + ", sequence_no=" + sequence_no + ", process_desc=" + process_desc + ", status="
				+ status + ", item_key=" + item_key + ", item_name=" + item_name + ", size=" + size + ", page=" + page
				+ ", start=" + start + ", end=" + end + ", keycode=" + keycode + ", keyword=" + keyword + "]";
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
	public int getItem_key() {
		return item_key;
	}
	public void setItem_key(int item_key) {
		this.item_key = item_key;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
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
