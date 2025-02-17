package main.java.org.example.model;

import lombok.Data;

@Data
public class VerdictRequest {
    private String authorId;
    private String targetId;
    private String targetType;
    private String text;
    private int rating;
    private boolean isPublic;
}
