package com.example.mastersql.model;

import androidx.annotation.NonNull;

public class Courses {

    private int id;
    private SubCourse subCourse;

    public Courses(int id, SubCourse subCourse) {
        this.id = id;
        this.subCourse = subCourse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubCourse getSubCourse() {
        return subCourse;
    }

    public void setSubCourse(SubCourse subCourse) {
        this.subCourse = subCourse;
    }

    @NonNull
    @Override
    public String toString() {
        return subCourse.toString();
    }
}
