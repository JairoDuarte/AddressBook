package gq.tiinline.www.tutolistcontact.data;

/**
 * Created by Jairo Duarte on 31/01/2017.
 */

public class Group {
    private int id;
    private String title;
    private String image;

    public Group(int id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
