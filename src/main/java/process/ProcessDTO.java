package process;

public class ProcessDTO {
	
	private int process_key;
	private String process_code;
	private String process_name;
	private int sequence_no;
	private String process_desc;
	private char status;
	
	
	@Override
	public String toString() {
		return "ProcessDTO [process_key=" + process_key + ", process_code=" + process_code + ", process_name="
				+ process_name + ", sequence_no=" + sequence_no + ", process_desc=" + process_desc + ", status="
				+ status + "]";
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
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	
	
	
	
	

}
