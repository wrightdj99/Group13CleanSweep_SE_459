package main;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

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
        canvas.setBackground(Color.MAGENTA);
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
                optionMenu();
                firstFrame.setVisible(false);
            }
        });
        //Final frame sets
        firstFrame.setSize(450, 500);
        firstFrame.getContentPane().add(BorderLayout.CENTER, canvas);
        firstFrame.add(canvas);
        firstFrame.setVisible(true);

        CleanSweep cleanSweep = new CleanSweep();
    }

    public static void optionMenu() {
        ArrayList<RoomNode> rN = CleanSweepMain.floor_master_list;
        JFrame newFrame = new JFrame();
        JPanel newPanel = new JPanel();
        JLabel newLabel = new JLabel("<html><h1>CLEAN SWEEP DEMO</h1><br><br></html>");
        JLabel demoLabel = new JLabel();
        //demoLabel.setText("l");
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        newPanel.setSize(500, 500);
        newFrame.setSize(500, 500);
        newLabel.setSize(300, 300);
        //newFrame.setLayout(new FlowLayout());
        newPanel.setBackground(Color.MAGENTA);
        newPanel.add(newLabel);
        for(RoomNode r : rN){
            int timeToSwitch = 0;
            JLabel zerosPanel = new JLabel();
            JLabel foursPanel = new JLabel();
            JLabel eightsPanel = new JLabel();
            JLabel twelvesPanel = new JLabel();
            JLabel sixteensPanel = new JLabel();
            if(r != null){
                timeToSwitch++;
                if(timeToSwitch >= 0 && timeToSwitch <= 4){
                    if(timeToSwitch == 0){
                        zerosPanel.setText("STARTING NODE: " + r.get_position().toString());
                    }
                    else{
                        foursPanel.setText("VISITED NODE:" + r.get_position().toString());
                    }
                }
                else if(timeToSwitch >= 5 && timeToSwitch <= 8){
                    eightsPanel.setText("VISITED NODE: " + r.get_position().toString());
                }
                else if(timeToSwitch >= 9 && timeToSwitch <= 12){
                    twelvesPanel.setText("VISITED NODE: " + r.get_position().toString());
                }
                else{
                    sixteensPanel.setText("VISITED NODE: " + r.get_position().toString());
                }
                newPanel.add(zerosPanel);
                newPanel.add(foursPanel);
                newPanel.add(eightsPanel);
                newPanel.add(twelvesPanel);
                newPanel.add(sixteensPanel);
            }
        }
        newFrame.add(newPanel);
        newFrame.setVisible(true);

    }
}

