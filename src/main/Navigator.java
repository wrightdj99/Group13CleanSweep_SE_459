package main;

import java.io.*;
import java.util.*;

public class Navigator {
    // This class will navigate the Clean Sweep.
    private static RoomNode curr; // Current node and position.

    public void move(Direction move_to) {
        // TODO - Is the Clean Sweep still in the same location it was before?
        // TODO - Does the clean sweep have enough battery life to move?
        // Checking if there's an obstacle ahead or not.
        RoomNode next = curr.get_node(move_to);
        if (next != null) { // Is there a node ahead at all?
            if (!next.is_obstacle()) { // Is the node ahead an obstacle?
                // Moving.
                curr = next;
            } else { // The node ahead is an obstacle . . .
                // TODO.
            }
        }
        else { // If there's a node ahead, we haven't visited it yet.
            // TODO.
        }
    }

    public void scan() {
        // TODO - For when the Clean Sweep scans its environment.
        // Should this be relegated to a different class?
    }

}
