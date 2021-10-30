package main;

public enum FloorType {

    BARE_FLOOR,
    LOW_CARPET,
    HIGH_CARPET,
    OBSTACLE;

    public static int floor_conv(FloorType floor) {
        // Will convert given floor enum value to corresponding power integer value.
        switch(floor) {
            case BARE_FLOOR:
                return 1;
            case LOW_CARPET:
                return 2;
            case HIGH_CARPET:
                return 3;
        }
        return 0;
    }

}
