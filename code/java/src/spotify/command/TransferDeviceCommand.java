package spotify.command;

import spotify.model.PlaybackSession;

public class TransferDeviceCommand implements PlaybackCommand{

    private final PlaybackSession session;
    private final String deviceId;

    public TransferDeviceCommand(PlaybackSession session, String deviceId) {
        this.deviceId = deviceId;
        this.session = session;
    }
    @Override
    public void execute() {
        session.transferDevice(deviceId);
    }
}
