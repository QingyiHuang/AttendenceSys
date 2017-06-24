package com.hqy.dao;

/**
 * Created by Administrator on 2017/6/21.
 */
import com.hqy.bean.AttendanceEntity;

public interface AttendanceEntityDAO {

    public int insertAttendanceEntity(AttendanceEntity attEntity);
    public AttendanceEntity[] queryAttendanceEntityByTeacherId(int teacherId);
    public AttendanceEntity[] queryAttendanceEntityByStuIdAndCourse(int studentId, int course_id);
    public AttendanceEntity[] queryAttendanceEntityByTchIdAndCourse(int teacherId, int course_id);
    public int updateAttendanceEntity(AttendanceEntity attEntity);
}

