package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.hqy.bean.AttendanceEntity;
import com.hqy.bean.ClassEntity;
import com.hqy.bean.StudentEntity;
import com.hqy.dao.AttendanceEntityDAO;
import com.hqy.dao.ClassEntityDAO;
import com.hqy.dao.StudentEntityDAO;
import com.hqy.dao.impl.AttendanceEntityDAOImpl;
import com.hqy.dao.impl.ClassEntityDAOImpl;
import com.hqy.dao.impl.StudentEntityDAOImpl;
import com.hqy.util.WindowsHandler;


public class TchAttRecordFrame extends JFrame {
    JFrame parentFrame;
    private int teacherId;
    private static final int DEFAULT_WIDTH = 310;
    private static final int DEFAULT_HEIGHT = 350;

    public TchAttRecordFrame(String title,  int tchId) {
        //设置窗体大小
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //设置窗体标题
        setTitle(title);

        //显示位置在屏幕长度和宽度的1/3处。
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidthpx = screenSize.width;
        int screenHeightpx = screenSize.height;
        setLocation(screenWidthpx / 3, screenHeightpx / 3);
        setLocationByPlatform(false);

        //设置窗口最小化时显示的图标
        Image img = new ImageIcon(this.getClass().getResource("/images/005.jpg")).getImage();
        setIconImage(img);

        //设置窗体的内容面板，该面板包含了所有的有效GUI组件信息
        teacherId = tchId;
        StuLoginPanel loginPanel = new StuLoginPanel();
        setContentPane(loginPanel);

        //设置窗口其他显示属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        //注册窗口监听器
        addWindowListener(new WindowsHandler());
    }

    private class StuLoginPanel extends JPanel {
        //定义登录容器Panel中的控件元素。
        JLabel picLabel;
        JLabel classIdLabel, courseNameLabel, stuIdLabel, stuNameLabel, attTimeLabel, attRecordLabel;
        JTextField courseNameTextField, attTimeTextField, stuNameTextField;
        JComboBox<String> classCombo, stuIdCombo, attRecordCombo;
        JButton okButton,returnButton;
        JPanel buttonPanel,infoPanel;
        ClassEntity[] classForTch;
        StudentEntity[] studentForClass;


        //登陆容器panel构造，将所有控件元素装入容器。
        public StuLoginPanel() {
            //生成所有控件对象
            picLabel = new JLabel();

            classIdLabel = new JLabel("教学班号："); //构造器参数为标签显示文本
            classCombo = new JComboBox<String>();
            classCombo.setPreferredSize(new Dimension(200, 20));
            ClassEntityDAO collegeEntity = new ClassEntityDAOImpl();
            if ((classForTch = collegeEntity.queryClassEntityByTeacherId(teacherId)) != null) {
                for (int i = 0; i < classForTch.length; i++) {
                    classCombo.addItem(Integer.toString(classForTch[i].getClass_Id()));
                }
            }

            courseNameLabel = new JLabel("课程名称：");
            courseNameTextField = new JTextField("数据结构",18);
            for (int i = 0; i < classForTch.length; i++) {
                if (classForTch[i].getClass_Id() == Integer.parseInt(classCombo
                        .getItemAt(classCombo.getSelectedIndex()))) {
                    courseNameTextField.setText(classForTch[i].getCourse_name());
                    break;
                }
            }

            stuIdLabel = new JLabel("学   生  ID：");
            stuIdCombo = new JComboBox<String>();
            stuIdCombo.setPreferredSize(new Dimension(200, 20));
            stuIdCombo.setActionCommand("clear remove all stuID");
            StudentEntityDAO stuEntity = new StudentEntityDAOImpl();
            if ((studentForClass = stuEntity.queryStudentEntityByClass(Integer.parseInt(classCombo
                    .getItemAt(classCombo.getSelectedIndex())))) != null
                    && studentForClass.length > 0) {
                for (int i = 0; i < studentForClass.length; i++) {
                    stuIdCombo.addItem(Integer.toString(studentForClass[i].getStudent_Id()));
                }
            }

            stuNameLabel = new JLabel("学生姓名：");
            stuNameTextField = new JTextField("张磊",18);
            for (int i = 0; i < studentForClass.length; i++) {
                if (studentForClass[i].getStudent_Id() == Integer.parseInt(stuIdCombo
                        .getItemAt(stuIdCombo.getSelectedIndex()))) {
                    stuNameTextField.setText(studentForClass[i].getStudent_name());
                    break;
                }
            }

            attTimeLabel = new JLabel("考勤时间："); //设置日期格式
            attTimeTextField = new JTextField(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),18);

            attRecordLabel = new JLabel("考勤记录：");
            attRecordCombo = new JComboBox<String>();
            attRecordCombo.addItem("正常");
            attRecordCombo.addItem("迟到");
            attRecordCombo.addItem("旷课");
            attRecordCombo.addItem("请假");
            attRecordCombo.setPreferredSize(new Dimension(200, 20));

            okButton = new JButton("录入考勤信息");  //构造器参数为按钮显示文本
            returnButton = new JButton("返回上级菜单"); //构造器参数为按钮显示文本

