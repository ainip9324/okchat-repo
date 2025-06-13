package com.ok.view;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.po.User;
import com.ok.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class FriendListView extends JFrame {
    String username;

    public FriendListView(String username){
        this.username = username;
    }

    //可以滑动的好友列表
    JScrollPane jScrollPane = null;
    JPanel jPanel = null;//放置好友列表的面板
    //好友列表信息
    List<User> users;

    public void createFrame(){
        try{
            Socket socket = new Socket("127.0.0.1",8888);
            Message message = new Message();
            message.setMessageType(MessageType.GET_USERS);

            SocketUtil.getSocketUtil().sendMessage(socket,message);

            //获取输入和反馈
            Message responseMessage = SocketUtil.getSocketUtil().getMessage(socket);
            users = responseMessage.getUsers();

        }catch (Exception e){
            e.printStackTrace();
        }

        jPanel = new JPanel(new GridLayout(users.size(),1,15,15));
        for(int i = 0;i<users.size();i++){
            ImageIcon imageIcon = new ImageIcon(FriendListView.class.getClassLoader().getResource("com/ok/images/tx.jpg"));
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
            JLabel jLabel = new JLabel(users.get(i).getUserName(),imageIcon,JLabel.LEFT);
            jPanel.add(jLabel);
        }
        //网格布局放入可滚动的面板中
        jScrollPane = new JScrollPane(jPanel);
        this.add(jScrollPane,BorderLayout.CENTER);

        this.setTitle(this.username+"的好友列表");
        this.setBounds(530,60,400,650);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
