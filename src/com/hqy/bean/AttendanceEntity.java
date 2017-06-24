package com.hqy.bean;

/**
 * Created by Administrator on 2017/6/21.
 */

public class AttendanceEntity {
    int    class_Id;
    int    attendance_id;
    int    student_Id;
    String course_name;
    String student_name;
    String attendance_date;
    String attendance_status;

    /**
     * @return the class_Id
     */
    public int getClass_Id() {
        return class_Id;
    }
    /**
     * @param class_Id the class_Id to set
     */
    public void setClass_Id(int class_Id) {
        this.class_Id = class_Id;
    }
    /**
     * @return the attendance_id
     */
    public int getAttendance_id() {
        return attendance_id;
    }
    /**
     * @param attendance_id the attendance_id to set
     */
    public void setAttendance_id(int attendance_id) {
        this.attendance_id = attendance_id;
    }
    /**
     * @return the student_Id
     */
    public int getStudent_Id() {
        return student_Id;
    }
    /**
     * @param student_Id the student_Id to set
     */
    public void setStudent_Id(int student_Id) {
        this.student_Id = student_Id;
    }
    /**
     * @return the student_name
     */
    public String getStudent_name() {
        return student_name;
    }
    /**
     * @param student_name the student_name to set
     */
    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
    /**
     * @return the attendance_date
     */
    public String getAttendance_date() {
        return attendance_date;
    }
    /**
     * @param attendance_date the attendance_date to set
     */
    public void setAttendance_date(String attendance_date) {
        this.attendance_date = attendance_date;
    }
    /**
     * @return the attendance_status
     */
    public String getAttendance_status() {
        return attendance_status;
    }
    /**
     * @param attendance_status the attendance_status to set
     */
    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    /**
     * @return the course_name
     */
    public String getCourse_name() {
        return course_name;
    }
    /**
     * @param course_name the course_name to set
     */
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

}
