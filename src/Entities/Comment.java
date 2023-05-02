package Entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Comment {
    private int id;
    private int userId;
    private int articleId;
    private String content;
    private String userName;
    

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private LocalDateTime createdAt;

    public Comment(int id, int userId, int articleId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Comment(int userId, int articleId, String content) {
        this.userId = userId;
        this.articleId = articleId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", userId=" + userId + ", articleId=" + articleId + ", content=" + content
                + ", createdAt=" + createdAt + "]";
    }
}

