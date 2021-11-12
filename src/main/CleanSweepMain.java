package main;

import UI.UserInterface;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class CleanSweepMain {

    private static final ArrayList<main.Account> accountList = new ArrayList<>(); // creating list of accounts.
    private static Scanner scanner = new Scanner(System.in); // used to read user input

    public static RoomNode floor_master; // This is the master floor plan.
    public static ArrayList<RoomNode> floor_master_list; // This is a list containing all nodes in the floor plan.
    // The master floor plan exists as a mock for testing purposes only.
    // Remember, the starting point is the charging station.

    // The control system's entry point.
    public static void main(String[] args) {
        // If accounts have been made, put contents of Accounts.json into accountList.
        accountList.clear();
        accountList.addAll(loadFile());

        // Run demo methods here.
        demo_1();
        demo_2();
        demo_3();
        demo_4();
        UserInterface ui = new UserInterface();
        // . . . etc.
    }

    public static Integer emailPosition(String email) {
        // Used when logging in to check if the email exists.
        // If it does, it returns the position in the array list accountsList

        for (int pos = 0; pos < accountList.size(); pos++) {
            main.Account a = accountList.get(pos);
            if (a.getEmail().equals(email)){
                return pos;
            }
        }
        return null;
    }

    public static boolean passwordCorrect(String password, int pos){
        // to check if password is associated with the email found at the account
        // in accountList's position 'pos'

        main.Account a = accountList.get(pos);
        if (password.equals(a.getPassword()))
            return true;

        return false;
    }


    public static ArrayList<Account> loadFile(){
        /* For JSON file loading. Creates the accountList arraylist from the JSON file. */

        ArrayList<Account> list = new ArrayList<>();

        try {
            File file = new File ("Accounts.json");
            if (file.exists()) {
                JsonReader reader = new JsonReader(new FileReader("Accounts.json"));

                reader.beginArray();

                while (reader.hasNext()) {
                    reader.beginObject();
                    reader.nextName();
                    String email = reader.nextString();
                    reader.nextName();
                    String password = reader.nextString();

                    list.add(new Account(email, password));
                    reader.endObject();
                }

                // close reader
                reader.endArray();
                reader.close();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public static void saveAccounts(){
        // creates the file Accounts.json and prints the contents of accountList to the file.

        try {
            FileWriter writer = new FileWriter(new File("Accounts.json"));
            Gson gson = new Gson();
            gson.toJson(accountList, writer);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getAccountFromUI(Account account){
        accountList.add(account);
        saveAccounts();
    }

    public static void demo_1() {
        // Manual traversal of the floor plan.
        build_1();
        Battery.set_curr_charge(250);
        System.out.println("demo_1");
        System.out.println(Navigator.get_curr().get_position().toString()); // (3, 1)
        System.out.println(Navigator.move(Direction.EAST));                 // Can't move east (out of bounds).
        System.out.println(Navigator.get_curr().get_position().toString()); // (3, 1)
        System.out.println(Navigator.move(Direction.WEST));                 // Move west.
        System.out.println(Navigator.get_curr().get_position().toString()); // (2, 1)
        System.out.println(Navigator.move(Direction.WEST));                 // Can't move west (obstacle).
        System.out.println(Navigator.get_curr().get_position().toString()); // (2, 1)
        System.out.println(Navigator.move(Direction.NORTH));                // Move north.
        System.out.println(Navigator.move(Direction.NORTH));                // Move north.
        System.out.println(Navigator.get_curr().get_position().toString()); // (2, 3)
        System.out.println(Navigator.move(Direction.WEST));                 // Move west.
        System.out.println(Navigator.move(Direction.WEST));                 // Move west.
        System.out.println(Navigator.get_curr().get_position().toString()); // (0, 3)
        System.out.println(Navigator.move(Direction.SOUTH));                // Move south.
        System.out.println(Navigator.move(Direction.SOUTH));                // Move south.
        System.out.println(Navigator.move(Direction.SOUTH));                // Move south.
        System.out.println(Navigator.move(Direction.SOUTH));                // Can't move south (out of bounds).
        System.out.println(Navigator.get_curr().get_position().toString()); // (0, 0) - We're back home.
        System.out.println("\n");
    }

    public static void demo_2() {
        // Automatic pathfinding to charging station.
        build_1();
        Battery.set_curr_charge(250);
        System.out.println("demo_2");
        ArrayList<RoomNode> charge_path = Navigator.pathfinder(new Vector2(0, 0));
        while (charge_path.size() != 0) {
            RoomNode charge_path_node = charge_path.remove(0);
            System.out.println(charge_path_node.get_position().toString());
        } System.out.println("\n");
    }

    public static void demo_3() {
        // Execution of cleaning cycle.
        build_1();
        Battery.set_curr_charge(250);
        System.out.println("demo_3");
        CleanSweep.clean_cycle();
        System.out.println("\n");
    }

    public static void demo_4() {
        // Execution of cleaning cycle with automatic return to charging station.
        build_1();
        Battery.set_curr_charge(35);
        System.out.println("demo_4");
        CleanSweep.clean_cycle();
        System.out.println("\n");
    }

    public static void build_1() {

        // Initializing our demo floor plan.
            // [L] [H] [B] [L]
            // [L] [X] [B] [X]
            // [B] [X] [H] [L] <-- Starting here.
            // [B] [B] [X] [L]
                // B = Bare Floor
                // L = Low Carpet
                // H = High Carpet
                // X = Obstacle
            // Bottom left node will be the charging station.
            // Clean Sweep will start at position (3, 1).

        RoomNode node_0_3 = new RoomNode(FloorType.LOW_CARPET, false, new Vector2(0, 3));
        RoomNode node_1_3 = new RoomNode(FloorType.HIGH_CARPET, false, new Vector2(1, 3));
        RoomNode node_2_3 = new RoomNode(FloorType.BARE_FLOOR, false, new Vector2(2, 3));
        RoomNode node_3_3 = new RoomNode(FloorType.LOW_CARPET, false, new Vector2(3, 3));

        RoomNode node_0_2 = new RoomNode(FloorType.LOW_CARPET, false, new Vector2(0, 2));
        RoomNode node_1_2 = new RoomNode(FloorType.OBSTACLE, true, new Vector2(1, 2));
        RoomNode node_2_2 = new RoomNode(FloorType.BARE_FLOOR, false, new Vector2(2, 2));
        RoomNode node_3_2 = new RoomNode(FloorType.OBSTACLE, true, new Vector2(3, 2));

        RoomNode node_0_1 = new RoomNode(FloorType.BARE_FLOOR, false, new Vector2(0, 1));
        RoomNode node_1_1 = new RoomNode(FloorType.OBSTACLE, true, new Vector2(1, 1));
        RoomNode node_2_1 = new RoomNode(FloorType.HIGH_CARPET, false, new Vector2(2, 1));
        RoomNode node_3_1 = new RoomNode(FloorType.LOW_CARPET, false, new Vector2(3, 1));

        RoomNode node_0_0 = new RoomNode(FloorType.BARE_FLOOR, false, new Vector2(0, 0));
        RoomNode node_1_0 = new RoomNode(FloorType.BARE_FLOOR, false, new Vector2(1, 0));
        RoomNode node_2_0 = new RoomNode(FloorType.OBSTACLE, true, new Vector2(2, 0));
        RoomNode node_3_0 = new RoomNode(FloorType.LOW_CARPET, false, new Vector2(3, 0));

        // --- // TODO - Arrange the below statements in a consistent order.

        node_0_3.set_node(Direction.EAST, node_1_3);
        node_0_3.set_node(Direction.SOUTH, node_0_2);

        node_1_3.set_node(Direction.WEST, node_0_3);
        node_1_3.set_node(Direction.EAST, node_2_3);
        node_1_3.set_node(Direction.SOUTH, node_1_2);

        node_2_3.set_node(Direction.WEST, node_1_3);
        node_2_3.set_node(Direction.EAST, node_3_3);
        node_2_3.set_node(Direction.SOUTH, node_2_2);

        node_3_3.set_node(Direction.WEST, node_2_3);
        node_3_3.set_node(Direction.SOUTH, node_3_2);

        // ---

        node_0_2.set_node(Direction.NORTH, node_0_3);
        node_0_2.set_node(Direction.EAST, node_1_2);
        node_0_2.set_node(Direction.SOUTH, node_0_1);

        node_1_2.set_node(Direction.NORTH, node_1_3);
        node_1_2.set_node(Direction.EAST, node_2_2);
        node_1_2.set_node(Direction.SOUTH, node_1_1);
        node_1_2.set_node(Direction.WEST, node_0_2);

        node_2_2.set_node(Direction.NORTH, node_2_3);
        node_2_2.set_node(Direction.EAST, node_3_2);
        node_2_2.set_node(Direction.SOUTH, node_2_1);
        node_2_2.set_node(Direction.WEST, node_1_2);

        node_3_2.set_node(Direction.NORTH, node_3_3);
        node_3_2.set_node(Direction.WEST, node_2_2);
        node_3_2.set_node(Direction.SOUTH, node_3_1);

        // ---

        node_0_1.set_node(Direction.NORTH, node_0_2);
        node_0_1.set_node(Direction.EAST, node_1_1);
        node_0_1.set_node(Direction.SOUTH, node_0_0);

        node_1_1.set_node(Direction.NORTH, node_1_2);
        node_1_1.set_node(Direction.EAST, node_2_1);
        node_1_1.set_node(Direction.SOUTH, node_1_0);
        node_1_1.set_node(Direction.WEST, node_0_1);

        node_2_1.set_node(Direction.NORTH, node_2_2);
        node_2_1.set_node(Direction.EAST, node_3_1);
        node_2_1.set_node(Direction.SOUTH, node_2_0);
        node_2_1.set_node(Direction.WEST, node_1_1);

        node_3_1.set_node(Direction.NORTH, node_3_2);
        node_3_1.set_node(Direction.WEST, node_2_1);
        node_3_1.set_node(Direction.SOUTH, node_3_0);

        // ---

        node_0_0.set_node(Direction.EAST, node_0_1);
        node_0_0.set_node(Direction.NORTH, node_1_0);

        node_1_0.set_node(Direction.WEST, node_2_0);
        node_1_0.set_node(Direction.EAST, node_0_0);
        node_1_0.set_node(Direction.NORTH, node_1_1);

        node_2_0.set_node(Direction.WEST, node_3_0);
        node_2_0.set_node(Direction.EAST, node_1_0);
        node_2_0.set_node(Direction.NORTH, node_2_1);

        node_3_0.set_node(Direction.WEST, node_2_0);
        node_3_0.set_node(Direction.NORTH, node_3_1);

        // ---

        floor_master_list = new ArrayList<RoomNode>();
        floor_master_list.add(node_0_0);
        floor_master_list.add(node_0_1);
        floor_master_list.add(node_0_2);
        floor_master_list.add(node_0_3);
        floor_master_list.add(node_1_0);
        floor_master_list.add(node_1_1);
        floor_master_list.add(node_1_2);
        floor_master_list.add(node_1_3);
        floor_master_list.add(node_2_0);
        floor_master_list.add(node_2_1);
        floor_master_list.add(node_2_2);
        floor_master_list.add(node_2_3);
        floor_master_list.add(node_3_0);
        floor_master_list.add(node_3_1);
        floor_master_list.add(node_3_2);
        floor_master_list.add(node_3_3);

        // ---

        floor_master = node_0_0;
        CleanSweep.set_floor_local(node_0_0);
        Navigator.set_curr(node_3_1);

    }

}
