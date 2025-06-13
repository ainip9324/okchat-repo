package com.ok.service;

import com.ok.common.Message;
import com.ok.common.MessageType;
import com.ok.dao.UserDao;
import com.ok.po.User;
import com.ok.util.SocketUtil;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerClientService {

    UserDao userDao = new UserDao();

    //启动服务端的服务，等待客户端的连接
    public void startServer() throws Exception{
        //服务器监听在8888端口上
        ServerSocket serverSocket = new ServerSocket(8888);

        //保证持续工作
        while(true){
            Socket socket = serverSocket.accept();

            //接收客户端的信息
            Message requestMessage = SocketUtil.getSocketUtil().getMessage(socket);

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
                    SocketUtil.getSocketUtil().sendMessage(socket,message);
                    break;
                }
                case MessageType.REGISTER:{
                    User user = requestMessage.getUser();

                    Message message = new Message();
                    if(userDao.getByUsername(user.getUserName())==null){
                        userDao.inserUser(user);
                        message.setMessageType(MessageType.REGISTER_SUCCESS);
                        message.setContent("注册成功");
                    }else{
                        message.setMessageType(MessageType.REGISTER_FAIL);
                        message.setContent("注册失败，账号已经被注册");
                    }
                    //向客户端输出验证结果
                    SocketUtil.getSocketUtil().sendMessage(socket,message);
                    break;
                }
                case MessageType.GET_USERS:{
                    Message message = new Message();
                    List<User> users = userDao.getUsers();
                    message.setUsers(users);

                    //向客户端输出验证结果
                    SocketUtil.getSocketUtil().sendMessage(socket,message);
                    break;
                }

            }


        }
    }
}
