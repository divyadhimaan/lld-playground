package adapter;

class AudioPlayer {
    public void play(String audioData) {
        System.out.println("Playing: " + audioData);
    }
}

// This is a legacy class with a different interface
class LegacySpeaker {
    public void makeSound(String data) {
        System.out.println("Old Speaker Sound: " + data);
    }
}

public class AdapterViolation {
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        player.play("Spotify Song");

        LegacySpeaker speaker = new LegacySpeaker();
        // ❌ Incompatible interface: Cannot pass speaker to player
        // player.play(speaker); // Not allowed
    }
}

