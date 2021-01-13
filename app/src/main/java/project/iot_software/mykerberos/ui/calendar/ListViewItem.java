package project.iot_software.mykerberos.ui.calendar;

public class ListViewItem {
    private String titleStr;
    private String valueStr;

    public ListViewItem(String titleStr, String valueStr) {
        this.titleStr = titleStr;
        this.valueStr = valueStr;
    }

    public void setCalendar(String calendar) {
        this.valueStr = calendar;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getTitleStr() {
        return this.titleStr;
    }

    public String getValueStr() {
        return this.valueStr;
    }
}
