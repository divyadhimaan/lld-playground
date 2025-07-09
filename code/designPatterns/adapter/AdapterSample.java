package adapter;


// Target interface
interface AudioPlayer1 {
    void play(String audioData);
}

// Adaptee
class LegacySpeaker1 {
    public void makeSound(String data) {
        System.out.println("Old Speaker Sound: " + data);
    }
}

// Adapter
class SpeakerAdapter implements AudioPlayer1 {
    private final LegacySpeaker1 legacySpeaker;

    public SpeakerAdapter(LegacySpeaker1 legacySpeaker) {
        this.legacySpeaker = legacySpeaker;
    }

    public void play(String audioData) {
        legacySpeaker.makeSound(audioData); // Converts play() → makeSound()
    }
}

// Client
public class AdapterSample {
    public static void main(String[] args) {
        AudioPlayer1 player = new SpeakerAdapter(new LegacySpeaker1());
        player.play("Spotify Song");
    }
}

