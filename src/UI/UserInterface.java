package UI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;
import main.CleanSweep;
import main.CleanSweepMain;
import main.RoomNode;

public class UserInterface extends JFrame {
    public UserInterface(){
        //Declarations and layout setting
        JFrame firstFrame = new JFrame("CLEAN SWEEP OPERATIONS");
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.PAGE_AXIS));
        Container ourContainer = getContentPane();
        JLabel firstLabel = new JLabel();
        JButton firstButton = new JButton("START");
        //Adding to canvas
        canvas.setBackground(Color.WHITE);
        canvas.add(firstLabel);
        firstFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Configuring canvas elements
        firstLabel.setText("THE CLEAN SWEEP EXPERIENCE");
        firstLabel.setLocation(400, 100);
        firstLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        firstLabel.setVerticalAlignment(SwingConstants.CENTER);
        canvas.add(firstButton);
        ourContainer.add(canvas, BorderLayout.CENTER);
        //Figuring out the button
        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                optionMenu();
                loginRegisterMenu();
                firstFrame.setVisible(false);
            }
        });
        //Final frame sets
        firstFrame.setSize(450, 500);
        firstFrame.getContentPane().add(BorderLayout.CENTER, canvas);
        firstFrame.add(canvas);
        firstFrame.setVisible(true);
        JLabel lowBattery = new JLabel();
        lowBattery.setText("Low Battery!");
        lowBattery.setVisible(CleanSweep.on_return_path);


        CleanSweep cleanSweep = new CleanSweep();
    }

    public static void loginRegisterMenu(){
        JFrame loginRegisterFrame = new JFrame("LOGIN / REGISTER");
        JPanel loginRegisterCanvas = new JPanel();
        loginRegisterCanvas.setLayout(new BoxLayout(loginRegisterCanvas, BoxLayout.PAGE_AXIS));

        JLabel firstLabel = new JLabel();
        JLabel secondLabel = new JLabel();

        JButton register = new JButton("Register");
        JButton login = new JButton("Login");

        firstLabel.setText("New user? Click Register");
        secondLabel.setText("Returning user? Click Login");

        register.addActionListener(e -> {
            RegisterUI.createAndShowGUI();
            loginRegisterFrame.setVisible(false);
        }) ;

        login.addActionListener(e -> {
            LoginUI.createAndShowGUI();
            loginRegisterFrame.setVisible(false);
        });

        loginRegisterCanvas.add(firstLabel);
        loginRegisterCanvas.add(secondLabel);
        loginRegisterCanvas.add(register);
        loginRegisterCanvas.add(login);
        loginRegisterFrame.add(loginRegisterCanvas);
        loginRegisterFrame.setSize(500,500);
        loginRegisterFrame.setVisible(true);

    }

    public static void optionMenu() {
        JFrame optionFrame = new JFrame("ROOM SELECTION MENU");
        JPanel optionCanvas = new JPanel();
        optionCanvas.setLayout(new BoxLayout(optionCanvas, BoxLayout.PAGE_AXIS));
        JLabel firstLabel = new JLabel();
        JLabel secondLabel = new JLabel();
        JButton roomThree = new JButton("Room Title: 'LivingRoom'");
        /*EDIT BELOW THIS COMMENT*/
        firstLabel.setText("Which room would you like to see the cleaning history of?");
        secondLabel.setText("Rooms registered: ");
        firstLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        secondLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        roomThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thirdRoom();
            }
        });
        /*END CONFIGURATION*/
        optionCanvas.add(firstLabel);
        optionCanvas.add(secondLabel);
        optionCanvas.add(roomThree);
        optionFrame.add(optionCanvas);
        optionFrame.setSize(500, 500);
        optionFrame.setVisible(true);

    }

    public static void thirdRoom(){
        ArrayList<RoomNode> rN = CleanSweepMain.floor_master_list;
        JFrame newFrame = new JFrame();
        JPanel newPanel = new JPanel();
        JLabel newLabel = new JLabel("<html><h1>CLEAN SWEEP ROOM 3</h1><br><br></html>");

        JButton demoLabel = new JButton("Back To Room Selection");
        //demoLabel.setText("l");
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        newPanel.setSize(500, 500);
        newFrame.setSize(500, 500);
        newLabel.setSize(300, 300);
        //newFrame.setLayout(new FlowLayout());
        newPanel.setBackground(Color.MAGENTA);
        newPanel.add(newLabel);
        ArrayList<RoomNode> reformedRoom = new ArrayList<RoomNode>();
        for (RoomNode r : rN) {
            CleanSweepMain.demo_3();
            if (r.is_obstacle()) {

            } else {
                reformedRoom.add(r);
            }
        }

        //Collections.reverse(reformedRoom);
        for (RoomNode r : reformedRoom) {
            //CleanSweepMain.demo_1();
            int timeToSwitch = 0;
            JLabel zerosPanel = new JLabel();
            JLabel foursPanel = new JLabel();
            JLabel eightsPanel = new JLabel();
            JLabel twelvesPanel = new JLabel();
            JLabel sixteensPanel = new JLabel();
            if (r != null) {
                timeToSwitch++;
                if (timeToSwitch >= 0 && timeToSwitch <= 4) {
                    if (timeToSwitch == 0) {
                        zerosPanel.setText("CHARGING STATION NODE: " + r.get_position().toString());
                    } else {
                        foursPanel.setText("VISITED NODE:" + r.get_position().toString());
                    }
                } else if (timeToSwitch >= 5 && timeToSwitch <= 8) {
                    eightsPanel.setText("VISITED NODE: " + r.get_position().toString());
                } else if (timeToSwitch >= 9 && timeToSwitch <= 12) {
                    twelvesPanel.setText("VISITED NODE: " + r.get_position().toString());
                } else {
                    sixteensPanel.setText("VISITED NODE: " + r.get_position().toString());
                }

                newPanel.add(zerosPanel);
                newPanel.add(foursPanel);
                newPanel.add(eightsPanel);
                newPanel.add(twelvesPanel);
                newPanel.add(sixteensPanel);
            }
        }
        demoLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionMenu();
            }
        });
        newPanel.add(demoLabel);
        newFrame.add(newPanel);
        newFrame.setVisible(true);

    }

    }

