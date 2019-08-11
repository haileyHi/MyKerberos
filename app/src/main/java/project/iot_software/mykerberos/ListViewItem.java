package project.iot_software.mykerberos;

import android.graphics.Bitmap;

public class ListViewItem {
    private Bitmap thumbnail;
    private String titleStr;
    private String calendar;

    public void setThumbnail(Bitmap img) {
        thumbnail = img;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public Bitmap getThumbnail() {
        return this.thumbnail;
    }

    public String getCalendar() {
        return this.calendar;
    }
    public String getTitleStr() {
        return this.titleStr;
    }
}
