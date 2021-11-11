package UI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import main.Account;
import main.CleanSweepMain;

public class LoginUI extends JPanel {

  private static JFrame frame;
  protected JLabel emailLabel, passwordLabel;
  protected JTextField emailField;
  protected JTextArea errorArea;
  protected JPasswordField passwordField;
  protected JButton loginButton, backButton;

  public LoginUI(){
    super(new GridBagLayout());

    emailLabel = new JLabel("Enter your email");
    emailField = new JTextField(20);

    passwordLabel = new JLabel("Enter your password");
    passwordField = new JPasswordField(30);

    errorArea = new JTextArea(1,1);
    errorArea.setEditable(false);

    loginButton = new JButton("Login");
    backButton = new JButton("Back");

    loginButton.addActionListener(e -> {
      String email = emailField.getText();
      String password = passwordField.getText();

      Integer pos = CleanSweepMain.emailPosition(email);
      if (pos != null && CleanSweepMain.passwordCorrect(password, pos)){
        UserInterface.optionMenu();
        frame.setVisible(false);
      } else {
        errorArea.setText("No account with that email and password exists");
      }
    });

    backButton.addActionListener(e -> {
      UserInterface.loginRegisterMenu();
      frame.setVisible(false);
    });

    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = GridBagConstraints.REMAINDER;

    c.fill = GridBagConstraints.HORIZONTAL;
    add(emailLabel, c);
    add(emailField, c);
    add(passwordLabel, c);
    add(passwordField, c);
    add(loginButton, c);
    add(errorArea, c);
    add(backButton, c);

    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    c.weighty = 1.0;
  }

  public static void createAndShowGUI() {
    frame = new JFrame("Login");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.add(new LoginUI());

    frame.pack();
    frame.setSize(500,500);
    frame.setVisible(true);
  }

}
