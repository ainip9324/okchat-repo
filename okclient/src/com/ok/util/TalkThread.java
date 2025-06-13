package com.ok.util;

import com.ok.common.Message;
import com.ok.common.MessageType;

import javax.swing.*;
import java.net.Socket;
import java.util.List;

public class TalkThread extends Thread{
    Socket socket;

    JTextArea jTextArea;

    Boolean isRun = true;

    public TalkThread(Socket socket,JTextArea jTextArea){
        this.socket = socket;
        this.jTextArea = jTextArea;
    }

    @Override
    public void run(){
        while(isRun){
            try {
                //接收服务端发来的消息
                Message responseMessage = SocketUtil.getSocketUtil().getMessage(socket);

                if(responseMessage.getMessageType()== MessageType.TALK){
                    jTextArea.append(responseMessage.getContent()+"\n");
                }else if(responseMessage.getMessageType()== MessageType.TALK_LEAVING_MESSAGE){
                    List<Message> messages = responseMessage.getMessages();
                    for(int i = 0;i<messages.size();i++){
                        Message message = messages.get(i);
                        jTextArea.append(message.getContent()+"\n");
                    }
                } else if(responseMessage.getMessageType()== MessageType.TALK_CLOSE){
                    this.isRun = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
