package spotify.command;

import spotify.model.PlaybackSession;

import java.util.List;

public class PlayPlaylistCommand implements PlaybackCommand {

    private final PlaybackSession session;
    private final List<Long> queue;

    public PlayPlaylistCommand(PlaybackSession session, List<Long> queue) {
        this.session = session;
        this.queue = queue;
    }

    @Override
    public void execute() {
        session.setQueue(queue);

        if (!queue.isEmpty()) {
            Long firstSong = queue.get(0);
            session.play(firstSong);
        }
    }
}