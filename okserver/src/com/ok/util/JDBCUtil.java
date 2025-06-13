package com.ok.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCUtil {

    private static JDBCUtil jdbcUtil = null;

    //确保工具类只会被new一次
    private JDBCUtil(){

    }

    //获取工具类
    public static JDBCUtil getJdbcUtil(){
        if(jdbcUtil==null){
            jdbcUtil = new JDBCUtil();
        }

        return jdbcUtil;
    }

    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");//注册到我系统里
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public Connection getConnection ()throws Exception{
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/okchat","ainip","123456");
    }

    //关闭数据库连接
    public void closeConnection(ResultSet resultSet, Statement statement,Connection connection){
        try{
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                statement.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
