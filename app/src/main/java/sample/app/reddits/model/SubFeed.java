package sample.app.reddits.model;

/**
 * Created by root on 12/12/16.
 */
public class SubFeed {

    private String text, url, user, id, parentId;
    private int score, comments;
    private boolean stickied;

    public SubFeed(String id, String parentId, String text, String url, String user, int score, int comments, boolean stickied) {
        this.id = id;
        this.parentId = parentId;
        this.text = text;
        this.url = url;
        this.user = user;
        this.score = score;
        this.comments = comments;
        this.stickied = stickied;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public boolean isStickied() {
        return stickied;
    }

    public void setStickied(boolean stickied) {
        this.stickied = stickied;
    }
}
