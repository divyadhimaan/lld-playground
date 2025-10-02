package RoomBookingSystem.repository;

import RoomBookingSystem.model.Room;
import RoomBookingSystem.model.RoomType;

import java.util.HashMap;
import java.util.Map;

public class RoomInventory {

    Map<RoomType,Map<String, Room>> roomList;

    public RoomInventory() {
        this.roomList = new HashMap<>();
        for (RoomType type : RoomType.values()) {
            roomList.put(type, new HashMap<>());
        }
    }

    public void addRoom(Room room) {
        roomList.get(room.getRoomType()).put(room.getRoomId(), room);
    }

    public Room getRoom(String roomId) {
       return getAllRooms().get(roomId);
    }
    public Map<String, Room> getAllRooms() {
        Map<String, Room> allRooms = new HashMap<>();
        for (Map<String, Room> rooms : roomList.values()) {
            allRooms.putAll(rooms);
        }
        return allRooms;
    }
    public Map<String, Room> getAllRoomsByType(RoomType roomType) {
        return new HashMap<>(roomList.getOrDefault(roomType, new HashMap<>()));
    }
}
