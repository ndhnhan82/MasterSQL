package model;

import androidx.annotation.NonNull;

public class CategoryProgress {

    String name;
    int completed;
    int total;

    public CategoryProgress(String name, int completed, int total) {
        this.name = name;
        this.completed = completed;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}