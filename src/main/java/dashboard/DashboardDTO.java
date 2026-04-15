package dashboard;

import java.util.ArrayList;
import java.util.List;

public class DashboardDTO {

	private int todayProdQty;
    private int todayWorkorderCnt;
    private int todayDefectQty;
    private int lowStockCnt;

    private List<String> prodLabels = new ArrayList<>();
    private List<Integer> prodData = new ArrayList<>();

    private List<String> defectLabels = new ArrayList<>();
    private List<Integer> defectData = new ArrayList<>();

    private List<DashboardWorkorderDTO> workorderList = new ArrayList<>();
    private List<DashboardStockDTO> lowStockList = new ArrayList<>();
    private List<DashboardNoticeDTO> noticeList = new ArrayList<>();

    public int getTodayProdQty() {
        return todayProdQty;
    }
    public void setTodayProdQty(int todayProdQty) {
        this.todayProdQty = todayProdQty;
    }
    public int getTodayWorkorderCnt() {
        return todayWorkorderCnt;
    }
    public void setTodayWorkorderCnt(int todayWorkorderCnt) {
        this.todayWorkorderCnt = todayWorkorderCnt;
    }
    public int getTodayDefectQty() {
        return todayDefectQty;
    }
    public void setTodayDefectQty(int todayDefectQty) {
        this.todayDefectQty = todayDefectQty;
    }
    public int getLowStockCnt() {
        return lowStockCnt;
    }
    public void setLowStockCnt(int lowStockCnt) {
        this.lowStockCnt = lowStockCnt;
    }
    public List<String> getProdLabels() {
        return prodLabels;
    }
    public void setProdLabels(List<String> prodLabels) {
        this.prodLabels = prodLabels;
    }
    public List<Integer> getProdData() {
        return prodData;
    }
    public void setProdData(List<Integer> prodData) {
        this.prodData = prodData;
    }
    public List<String> getDefectLabels() {
        return defectLabels;
    }
    public void setDefectLabels(List<String> defectLabels) {
        this.defectLabels = defectLabels;
    }
    public List<Integer> getDefectData() {
        return defectData;
    }
    public void setDefectData(List<Integer> defectData) {
        this.defectData = defectData;
    }
    public List<DashboardWorkorderDTO> getWorkorderList() {
        return workorderList;
    }
    public void setWorkorderList(List<DashboardWorkorderDTO> workorderList) {
        this.workorderList = workorderList;
    }
    public List<DashboardStockDTO> getLowStockList() {
        return lowStockList;
    }
    public void setLowStockList(List<DashboardStockDTO> lowStockList) {
        this.lowStockList = lowStockList;
    }
    public List<DashboardNoticeDTO> getNoticeList() {
        return noticeList;
    }
    public void setNoticeList(List<DashboardNoticeDTO> noticeList) {
        this.noticeList = noticeList;
    }
	
	
	
	
}
