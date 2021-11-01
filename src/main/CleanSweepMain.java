package main;

import java.util.*;

public class CleanSweepMain {

    private static final ArrayList<Account> accountList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static RoomNode floor_master; // This is the master floor plan.
    // The master floor plan exists as a mock for testing purposes only.
    // Remember, the starting point is the charging station.

    // The control system's entry point.
    public static void main(String[] args) {
        // User login will happen first.
        accountRegistrationOrLogin();
        // Run demo methods here.
        demo_1();
        demo_2();
        UserInterface ui = new UserInterface();
        // . . . etc.


    }

    public static void accountRegistrationOrLogin(){
        // asks the user if they have an existing account or if they would like to register a new account.
        // It then calls either login() or register()

        // creating accounts for testing purposes
        accountList.add(0, new Account("colin@mail.com", "123")); // tmp user
        accountList.add(0, new Account("aneri@mail.com", "123"));
        accountList.add(0, new Account("brandon@mail.com", "123"));
        accountList.add(0, new Account("daniel@mail.com", "123"));

        // ask the user if they're returning users, AKA already registered an account
        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("Returning user? (Y/N) ");
            String yesOrNo = scanner.nextLine();
            if (yesOrNo.equals("Y")) {
                invalidInput = false;
                login();
            } else if (yesOrNo.equals("N")) {
                invalidInput = false;
                register();
            } else {
                System.out.print("Invalid input. Please enter 'Y' or 'N'\n");
            }
        }
        scanner.close();
    }

    public static void register(){
        // Asks users for their email, a password, and to confirm password. If password and
        // confirmed password are the same, their account is created and added to accountList.

        String email = null, password;

        // Check if user input a string containing both '@' and '.com'
        boolean invalidEmail = true;
        while(invalidEmail) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (email.contains("@") && email.contains(".")) { // not a good way to check for email, will update if we work with email functionality.
                invalidEmail = false; // causes while loop to stop
            } else {
                System.out.print("Please enter valid email containing '@' and '.com'\n");
            }
        }

        // User inputs a password and confirms password. Check if they're the same.
        boolean passwordsDoNotMatch = true;

        while (passwordsDoNotMatch) {

            System.out.print("Create New Password: ");
            password = scanner.nextLine();

            System.out.print("Confirm Password: ");
            String confirmPassword = scanner.nextLine();

            if (password.equals(confirmPassword)) {
                passwordsDoNotMatch = false; // causes while loop to stop.

                // check if email is null somehow.
                if (email != null) {
                    accountList.add(new Account(email, password));
                    System.out.print("\nAccount successfully created!\n\n");
                } else {
                    System.out.print("\nemail was null somehow\n\n");
                }
            } else {
                System.out.print("Passwords do not match, try again.\n");
            }
        }

    }

    public static void login() {
        // asks users to login with their email and password.
        // Email must be connected to an account in accountList.
        // Password must be associated with the specific account the email is connected to.
            // --> doesn't handle separate accounts with the same email.
        boolean invalidEmail = true;
        boolean invalidPassword = true;

        String email, password;
        Integer pos = null; // used to check position in accountList. Updated using emailPosition(). If null, no account with that email exists.

        // check if email exists in accountList and get position if it does exist.
        while (invalidEmail) {
            System.out.print("Enter Email: ");
            email = scanner.nextLine();

            pos = emailPosition(email);

            if (pos == null) {
                System.out.print("Email doesn't exist, try again.\n");
            } else {
                invalidEmail = false;
            }
        }

        // user will enter the password associated with their email.
        while (invalidPassword) {
            System.out.print("Enter Password: ");
            // check if password is correct
            password = scanner.nextLine();
            if (passwordCorrect(password, pos)) {
                invalidPassword = false;
                System.out.print("\nLogin successful!\n\n");
            } else {
                System.out.print("Wrong password, try again.\n");
            }
        }
    }

    public static Integer emailPosition(String email) {
        int pos;
        for (pos = 0; pos < accountList.size(); pos++) {
            Account a = accountList.get(pos);
            if (a.getEmail().equals(email)){
                return pos;
            }
        }
        return null;
    }

    public static boolean passwordCorrect(String password, int pos){
        Account a = accountList.get(pos);
        if (password.equals(a.getPassword()))
            return true;

        return false;

    }

    public static void demo_1() {
        // Manual traversal of the floor plan.
        build_1();
        CleanSweep.set_curr_charge(250);
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
        CleanSweep.set_curr_charge(250);
        System.out.println("demo_2");
        ArrayList<RoomNode> charge_path = Navigator.auto_charge_pathfinder();
        while (charge_path.size() != 0) {
            RoomNode charge_path_node = charge_path.remove(0);
            System.out.println(charge_path_node.get_position().toString());
        } System.out.println("\n");
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

        floor_master = node_0_0;
        CleanSweep.set_floor_local(node_0_0);
        Navigator.set_curr(node_3_1);

    }

}
