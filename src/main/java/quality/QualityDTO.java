package quality;

import java.sql.Date;

public class QualityDTO {
	private int quality_key;
	private String quality_code;
	private String item_code;
	private String item_name;
	private String inspect_type;
	private int inspect_qty;
	private String qc_status;
	private String inspector;
	private Date inspect_date;
	private String remarks;

	// Getter & Setter
	public int getQuality_key() {
		return quality_key;
	}

	public void setQuality_key(int quality_key) {
		this.quality_key = quality_key;
	}

	public String getQuality_code() {
		return quality_code;
	}

	public void setQuality_code(String quality_code) {
		this.quality_code = quality_code;
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

	public String getInspect_type() {
		return inspect_type;
	}

	public void setInspect_type(String inspect_type) {
		this.inspect_type = inspect_type;
	}

	public int getInspect_qty() {
		return inspect_qty;
	}

	public void setInspect_qty(int inspect_qty) {
		this.inspect_qty = inspect_qty;
	}

	public String getQc_status() {
		return qc_status;
	}

	public void setQc_status(String qc_status) {
		this.qc_status = qc_status;
	}

	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public Date getInspect_date() {
		return inspect_date;
	}

	public void setInspect_date(Date inspect_date) {
		this.inspect_date = inspect_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}