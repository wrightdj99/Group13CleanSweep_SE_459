package main;

import java.io.*;
import java.util.*;

// This class will keep track of the battery of the Clean Sweep
public class Battery {

    float remaining_battery = 250;  // Remaining battery in the Clean Sweep (battery life = 250 units)


    // Calculate how my battery each floor consumes
    public void battery_consume(FloorType type1, FloorType type2) {
        switch (type1) {
            case BARE_FLOOR:                    // BARE_FLOOR consumes only 1 unit
                this.remaining_battery -= 1;    // So, cut 1 unit from remaining_battery
            case LOW_CARPET:                    // LOW_CARPET consumes only 2 units
                this.remaining_battery -= 2;    // So, cut 2 units from remaining_battery
            case HIGH_CARPET:                   // HIGH_CARPET consumes only 2 units
                this.remaining_battery -= 3;    // So, cut 3 units from remaining_battery
        }

        // What happens when the Clean Sweep moves from floor type A to B?
        // It will use the average cost (units) of those two floor types (type1 and type2)

        // Store the unit of floor type1 and type2. Get the average cost (units)
        float a = 0;            // Stores the unit(s) that is(are) being used in type1
        float b = 0;            // Stores the unit(s) that is(are) being used in type2

        if (type1 == FloorType.BARE_FLOOR) {
            a = 1;
        }
        if (type1 == FloorType.LOW_CARPET) {
            a = 2;
        }
        if (type1 == FloorType.HIGH_CARPET) {
            a = 3;
        }

        if (type2 == FloorType.BARE_FLOOR) {
            b = 1;
        }
        if (type2 == FloorType.LOW_CARPET) {
            b = 2;
        }
        if (type2 == FloorType.HIGH_CARPET) {
            b = 3;
        }

        remaining_battery -= ((a + b) / 2);         // Cut the average cost (units) from remaining_battery

    }

}
