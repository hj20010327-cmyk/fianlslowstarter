package process;

public class ProcessDTO {
	
	private String process_key;
	private int sequence_no; 
	private String work_desc;
	private String process_note;
	private String code_id; 
	private String system_key;
	
	
	@Override
	public String toString() {
		return "ProcessDTO [process_key=" + process_key + ", sequence_no=" + sequence_no + ", work_desc=" + work_desc
				+ ", process_note=" + process_note + ", code_id=" + code_id + ", system_key=" + system_key + "]";
	}
	
	
	public String getProcess_key() {
		return process_key;
	}
	public void setProcess_key(String process_key) {
		this.process_key = process_key;
	}
	public int getSequence_no() {
		return sequence_no;
	}
	public void setSequence_no(int sequence_no) {
		this.sequence_no = sequence_no;
	}
	public String getWork_desc() {
		return work_desc;
	}
	public void setWork_desc(String work_desc) {
		this.work_desc = work_desc;
	}
	public String getProcess_note() {
		return process_note;
	}
	public void setProcess_note(String process_note) {
		this.process_note = process_note;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getSystem_key() {
		return system_key;
	}
	public void setSystem_key(String system_key) {
		this.system_key = system_key;
	}

}
