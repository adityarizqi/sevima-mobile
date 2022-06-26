package com.sevima.mobile.model;

public class Course {
    private int id;
    private String title;
    private String price;
    private String image;
    private String content;

    public Course(){}

    public Course(int id, String title, String price, String image, String content) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }
}
