package UI;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import main.Battery;
import main.CleanSweep;
import main.CleanSweepMain;
import main.LogInfo;
import main.RoomNode;

public class UserInterface extends JFrame {
    static ArrayList<LogInfo> logs;
    public UserInterface(){
        //Declarations and layout setting
        JFrame firstFrame = new JFrame("CLEAN SWEEP OPERATIONS");
        JPanel canvas = new JPanel();
        canvas.setLayout(new BoxLayout(canvas, BoxLayout.PAGE_AXIS));
        Container ourContainer = getContentPane();
        JLabel firstLabel = new JLabel();
        JLabel imageLabel = new JLabel();
        ImageIcon shiningStarFinal = new ImageIcon("shiningStarFinal.png");
        imageLabel.setIcon(shiningStarFinal);
        JButton firstButton = new JButton("START");
        imageLabel.setIcon(shiningStarFinal);
        //Adding to canvas
        canvas.setBackground(Color.PINK);
        canvas.add(firstLabel);
        firstFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Configuring canvas elements
        firstLabel.setText("THE CLEAN SWEEP USER PORTAL");
        firstLabel.setLocation(400, 100);
        firstLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        firstLabel.setVerticalAlignment(SwingConstants.CENTER);
        canvas.add(firstButton);
        canvas.add(imageLabel);
        ourContainer.add(canvas, BorderLayout.CENTER);
        //Figuring out the button
        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loginRegisterMenu();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                firstFrame.setVisible(false);
            }
        });
        //Final frame sets
        firstFrame.setSize(500, 500);
        firstFrame.getContentPane().add(BorderLayout.CENTER, canvas);
        firstFrame.add(canvas);
        firstFrame.setVisible(true);
        JLabel lowBattery = new JLabel();
        lowBattery.setText("Low Battery!");
        lowBattery.setVisible(CleanSweep.on_return_path);
        canvas.add(lowBattery);


        //CleanSweep cleanSweep = new CleanSweep();
