package com.hqy.dao;

/**
 * Created by Administrator on 2017/6/21.
 */

import com.hqy.bean.ClassEntity;

public interface ClassEntityDAO {

    public ClassEntity[] queryClassEntityByStudentId(int studentId);
    public ClassEntity[] queryClassEntityByTeacherId(int teacherId);

}
