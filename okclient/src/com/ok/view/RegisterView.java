package com.ok.view;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.po.User;
import com.ok.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RegisterView extends JFrame {

    JPanel centerJPanel = null;
    JLabel userNameJLabel = null;
    JTextField userNameJTextField = null;
    JLabel pwdJLabel = null;
    JPasswordField pwdJPasswordField = null;
    JLabel realNameJLabel = null;
    JTextField realNameJTextField = null;

    JPanel southJPanel = null;
    JButton submitJButton = null;
    JButton resetJButton = null;

    public void createFrame(){
        //center
        centerJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userNameJLabel = new JLabel("用户账号:");
        userNameJTextField = new JTextField(21);
        userNameJTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        pwdJLabel = new JLabel("用户密码:");
        pwdJPasswordField = new JPasswordField(21);
        pwdJPasswordField.setFont(new Font(Font.DIALOG,Font.PLAIN,18));
        realNameJLabel = new JLabel("用户姓名:");
        realNameJTextField = new JTextField(21);
        realNameJTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,18));

        centerJPanel.add(userNameJLabel);
        centerJPanel.add(userNameJTextField);
        centerJPanel.add(pwdJLabel);
        centerJPanel.add(pwdJPasswordField);
        centerJPanel.add(realNameJLabel);
        centerJPanel.add(realNameJTextField);
        this.add(centerJPanel,BorderLayout.CENTER);
        //south
        southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitJButton = new JButton("提交");
        submitJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String pwd = new String(pwdJPasswordField.getPassword());
                User user = new User();
                user.setUserName(userNameJTextField.getText());
                user.setPwd(pwd);
                user.setRealname(realNameJTextField.getText());

                //装在客户端与服务端的通信信息
                Message message = new Message();
                message.setMessageType(MessageType.REGISTER);
                message.setUser(user);

                try{
                    Socket socket = new Socket("127.0.0.1",8888);

                    //向服务端写注册信息
                    SocketUtil.getSocketUtil().sendMessage(socket,message);
                    //接收服务端读注册反馈
                    Message responseMessage = SocketUtil.getSocketUtil().getMessage(socket);

                    if(responseMessage.getMessageType()==MessageType.REGISTER_SUCCESS){
                        JOptionPane.showMessageDialog(RegisterView.this,responseMessage.getContent(),"提示",JOptionPane.WARNING_MESSAGE);
                        RegisterView.this.dispose();
                    }else{
                        JOptionPane.showMessageDialog(RegisterView.this,responseMessage.getContent(),"提示",JOptionPane.WARNING_MESSAGE);
                    }

                }catch (Exception c){
                    c.printStackTrace();
                }
            }
        });
        resetJButton = new JButton("重置");
        resetJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userNameJTextField.setText("");
                pwdJPasswordField.setText("");
                realNameJTextField.setText("");
            }
        });

        southJPanel.add(submitJButton);
        southJPanel.add(resetJButton);
        this.add(southJPanel,BorderLayout.SOUTH);

        this.setTitle("OkChat注册");
        this.setBounds(705,300,400,350);
        this.setVisible(true);
        this.setResizable(false);
    }
}
