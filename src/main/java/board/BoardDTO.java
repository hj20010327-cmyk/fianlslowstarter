package board;

import java.sql.Date;

public class BoardDTO {

	private int board_key;
	private String board_type;
	private String title;
	private String content;
	private int user_key;
	private int view_count;
	private Date created_at;
	private Date updated_at;
	private String status;
	private String user_name;
	
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getBoard_key() {
		return board_key;
	}
	public void setBoard_key(int board_key) {
		this.board_key = board_key;
	}
	public String getBoard_type() {
		return board_type;
	}
	public void setBoard_type(String board_type) {
		this.board_type = board_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getUser_key() {
		return user_key;
	}
	public void setUser_key(int user_key) {
		this.user_key = user_key;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BoardDTO [board_key=" + board_key + ", board_type=" + board_type + ", title=" + title + ", content="
				+ content + ", user_key=" + user_key + ", view_count=" + view_count + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + ", status=" + status + ", user_name=" + user_name + "]";
	}
	
	
	
	
}
