package report;

public class DefectReasonDTO {
	
	private String defectReason;
    private int defectCount;
    private int totalDefectQty;

    public String getDefectReason() {
        return defectReason;
    }

    public void setDefectReason(String defectReason) {
        this.defectReason = defectReason;
    }

    public int getDefectCount() {
        return defectCount;
    }

    public void setDefectCount(int defectCount) {
        this.defectCount = defectCount;
    }

    public int getTotalDefectQty() {
        return totalDefectQty;
    }

    public void setTotalDefectQty(int totalDefectQty) {
        this.totalDefectQty = totalDefectQty;
    }
	
}
