package com.hqy.dao.impl;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hqy.bean.FacultyEntity;
import com.hqy.dao.FacultyEntityDAO;
import com.hqy.util.DatabaseConnection;

public class FacultyEntityDAOImpl implements FacultyEntityDAO {

    // 获取所有教师信息
    public FacultyEntity[] queryFacultyEntityAll() {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT * FROM facultyinfo";
        FacultyEntity[] faculty = null;
        try {
            // 建立可滚动的结果集
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询
            rs.last();
            faculty = new FacultyEntity[rs.getRow()];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                faculty[i] = new FacultyEntity();
                faculty[i].setFaculty_name(rs.getString("faculty_name"));
                faculty[i].setFaculty_addr(rs.getString("faculty_address"));
                faculty[i].setFaculty_contact(rs.getString("faculty_contact"));
                faculty[i].setFaculty_tel(rs.getString("faculty_tel"));
                i++;
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return faculty;
    }
}
