package apiPayload;

public class Pet {

    private int id;
    private int categoryId;
    private String categoryName;
    private String name;
    private String photoUrls;
    private int tagId;
    private String tagName;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{\r\n  \"id\": " + id + ",\r\n  " +
                "\"category\": {\r\n    \"id\": " + categoryId + ",\r\n  \"name\": \"" + categoryName +"\"\r\n  },\r\n  " +
                "\"name\": \"" + name + "\",\r\n " +
                "\"photoUrls\": [\r\n    \"" + photoUrls + "\"\r\n  ],\r\n  " +
                "\"tags\": [\r\n    {\r\n      \"id\": " + tagId + ",\r\n      \"name\": \"" + tagName + "\"\r\n    }\r\n  ],\r\n  " +
                "\"status\": \"" + status + "\"\r\n}";
    }

}
