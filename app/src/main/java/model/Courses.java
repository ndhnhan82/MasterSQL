package com.example.mastersql.model;

import androidx.annotation.NonNull;

public class Courses {


    private SubCourse subCourse;

    public Courses( SubCourse subCourse) {

        this.subCourse = subCourse;
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
