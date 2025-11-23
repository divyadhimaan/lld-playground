package spotify.service;

import spotify.command.*;
import spotify.model.PlaybackSession;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PlaybackService {
    private final ConcurrentHashMap<Long, PlaybackSession> sessionMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ExecutorService> sessionExecutors = new ConcurrentHashMap<>();

    private ExecutorService getExecutor(Long userId) {
        return sessionExecutors.computeIfAbsent(userId, id -> Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setName("playback-session-" + id);
            t.setDaemon(true);
            return t;
        }));
    }
    public PlaybackSession getSession(Long userId){
        return sessionMap.computeIfAbsent(userId, id -> new PlaybackSession(userId, "DEVICE_DEFAULT"));
    }
    private void submit(Long userId, PlaybackCommand command) {
        ExecutorService executor = getExecutor(userId);
        executor.submit(command::execute);
    }
    public void play(Long userId, Long songId) {
        submit(userId, new PlayCommand(getSession(userId), songId));
    }

    public void pause(Long userId) {
        submit(userId, new PauseCommand(getSession(userId)));
    }

    public void seek(Long userId, Long position) {
        submit(userId, new SeekCommand(getSession(userId), position));
    }

    public void skip(Long userId, Long nextSongId) {
        submit(userId, new SkipCommand(getSession(userId), nextSongId));
    }

    public void transferDevice(Long userId, String deviceId) {
        submit(userId, new TransferDeviceCommand(getSession(userId), deviceId));
    }

    public void playPlaylist(Long userId, List<Long> songQueue) {
        submit(userId, new PlayPlaylistCommand(getSession(userId), songQueue));
    }

    public void next(Long userId) {
        PlaybackSession session = getSession(userId);

        submit(userId, () -> {
            Long nextSong = session.getNextSong();

            if (nextSong == null) {
                System.out.println("[END] Playlist finished.");
                return;
            }

            new PlayCommand(session, nextSong).execute();
        });
    }

    // Call this on shutdown of system
    public void shutdown() {
        sessionExecutors.values().forEach(ExecutorService::shutdown);
    }

}

