package com.example.mastersql.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class SubCourse {

    private int id;

    private String SubTitle;

    private Content content;

    public SubCourse(int id, String subTitle, Content content) {
        this.id = id;
        SubTitle = subTitle;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id + " SubCourse Title: " +SubTitle + " Content: " + content;
    }
}
