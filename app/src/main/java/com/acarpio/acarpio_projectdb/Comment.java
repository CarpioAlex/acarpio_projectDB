package com.acarpio.acarpio_projectdb;

public class Comment {
    private String title;
    private String text;

    public Comment() {
        this.title = "";
        this.text = "";
    }

    public Comment(String title, String text) {
        this.title = title;
        this.text = text;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
