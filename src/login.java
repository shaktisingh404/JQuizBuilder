import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class login {
    int id;

    public void loginView() throws SQLException {
        sqloperations manage = new sqloperations();
        JFrame frame = new JFrame();
        frame.setSize(700, 550);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#2ecc71"));
        frame.setLocationRelativeTo(null);

        JLabel heading = new JLabel("JQuizBuilder");
        heading.setBounds(0, 50, 700, 50);
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 40));
        heading.setForeground(Color.white);
        frame.add(heading);

        JLabel uname = new JLabel("Username: ");
        uname.setBounds(175, 130, 150, 50);
        uname.setForeground(Color.white);
        frame.add(uname);

        JTextField name = new JTextField();
        name.setBounds(175, 170, 350, 30);
        frame.add(name);

        JLabel upass = new JLabel("Password: ");
        upass.setBounds(175, 200, 150, 50);
        upass.setForeground(Color.white);
        frame.add(upass);

        JPasswordField pass = new JPasswordField();
        pass.setBounds(175, 240, 350, 30);
        frame.add(pass);

        /*Login section */
        JButton login = new JButton("LOGIN");
        login.setBounds(225, 300, 100, 40);
        frame.add(login);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = name.getText();
                String password = new String(pass.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please Enter All Information.", "Warning Message",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        sqloperations manage = new sqloperations();
                        id = manage.authUser(username, password);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    if (id == -1) {
                        JOptionPane.showMessageDialog(frame, "User Does Not Exist.", "Warning Message",
                                JOptionPane.WARNING_MESSAGE);
                    } else if (id == 0) {
                        JOptionPane.showMessageDialog(frame, "Incorrect Password, please try again.",
                                "Warning Message", JOptionPane.WARNING_MESSAGE);
                    } else {
                        MainPage mainPage = new MainPage();
                        try {
                            mainPage.mainPageView(id);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        frame.dispose();
                    }
                }
            }
        });

        /*Signup section option */
        JButton signUp = new JButton("SIGNUP");
        signUp.setBounds(375, 300, 100, 40);
        frame.add(signUp);
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp signup = new signUp();
                signup.signUpView();
            }
        });

        /*Guest Quiz Option */
        JButton attend = new JButton("Complete Quiz (Guest)");
        attend.setBounds(225, 350, 250, 40);
        frame.add(attend);
        attend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String surveyCode = JOptionPane.showInputDialog("Please Enter the Unique Survey Code: ");
                try {
                    if (!surveyCode.isEmpty() && surveyCode.length() == 5) {
                        if (manage.check(surveyCode)) {
                            Guest guest = new Guest();
                            guest.guestView(surveyCode);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Incorrect Survey Code, please try again.",
                                    "Warning Message", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception e2) {
                }
            }
        });

        frame.setVisible(true);
    }
}
