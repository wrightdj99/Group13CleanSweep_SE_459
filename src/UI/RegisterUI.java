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

public class RegisterUI extends JPanel {

  private static JFrame frame;
  protected JLabel emailLabel, passwordLabel, confirmPasswordLabel;
  protected JTextField emailTextField;
  protected JPasswordField passwordField, confirmPasswordField;
  protected JButton createButton, backButton;
  protected JTextArea errorArea;

  public RegisterUI() {
    super(new GridBagLayout());

    emailLabel = new JLabel("Enter your email");
    emailTextField = new JTextField(20);

    passwordLabel = new JLabel("Enter a password");
    passwordField = new JPasswordField(20);

    confirmPasswordLabel = new JLabel("Confrim password");
    confirmPasswordField = new JPasswordField(20);

    errorArea = new JTextArea(1,30);
    errorArea.setEditable(false);

    createButton = new JButton("Create");
    backButton = new JButton("Back");

    createButton.addActionListener(e -> {
      String email = emailTextField.getText();
      String password = passwordField.getText();
      String confirmPassword = confirmPasswordField.getText();
      if (confirmPassword.equals(password)) {
        Account account = new Account(email, password);
        CleanSweepMain.getAccountFromUI(account);
        UserInterface.optionMenu();
        frame.setVisible(false);
      } else {
        errorArea.setText("Passwords don't match");
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
    add(emailTextField, c);
    add(passwordLabel, c);
    add(passwordField, c);
    add(confirmPasswordLabel, c);
    add(confirmPasswordField, c);
    add(createButton, c);
    add(errorArea, c);
    add(backButton, c);

    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    c.weighty = 1.0;
  }

  public static void createAndShowGUI() {
    frame = new JFrame("Register");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.add(new RegisterUI());

    frame.pack();
    frame.setSize(500,500);
    frame.setVisible(true);
  }
}
