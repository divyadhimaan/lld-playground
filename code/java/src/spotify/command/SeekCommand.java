package spotify.command;

import spotify.model.PlaybackSession;

public class SeekCommand implements PlaybackCommand{

    private final PlaybackSession session;
    private final Long position;

    public SeekCommand(PlaybackSession session, Long position) {
        this.position = position;
        this.session = session;
    }
    @Override
    public void execute() {
        session.seek(position);
    }
}
