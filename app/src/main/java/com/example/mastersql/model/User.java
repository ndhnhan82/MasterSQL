package com.example.mastersql.model;

public class User {
    public enum languages {En,Fr};
    private String fullName;
    private String emailAddress;
    private languages languagePrefer;
    private int age;
    private String country;
    private int topic;
    private float progress;
    private int start;

    public User(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public User(String fullName, String emailAddress, int age, String country, languages languagePrefer) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.age = age;
        this.country = country;
        this.languagePrefer = languagePrefer;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public languages getLanguagePrefer() {
        return languagePrefer;
    }

    public void setLanguagePrefer(languages languagePrefer) {
        this.languagePrefer = languagePrefer;
    }
}
