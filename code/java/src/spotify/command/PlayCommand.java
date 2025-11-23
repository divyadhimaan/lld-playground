package spotify.command;

import spotify.model.PlaybackSession;

public class PlayCommand implements PlaybackCommand{

    private final PlaybackSession session;
    private final Long songId;

    public PlayCommand(PlaybackSession session, Long songId) {
        this.session = session;
        this.songId = songId;
    }
    @Override
    public void execute() {
        session.play(songId);
    }
}
