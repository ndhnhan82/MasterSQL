package model;

import androidx.annotation.NonNull;

public class User {
    public enum languages {English, French};
    public enum roles{Admin, NormalUser}

    @NonNull
    @Override
    public String toString() {
        return "Email: "+emailAddress+", name: "+fullName;
    }

    private String fullName;
    private String emailAddress;
    private languages languagePrefer;
    private int age;
    private String country;
    private int currentTopic;
    private float progress;
    private int start;

    public roles getRole() {
        return role;
    }

    public void setRole(roles role) {
        this.role = role;
    }

    private roles role;
    public User(){};

    public User(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public User(String emailAddress, roles role) {
        this.emailAddress = emailAddress;
        this.role = role;
    }

    public User(String fullName, String emailAddress, languages languagePrefer, int age, String country, int currentTopic, float progress, int start, roles role) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.languagePrefer = languagePrefer;
        this.age = age;
        this.country = country;
        this.currentTopic = currentTopic;
        this.progress = progress;
        this.start = start;
        this.role = roles.NormalUser;
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
    public int getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(int currentTopic) {
        this.currentTopic = currentTopic;
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
