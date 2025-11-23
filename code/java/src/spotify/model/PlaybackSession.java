package spotify.model;

import java.util.ArrayList;
import java.util.List;

public class PlaybackSession {
    private Long userId;
    private Long currentSongId;
    private Long position = 0L; // in seconds
    private String deviceId;
    private List<Long> queue = new ArrayList<>();
    private int queueIndex = 0;


    public PlaybackSession(Long userId, String deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }

    public void play(Long songId) {
        this.currentSongId = songId;
        System.out.println("[PLAY] User " + userId + " started song " + songId + " on device " + deviceId);
    }

    public void pause() {
        System.out.println("[PAUSE] User " + userId + " paused at " + position + " sec");
    }

    public void seek(Long newPosition) {
        this.position = newPosition;
        System.out.println("[SEEK] User " + userId + " seeked to " + position + " sec");
    }

    public void skip(Long nextSongId) {
        this.currentSongId = nextSongId;
        this.position = 0L;
        System.out.println("[SKIP] User " + userId + " skipped to song " + nextSongId);
    }

    public void transferDevice(String newDeviceId) {
        this.deviceId = newDeviceId;
        System.out.println("[TRANSFER] Playback switched to device " + newDeviceId);
    }

    public void setQueue(List<Long> queue) {
        this.queue = queue;
        this.queueIndex = 0;
    }
    public Long getCurrentSong() {
        if (queue.isEmpty()) return null;
        return queue.get(queueIndex);
    }

    public Long getNextSong() {
        if (queue.isEmpty()) return null;
        if (queueIndex + 1 < queue.size()) {
            queueIndex++;
            return queue.get(queueIndex);
        }
        return null; // No more songs
    }
}
