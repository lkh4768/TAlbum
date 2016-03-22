package kr.wes.talbum.model;

/**
 * Created by wes on 16. 3. 15.
 */
public class Image {
    private Bucket bucket;
    private String date;
    private String id;
    private String path;

    public Image(Bucket bucket, String date, String id, String path) {
        this.bucket = bucket;
        this.date = date;
        this.id = id;
        this.path = path;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }
}
