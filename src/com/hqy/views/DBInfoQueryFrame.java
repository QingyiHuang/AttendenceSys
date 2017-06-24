package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.hqy.bean.AttendanceEntity;
import com.hqy.bean.ClassEntity;
import com.hqy.bean.TeacherEntity;
import com.hqy.util.WindowsHandler;

public class DBInfoQueryFrame extends JFrame {
    public static final int TEACHER_INFO_REQ = 0;
    public static final int CLASS_INFO_REQ = 1;
    public static final int ATTENCE_INFO_REQ = 2;
    JFrame parentFrame;
    private int queryType;
    private Object[] queryResult;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 380;

    public DBInfoQueryFrame(String title, Object[] object, int queryType) {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle(title);

        // 显示位置在屏幕长度和宽度的1/3处。
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidthpx = screenSize.width;
        int screenHeightpx = screenSize.height;
        setLocation(screenWidthpx / 3, screenHeightpx / 3);
        setLocationByPlatform(false);

        // 设置窗口最小化时显示的图标
        Image img = new ImageIcon(this.getClass().getResource("/images/005.jpg")).getImage();
        setIconImage(img);

        // 装入panel，并显示在中间（默认布局为边框布局borderLayout）
        queryResult = object;
        this.queryType = queryType;
        DBInfoPanel dbInfoPanel = new DBInfoPanel();
        getContentPane().add(dbInfoPanel);

        // 注册窗口监听器
        addWindowListener(new WindowsHandler());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private class DBInfoPanel extends JPanel {
        // 定义登录容器Panel中的控件元素。
        JButton returnButton;
        JPanel queryResultPanel, buttonPanel;
        JScrollPane scrollPanel;
        JTable dbInfoTable;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public DBInfoPanel() {
            String[] columnName = { "姓名", "任课课程", "所属学院" };
            Object data[][] = { { "屈欣欣", "传媒与科学", "旅游学院" },
                    { "陆磊磊", "数据库", "软件学院" }, { "海甜甜", "面向对象程序设计", "通信学院" } };

            if(queryResult != null && queryResult.length > 0)
            {
                if(queryType == DBInfoQueryFrame.CLASS_INFO_REQ)  //教学班和课程信息
                {
                    String[] columnName1 = { "教学班号", "课程名称", "上课时间", "任课教师" };
                    ClassEntity classTemp = new ClassEntity();
                    Object[][] data1 = new Object[queryResult.length][4];
                    for(int i = 0; i < queryResult.length; i++){
                        classTemp = (ClassEntity)queryResult[i];
                        data1[i][0] = new Integer(classTemp.getClass_Id()) ;
                        data1[i][1] = new String(classTemp.getCourse_name());
                        data1[i][2] = new String(classTemp.getClass_time());
                        data1[i][3] = new String(classTemp.getTeacher_name());
                    }
                    columnName = columnName1;
                    data = data1;
                }
                else if(queryType == DBInfoQueryFrame.TEACHER_INFO_REQ) //教师信息
                {
                    String[] columnName1 = { "教师编号", "姓名", "所属学院", "所属教研室","电子邮箱" };
                    TeacherEntity tchTemp = new TeacherEntity();
                    Object[][] data1 = new Object[queryResult.length][5];
                    for(int i = 0; i < queryResult.length; i++){
                        tchTemp = (TeacherEntity)queryResult[i];
                        data1[i][0] = new Integer(tchTemp.getTeacher_Id()) ;
                        data1[i][1] = new String(tchTemp.getTeacher_name());
                        data1[i][2] = new String(tchTemp.getTeacher_colleage());
                        data1[i][3] = new String(tchTemp.getTeacher_faculty());
                        data1[i][4] = new String(tchTemp.getTeacher_email());
                    }
                    columnName = columnName1;
                    data = data1;
                }
                else //考勤信息
                {
                    String[] columnName1 = { "教学班号", "课程名称", "学生ID", "姓名", "考勤时间", "考勤记录" };
                    AttendanceEntity attTemp = new AttendanceEntity();
                    Object[][] data1 = new Object[queryResult.length][6];
                    for(int i = 0; i < queryResult.length; i++){
                        attTemp = (AttendanceEntity)queryResult[i];
                        data1[i][0] = new Integer(attTemp.getClass_Id()) ;
                        data1[i][1] = new String(attTemp.getCourse_name());
                        data1[i][2] = new Integer(attTemp.getStudent_Id());
                        data1[i][3] = new String(attTemp.getStudent_name());
                        data1[i][4] = new String(attTemp.getAttendance_date());
                        data1[i][5] = new String(attTemp.getAttendance_status());
                    }
                    columnName = columnName1;
                    data = data1;
                }
            }

            dbInfoTable = new JTable(data, columnName);
            dbInfoTable.setAutoCreateColumnsFromModel(true);
            dbInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            dbInfoTable.setRowHeight(20);
            dbInfoTable.setEnabled(false);
            scrollPanel = new JScrollPane(dbInfoTable,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 90));
            //queryBoder = BorderFactory.createEtchedBorder();

            buttonPanel = new JPanel();
            queryResultPanel = new JPanel();

            setLayout(new BorderLayout(10,10));
            queryResultPanel.add(scrollPanel);
            add(queryResultPanel, BorderLayout.CENTER);

            returnButton = new JButton("返回上级菜单");
            buttonPanel.add(returnButton);
            add(buttonPanel, BorderLayout.SOUTH);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();
            // 事件源注册事件监听器
            returnButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                DBInfoQueryFrame.this.dispose();
                parentFrame.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DBInfoQueryFrame("查看教师信息", null, DBInfoQueryFrame.TEACHER_INFO_REQ);
            }
        });
    }

}

