package main.java.org.example.service;

import org.example.factory.VerdictFactory;
import org.example.model.Verdict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerdictService {
    private final Map<String, Verdict> verdicts = new HashMap<>();
    private final VerdictFactory verdictFactory;

    public VerdictService(VerdictFactory verdictFactory) {
        this.verdictFactory = verdictFactory;
    }

    public Verdict addVerdict(String authorId, String targetId, String targetType, String text, int rating, boolean isPublic){
        Verdict verdict =  verdictFactory.createVerdict(authorId, targetId, targetType, text, rating, isPublic);
        verdicts.put(verdict.getVerdictId(), verdict);
        return verdict;
    }

    public List<Verdict> getVerdicts(String targetId)
    {
        List<Verdict> collection = new java.util.ArrayList<>(List.of());
        for(Verdict verdict: verdicts.values())
        {
            if(verdict.getTargetId().equals(targetId) && verdict.isPublic())
                collection.add(verdict);
        }
        return collection;
    }

    public boolean likeVerdict(String verdictId)
    {
        Verdict verdict = verdicts.get(verdictId);
        if(verdict != null){
            verdict.setLikeCount(verdict.getLikeCount() +1);
            return true;
        }
        return false;
    }
    public boolean reportVerdict(String verdictId)
    {
        Verdict verdict = verdicts.get(verdictId);
        if(verdict != null){
            verdict.setReportCount(verdict.getReportCount() +1);
            return true;
        }
        return false;
    }
}
