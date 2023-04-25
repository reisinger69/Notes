package reisigner.htl.notes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ToDo implements Serializable {
    private String title;
    private String details;
    private LocalDateTime date;

    public ToDo(String title, String details, LocalDateTime date) {
        this.title = title;
        this.details = details;
        this.date = date;
    }

    public String serialize() {
        return title + ";" + details + ";" + date.toString();
    }

    public static ToDo deserialize(String line) {
        String[] parts = line.split(";");
        return new ToDo(parts[0], parts[1], LocalDateTime.parse(parts[2]));
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
