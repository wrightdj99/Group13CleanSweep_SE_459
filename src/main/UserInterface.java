package main;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.awt.*;

public class UserInterface extends JFrame {
    public UserInterface(){
        //Declarations and layout setting
        JFrame firstFrame = new JFrame("CLEAN SWEEP OPERATIONS");
        JPanel canvas = new JPanel();
        canvas.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
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
    }

    public static void optionMenu() {
        JFrame newFrame = new JFrame();
        JPanel newPanel = new JPanel();
        JLabel newLabel = new JLabel("CLEAN SWEEP DEMO");
        JLabel demoLabel = new JLabel();
        newFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        newPanel.setSize(500, 500);
        newFrame.setSize(500, 500);
        newFrame.setLayout(new FlowLayout());
        newPanel.setBackground(Color.MAGENTA);
        newPanel.add(newLabel);
        for(int i = 0; i < 9; i++){
            JLabel out = new JLabel((String.valueOf(i)));
            out.setBorder(new EmptyBorder(0, 20, 0, 0));
            newPanel.add(out);
        }
        newFrame.add(newPanel);
        newFrame.setVisible(true);

    }
}

