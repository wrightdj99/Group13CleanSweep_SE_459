package main;

import java.io.*;
import java.util.*;

// This class will keep track of the battery of the Clean Sweep
public class Battery {

    private static float curr_charge;  // Remaining battery in the Clean Sweep (battery life = 250 units)
    // TODO - Is this charge being managed and calculated by Navigator correctly? Verify power management in Sprint 3.

    // Calculate how my battery each floor consumes
    public static void consume(RoomNode type1) {
        switch (type1.get_floor()) {
            case BARE_FLOOR:                    // BARE_FLOOR consumes only 1 unit
                curr_charge -= 1;    // So, cut 1 unit from remaining_battery
            case LOW_CARPET:                    // LOW_CARPET consumes only 2 units
                curr_charge -= 2;    // So, cut 2 units from remaining_battery
            case HIGH_CARPET:                   // HIGH_CARPET consumes only 2 units
                curr_charge -= 3;    // So, cut 3 units from remaining_battery
        }
        // What happens when the Clean Sweep moves from floor type A to B?
        // It will use the average cost (units) of those two floor types (type1 and type2)
        // curr_charge -= power_req_calc(type1, type2);         // Cut the average cost (units) from remaining_battery
    }

    public static int check(RoomNode type1) {
        switch (type1.get_floor()) {
            case BARE_FLOOR:                    // BARE_FLOOR consumes only 1 unit
                return 1;    // So, cut 1 unit from remaining_battery
            case LOW_CARPET:                    // LOW_CARPET consumes only 2 units
                return 2;    // So, cut 2 units from remaining_battery
            case HIGH_CARPET:                   // HIGH_CARPET consumes only 2 units
                return 3;    // So, cut 3 units from remaining_battery
        }
        return 0;
    }

    public static float power_req_calc(RoomNode at, RoomNode to) {
        // This will calculate the amount of power needed to move from one node to another.
        float node_1_req = FloorType.floor_conv(at.get_floor());
        float node_2_req = FloorType.floor_conv(to.get_floor());
        return (node_1_req + node_2_req) / 2.0f;
    }

    public static float get_curr_charge() {
        return curr_charge;
    }

    public static void set_curr_charge(float curr_charge) {
        Battery.curr_charge = curr_charge;
    }

}
