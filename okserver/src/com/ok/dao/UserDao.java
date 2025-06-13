package com.ok.dao;

import com.ok.po.User;
import com.ok.util.JDBCUtil;
import com.ok.util.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDao {

    public User login(String username,String pwd){
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
        }finally {
            JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
        }

        return user;
    }

    //存入数据库的方法
    public void inserUser(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //$$
        int getOkId = getId();
        try{
            if(getOkId==-1) throw new CustomException("id生成错误!");
        }catch (CustomException e){
            System.err.println("捕获到自定义异常：" + e.getMessage());
            e.printStackTrace();
        }
        //

        try{
            //获取连接
            connection = JDBCUtil.getJdbcUtil().getConnection();
            //准备数据库语句
            StringBuffer stringBuffer = new StringBuffer(" insert into user(id,user_name,pwd,real_name) value(?,?,?,?)");
            preparedStatement = connection.prepareStatement(stringBuffer.toString());
            preparedStatement.setInt(1,getOkId);
            preparedStatement.setString(2,user.getUserName());
            preparedStatement.setString(3,user.getPwd());
            preparedStatement.setString(4,user.getRealname());
            //执行sql
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
        }
    }

    //根据username获取user信息，看是否已经存在
    public User getByUsername(String username){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try{
            //获取连接
            connection = JDBCUtil.getJdbcUtil().getConnection();
            //准备数据库语句
            StringBuffer stringBuffer = new StringBuffer(" select id,user_name,pwd,real_name from user where user_name=? ");
            preparedStatement = connection.prepareStatement(stringBuffer.toString());
            preparedStatement.setString(1,username);
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
        }finally {
            JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
        }

        return user;
    }

    //获取数据库里面的数据信息
    public List<User> getUsers(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users  = null;

        try{
            //获取连接
            connection = JDBCUtil.getJdbcUtil().getConnection();
            //准备数据库语句
            StringBuffer stringBuffer = new StringBuffer(" select id,user_name,pwd,real_name from user");
            preparedStatement = connection.prepareStatement(stringBuffer.toString());
            //执行sql
            resultSet = preparedStatement.executeQuery();
            //
            users = new ArrayList<>();
            //遍历信息
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setPwd(resultSet.getString("pwd"));
                user.setRealname(resultSet.getString("real_name"));
                users.add(user);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
        }

        return users;
    }

    //$$生成id，并保证id唯一,将自动生成一个八位或九位的id
    public int getId() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int resId = -1;

        while (true) {
            // 生成 ID 算法（使用更可靠的唯一 ID 生成方式）
            int randomId = generateUniqueId();

            try {
                // 获取连接
                connection = JDBCUtil.getJdbcUtil().getConnection();
                // 准备数据库语句
                String sql = "SELECT id FROM user WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, randomId);
                // 执行 SQL
                resultSet = preparedStatement.executeQuery();

                // 检查 ID 是否存在
                if (!resultSet.next()) {
                    resId = randomId;
                    break; // 如果 ID 不存在，退出循环
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                JDBCUtil.getJdbcUtil().closeConnection(resultSet,preparedStatement,connection);
            }
        }

        return resId;
    }

    // 使用更可靠的唯一 ID 生成方式（例如结合时间戳和随机数）
    private int generateUniqueId() {
        Random random = new Random();
        return (int) (System.currentTimeMillis() % 100000000 + random.nextInt(100000000));
    }

}
