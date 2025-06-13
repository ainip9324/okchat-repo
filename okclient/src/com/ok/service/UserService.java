package com.ok.service;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.po.User;

import java.io.*;
import java.net.Socket;

public class UserService {

    public boolean login(User user){
        try{
            //和本机建立连接通信
            Socket socket = new Socket("127.0.0.1",8888);

            Message message = new Message();
            message.setMessageType(MessageType.LOGIN);
            message.setUser(user);

            //获取输出流，向外输出内容
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(message);
            //通过socket连接的输入流，读入服务器的返回信息
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Message acceptmessage = (Message)objectInputStream.readObject();

            if(acceptmessage.getMessageType()== MessageType.LOGIN_SUCCESS){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

}
