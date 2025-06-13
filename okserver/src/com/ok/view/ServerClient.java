package com.ok.view;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.dao.UserDao;
import com.ok.po.User;

public class ServerClient extends JFrame {

    public static void main(String[] args) {
        ServerClient serverClient = new ServerClient();
        serverClient.createJFrame();
    }

    UserDao userDao = new UserDao();

    public void createJFrame(){
        JLabel jLabel = new JLabel("服务器已启动，监听在8888端口等待客户端连接",JLabel.CENTER);
        this.add(jLabel, BorderLayout.CENTER);

        this.setTitle("OkChat服务器");
        this.setBounds(505,305,350,250);
        this.setVisible(true);
        this.setResizable(false);//不能调大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //启动服务
        try{
            this.server();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //服务器端连接方法
    public void server() throws Exception{
        //服务器监听在8888端口上
        ServerSocket serverSocket = new ServerSocket(8888);

        //保证持续工作
        while(true){
            Socket socket = serverSocket.accept();

            //接收客户端的信息
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Message requestMessage = (Message)objectInputStream.readObject();

            switch (requestMessage.getMessageType()){
                case MessageType.LOGIN:{
                    User user = requestMessage.getUser();
                    //验证
                    Message message = new Message();
                    if(userDao.login(user.getUserName(),user.getPwd())!=null){
                        message.setMessageType(MessageType.LOGIN_SUCCESS);
                    }else{
                        message.setMessageType(MessageType.LOGIN_FAIL);
                    }
                    //向客户端输出验证结果
                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(message);
                    break;
                }

            }


        }
    }
}
