package spotify.command;

import spotify.model.PlaybackSession;

public class PauseCommand implements PlaybackCommand{

    private final PlaybackSession session;

    public PauseCommand(PlaybackSession session) {
        this.session = session;
    }
    @Override
    public void execute() {
        session.pause();
    }
}
