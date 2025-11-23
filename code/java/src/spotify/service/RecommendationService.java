package spotify.service;

import spotify.model.Song;
import spotify.strategy.recommend.RecommendationStrategy;

import java.util.List;

public class RecommendationService {
    private RecommendationStrategy strategy;

    public void setStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Song> getRecommendations(Long userId) {
        if (strategy == null) {
            throw new RuntimeException("Recommendation strategy not set");
        }
        return strategy.recommend(userId);
    }
}
