package machine;

import java.sql.Date;

public class MachineDTO {
	private int machineKey;
	private String machineCode;
	private String machineName;
	private String machineStatus;
	private Date buyDate;
	private Date lastCheckDate;
	private String remark;
	private String status;
	private Date createdAt;
	private int processKey;
	private String processName;
	private int sequenceNo;
	public int getMachineKey() {
		return machineKey;
	}
	public void setMachineKey(int machineKey) {
		this.machineKey = machineKey;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public int getProcessKey() {
		return processKey;
	}
	public void setProcessKey(int processKey) {
		this.processKey = processKey;
	}
	public String getMachineStatus() {
		return machineStatus;
	}
	public void setMachineStatus(String machineStatus) {
		this.machineStatus = machineStatus;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public Date getLastCheckDate() {
		return lastCheckDate;
	}
	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public int getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	@Override
	public String toString() {
		return "MachineDTO [machineKey=" + machineKey + ", machineCode=" + machineCode + ", machineName=" + machineName
				+ ", machineStatus=" + machineStatus + ", buyDate=" + buyDate + ", lastCheckDate=" + lastCheckDate
				+ ", remark=" + remark + ", status=" + status + ", createdAt=" + createdAt + ", processKey="
				+ processKey + ", processName=" + processName + ", sequenceNo=" + sequenceNo + "]";
	}
	
	
	
	
}