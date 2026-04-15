package comment;

import java.sql.Date;

public class CommentDTO {

	private int comment_key;
    private String content;
    private int board_key;
    private int user_key;
    private String user_name;
    private Date created_at;
    private Date updated_at;
    private String status;

    public int getComment_key() {
        return comment_key;
    }

    public void setComment_key(int comment_key) {
        this.comment_key = comment_key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBoard_key() {
        return board_key;
    }

    public void setBoard_key(int board_key) {
        this.board_key = board_key;
    }

    public int getUser_key() {
        return user_key;
    }

    public void setUser_key(int user_key) {
        this.user_key = user_key;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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
	
}
