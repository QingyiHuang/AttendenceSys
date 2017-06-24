package com.hqy.util;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DBDRIVER = "com.mysql.jdbc.Driver"; // 驱动串
    private static final String DBURL = "jdbc:mysql://localhost:3306/crebas";// 连接URL
    private static final String DBUSER = "root"; // 用户名
    private static final String DBPASSWORD = ""; // 数据库密码

    public static Connection getConnection() {
        Connection conn = null; // 声明一个连接对象
        try {
            Class.forName(DBDRIVER); // 注册驱动
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) { // 如果conn 连接对象不为空
            try {
                conn.close(); // 关闭conn 连接对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) { // 如果pstmt 预处理对象不为空
            try {
                pstmt.close(); // 关闭pstmt 预处理对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) { // 如果stmt 处理对象不为空
            try {
                stmt.close(); // 关闭stmt 处理对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) { // 如果rs 结果集对象不为null
            try {
                rs.close(); // 关闭rs 结果集对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
