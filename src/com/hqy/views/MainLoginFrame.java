package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.hqy.util.WindowsHandler;


public class MainLoginFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 280;
    private static final int DEFAULT_HEIGHT = 320;

    public MainLoginFrame(String title) {
        // 设置窗体大小
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // 设置窗体标题
        setTitle(title);

        // 显示位置在屏幕长度和宽度的1/3处。
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidthpx = screenSize.width;
        int screenHeightpx = screenSize.height;
        setLocation(screenWidthpx /3, screenHeightpx /3);
        setLocationByPlatform(false);
        // 设置窗口最小化时显示的图标
        Image img = new ImageIcon(this.getClass().getResource("/images/top.jpg")).getImage();
        setIconImage(img);
        // 设置窗体的内容面板，该面板包含了所有的有效GUI组件信息
        MainLoginPanel loginPanel = new MainLoginPanel();
        setContentPane(loginPanel);
        // 设置窗口其他显示属性
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        // 注册窗口监听器
        addWindowListener(new WindowsHandler());
    }

    private class MainLoginPanel extends JPanel {
        // 定义登录容器Panel中的控件元素。
        JLabel picLabel;
        JButton stuLoginButton, tchLoginButton;
        JPanel infoPanel;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public MainLoginPanel() {
            // 生成所有控件对象
            picLabel = new JLabel();
            stuLoginButton = new JButton("这里是学生入口"); // 构造器参数为按钮显示文本
            tchLoginButton = new JButton("这里是教师入口"); // 构造器参数为按钮显示文本
            stuLoginButton.setBackground(Color.black);
            tchLoginButton.setBackground(Color.black);
            stuLoginButton.setForeground(Color.white);
            tchLoginButton.setForeground(Color.white);
            infoPanel = new JPanel();

            // 设置MainLoginPanel布局为BorderLayout。
            setLayout(new BorderLayout());

            // 依次添加pic、按钮控件信息控件到面板容器，并设置infoPanel布局管理器为FlowLayout。
            infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            picLabel.setSize(DEFAULT_WIDTH, DEFAULT_WIDTH / 2);
            ImageIcon image = new ImageIcon(this.getClass().getResource("/images/top2.jpg"));
            image.setImage(image.getImage().getScaledInstance(
                    picLabel.getWidth(), picLabel.getHeight(), Image.SCALE_DEFAULT));
            picLabel.setIcon(image);
            infoPanel.add(picLabel);
            infoPanel.add(stuLoginButton);
            infoPanel.add(tchLoginButton);

            // 让infoPanel充满整个容器。
            add(infoPanel, BorderLayout.CENTER);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();

            // 事件源注册事件监听器
            stuLoginButton.addActionListener(buttonAction);
            tchLoginButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource().equals(stuLoginButton)) {
                    // 所有swing组件必须由事件分派线程（event dispatch
                    // thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            StuLoginFrame frame = new StuLoginFrame("学生登陆入口");
                            frame.parentFrame = MainLoginFrame.this;
                        }
                    });

                    MainLoginFrame.this.setVisible(false);
                } else {
                    // 所有swing组件必须由事件分派线程（event dispatch
                    // thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            TchLoginFrame frame = new TchLoginFrame("教工登陆入口");
                            frame.parentFrame = MainLoginFrame.this;
                        }
                    });
                    MainLoginFrame.this.setVisible(false);
                }
            }
        }

        private class KKButtonAction implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

            }

        }

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainLoginFrame("学生考勤管理系统");
            }
        });
    }

}
