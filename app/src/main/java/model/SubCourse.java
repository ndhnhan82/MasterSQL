package com.example.mastersql.model;

import android.icu.text.CaseMap;

import androidx.annotation.NonNull;

import java.security.PrivateKey;
import java.util.ArrayList;

public class SubCourse {



    private String SubTitle;

    private Content content;

    public SubCourse(String subTitle, Content content) {

        SubTitle = subTitle;
        this.content = content;
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
        return " Content: " + content;
    }
}
