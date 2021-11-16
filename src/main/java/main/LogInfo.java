package main;

import java.time.LocalDate;

public class LogInfo {
    //This class is used to keep track of all the logging information
    private LocalDate currTime;
    private Vector2 position;
    private FloorType floorType;
    private boolean isObstacle;
    private boolean isOnReturnPath;
    private float currCharge;

    public LogInfo(LocalDate currTime,
                   Vector2 position, FloorType floorType,
                   boolean isObstacle, boolean isOnReturnPath, float currCharge){
        this.currTime = currTime;
        this.position = position;
        this.floorType = floorType;
        this.isObstacle = isObstacle;
        this.isOnReturnPath = isOnReturnPath;
        this.currCharge = currCharge;

    }

    public LocalDate getCurrTime(){
        return this.currTime;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public FloorType getFloorType(){
        return this.floorType;
    }

    public boolean getIsObstacle(){
        return this.isObstacle;
    }

    public boolean getIsOnReturnPath(){
        return this.isOnReturnPath;
    }

    public float getCurrCharge(){
        return this.currCharge;
    }
}
