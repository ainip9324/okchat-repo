package com.ok.view;

import com.ok.po.User;
import com.ok.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.createFrame();
    }

    JPanel northJPanel = null;
    JLabel photooJLabel = null;

    JLabel userNameJLabel = null;
    JTextField userNameJTextField = null;
    JLabel pwdJLabel = null;
    JPasswordField pwdJPasswordField = null;
    JPanel centerJPanel = null;

    JButton loginJButton = null;
    JButton registerJButton = null;
    JPanel southJPanel = null;

    public void createFrame(){
        ImageIcon imageIcon = new ImageIcon(LoginView.class.getClassLoader().getResource("com/ok/images/logo.png"));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
        photooJLabel = new JLabel("",imageIcon,JLabel.CENTER);
        northJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northJPanel.add(photooJLabel);
        this.add(northJPanel,BorderLayout.NORTH);

        userNameJLabel = new JLabel("账号：");
        userNameJTextField = new JTextField(21);
        userNameJTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        pwdJLabel = new JLabel("密码：");
        pwdJPasswordField = new JPasswordField(21);
        pwdJPasswordField.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        centerJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerJPanel.add(userNameJLabel);
        centerJPanel.add(userNameJTextField);
        centerJPanel.add(pwdJLabel);
        centerJPanel.add(pwdJPasswordField);
        this.add(centerJPanel,BorderLayout.CENTER);

        loginJButton = new JButton("登录");
        loginJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                User user = new User();
                user.setUserName(userNameJTextField.getText());
                user.setPwd(new String(pwdJPasswordField.getPassword()));

                UserService userService = new UserService();
                if(userService.login(user)){
                    LoginView.this.dispose();

                    FriendListView friendListView = new FriendListView(user.getUserName());
                    friendListView.createFrame();

                }else{
                    JOptionPane.showMessageDialog(LoginView.this,"账号或密码错误","提示",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        registerJButton = new JButton("注册");
        registerJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterView registerView = new RegisterView();
                registerView.createFrame();
            }
        });

        southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southJPanel.add(loginJButton);
        southJPanel.add(registerJButton);
        this.add(southJPanel,BorderLayout.SOUTH);

        this.setTitle("OkChat登录");
        this.setBounds(505,300,400,350);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