            buttonPanel = new JPanel();
            infoPanel = new JPanel();

            //设置StuLoginPanel布局为BorderLayout。
            setLayout(new BorderLayout());

            //依次添加pic、login信息控件到面板容器，并设置infoPanel布局管理器为FlowLayout。
            infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            picLabel.setSize(DEFAULT_WIDTH, DEFAULT_WIDTH /4);
            ImageIcon image = new ImageIcon(this.getClass().getResource("/images/top2.jpg"));
            image.setImage(image.getImage().getScaledInstance(
                    picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_DEFAULT));
            picLabel.setIcon(image);
            infoPanel.add(picLabel);
            infoPanel.add(classIdLabel);
            infoPanel.add(classCombo);
            infoPanel.add(courseNameLabel);
            infoPanel.add(courseNameTextField);
            infoPanel.add(stuIdLabel);
            infoPanel.add(stuIdCombo);
            infoPanel.add(stuNameLabel);
            infoPanel.add(stuNameTextField);
            infoPanel.add(attTimeLabel);
            infoPanel.add(attTimeTextField);
            infoPanel.add(attRecordLabel);
            infoPanel.add(attRecordCombo);
            add(infoPanel,BorderLayout.CENTER);

            //依次添加按钮控件到面板容器，buttonPanel布局管理器默认流布局，
            //并将buttonPanel控件作为StuLoginPanel的底端组件。
            buttonPanel.add(okButton);
            buttonPanel.add(returnButton);
            add(buttonPanel,BorderLayout.SOUTH);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();
            ComoboBoxAction comboBoxAction = new ComoboBoxAction();

            //事件源注册事件监听器
            okButton.addActionListener(buttonAction);
            returnButton.addActionListener(buttonAction);
            stuIdCombo.addItemListener(comboBoxAction);
            classCombo.addItemListener(comboBoxAction);
        }


        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(e.getSource().equals(returnButton))
                {
                    TchAttRecordFrame.this.dispose();
                    parentFrame.setVisible(true);
                }
                else
                {
                    AttendanceEntity attRecord = new AttendanceEntity();
                    attRecord.setClass_Id(Integer.parseInt(classCombo.getItemAt(classCombo.getSelectedIndex())));
                    attRecord.setStudent_Id(Integer.parseInt(stuIdCombo.getItemAt(stuIdCombo.getSelectedIndex())));
                    attRecord.setAttendance_status(attRecordCombo.getItemAt(attRecordCombo.getSelectedIndex()));
                    attRecord.setAttendance_date(attTimeTextField.getText());

                    AttendanceEntityDAO attEntity = new AttendanceEntityDAOImpl();
                    if ((attEntity.insertAttendanceEntity(attRecord) ) > 0) {
                        JOptionPane.showMessageDialog(null,
                                "考勤记录录入成功！", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,
                                "无法录入考勤记录，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }


        // 定义事件监听器类，实现事件监听器接口中的itemStateChanged方法，引入一个用户具体的操作。
        private class ComoboBoxAction implements ItemListener {

            public void itemStateChanged(ItemEvent arg0) {
                // TODO Auto-generated method stub
                if(arg0.getSource().equals(classCombo))
                {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            for (int i = 0; i < classForTch.length; i++) {
                                if (classForTch[i].getClass_Id() == Integer.parseInt(classCombo
                                        .getItemAt(classCombo.getSelectedIndex()))) {
                                    courseNameTextField.setText(classForTch[i].getCourse_name());
                                    break;
                                }
                            }

                            StudentEntityDAO stuEntity = new StudentEntityDAOImpl();
                            if ((studentForClass = stuEntity.queryStudentEntityByClass(Integer.parseInt(classCombo
                                    .getItemAt(classCombo.getSelectedIndex())))) != null
                                    && studentForClass.length > 0) {
                                stuIdCombo.setActionCommand("reset remove all stuID");
                                stuIdCombo.removeAllItems();
                                stuIdCombo.setActionCommand("clear remove all stuID");
                                for (int i = 0; i < studentForClass.length; i++) {
                                    stuIdCombo.addItem(Integer.toString(studentForClass[i].getStudent_Id()));
                                }
                            }
                            for (int i = 0; i < studentForClass.length; i++) {
                                if (studentForClass[i].getStudent_Id() == Integer.parseInt(stuIdCombo
                                        .getItemAt(stuIdCombo.getSelectedIndex()))) {
                                    stuNameTextField.setText(studentForClass[i].getStudent_name());
                                    break;
                                }
                            }
                        }
                    });
                }
                else if(arg0.getSource().equals(stuIdCombo))
                {
                    if(stuIdCombo.getActionCommand().equals("clear remove all stuID")){
                        for (int i = 0; i < studentForClass.length; i++) {
                            if (studentForClass[i].getStudent_Id() == Integer.parseInt(stuIdCombo
                                    .getItemAt(stuIdCombo.getSelectedIndex()))) {
                                stuNameTextField.setText(studentForClass[i].getStudent_name());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TchAttRecordFrame("学生考勤信息录入", 130507);
            }
        });
    }
}
