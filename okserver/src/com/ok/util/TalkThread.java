package com.ok.util;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.dao.MessageDao;

import java.net.Socket;

public class TalkThread extends Thread{

    Socket socket;

    Boolean isRun = true;

    MessageDao messageDao = new MessageDao();

    public TalkThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        while(isRun){
            try {
                //接收
                Message requestMessage = SocketUtil.getSocketUtil().getMessage(socket);
                if(requestMessage.getMessageType()== MessageType.TALK){
                    //消息转发逻辑,get的内容反过来才是fri的socket

                    if(TalkThreadCache.talkThreadCache.get(requestMessage.getFriendName()+"-"+requestMessage.getUserName())!=null){//人在才正常发
                        Socket friendSocket = TalkThreadCache.talkThreadCache.get(requestMessage.getFriendName()+"-"+requestMessage.getUserName()).socket;
                        Message responseMessage = new Message();
                        responseMessage.setMessageType(MessageType.TALK);
                        responseMessage.setContent(requestMessage.getContent());
                        SocketUtil.getSocketUtil().sendMessage(friendSocket,responseMessage);
                    }else{//不在发数据库
                        requestMessage.setIsRead(0);//0代表未读，1代表已读
                        messageDao.insertMessage(requestMessage);
                    }

                }else if(requestMessage.getMessageType()==MessageType.TALK_CLOSE){
                    this.isRun = false;

                    //告知客户端的多线程程序已经结束,己方关闭，故找己方的socket
                    Socket currentSocket = TalkThreadCache.talkThreadCache.get(requestMessage.getUserName()+"-"+requestMessage.getFriendName()).socket;
                    Message responseMessage = new Message();
                    responseMessage.setMessageType(MessageType.TALK_CLOSE);
                    SocketUtil.getSocketUtil().sendMessage(currentSocket,responseMessage);

                    //移除编号
                    TalkThreadCache.talkThreadCache.remove(requestMessage.getUserName()+"-"+requestMessage.getFriendName());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
