package spotify.utils;

public class TokenProvider {
    public String generateToken(Long userId){
        return "token_" + userId + "_" + System.currentTimeMillis();
    }

    public Long extractUserId(String token){
        String[] parts = token.split("_");
        return Long.parseLong(parts[1]);
    }
}
