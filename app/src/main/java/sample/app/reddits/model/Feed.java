package sample.app.reddits.model;

/**
 * Created by root on 11/12/16.
 */
public class Feed {

    private String id, icon, header, text, url, description, keycolor;
    private int subscribers;

    public Feed(String id, String description, String icon, String header, String text, String url, String keycolor, int subscribers) {
        this.id = id;
        this.description = description;
        this.icon = icon;
        this.header = header;
        this.text = text;
        this.url = url;
        this.keycolor = keycolor;
        this.subscribers = subscribers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public String getKeycolor() {
        return keycolor;
    }

    public void setKeycolor(String keycolor) {
        this.keycolor = keycolor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
