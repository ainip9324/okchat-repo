package com.ok.dao;

import com.ok.common.Message;
import com.ok.po.User;
import com.ok.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    //存入数据库
    public void insertMessage(Message message){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //获取连接
            connection = JDBCUtil.getJdbcUtil().getConnection();
            //
            StringBuffer stringBuffer = new StringBuffer("insert into message(user_name,friend_name,is_read,content) value(?,?,?,?)");
            preparedStatement = connection.prepareStatement(stringBuffer.toString());
            preparedStatement.setString(1,message.getUserName());
            preparedStatement.setString(2,message.getFriendName());
            preparedStatement.setInt(3,message.getIsRead());
            preparedStatement.setString(4,message.getContent());
            //SQL
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.getMessage();
        }finally {
            JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
        }
    }

    //获取数据库未读留言
    public List<Message> getMessages(String userName,String friendName){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Message> messages  = null;

        try{
            //获取连接
            connection = JDBCUtil.getJdbcUtil().getConnection();
            //准备数据库语句
            StringBuffer stringBuffer = new StringBuffer(" select user_name,friend_name,content from message where is_read=0 and user_name=? and friend_name=?");
            preparedStatement = connection.prepareStatement(stringBuffer.toString());
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,friendName);
            //执行sql
            resultSet = preparedStatement.executeQuery();
            //
            messages = new ArrayList<>();
            //遍历信息
            while(resultSet.next()){
                Message message = new Message();
                message.setUserName(resultSet.getString("user_name"));
                message.setFriendName(resultSet.getString("friend_name"));
                message.setContent(resultSet.getString("content"));
                messages.add(message);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
        }

        return messages;
    }

    //更新消息的状态为已读
    public void updateMessageState(Message message){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //获取连接
            connection = JDBCUtil.getJdbcUtil().getConnection();
            //
            StringBuffer stringBuffer = new StringBuffer("update message set is_read=1 where user_name=? and friend_name=?");
            preparedStatement = connection.prepareStatement(stringBuffer.toString());
            preparedStatement.setString(1,message.getUserName());
            preparedStatement.setString(2,message.getFriendName());
            //SQL
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.getMessage();
        }finally {
            JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
        }
    }
}
