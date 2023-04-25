package reisigner.htl.notes;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ToDo implements Serializable {
    private String title;
    private String details;
    private long date;

    public ToDo(String title, String details, LocalDateTime date) {
        this.title = title;
        this.details = details;
        this.date = date.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public String serialize() {
        return title + ";" + details + ";" + date;
    }

    public static ToDo deserialize(String line) {
        String[] parts = line.split(";");
        return new ToDo(parts[0], parts[1], LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.parseInt(parts[2])), ZoneId.systemDefault()));
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
        return  LocalDateTime.ofInstant(Instant.ofEpochSecond(this.date), ZoneId.systemDefault());
    }

}
