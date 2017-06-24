package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import com.hqy.dao.StudentEntityDAO;
import com.hqy.dao.TeacherEntityDAO;
import com.hqy.dao.impl.StudentEntityDAOImpl;
import com.hqy.dao.impl.TeacherEntityDAOImpl;
import com.hqy.util.WindowsHandler;


public class ModifyPwdFrame extends JFrame {
    JFrame parentFrame;
    private int entityId, entityType;
    public static final int TEACHER_PWD = 0;
    public static final int STUDENT_PWD = 1;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ModifyPwdFrame(String title, int entityId, int entityType) {
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
        this.entityId = entityId;
        this.entityType = entityType;
        PersonPwdPanel pwdInfoPanel = new PersonPwdPanel();
        getContentPane().add(pwdInfoPanel);

        // 注册窗口监听器
        addWindowListener(new WindowsHandler());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private class PersonPwdPanel extends JPanel {
        // 定义登录容器Panel中的控件元素。
        JLabel currPwdLabel, newPwdLabel, reNewPwdLabel;
        JButton okButton, returnButton;
        JPanel infoPanel, buttonPanel;
        JPasswordField currPwdField, newPwdField, reNewPwdField;
        Border pwdBorder, titleBorder;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public PersonPwdPanel() {
            currPwdLabel = new JLabel("当前密码");  // 构造器参数为标签显示文本
            newPwdLabel = new JLabel("新密码");
            reNewPwdLabel = new JLabel("确认密码");

            currPwdField = new JPasswordField(18);
            newPwdField = new JPasswordField(18);
            reNewPwdField = new JPasswordField(18);

            okButton = new JButton("确认修改"); // 构造器参数为按钮显示文本 = new JButton("修改个人照片"); // 构造器参数为按钮显示文本
            returnButton = new JButton("返回上级菜单"); // 构造器参数为按钮显示文本

            pwdBorder = BorderFactory.createEtchedBorder();
            titleBorder = BorderFactory.createTitledBorder(pwdBorder, "修改密码");
            infoPanel = new JPanel();
            buttonPanel = new JPanel();

            setLayout(new BorderLayout());

            buttonPanel.add(okButton);
            buttonPanel.add(returnButton);
            add(buttonPanel, BorderLayout.SOUTH);

            infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            infoPanel.setBorder(titleBorder);
            infoPanel.add(currPwdLabel);
            infoPanel.add(currPwdField);
            infoPanel.add(newPwdLabel);
            infoPanel.add(newPwdField);
            infoPanel.add(reNewPwdLabel);
            infoPanel.add(reNewPwdField);
            add(infoPanel, BorderLayout.CENTER);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();

            //事件源注册事件监听器
            okButton.addActionListener(buttonAction);
            returnButton.addActionListener(buttonAction);
            returnButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            //@Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(e.getSource().equals(returnButton))
                {
                    ModifyPwdFrame.this.dispose();
                    parentFrame.setVisible(true);
                }
                else
                {
                    String oldPwd = new String(currPwdField.getPassword());
                    String newPwd = new String(newPwdField.getPassword());
                    String reNewPwd = new String(reNewPwdField.getPassword());
                    if(newPwd.compareTo(reNewPwd) == 0)
                    //if(newPwdField.getPassword().equals(reNewPwdField.getPassword()))
                    {
                        int returnVal;

                        if(entityType == ModifyPwdFrame.STUDENT_PWD)
                        {
                            StudentEntityDAO stuEntity = new StudentEntityDAOImpl();
                            returnVal = stuEntity.updateStudentPwd(entityId, oldPwd, reNewPwd);
                        }
                        else
                        {
                            TeacherEntityDAO tchEntity = new TeacherEntityDAOImpl();
                            returnVal = tchEntity.updateTeacherPwd(entityId, oldPwd, reNewPwd);
                        }
                        // if(new String(pwdField.getPassword()).compareTo("123456") == 0)
                        if (returnVal == -1) {
                            JOptionPane.showMessageDialog(null,
                                    "密码错误，请重新输入密码！", "警告", JOptionPane.ERROR_MESSAGE);
                            currPwdField.setText("");
                            newPwdField.setText("");
                            reNewPwdField.setText("");
                        }
                        else if (returnVal == 1) {
                            JOptionPane.showMessageDialog(null,
                                    "密码修改成功！", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,
                                    "无法修改数据库中信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,
                                "请重新输入确认密码！", "警告", JOptionPane.ERROR_MESSAGE);
                        newPwdField.setText("");
                        reNewPwdField.setText("");
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
                new ModifyPwdFrame("修改个人密码", 1310001, ModifyPwdFrame.STUDENT_PWD);
            }
        });
    }

}

