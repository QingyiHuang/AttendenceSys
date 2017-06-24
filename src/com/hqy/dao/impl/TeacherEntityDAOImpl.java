package com.hqy.dao.impl;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;

        import com.hqy.bean.TeacherEntity;
        import com.hqy.dao.TeacherEntityDAO;
        import com.hqy.util.DatabaseConnection;

public class TeacherEntityDAOImpl implements TeacherEntityDAO {
    // 完成登陆的检查，如果登陆成功返回该教师 的姓名信息。
    public String login(int id, String pwd) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT * FROM teacherinfo WHERE teacher_id = " + id
                + " AND teacher_login_pwd = " + "'" + pwd + "'";
        String stuName = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询

            if (rs.next()) {
                stuName = rs.getString("teacher_name");
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stuName;
    }

    // 获取指定教师编号的教师信息
    public TeacherEntity queryTeacherEntityById(int id) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT * FROM teacherinfo WHERE teacher_id = " + id;
        TeacherEntity teacher = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询

            if (rs.next()) {
                teacher = new TeacherEntity();
                teacher.setTeacher_Id(rs.getInt("teacher_id"));
                teacher.setTeacher_name(rs.getString("teacher_name"));
                teacher.setTeacher_colleage(rs.getString("college_name"));
                teacher.setTeacher_faculty(rs.getString("faculty_name"));
                teacher.setTeacher_email(rs.getString("teacher_email"));
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacher;
    }

    // 获取所有教师信息
    public TeacherEntity[] queryTeacherEntityAll() {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT * FROM teacherinfo";
        TeacherEntity[] teacher = null;
        try {
            // 建立可滚动的结果集
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询
            rs.last();
            teacher = new TeacherEntity[rs.getRow()];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                teacher[i] = new TeacherEntity();
                teacher[i].setTeacher_Id(rs.getInt("teacher_id"));
                teacher[i].setTeacher_name(rs.getString("teacher_name"));
                teacher[i].setTeacher_colleage(rs.getString("college_name"));
                teacher[i].setTeacher_faculty(rs.getString("faculty_name"));
                teacher[i].setTeacher_email(rs.getString("teacher_email"));
                i++;
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacher;
    }

    // 修改用户密码
    public int updateTeacherPwd(int id, String oldPwd, String newPwd) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT * FROM teacherinfo WHERE teacher_id = " + id
                + " AND teacher_login_pwd = " + "'" + oldPwd + "'";
        String updateSQL = "UPDATE teacherinfo SET teacher_login_pwd = " + "'"
                + newPwd + "'" + " WHERE teacher_id = " + id;
        int returnVal = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询
            if (rs.next()) {
                returnVal = stmt.executeUpdate(updateSQL); // 声明结果集, 执行更新
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnVal;
    }

    // 修改用户邮箱地址
    public int updateTeacherEmail(int id, String email) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String updateSQL = "UPDATE teacherinfo SET teacher_email = " + "'"
                + email + "'" + " WHERE teacher_id = " + id;
        int returnVal = 0;
        try {
            Statement stmt = conn.createStatement();
            returnVal = stmt.executeUpdate(updateSQL); // 执行更新
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnVal;
    }

    // 更新教师信息
    public int updateTeacherEntity(TeacherEntity teacher) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
/*		String updateSQL = "UPDATE teacherinfo SET teacher_name = ?, college_name = ?, faculty_name = ?, teacher_email = ? "
				+ " WHERE teacher_id = " + student.getTeacher_Id();*/
        String updateSQL = "UPDATE teacherinfo SET teacher_name = " + "'" + teacher.getTeacher_name() + "'"
                + ", college_name = " + "'" + teacher.getTeacher_colleage() + "'"
                + ", faculty_name = " + "'" + teacher.getTeacher_faculty() + "'"
                + ", teacher_email = " + "'" + teacher.getTeacher_email() + "'"
                + " WHERE teacher_id = " + teacher.getTeacher_Id();
        int returnVal = 0;
        try {
            Statement stmt = conn.createStatement();
            returnVal = stmt.executeUpdate(updateSQL); // 执行更新

            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnVal;
    }
}

