package model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Content {

    private int id;

    private ArrayList<String> Header;

    private ArrayList<String> Text;

    public Content(int id, ArrayList<String> header, ArrayList<String> text) {
        this.id = id;
        Header = header;
        Text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getHeader() {
        return Header;
    }

    public void setHeader(ArrayList<String> header) {
        Header = header;
    }

    public ArrayList<String> getText() {
        return Text;
    }

    public void setText(ArrayList<String> text) {
        Text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return "HELLOO";
    }
}
