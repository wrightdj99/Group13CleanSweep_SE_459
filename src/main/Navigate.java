package main;
import java.util.*;

public class Navigate {

    // Will check if the Clean Sweep is running or not
    boolean running = false;

    // Will start the Clean Sweep
    public void start () {

        //x and y coordinates
        int x = 0;
        int y = 0;

        //2D Array To Keep Track Of Movement
        ArrayList <Integer> xCoor = new ArrayList <Integer>();
        ArrayList <Integer> yCoor = new ArrayList <Integer>();

        // Will keep track of the direction of the Clean Sweep
        int direction = 0;

        while (running) {
            if (direction == 0) {                           // EAST
                //if (no_obstacles)                         // If there is no obstacle, then...
                x = x + 1;                                  // Then the Clean Sweep will move towards East

                //else {                                    // If there is an obstacle, then...
                    //direction = (direction + 1)%4;        // Then the Clean Sweep will change its direction
                //}
            }
            else if (direction == 1) {                      // NORTH
                //if (no_obstacles)                         // If there is no obstacle, then...
                y = y + 1;                                  // Then Clean Sweep will move towards North

                //else                                      // If there is an obstacle, then...
                    //direction = (direction + 1)%4;        // Then the Clean Sweep will change its direction
                //}
            }
            else if (direction == 2) {                      // WEST
                //if (no_obstacles)                         // If there is no obstacle, then...
                x = x - 1;                                  // Then Clean Sweep will move towards West

                //else                                      // If there is an obstacle, then...
                    //direction = (direction + 1)%4;        // Then the Clean Sweep will change its direction
                //}
            }
            else if (direction == 3) {  //else              // SOUTH
                //if (no_obstacles)                         // If there is no obstacle, then...
                y = y - 1;                                  // Then Clean Sweep will move towards South

                //else                                      // If there is an obstacle, then...
                    //direction = (direction + 1)%4;        // Then the Clean Sweep will change its direction
                //}
            }
        }
    }

    // Will stop the Clean Sweep
    public void stop() {
        running = false;
    }
}
