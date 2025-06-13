package com.ok.view;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.dao.UserDao;
import com.ok.po.User;
import com.ok.service.ServerClientService;
import com.ok.util.SocketUtil;

public class ServerClient extends JFrame {

    public static void main(String[] args) {
        ServerClient serverClient = new ServerClient();
        serverClient.createJFrame();
    }

    ServerClientService service = new ServerClientService();

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
            service.startServer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