//        logs = new ArrayList<>();
//        CleanSweep.set_log_info_list(logs);

    }

    public static void loginRegisterMenu() throws IOException {
        LogoEditor logoEditor = new LogoEditor();
        JFrame loginRegisterFrame = new JFrame("LOGIN / REGISTER");
        JPanel loginRegisterCanvas = new JPanel();
        loginRegisterCanvas.setLayout(new BoxLayout(loginRegisterCanvas, BoxLayout.PAGE_AXIS));

        loginRegisterFrame.setBackground(Color.PINK);
        loginRegisterCanvas.setBackground(Color.PINK);
        //Creating first logo size made by a designer
        ImageIcon shiningStar = new ImageIcon("shiningStar2.png");
        logoEditor.imageResize("shiningStar2.png", "shiningStarFinal.png", 400, 400);
        ImageIcon shiningStarFinal = new ImageIcon("shiningStarFinal.png");
        //Creating second logo size made by a designer
        logoEditor.imageResize("shiningStar2.png", "shiningStarTiny.png", 100, 100);
        ImageIcon shiningStarTiny = new ImageIcon("shiningStarTiny.png");
        //Setting up the other labels
        JLabel imageLabel = new JLabel();
        JLabel firstLabel = new JLabel();
        JLabel secondLabel = new JLabel();
        imageLabel.setIcon(shiningStarFinal);


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
        loginRegisterCanvas.add(imageLabel);
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
        JButton roomTwo = new JButton("Room Title: 'Kitchen'");
        JButton roomThree = new JButton("Room Title: 'LivingRoom'");
        JLabel imageLabel = new JLabel();
        ImageIcon optionIcon = new ImageIcon("shiningStarFinal.png");
        imageLabel.setIcon(optionIcon);
        optionCanvas.setBackground(Color.PINK);
        optionFrame.setBackground(Color.PINK);
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
        roomTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { secondRoom(); }
        });
        /*END CONFIGURATION*/
        optionCanvas.add(firstLabel);
        optionCanvas.add(secondLabel);
        optionCanvas.add(roomTwo);
        optionCanvas.add(roomThree);
        optionCanvas.add(imageLabel);
        optionFrame.add(optionCanvas);
        optionFrame.setSize(500, 500);
        optionFrame.setVisible(true);

    }

    public static void secondRoom() {
        JFrame secondFrame = new JFrame();
        JPanel secondPanel = new JPanel();
        JLabel details = new JLabel();
        JLabel title = new JLabel("<html><h1>CLEAN SWEEP ROOM: 'Kitchen'</h1><br><br></html>");
        JButton demoLabel = new JButton("Back To Room Selection");

        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.PAGE_AXIS));
        System.out.println("SECOND DEMO RIGHT HERE");
        String returnValue = CleanSweepMain.demo_2();
        secondFrame.setSize(500, 500);
        secondPanel.setSize(500, 500);
        secondPanel.setBackground(Color.PINK);
        details.setText("<html>" + returnValue + "</html>");
        secondPanel.add(title);
        secondPanel.add(details);
        secondPanel.add(demoLabel);
        demoLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondFrame.setVisible(false);
            }
        });
        secondFrame.add(secondPanel);
        secondFrame.setVisible(true);
    }

        public static void history () {

            JTextArea jl = new JTextArea("<html><h1>CLEAN SWEEP HISTORY</h1><br/></html>");
            jl.setBackground(Color.MAGENTA);
            JFrame optionFrame = new JFrame("CLEAN SWEEP HISTORY");
            JButton returnToRoom = new JButton("Return to LivingRoom");
            JPanel jp = new JPanel();
            jp.setBackground(Color.MAGENTA);
            jl.setSize(500, 200);
            optionFrame.setSize(600, 550);
            jp.setSize(600, 600);
            jp.add(jl);


            jp.setLayout(new BoxLayout(jp, BoxLayout.PAGE_AXIS));

            returnToRoom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //thirdRoom();
                    optionFrame.setVisible(false);
                }
            });
            JTextArea titles = new JTextArea();
            titles.setBackground(Color.MAGENTA);
            titles.setText("Curr_time\tCurr_Charge\tPosition\tIs_obstacle\tFloor_type\tis_not_on_return_path");
            titles.setEditable(false);
            jp.add(titles);

            optionFrame.add(jp);
            optionFrame.add(jp);

            StringBuilder sb = new StringBuilder();

            for (LogInfo l : CleanSweep.log_info_list) {

                sb.append(l.getCurrTime() + "\t");
                sb.append(l.getCurrCharge() + "\t");
                sb.append(l.getPosition() + "\t");
                sb.append(l.getIsObstacle() + "\t");
                sb.append(l.getFloorType() + "\t");
                sb.append(l.getIsOnReturnPath() + "\t");
                sb.append("\n");
            }

            jl.setVisible(true);
            jl.setEditable(false);
            jl.setText(sb.toString());
            jp.add(jl);
            jp.add(returnToRoom);
            optionFrame.add(jp);
            optionFrame.setVisible(true);
        }

    public static void thirdRoom(){
        ArrayList<RoomNode> rN = CleanSweepMain.floor_master_list;
        logs = new ArrayList<>();
        CleanSweep.set_log_info_list(logs);
        JFrame newFrame = new JFrame();
        JPanel newPanel = new JPanel();
        JLabel newLabel = new JLabel("<html><h1>CLEAN SWEEP ROOM: 'Living Room'</h1><br><br></html>");
        JLabel imageLabel = new JLabel();
        ImageIcon thirdRoomLogo = new ImageIcon("shiningStarTiny.png");
        imageLabel.setIcon(thirdRoomLogo);

        JButton demoLabel = new JButton("Back To Room Selection");
        JButton historyLabel = new JButton ("Clean Sweep History");
        //demoLabel.setText("l");
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        newPanel.setSize(500, 500);
        newFrame.setSize(500, 500);
        newLabel.setSize(300, 300);
        //newFrame.setLayout(new FlowLayout());
        newPanel.setBackground(Color.PINK);
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

        JLabel lowBattery = new JLabel();
        lowBattery.setText("Low Battery!");
        lowBattery.setVisible(!CleanSweep.on_return_path); // Clean Sweep is not on return path
        newPanel.add(lowBattery);

        demoLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrame.setVisible(false);
            }
        });
        newPanel.add(demoLabel);
        newFrame.add(newPanel);
        newFrame.setVisible(true);

        historyLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                history();
            }
        });
        newPanel.add(historyLabel);
        newPanel.add(imageLabel);
        newFrame.add(newPanel);
        newFrame.setVisible(true);

    }

    }

