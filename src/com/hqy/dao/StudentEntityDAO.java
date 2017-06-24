package com.hqy.dao;

/**
 * Created by Administrator on 2017/6/21.
 */
import com.hqy.bean.StudentEntity;

public interface StudentEntityDAO {
    public String login(int id, String pwd);
    public String login2(int id, String pwd);
    public StudentEntity queryStudentEntityById(int id);
    public StudentEntity[] queryStudentEntityByName(String name);
    public StudentEntity[] queryStudentEntityByMajor(String major);
    public StudentEntity[] queryStudentEntityByClass(int classId);
    public int updateStudentPwd(int id, String oldPwd, String newPwd);
    public int updateStudentEmail(int id, String email);
}
