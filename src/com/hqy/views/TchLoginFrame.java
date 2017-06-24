package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.hqy.dao.TeacherEntityDAO;
import com.hqy.dao.impl.TeacherEntityDAOImpl;
import com.hqy.util.Communal;
import com.hqy.util.WindowsHandler;

public class TchLoginFrame extends JFrame {
    JFrame parentFrame;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 320;

    public TchLoginFrame(String title) {
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
        Image img = new ImageIcon(this.getClass().getResource("/images/top.jpg")).getImage();
        setIconImage(img);

        //设置窗体的内容面板，该面板包含了所有的有效GUI组件信息
        TchLoginPanel loginPanel = new TchLoginPanel();
        setContentPane(loginPanel);

        //设置窗口其他显示属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        //注册窗口监听器
        addWindowListener(new WindowsHandler());
    }

    private class TchLoginPanel extends JPanel {
        //定义登录容器Panel中的控件元素。
        JLabel picLabel;
        JLabel idLabel, pwdLabel;
        JTextField tchIdTextField;
        JPasswordField pwdField;
        JButton okButton,clearButton;
        JPanel buttonPanel,infoPanel;
        JCheckBox adminCheckBox;

        //登陆容器panel构造，将所有控件元素装入容器。
        public TchLoginPanel() {
            //生成所有控件对象
            picLabel = new JLabel();
            idLabel = new JLabel("教师ID："); //构造器参数为标签显示文本
            pwdLabel = new JLabel("密    码："); //构造器参数为标签显示文本
            tchIdTextField = new JTextField(20);
            pwdField = new JPasswordField(20);
            okButton = new JButton("Login");  //构造器参数为按钮显示文本
            clearButton = new JButton("Reset"); //构造器参数为按钮显示文本
            okButton.setBackground(Color.darkGray);
            okButton.setForeground(Color.white);
            clearButton.setBackground(Color.darkGray);
            clearButton.setForeground(Color.white);
            buttonPanel = new JPanel();
            infoPanel = new JPanel();
            adminCheckBox = new JCheckBox("我是管理员");

            //设置TchLoginPanel布局为BorderLayout。
            setLayout(new BorderLayout());

            //依次添加pic、login信息控件到面板容器，并设置infoPanel布局管理器为FlowLayout。
            infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            picLabel.setSize(DEFAULT_WIDTH, DEFAULT_WIDTH/2);
            ImageIcon image = new ImageIcon(this.getClass().getResource("/images/top2.jpg"));
            image.setImage(image.getImage().getScaledInstance(
                    picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_DEFAULT));
            picLabel.setIcon(image);
            infoPanel.add(picLabel);
            infoPanel.add(idLabel);
            infoPanel.add(tchIdTextField);
            infoPanel.add(pwdLabel);
            infoPanel.add(pwdField);
            infoPanel.add(adminCheckBox);
            add(infoPanel,BorderLayout.CENTER);

            //依次添加按钮控件到面板容器，buttonPanel布局管理器默认流布局，
            //并将buttonPanel控件作为StuLoginPanel的底端组件。
            buttonPanel.add(okButton);
            buttonPanel.add(clearButton);
            add(buttonPanel,BorderLayout.SOUTH);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();

            //事件源注册事件监听器
            okButton.addActionListener(buttonAction);
            clearButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource().equals(okButton)) {
                    if(tchIdTextField.getText().trim().equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "教师ID信息不能为空！",
                                "警告", JOptionPane.ERROR_MESSAGE);
                        tchIdTextField.setText("");
                    }
                    else if(new String(pwdField.getPassword()).equals(""))
                    {
                        JOptionPane.showMessageDialog(null, "密码信息不能为空！",
                                "警告", JOptionPane.ERROR_MESSAGE);
                        pwdField.setText("");
                    }
                    else if(Communal.isInteger(tchIdTextField.getText().trim()) == false)
                    {
                        JOptionPane.showMessageDialog(null, "无效ID，只能为数字形式！",
                                "警告", JOptionPane.ERROR_MESSAGE);
                        tchIdTextField.setText("");
                    }
                    else
                    {
                        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，
                        // 线程将鼠标点击和按键控制转移到用户接口组件。
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                String tchName = null;
                                TeacherEntityDAO tchEntity = new TeacherEntityDAOImpl();
                                if ((tchName = tchEntity.login(
                                        Integer.parseInt(tchIdTextField.getText().trim()),
                                        new String(pwdField.getPassword()))) != null) {
                                    TchFuncPortalFrame frame = new TchFuncPortalFrame(
                                            "欢迎教师 " + tchName +" 登录考勤客户端!",
                                            Integer.parseInt(tchIdTextField.getText().trim()));
                                    frame.parentFrame = TchLoginFrame.this;
                                    TchLoginFrame.this.setVisible(false);
                                } else {
                                    JOptionPane.showMessageDialog(null, "请重新输入密码！",
                                            "密码错误", JOptionPane.ERROR_MESSAGE);
                                    pwdField.setText("");
                                }
                            }
                        });
                    }
                } else {
                    tchIdTextField.setText("");
                    pwdField.setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TchLoginFrame("考勤客户端-教师登陆入口");
            }
        });
    }
}

