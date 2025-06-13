package com.ok.service;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.po.User;
import com.ok.util.SocketUtil;

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
            SocketUtil.getSocketUtil().sendMessage(socket,message);
            //通过socket连接的输入流，读入服务器的返回信息
            Message responseMessage = SocketUtil.getSocketUtil().getMessage(socket);

            if(responseMessage.getMessageType()== MessageType.LOGIN_SUCCESS){
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
