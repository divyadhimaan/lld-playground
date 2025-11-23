package spotify.command;

import spotify.model.PlaybackSession;

public class SkipCommand implements PlaybackCommand{

    private final PlaybackSession session;
    private final Long nextSongId;

    public SkipCommand(PlaybackSession session, Long nextSongId) {
        this.nextSongId = nextSongId;
        this.session = session;
    }
    @Override
    public void execute() {
        session.skip(nextSongId);
    }
}
