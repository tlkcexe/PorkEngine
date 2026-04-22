package model;

/**
 * Concrete implementation of the Exit interface.
 * Encapsulates the routing logic and access constraints between instances of Room.
 */
public class ExitImpl implements Exit {
    private final String direction;
    private final String targetRoomId;
    private boolean isLocked;
    private final String requiredItemId;

    public ExitImpl(String direction, String targetRoomId, boolean isLocked, String requiredItemId) {
        this.direction = direction;
        this.targetRoomId = targetRoomId;
        this.isLocked = isLocked;
        this.requiredItemId = requiredItemId;
    }

    @Override
    public String getDirection() { 
        return direction; 
    }

    @Override
    public String getTargetRoomId() { 
        return targetRoomId; 
    }

    @Override
    public boolean isLocked() { 
        return isLocked; 
    }

    @Override
    public void setLocked(boolean locked) { 
        this.isLocked = locked; 
    }

    @Override
    public String getRequiredItemId() { 
        return requiredItemId; 
    }
}