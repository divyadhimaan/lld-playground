package main.java.org.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verdict {
    private String verdictId;
    private String authorId;
    private String targetId;
    private String targetType;
    private String text;
    private int rating;
    private boolean isPublic;
    private int likeCount;
    private int reportCount;

    public Verdict(String authorId, String targetId, String targetType, String text, int rating, boolean isPublic) {
        this.verdictId = UUID.randomUUID().toString();
        this.authorId = authorId;
        this.targetId = targetId;
        this.targetType = targetType;
        this.text = text;
        this.rating = rating;
        this.isPublic = isPublic;
        this.likeCount = 0;
        this.reportCount = 0;
    }

}
