package com.ok.util;

import com.ok.common.Message;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketUtil {

    //确保内存中同一时间只有一个对象
    private static SocketUtil socketUtil = null;
    private SocketUtil(){

    }

    //获取单例对象的方法
    public static SocketUtil getSocketUtil(){
        if(socketUtil==null){
            socketUtil = new SocketUtil();
        }
        return socketUtil;
    }

    //发送Message
    public void sendMessage(Socket socket, Message requestMessage)throws Exception{
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(requestMessage);
    }
    //获取Message
    public Message getMessage(Socket socket)throws Exception{
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Message responseMessage = (Message)objectInputStream.readObject();
        return responseMessage;
    }

}
