package main;

import java.io.*;
import java.util.*;

public class Navigator {

    // This class will navigate the Clean Sweep.
    // private static RoomNode curr_prev; // Previous node and position.
    private static RoomNode curr; // Current node and position.
    private static int invalid; // How many times the Clean Sweep failed to move consecutively.

    public static boolean move(Direction move_to) {
        // curr.adj_list_refresh(); // For debug purposes.
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
        float power_req = path_power_req_calc(move_to); // How much power do we need to return to the charging station?
        // System.out.println(power_req); // For debug purposes.
        // TODO - Some of the below code might get a bit redundant. Might be worth cleaning up a little bit.
        RoomNode next = curr.get_node(move_to); // Checking if there's an obstacle ahead or not.
        if (next != null) { // Is there a node ahead at all?
            if (!next.is_obstacle()) { // Is the node ahead an obstacle?
                // It's not! So we can move.
                float charge_after_move = CleanSweep.get_curr_charge() - power_req_calc(curr, next);
                if (charge_after_move < power_req) { // But do we have enough power?
                    return false;
                    // TODO - Return to the charging station.
                    // Whatever is calling move(), it can use auto_charge_pathfinder() to chart a course back.
                }
                // If we could make a system call to get the Clean Sweep to physically move, we'd do that here.
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
                    float charge_after_move = CleanSweep.get_curr_charge() - power_req_calc(curr, next);
                    if (charge_after_move < power_req) { // But do we have enough power?
                        return false;
                        // TODO - Return to the charging station.
                        // Whatever is calling move(), it can use auto_charge_pathfinder() to chart a course back.
                    }
                    // If we could make a system call to get the Clean Sweep to physically move, we'd do that here.
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
        return true; // TODO - This should somehow verify the Clean Sweep's movements.
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
            peek_pos.set_y(peek_pos.get_y() - 1);
        }
        return fetch(peek_pos, CleanSweepMain.floor_master);
    }

    public static RoomNode get_curr() {
        return curr;
    }

    public static void set_curr(RoomNode curr) {
        Navigator.curr = curr;
    }

    public static float path_power_req_calc(Direction move_to) {
        // This method will determine the power cost of the Clean Sweep's return path.
        ArrayList<RoomNode> charge_path = Navigator.auto_charge_pathfinder();
        float charge_path_power_req = 0.0f;
        while (charge_path.size() > 1) {
            RoomNode charge_node_curr = charge_path.remove(0);
            RoomNode charge_node_next = charge_path.get(0);
            charge_node_curr.adj_list_refresh();
            charge_node_next.adj_list_refresh();
            charge_path_power_req += power_req_calc(charge_node_curr, charge_node_next);
        } return charge_path_power_req;
    }

    public static float power_req_calc(RoomNode at, RoomNode to) {
        // This will calculate the amount of power needed to move from one node to another.
        float node_1_req = FloorType.floor_conv(at.get_floor());
        float node_2_req = FloorType.floor_conv(to.get_floor());
        return (node_1_req + node_2_req) / 2.0f;
    }

    public static ArrayList<RoomNode> auto_charge_pathfinder() {
        // This method will find a path from the Clean Sweep to its charging station using a breadth-first approach.
        ArrayList<ArrayList<RoomNode>> path_list = new ArrayList<ArrayList<RoomNode>>();
        ArrayList<RoomNode> start_path = new ArrayList<RoomNode>();
        ArrayList<RoomNode> visited = new ArrayList<>();
        start_path.add(curr);
        path_list.add(start_path);
        while (path_list.size() != 0) {
            ArrayList<RoomNode> curr_path = path_list.remove(0);
            RoomNode curr_node = curr_path.get(curr_path.size() - 1);
            if (!visited.contains(curr_node)) {
                visited.add(curr_node);
                if (curr_node.get_position().get_x() == 0 && curr_node.get_position().get_y() == 0)
                    return curr_path;
                ArrayList<RoomNode> curr_adj_list = curr_node.get_adj_list();
                while (curr_adj_list.size() != 0) {
                    RoomNode to_path = curr_adj_list.remove(0);
                    ArrayList<RoomNode> new_path = new ArrayList<RoomNode>(curr_path);
                    new_path.add(to_path);
                    path_list.add(new_path);
                }
            }
        } return null; // There is no path.
    }

}
