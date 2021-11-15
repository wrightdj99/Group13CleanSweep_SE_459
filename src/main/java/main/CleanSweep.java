package main;

import java.time.LocalDate;
import java.util.ArrayList;

public class CleanSweep {

    private static RoomNode floor_local; // This is the Clean Sweep's local floor plan.
    private static ArrayList<RoomNode> floor_local_list; // This is a list containing all nodes in the floor plan.
    // TODO - When the Clean Sweep first wakes up, it should assign its position to floor_local.
    // This is assuming that the Clean Sweep is starting from its charging station, which should be (0, 0).
    public static boolean on_return_path = false;
    //ArrayList to keep history of all the logging information
    public static ArrayList<LogInfo> log_info_list;

    public static void clean_cycle() {
        // TODO - This method currently assumes that the Clean Sweep has access to an accurate floor plan.
        // TODO - This method would be much more efficient if cycle_queue took node adjacency into account.
        ArrayList<RoomNode> cycle_queue = new ArrayList<RoomNode>(CleanSweepMain.floor_master_list);
        ArrayList<RoomNode> path_to_next = new ArrayList<RoomNode>();
        cycle_queue.add(cycle_queue.remove(0)); // So that the Clean Sweep returns home after a cycle.
        while (cycle_queue.size() != 0) {
            RoomNode next_in_cycle = cycle_queue.remove(0);
            if(log_info_list!=null){
                //if log_info_list has been set than we will add LogInfo to the list
                log_info_list.add(new LogInfo(LocalDate.now(), next_in_cycle.get_position(), next_in_cycle.get_floor(),
                        next_in_cycle.is_obstacle(), !on_return_path, Battery.get_curr_charge() ));
            }

            if (!next_in_cycle.is_obstacle()) {
                path_to_next = Navigator.pathfinder(next_in_cycle.get_position());
                path_to_next.remove(0); // Removing redundant node (we're already here).
                while (path_to_next.size() != 0) {
                    RoomNode next_in_curr_path = path_to_next.remove(0);
                    if (!Navigator.move(Navigator.get_next_dir(next_in_curr_path))) {
                        // We're assuming here that false was returned because the Clean Sweep is low on power.
                        System.out.println("Low power detected. Returning to charging station.");
                        ArrayList<RoomNode> to_home_queue = new ArrayList<RoomNode>();
                        if (next_in_cycle.get_position().get_x() == 0 && next_in_cycle.get_position().get_y() == 0) {
                            to_home_queue.add(next_in_cycle);
                            cycle_queue = to_home_queue;
                        }
                        else {
                            to_home_queue.add(cycle_queue.get(cycle_queue.size() - 1));
                            cycle_queue = to_home_queue;
                        }
                        on_return_path = true;
                        break;
                    } // System.out.println("Moving . . . " + Navigator.get_curr().get_position());
                }
                if (Navigator.get_curr().get_position().get_x() == 0
                        && Navigator.get_curr().get_position().get_y() == 0)
                    on_return_path = false; // We've made it back to the charging station.
                if (!on_return_path) {
                    System.out.println("Visited the node at " + next_in_cycle.get_position()); // For demo purposes.
                    if (Battery.get_curr_charge() >= 0)
                        System.out.println("Remaining charge: " + Battery.get_curr_charge());
                }
            }
        }
    }

    public static void power_off() {
        // TODO - This method should turn off the Clean Sweep. (?)
        // I guess this is more of a symbolic method.
        // If we could make some system calls to turn off the Clean Sweep, we'd do that here.
    }

    public static void clean() {
        Battery.consume(Navigator.get_curr());
        // If we could make some system calls to make the Clean Sweep clean, we'd do that here.
    }

    public static RoomNode get_floor_local() {
        return floor_local;
    }

    public static void set_floor_local(RoomNode floor_local) {
        CleanSweep.floor_local = floor_local;
    }

    public static ArrayList<RoomNode> get_floor_local_list() {
        return floor_local_list;
    }

    public static void set_floor_local_list(ArrayList<RoomNode> floor_local_list) {
        CleanSweep.floor_local_list = floor_local_list;
    }

    public static void set_log_info_list(ArrayList<LogInfo> log_info_list) {
        CleanSweep.log_info_list = log_info_list;
    }

}
