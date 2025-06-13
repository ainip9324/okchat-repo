package com.ok.dao;

import com.ok.po.User;
import com.ok.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public  User login(String username,String pwd){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try{
            //获取连接
            connection = JDBCUtil.getJdbcUtil().getConnection();
            //准备数据库语句
            StringBuffer stringBuffer = new StringBuffer(" select id,user_name,pwd,real_name from user where user_name=? and pwd=? ");
            preparedStatement = connection.prepareStatement(stringBuffer.toString());
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,pwd);
            //执行sql
            resultSet = preparedStatement.executeQuery();
            //遍历信息
            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setPwd(resultSet.getString("pwd"));
                user.setRealname(resultSet.getString("real_name"));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }
}
