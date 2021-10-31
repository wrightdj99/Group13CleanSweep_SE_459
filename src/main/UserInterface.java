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
        CleanSweep cleanSweep = new CleanSweep();
        CleanSweepMain cleanSweepMain = new CleanSweepMain();
        ArrayList<RoomNode> rN = CleanSweepMain.floor_master_list;
        JFrame newFrame = new JFrame();
        JPanel newPanel = new JPanel();
        JLabel newLabel = new JLabel("CLEAN SWEEP DEMO:" + "\n\n");
        JLabel demoLabel = new JLabel();
        demoLabel.setText("l");
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        newPanel.setSize(500, 500);
        newFrame.setSize(500, 500);
        newLabel.setSize(300, 300);
        newFrame.setLayout(new FlowLayout());
        newPanel.setBackground(Color.MAGENTA);
        for(RoomNode r : rN){
            JPanel outputOfCS = new JPanel(new GridLayout(0, 1));
            JLabel outToScreen = new JLabel();
            JLabel newLine = new JLabel("\n");
            newPanel.add(outToScreen);
            newPanel.add(newLine);
            newPanel.add(outputOfCS);
            outToScreen.setText("VISITED NODE AT: " + r.get_position());
            outputOfCS.add(outToScreen);
            System.out.println("TO THE SCREEN!!!!!!!!!");
            System.out.println(r);
        }
        newPanel.add(newLabel);
        newFrame.add(newPanel);
        newPanel.add(demoLabel);
        newFrame.setVisible(true);

    }
}

