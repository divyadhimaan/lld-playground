package main.java.org.example.factory;

import org.example.model.Verdict;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VerdictFactory {
    public Verdict createVerdict(String authorId, String targetId, String targetType, String text, int rating, boolean isPublic) {
        return new Verdict(UUID.randomUUID().toString(), authorId, targetId, targetType, text, rating, isPublic, 0, 0);
    }
}