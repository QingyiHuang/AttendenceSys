package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.hqy.bean.TeacherEntity;
import com.hqy.dao.TeacherEntityDAO;
import com.hqy.dao.impl.TeacherEntityDAOImpl;
import com.hqy.util.WindowsHandler;


public class TchInfoQueryFrame extends JFrame {
    JFrame parentFrame;
    TeacherEntity queryTeacher = null;
    private static final int DEFAULT_WIDTH = 350;
    private static final int DEFAULT_HEIGHT = 350;

    public TchInfoQueryFrame(String title, TeacherEntity tchEntity) {
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
        queryTeacher = tchEntity;
        TchInfoPanel tchInfoPanel = new TchInfoPanel();
        getContentPane().add(tchInfoPanel);

        // 注册窗口监听器
        addWindowListener(new WindowsHandler());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private class TchInfoPanel extends JPanel {
        // 定义登录容器Panel中的控件元素。
        JLabel picLabel, picInfoLabel,nameLabel, IdLabel, acedmyLabel, subjectLabel, emailLabel;
        JButton modifyPicButton, modifyEmailButton, returnButton;
        JPanel infoPanel, buttonPanel, picPanel, stuPanel;
        JTextField nameTextField, IdTextField, acedmyTextField, subjectTextField, emailTextField;
        JFileChooser chooser;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public TchInfoPanel() {
            picInfoLabel = new JLabel("个人照片");  // 构造器参数为标签显示文本
            picLabel = new JLabel();
            modifyPicButton = new JButton("修改个人照片");
            //建立文件选择器对象。
            chooser = new JFileChooser();
            //设计文件过滤器，接受以jpg、jpeg、gif结尾的文件
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files","jpg","jpeg","gif");
            chooser.setFileFilter(filter);

            nameLabel = new JLabel("姓名"); // 构造器参数为标签显示文本
            IdLabel = new JLabel("教师编号");
            acedmyLabel = new JLabel("所属学院");
            subjectLabel = new JLabel("所属教研室");
            emailLabel = new JLabel("电子邮箱");

            nameTextField = new JTextField("校长",15);
            IdTextField = new JTextField("13111506",15);
            acedmyTextField = new JTextField("软件工程学院",15);
            subjectTextField = new JTextField("软件实验中心",15);
            emailTextField = new JTextField("xiaozhang@cqupt.edu.cn",15);

            if (queryTeacher != null) {
                nameTextField.setText(queryTeacher.getTeacher_name());
                IdTextField.setText(Integer.toString(queryTeacher.getTeacher_Id()));
                acedmyTextField.setText(queryTeacher.getTeacher_colleage());
                subjectTextField.setText(queryTeacher.getTeacher_faculty());
                emailTextField.setText(queryTeacher.getTeacher_email());
            }

            nameTextField.setEditable(false);
            IdTextField.setEditable(false);
            acedmyTextField.setEditable(false);
            subjectTextField.setEditable(false);
            emailTextField.setEditable(false);

            modifyEmailButton = new JButton("修改个人邮箱"); // 构造器参数为按钮显示文本
            returnButton = new JButton("返回上级菜单"); // 构造器参数为按钮显示文本

            infoPanel = new JPanel();
            picPanel = new JPanel();
            buttonPanel = new JPanel();
            stuPanel = new JPanel();

            setLayout(new BorderLayout());

            buttonPanel.add(modifyEmailButton);
            buttonPanel.add(returnButton);
            add(buttonPanel, BorderLayout.SOUTH);

            picPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            picPanel.add(picInfoLabel);
            picLabel.setSize(140, 110);
            ImageIcon image = new ImageIcon(this.getClass().getResource("/images/003.jpg"));
            image.setImage(image.getImage().getScaledInstance(
                    picLabel.getWidth(), picLabel.getHeight(),Image.SCALE_DEFAULT));
            picLabel.setIcon(image);
            picPanel.add(picLabel);
            picPanel.add(modifyPicButton);
            picPanel.setBounds(200, 0, 140, 180);
            add(picPanel);

            infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            infoPanel.add(nameLabel);
            infoPanel.add(nameTextField);
            infoPanel.add(IdLabel);
            infoPanel.add(IdTextField);
            infoPanel.add(acedmyLabel);
            infoPanel.add(acedmyTextField);
            infoPanel.add(subjectLabel);
            infoPanel.add(subjectTextField);
            infoPanel.add(emailLabel);
            infoPanel.add(emailTextField);
            infoPanel.setBounds(0, 0, 160, 340);
            add(infoPanel);

            stuPanel.setLayout(null);
            stuPanel.add(infoPanel);
            stuPanel.add(picPanel);
            add(stuPanel,BorderLayout.CENTER);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();

            //事件源注册事件监听器
            modifyPicButton.addActionListener(buttonAction);
            modifyEmailButton.addActionListener(buttonAction);
            returnButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(e.getSource().equals(returnButton))
                {
                    TchInfoQueryFrame.this.dispose();
                    parentFrame.setVisible(true);
                }
                else if(e.getSource().equals(modifyEmailButton))
                {
                    String inputVal = JOptionPane.showInputDialog(null, "请输入新邮箱地址",
                            "更新邮箱地址", JOptionPane.INFORMATION_MESSAGE);
                    if (inputVal != null) {
                        TeacherEntityDAO tchEntity = new TeacherEntityDAOImpl();
                        if (tchEntity.updateTeacherEmail(queryTeacher.getTeacher_Id(), inputVal) != 0) {
                            emailTextField.setText(inputVal);
                        }
                        else{
                            JOptionPane.showMessageDialog(null,
                                    "数据库中Email信息更新失败，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else if(e.getSource().equals(modifyPicButton))
                {
                    //设置当前目录
                    chooser.setCurrentDirectory(new File("."));
                    //显示文件选择对话框
                    int result = chooser.showOpenDialog(TchInfoQueryFrame.this);
                    //如果文件选择，则更新图片信息
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String fileName = chooser.getSelectedFile().getPath();
                        ImageIcon image = new ImageIcon(fileName);
                        image.setImage(image.getImage().getScaledInstance(
                                picLabel.getWidth(), picLabel.getHeight(),Image.SCALE_DEFAULT));
                        picLabel.setIcon(image);
                        TchInfoQueryFrame.this.repaint();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,
                            "该功能暂不支持！", "警告", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TchInfoQueryFrame("教师个人信息查询", null);
            }
        });
    }
}
