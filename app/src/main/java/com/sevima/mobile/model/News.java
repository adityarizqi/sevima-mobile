package com.sevima.mobile.model;

public class News {
    private int id;
    private String title;
    private String image;
    private String content;

    public News(){}

    public News(int id, String title, String image, String content) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }
}
