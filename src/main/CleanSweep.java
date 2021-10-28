package main;

public class CleanSweep {

    // Various methods for interfacing with the Clean Sweep's hardware.

    private static float curr_charge; // TODO - As of right now, at no point is this charge actually expended.

    private static RoomNode floor_local; // This is the Clean Sweep's local floor plan.
    // TODO - When the Clean Sweep first wakes up, it should assign its position to floor_local.
    // This is assuming that the Clean Sweep is starting from its charging station, which should be (0, 0).

    public static void power_off() {
        // TODO - This method should turn off the Clean Sweep. (?)
        // I guess this is more of a symbolic method.
        // If we could make some system calls to turn off the Clean Sweep, we'd do that here.
    }

    public static RoomNode get_floor_local() {
        return floor_local;
    }

    public static void set_floor_local(RoomNode floor_local) {
        CleanSweep.floor_local = floor_local;
    }

    public static float get_curr_charge() {
        return curr_charge;
    }

    public static void set_curr_charge(float curr_charge) {
        CleanSweep.curr_charge = curr_charge;
    }

}
