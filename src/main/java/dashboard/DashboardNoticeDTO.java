package dashboard;

import java.sql.Date;

public class DashboardNoticeDTO {

	private int board_key;
	private String title;
	private Date created_at;
	private int view_count;

	public int getBoard_key() {
		return board_key;
	}

	public void setBoard_key(int board_key) {
		this.board_key = board_key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	@Override
	public String toString() {
		return "DashboardNoticeDTO [board_key=" + board_key + ", title=" + title + ", created_at=" + created_at
				+ ", view_count=" + view_count + "]";
	}

	
}
