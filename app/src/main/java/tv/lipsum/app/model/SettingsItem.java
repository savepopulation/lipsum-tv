package tv.lipsum.app.model;

/**
 * Created by tyln on 28.10.2015.
 */
public class SettingsItem {
    private int id;
    private int iconId;
    private String title;
    private int type;

    public SettingsItem(int id, int iconId, String title, int type) {
        this.id = id;
        this.iconId = iconId;
        this.title = title;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
