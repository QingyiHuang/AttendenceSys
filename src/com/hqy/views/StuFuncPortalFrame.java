package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.hqy.bean.AttendanceEntity;
import com.hqy.bean.ClassEntity;
import com.hqy.bean.StudentEntity;
import com.hqy.bean.TeacherEntity;
import com.hqy.dao.AttendanceEntityDAO;
import com.hqy.dao.ClassEntityDAO;
import com.hqy.dao.StudentEntityDAO;
import com.hqy.dao.TeacherEntityDAO;
import com.hqy.dao.impl.AttendanceEntityDAOImpl;
import com.hqy.dao.impl.ClassEntityDAOImpl;
import com.hqy.dao.impl.StudentEntityDAOImpl;
import com.hqy.dao.impl.TeacherEntityDAOImpl;
import com.hqy.util.Communal;
import com.hqy.util.WindowsHandler;


public class StuFuncPortalFrame extends JFrame {
    JFrame parentFrame;
    private int studentId = 0;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 450;

    public StuFuncPortalFrame(String title, int stuId) {
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
        studentId = stuId;
        StuInfoPanel stuInfoPanel = new StuInfoPanel();
        getContentPane().add(stuInfoPanel);

        // 注册窗口监听器
        addWindowListener(new WindowsHandler());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private class StuInfoPanel extends JPanel {
        // 定义登录容器Panel中的控件元素。
        JLabel picLabel, stuIdLabel;
        JButton quereyPersonalButton, modifyInfoButton;
        JButton modifyPwdButton, quereyTeacherButton, quereyClassButton;
        JPanel infoPanel, queryPanel, picPanel, QueryResultPanel;
        Border infoBoder, QueryBoder, picBoder;
        JScrollPane scrollPanel;

        final JLabel classQueryLabel;
        JTextField classQueryTextField;
        JButton quereyButton;
        JTable attRecordTable;

        DefaultTableModel tableModel;
        TableColumnModel columnModel;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public StuInfoPanel() {
            String[] columnName = { "考勤时间", "考勤课程", "考勤结果" };
            Object data[][] = { { "2017-6-21", "NodeJs", "正常" },
                    { "2017-6-21", "PHP", "迟到" },
                    { "2017-7-15", "react_native", "正常" } };

            picLabel = new JLabel(); // 构造器参数为标签显示文本
            stuIdLabel = new JLabel("13111505", JLabel.CENTER);
            stuIdLabel.setText(Integer.toString(studentId));
            quereyPersonalButton = new JButton("查看个人信息"); // 构造器参数为按钮显示文本
            modifyInfoButton = new JButton("修改个人信息");
            modifyPwdButton = new JButton("修改个人密码");
            quereyTeacherButton = new JButton("查看教师信息");
            quereyClassButton = new JButton("查看课程信息");

            quereyPersonalButton.setBackground(Color.DARK_GRAY);
            modifyInfoButton.setBackground(Color.DARK_GRAY);
            modifyPwdButton.setBackground(Color.DARK_GRAY);
            quereyClassButton.setBackground(Color.DARK_GRAY);
            quereyTeacherButton.setBackground(Color.DARK_GRAY);
            quereyPersonalButton.setForeground(Color.white);
            modifyPwdButton.setForeground(Color.white);
            modifyInfoButton.setForeground(Color.white);
            quereyClassButton.setForeground(Color.white);
            quereyTeacherButton.setForeground(Color.white);

            infoBoder = BorderFactory.createEtchedBorder();
            picBoder = BorderFactory.createEtchedBorder();

            classQueryLabel = new JLabel("请输入要查询考勤记录的课程编号"); // 构造器参数为标签显示文本
            classQueryTextField = new JTextField(18);
            quereyButton = new JButton("查询");
            quereyButton.setBackground(Color.GREEN);
            quereyButton.setForeground(Color.white);
            //create a TableModel
            tableModel = new DefaultTableModel(data,columnName);
            attRecordTable = new JTable(tableModel);
            columnModel = attRecordTable.getColumnModel();
            attRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            attRecordTable.setRowHeight(20);
            attRecordTable.setEnabled(false);
            scrollPanel = new JScrollPane(attRecordTable,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            QueryBoder = BorderFactory.createEtchedBorder();

            queryPanel = new JPanel();
            infoPanel = new JPanel();
            picPanel = new JPanel();
            QueryResultPanel = new JPanel();
            setLayout(null);

            picPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            picPanel.setBorder(picBoder);
            picLabel.setSize(140, 110);
            ImageIcon image = new ImageIcon(this.getClass().getResource("/images/001.jpg"));
            image.setImage(image.getImage().getScaledInstance(
                    picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_DEFAULT));
            picLabel.setIcon(image);
            picPanel.add(picLabel);
            picPanel.add(stuIdLabel);
            picPanel.setBounds(0, 0, 150, 145);
            add(picPanel);

            infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            infoPanel.setBorder(infoBoder);
            infoPanel.add(quereyPersonalButton);
            infoPanel.add(modifyInfoButton);
            infoPanel.add(modifyPwdButton);
            infoPanel.add(quereyTeacherButton);
            infoPanel.add(quereyClassButton);
            infoPanel.setBounds(0, 150, 150, 265);
            add(infoPanel);

            queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            queryPanel.setBorder(QueryBoder);
            queryPanel.add(classQueryLabel);
            queryPanel.add(classQueryTextField);
            queryPanel.add(quereyButton);
            queryPanel.setBounds(160, 0, 340, 70);
            add(queryPanel);

            QueryResultPanel.setLayout(new BorderLayout());
            QueryResultPanel.add(scrollPanel, BorderLayout.CENTER);
            QueryResultPanel.setBounds(160, 70, 500, 345);
            add(QueryResultPanel);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();
            //事件源注册事件监听器
            quereyPersonalButton.addActionListener(buttonAction);
            modifyInfoButton.addActionListener(buttonAction);
            modifyPwdButton.addActionListener(buttonAction);
            quereyTeacherButton.addActionListener(buttonAction);
            quereyClassButton.addActionListener(buttonAction);
            quereyButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(e.getSource().equals(quereyPersonalButton))
                {
                    final StudentEntity student;
                    StudentEntityDAO stuEntity = new StudentEntityDAOImpl();
                    // if(new String(pwdField.getPassword()).compareTo("123456") == 0)
                    if ((student = stuEntity.queryStudentEntityById(studentId)) != null) {
                        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                StuInfoQueryFrame frame = new StuInfoQueryFrame("学生个人信息查询", student);
                                frame.parentFrame = StuFuncPortalFrame.this;
                                StuFuncPortalFrame.this.setVisible(false);
                            }
                        });
                    } else{
                        JOptionPane.showMessageDialog(null,
                                "数据库中没有您的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(e.getSource().equals(modifyInfoButton))
                {
                    JOptionPane.showMessageDialog(null,
                            "只有管理员才可以修改学生信息！", "警告", JOptionPane.ERROR_MESSAGE);
                }
                else if(e.getSource().equals(modifyPwdButton))
                {
                    // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            ModifyPwdFrame frame = new ModifyPwdFrame("修改学生密码", studentId, ModifyPwdFrame.STUDENT_PWD);
                            frame.parentFrame = StuFuncPortalFrame.this;
                            StuFuncPortalFrame.this.setVisible(false);
                        }
                    });
                }
                else if(e.getSource().equals(quereyTeacherButton))
                {
                    final TeacherEntity[] teacher;
                    TeacherEntityDAO tchEntity = new TeacherEntityDAOImpl();
                    // if(new String(pwdField.getPassword()).compareTo("123456") == 0)
                    if ((teacher = tchEntity.queryTeacherEntityAll()) != null) {
                        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                DBInfoQueryFrame frame = new DBInfoQueryFrame("查看教师信息", teacher, DBInfoQueryFrame.TEACHER_INFO_REQ);
                                frame.parentFrame = StuFuncPortalFrame.this;
                                StuFuncPortalFrame.this.setVisible(false);
                            }
                        });
                    } else{
                        JOptionPane.showMessageDialog(null,
                                "数据库中没有您查询的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else  if(e.getSource().equals(quereyClassButton))
                {
                    final ClassEntity[] classInfo;
                    ClassEntityDAO classEntity = new ClassEntityDAOImpl();
                    if ((classInfo = classEntity.queryClassEntityByStudentId(studentId)) != null) {
                        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                DBInfoQueryFrame frame = new DBInfoQueryFrame("查看课程信息", classInfo, DBInfoQueryFrame.CLASS_INFO_REQ);
                                frame.parentFrame = StuFuncPortalFrame.this;
                                StuFuncPortalFrame.this.setVisible(false);
                            }
                        });
                    } else{
                        JOptionPane.showMessageDialog(null,
                                "数据库中没有您查询的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else  if(e.getSource().equals(quereyButton))
                {
                    if (classQueryTextField.getText().trim().equals("")) {
                        JOptionPane.showMessageDialog(null,
                                "待查询课程编号不能够为空！", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(Communal.isInteger(classQueryTextField.getText().trim()) == false)
                    {
                        JOptionPane.showMessageDialog(null, "无效课程编号，只能为数字形式！",
                                "警告", JOptionPane.ERROR_MESSAGE);
                        classQueryTextField.setText("");
                    }else
                    {
                        AttendanceEntityDAO attEntity = new AttendanceEntityDAOImpl();
                        AttendanceEntity[] queryResult;
                        if ((queryResult = attEntity.queryAttendanceEntityByStuIdAndCourse(studentId,
                                Integer.parseInt(classQueryTextField.getText().trim()))) != null
                                && queryResult.length > 0) {
                            String[] column = {"教学班号", "课程名称", "学生姓名", "考勤状态", "考勤时间" };
                            AttendanceEntity attendanceTemp = new AttendanceEntity();
                            Object[][] rowData = new Object[queryResult.length][5];
                            for(int i = 0; i < queryResult.length; i++){
                                attendanceTemp = queryResult[i];
                                rowData[i][0] = new Integer(attendanceTemp.getClass_Id()) ;
                                rowData[i][1] = new String(attendanceTemp.getCourse_name());
                                rowData[i][2] = new String(attendanceTemp.getStudent_name());
                                rowData[i][3] = new String(attendanceTemp.getAttendance_status());
                                rowData[i][4] = new String(attendanceTemp.getAttendance_date());
                            }
                            clearRequeryResultAndRepaint(column, rowData);
                        } else{
                            JOptionPane.showMessageDialog(null,
                                    "数据库中没有您要查询的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(null,
                            "该功能暂不支持！", "警告", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void clearRequeryResultAndRepaint(String[] columnname, Object[][] rowdata){

            while(columnModel.getColumnCount() > 0)
            {
                columnModel.removeColumn(columnModel.getColumn(0));
                tableModel.setColumnCount(columnModel.getColumnCount());
            };
            while(tableModel.getRowCount() > 0)
            {
                tableModel.removeRow(0);
            };

            for(int i = 0; i < columnname.length; i++)
            {
                tableModel.addColumn(columnname[i]);
            }

            for(int i = 0; i < rowdata.length; i++)
            {
                tableModel.addRow(rowdata[i]);
            }

            for(int i = 0; i < columnModel.getColumnCount(); i++)
            {
                columnModel.getColumn(i).setPreferredWidth(90);
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StuFuncPortalFrame("学生考勤系统", 13111505);
            }
        });
    }

}

