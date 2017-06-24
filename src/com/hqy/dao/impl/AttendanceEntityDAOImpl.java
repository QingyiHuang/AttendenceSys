package com.hqy.dao.impl;

/**
 * Created by Administrator on 2017/6/21.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hqy.bean.AttendanceEntity;
import com.hqy.dao.AttendanceEntityDAO;
import com.hqy.util.DatabaseConnection;

public class AttendanceEntityDAOImpl implements AttendanceEntityDAO {
    // 插入学生考勤信息。
    public int insertAttendanceEntity(AttendanceEntity attEntity) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String insertSQL1 = "INSERT INTO attendanceinfo(class_id, record_time) VALUES(?, ?)";
        String querySQL = "SELECT attendance_id FROM attendanceinfo WHERE class_id = ? AND record_time = ?";
        String insertSQL2 = "INSERT INTO attendancerecord(attendance_Id, student_id, attendance_status) VALUES(?, ?, ?)";
        int returnedVal = -1;
        int attendance_id = 10;
        try {
            // 建立可滚动的结果集
            PreparedStatement pstmt = conn.prepareStatement(insertSQL1);
            pstmt.setInt(1, attEntity.getClass_Id());
            pstmt.setString(2, attEntity.getAttendance_date());
            returnedVal = pstmt.executeUpdate();
            ResultSet rs = null;
            if (returnedVal != 0) {
                pstmt = conn.prepareStatement(querySQL);
                pstmt.setInt(1, attEntity.getClass_Id());
                pstmt.setString(2, attEntity.getAttendance_date());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    pstmt = conn.prepareStatement(insertSQL2);
                    pstmt.setInt(1, rs.getInt(1));
                    pstmt.setInt(2, attEntity.getStudent_Id());
                    pstmt.setString(3, attEntity.getAttendance_status());
                    returnedVal = pstmt.executeUpdate();
                }
            }

            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(pstmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnedVal;
    }

    // 依据教师权限获取所有考勤记录
    public AttendanceEntity[] queryAttendanceEntityByTeacherId(int teacherId) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT DISTINCT attendanceinfo.attendance_id, attendanceinfo.class_id,"
                + " classinfo.course_name, attendancerecord.student_id, studentinfo.student_name,"
                + " attendanceinfo.record_time, attendancerecord.attendance_status"
                + " FROM attendanceinfo, classinfo, attendancerecord, studentinfo, classstudentinfo"
                + " WHERE classinfo.teacher_id = " + teacherId
                + " AND classinfo.class_id = attendanceinfo.class_id"
                + " AND attendanceinfo.attendance_id = attendancerecord.attendance_id"
                + " AND classstudentinfo.student_id = attendancerecord.student_id"
                + " AND classstudentinfo.student_id = studentinfo.student_id";

        AttendanceEntity[] attRecord = null;
        try {
            // 建立可滚动的结果集
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询
            rs.last();
            attRecord = new AttendanceEntity[rs.getRow()];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                attRecord[i] = new AttendanceEntity();
                attRecord[i].setAttendance_id(rs.getInt(1));
                attRecord[i].setClass_Id(rs.getInt(2));
                attRecord[i].setCourse_name(rs.getString(3));
                attRecord[i].setStudent_Id(Integer.parseInt(rs.getString(4)));
                attRecord[i].setStudent_name(rs.getString(5));
                attRecord[i].setAttendance_date(rs.getString(6));
                attRecord[i].setAttendance_status(rs.getString(7));
                i++;
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attRecord;
    }

    // 依据学生权限获取所有考勤记录
    public AttendanceEntity[] queryAttendanceEntityByStuIdAndCourse(int studentId, int course_id){
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT DISTINCT attendanceinfo.class_id, classinfo.course_name,"
                + " attendancerecord.student_id, studentinfo.student_name,"
                + " attendanceinfo.record_time, attendancerecord.attendance_status"
                + " FROM attendanceinfo, classinfo, attendancerecord, studentinfo, classstudentinfo"
                + " WHERE classinfo.course_id = " + course_id
                + " AND classinfo.class_id = attendanceinfo.class_id"
                + " AND attendanceinfo.attendance_id = attendancerecord.attendance_id"
                + " AND classstudentinfo.student_id = attendancerecord.student_id"
                + " AND classstudentinfo.student_id = studentinfo.student_id"
                + " AND studentinfo.student_id = " + studentId;

        AttendanceEntity[] attRecord = null;
        try {
            // 建立可滚动的结果集
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询
            rs.last();
            attRecord = new AttendanceEntity[rs.getRow()];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                attRecord[i] = new AttendanceEntity();
                attRecord[i].setClass_Id(rs.getInt(1));
                attRecord[i].setCourse_name(rs.getString(2));
                attRecord[i].setStudent_Id(rs.getInt(3));
                attRecord[i].setStudent_name(rs.getString(4));
                attRecord[i].setAttendance_date(rs.getString(5));
                attRecord[i].setAttendance_status(rs.getString(6));
                i++;
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attRecord;

    }

    // 依据学生权限获取所有考勤记录
    public AttendanceEntity[] queryAttendanceEntityByTchIdAndCourse(int teacherId, int course_id){
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String querySQL = "SELECT DISTINCT attendanceinfo.class_id, classinfo.course_name,"
                + " attendancerecord.student_id, studentinfo.student_name,"
                + " attendanceinfo.record_time, attendancerecord.attendance_status"
                + " FROM attendanceinfo, classinfo, attendancerecord, studentinfo, classstudentinfo"
                + " WHERE classinfo.course_id = " + course_id
                + " AND classinfo.class_id = attendanceinfo.class_id"
                + " AND classinfo.teacher_id = " + teacherId
                + " AND attendanceinfo.attendance_id = attendancerecord.attendance_id"
                + " AND classstudentinfo.student_id = attendancerecord.student_id"
                + " AND classstudentinfo.student_id = studentinfo.student_id";

        AttendanceEntity[] attRecord = null;
        try {
            // 建立可滚动的结果集
            Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(querySQL); // 声明结果集, 执行查询
            rs.last();
            attRecord = new AttendanceEntity[rs.getRow()];
            int i = 0;
            rs.beforeFirst();
            while (rs.next()) {
                attRecord[i] = new AttendanceEntity();
                attRecord[i].setClass_Id(rs.getInt(1));
                attRecord[i].setCourse_name(rs.getString(2));
                attRecord[i].setStudent_Id(rs.getInt(3));
                attRecord[i].setStudent_name(rs.getString(4));
                attRecord[i].setAttendance_date(rs.getString(5));
                attRecord[i].setAttendance_status(rs.getString(6));
                i++;
            }
            DatabaseConnection.close(rs); // 关闭结果集
            DatabaseConnection.close(stmt); // 关闭预处理对象
            DatabaseConnection.close(conn); // 关闭连接对象

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attRecord;

    }


    // 更新某一条考勤记录
    public int updateAttendanceEntity(AttendanceEntity attEntity) {
        Connection conn = DatabaseConnection.getConnection(); // 获得连接对象
        String updateSQL = "UPDATE attendancerecord SET attendance_status = "
                + "'" + attEntity.getAttendance_status() + "'"
                + " WHERE attendance_id = " + attEntity.getAttendance_id()
                + " AND student_id = " + attEntity.getStudent_Id();
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
