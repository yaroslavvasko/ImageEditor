package history;

import functionality.Picture;

/**
 * Created by yaros on 31.10.2016.
 */
public class HistoryItem {
    private Picture picture;
    private String title;

    public HistoryItem(Picture picture, String title){
        this.picture = picture;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }


}
