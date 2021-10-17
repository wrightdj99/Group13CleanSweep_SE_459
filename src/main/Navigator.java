package main;

import java.io.*;
import java.util.*;

public class Navigator {

    // This class will navigate the Clean Sweep.
    // private static RoomNode curr_prev; // Previous node and position.
    private static RoomNode curr; // Current node and position.
    private static int invalid; // How many times the Clean Sweep failed to move consecutively.

    public static boolean move(Direction move_to) {
        // Is the Clean Sweep still in the same location it was before?
        // TODO - Would the Clean Sweep even know it was stuck if it didn't have a full floor plan for reference?
        if (!validate()) { // Let's check . . .
            invalid += 1;
            if (invalid >= 3) { // If this is the third failed attempt, power off.
                invalid = 0;
                CleanSweep.power_off();
            }
            else {
                return false; // ?
            }
        }
        // TODO - Does the clean sweep have enough battery life to move? Check for that here!
        // TODO - Some of the below code might get a bit redundant. Might be worth cleaning up a little bit.
        RoomNode next = curr.get_node(move_to); // Checking if there's an obstacle ahead or not.
        if (next != null) { // Is there a node ahead at all?
            if (!next.is_obstacle()) { // Is the node ahead an obstacle?
                // It's not! So we can move.
                // Here might go some system call to get the Clean Sweep to physically move.
                // curr_prev = curr;
                curr = next;
                invalid = 0;
                return true; // We let the caller know that we moved successfully.
            } else { // The node ahead is an obstacle . . .
                return false; // We let the caller know that we can't move in that direction.
                // Whoever called move(), they'll have to figure out how to reroute the Clean Sweep.
            }
        }
        else { // If there's a node ahead, we haven't visited it yet.
            RoomNode discovered = peek(move_to); // Let's have a peek . . .
            if (discovered != null) { // Is there a node there at all?
                if (!discovered.is_obstacle()) { // Is the node an obstacle?
                    // It's not! So we can move.
                    // Here might go some system call to get the Clean Sweep to physically move.
                    // curr_prev = curr;
                    curr = discovered;
                    invalid = 0;
                    return true; // We let the caller know that we moved successfully.
                }
                // TODO - Right here we'd want to add our newly discovered node to the Clean Sweep's local floor plan.
                // That'd be the floor_local field in 'CleanSweep.java'.
            }
            return false; // We let the caller know that we can't move in that direction.
            // Whoever called move(), they'll have to figure out how to reroute the Clean Sweep.
        }
    }

    public static boolean validate() {
        return false; // TODO - This should somehow verify the Clean Sweep's movements.
    }

    public static RoomNode fetch(Vector2 target_pos, RoomNode floor_plan) {
        // Given a set of coordinates, this method will walk a floor plan to that position.
        // Once there, it will return the node at that position (or null if it finds nothing).
        int dist_to_target_x = target_pos.get_x();
        int dist_to_target_y = target_pos.get_y();
        RoomNode node_walker = floor_plan;
        while (dist_to_target_x != 0) { // Walking up or down to the node.
            if (dist_to_target_x > 0) { // Traveling up.
                if (node_walker.get_node(Direction.NORTH) != null) {
                    dist_to_target_x -= 1;
                    node_walker = node_walker.get_node(Direction.NORTH);
                }
                else
                    return null; // We couldn't find anything at that position!
            }
            if (dist_to_target_x < 0) { // Traveling down.
                if (node_walker.get_node(Direction.SOUTH) != null) {
                    dist_to_target_x += 1;
                    node_walker = node_walker.get_node(Direction.SOUTH);
                }
                else
                    return null; // We couldn't find anything at that position!
            }
        }
        while (dist_to_target_y != 0) { // Walking left or right to the node.
            if (dist_to_target_y > 0) { // Traveling right.
                if (node_walker.get_node(Direction.EAST) != null) {
                    dist_to_target_x -= 1;
                    node_walker = node_walker.get_node(Direction.EAST);
                }
                else
                    return null; // We couldn't find anything at that position!
            }
            if (dist_to_target_y < 0) {  // Traveling left.
                if (node_walker.get_node(Direction.WEST) != null) {
                    dist_to_target_x += 1;
                    node_walker = node_walker.get_node(Direction.WEST);
                }
                else
                    return null; // We couldn't find anything at that position!
            }
        }
        return node_walker;
    }

    public static RoomNode peek(Direction peek_dir) {
        // This would, in theory, use the Clean Sweep's sensors to see if there's an obstacle ahead.
        // For the purposes of this 'mock', it's simply omniscient and consults the floor plan.
        Vector2 peek_pos = new Vector2(curr.get_position().get_x(), curr.get_position().get_y());
        if (peek_dir == Direction.NORTH) {
            peek_pos.set_x(peek_pos.get_x() + 1);
        }
        if (peek_dir == Direction.SOUTH) {
            peek_pos.set_x(peek_pos.get_x() - 1);
        }
        if (peek_dir == Direction.EAST) {
            peek_pos.set_y(peek_pos.get_y() + 1);
        }
        if (peek_dir == Direction.WEST) {
            peek_pos.set_y(peek_pos.get_y() + 1);
        }
        return fetch(peek_pos, CleanSweepMain.floor_master);
    }

}
