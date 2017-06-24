package com.hqy.dao.impl;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hqy.bean.CollegeEntity;
import com.hqy.dao.CollegeEntityDAO;
import com.hqy.util.DatabaseConnection;

public class CollegeEntityDAOImpl implements CollegeEntityDAO {

    // 获取所有教师信息
    public CollegeEntity[] queryCollegeEntityAll() {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT * FROM collegeinfo";
        CollegeEntity[] college = null;
        try {
            // 建立可滚动的结果集
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询
            rs.last();
            college = new CollegeEntity[rs.getRow()];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                college[i] = new CollegeEntity();
                college[i].setCollege_name(rs.getString("college_name"));
                college[i].setCollege_addr(rs.getString("college_address"));
                college[i].setCollege_contact(rs.getString("college_contact"));
                college[i].setCollege_tel(rs.getString("college_tel"));
                i++;
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return college;
    }
}

