package machine;

public class MachineDTO {
	  	private String systemKey;       // 설비번호
	    private int systemCode;       // 설비코드
	    private String systemName;    // 설비 이름
	    private String systemStatus;  // 설비 상태 run check stop
		public String getSystemKey() {
			return systemKey;
		}
		public void setSystemKey(String systemKey) {
			this.systemKey = systemKey;
		}
		public int getSystemCode() {
			return systemCode;
		}
		public void setSystemCode(int systemCode) {
			this.systemCode = systemCode;
		}
		public String getSystemName() {
			return systemName;
		}
		public void setSystemName(String systemName) {
			this.systemName = systemName;
		}
		public String getSystemStatus() {
			return systemStatus;
		}
		public void setSystemStatus(String systemStatus) {
			this.systemStatus = systemStatus;
		}
		@Override
		public String toString() {
			return "MachineDTO [systemKey=" + systemKey + ", systemCode=" + systemCode + ", systemName=" + systemName
					+ ", systemStatus=" + systemStatus + "]";
		}
	
}