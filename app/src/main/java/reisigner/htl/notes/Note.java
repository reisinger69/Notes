package reisigner.htl.notes;

import java.io.Serializable;

public class Note implements Serializable {
    private String title;
    private String details;
    private String date;

    public Note(String title, String details, String date) {
        this.title = title;
        this.details = details;
        this.date = date;
    }

    public String serialize() {
        return title + ";" + details + ";" + date;
    }

    public static Note deserialize(String line) {
        String[] parts = line.split(";");
        return new Note(parts[0], parts[1], parts[2]);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
