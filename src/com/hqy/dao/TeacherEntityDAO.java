package com.hqy.dao;

/**
 * Created by Administrator on 2017/6/21.
 */
import com.hqy.bean.TeacherEntity;

public interface TeacherEntityDAO {
    public String login(int id, String pwd);
    public TeacherEntity queryTeacherEntityById(int id);
    public TeacherEntity[] queryTeacherEntityAll();
    public int updateTeacherEntity(TeacherEntity student);
    public int updateTeacherPwd(int id, String oldPwd, String newPwd);
    public int updateTeacherEmail(int id, String email);
}
