package com.ok.dao;

import com.ok.common.Message;
import com.ok.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
