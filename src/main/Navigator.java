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
        if (!power_req_peek(move_to)) {
            // TODO.
        }
        // TODO - Some of the below code might get a bit redundant. Might be worth cleaning up a little bit.
        RoomNode next = curr.get_node(move_to); // Checking if there's an obstacle ahead or not.
        if (next != null) { // Is there a node ahead at all?
            if (!next.is_obstacle()) { // Is the node ahead an obstacle?
                // It's not! So we can move.
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

    public static boolean power_req_peek(Direction move_to) {
        // This method will check to see if the Clean Sweep will be able to return home after moving.
        return true; // TODO.
    }

    public static float power_req_calc(RoomNode at, RoomNode to) {
        // This will calculate the amount of power needed to move from one node to another.
        float node_1_req = FloorType.floor_conv(at.get_floor());
        float node_2_req = FloorType.floor_conv(to.get_floor());
        return (node_1_req + node_2_req) / 2.0f;
    }

    public static float breadth_first_calc() {
        // This method will calculate the weight of the path from the Clean Sweep to its charging station.
        ArrayList<ArrayList<RoomNode>> path_list = new ArrayList<ArrayList<RoomNode>>();
        ArrayList<RoomNode> visited = new ArrayList<>();
        path_list.add(curr.get_adj_list());
        visited.add(curr);
        while (path_list.size() != 0) {
            ArrayList<RoomNode> pop_list = path_list.remove(0);
            RoomNode pop_node = pop_list.get(pop_list.size() - 1);
            if (!visited.contains(pop_node)) {
                visited.add(pop_node);
                if (pop_node.get_position().get_x() == 0 && pop_node.get_position().get_y() == 0) {
                    // We've made it home!
                    return 1.0f; // TODO - Return path weight (in power units).
                }
                if (pop_node.get_node(Direction.NORTH) != null && !pop_node.get_node(Direction.NORTH).is_obstacle()) {
                    ArrayList<RoomNode> new_list = pop_list;
                    // TODO - The path shouldn't be built off of curr.adj_list. Fix this so the path builds correctly.
                    new_list.add(pop_node.get_node(Direction.NORTH));
                    path_list.add(new_list);
                }
                if (pop_node.get_node(Direction.SOUTH) != null && !pop_node.get_node(Direction.SOUTH).is_obstacle()) {
                    ArrayList<RoomNode> new_list = pop_list;
                    // TODO - The path shouldn't be built off of curr.adj_list. Fix this so the path builds correctly.
                    new_list.add(pop_node.get_node(Direction.SOUTH));
                    path_list.add(new_list);
                }
                if (pop_node.get_node(Direction.EAST) != null && !pop_node.get_node(Direction.EAST).is_obstacle()) {
                    ArrayList<RoomNode> new_list = pop_list;
                    // TODO - The path shouldn't be built off of curr.adj_list. Fix this so the path builds correctly.
                    new_list.add(pop_node.get_node(Direction.EAST));
                    path_list.add(new_list);
                }
                if (pop_node.get_node(Direction.WEST) != null && !pop_node.get_node(Direction.WEST).is_obstacle()) {
                    ArrayList<RoomNode> new_list = pop_list;
                    // TODO - The path shouldn't be built off of curr.adj_list. Fix this so the path builds correctly.
                    new_list.add(pop_node.get_node(Direction.WEST));
                    path_list.add(new_list);
                }
            }
        } return -1.0f; // There is no path!
    }

    public static float greedy_best_first_calc() {
        // This method will calculate the weight of the path from the Clean Sweep to its charging station.
        ArrayList<RoomNode> to_explore = new ArrayList<RoomNode>();
        ArrayList<RoomNode> explored = new ArrayList<RoomNode>();
        to_explore.add(curr);
        float weight = 0.0f;
        while(true) {
            if (to_explore.size() == 0)
                return -1.0f; // There is no path!
            RoomNode next = to_explore.get(0);
            to_explore.remove(0); // ?
            explored.add(next);
            if (next.get_position().get_x() == 0 && next.get_position().get_y() == 0) {
                // We've made it home!
                break; }
            if (next.get_node(Direction.NORTH) != null)
                to_explore.add(next.get_node(Direction.NORTH));
            if (next.get_node(Direction.SOUTH) != null)
                to_explore.add(next.get_node(Direction.SOUTH));
            if (next.get_node(Direction.EAST) != null)
                to_explore.add(next.get_node(Direction.EAST));
            if (next.get_node(Direction.WEST) != null)
                to_explore.add(next.get_node(Direction.WEST));
            // TODO - Sort the 'to_explore' list.
            ArrayList<RoomNode> to_explore_sorted = new ArrayList<RoomNode>();
            to_explore_sorted.add(to_explore.get(0));
            to_explore.remove(to_explore.get(0));
            // TODO - Weight needs to be added.
            // TODO - How to backtrace path?
        } return weight;
    }

    public static int greedy_eval(RoomNode to_eval) {
        // The evaluation function for greedy_best_first_calc().
        // The lower the number, the closer we are to the home position.
        return to_eval.get_position().get_x() + to_eval.get_position().get_y();
    }

}
