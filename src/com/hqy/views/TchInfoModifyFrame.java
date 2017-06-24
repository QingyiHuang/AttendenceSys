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

import com.hqy.bean.CollegeEntity;
import com.hqy.bean.FacultyEntity;
import com.hqy.bean.TeacherEntity;
import com.hqy.dao.CollegeEntityDAO;
import com.hqy.dao.FacultyEntityDAO;
import com.hqy.dao.TeacherEntityDAO;
import com.hqy.dao.impl.CollegeEntityDAOImpl;
import com.hqy.dao.impl.FacultyEntityDAOImpl;
import com.hqy.dao.impl.TeacherEntityDAOImpl;
import com.hqy.util.WindowsHandler;

public class TchInfoModifyFrame extends JFrame {
    JFrame parentFrame;
    TeacherEntity queryTeacher = null;
    private static final int DEFAULT_WIDTH = 350;
    private static final int DEFAULT_HEIGHT = 350;

    public TchInfoModifyFrame(String title, TeacherEntity tcherEntity) {
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
        queryTeacher = tcherEntity;
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
        JLabel picLabel, picInfoLabel, nameLabel, IdLabel, collegeLabel,
                facultyLabel, emailLabel;
        JButton modifyPicButton, okButton, returnButton;
        JPanel infoPanel, buttonPanel, picPanel, stuPanel;
        JTextField nameTextField, IdTextField, emailTextField;
        JFileChooser chooser;
        JComboBox<String> collegeCombo, facultyCombo;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public TchInfoPanel() {

            picInfoLabel = new JLabel("个人照片"); // 构造器参数为标签显示文本
            picLabel = new JLabel();
            modifyPicButton = new JButton("修改个人照片");
            // 建立文件选择器对象。
            chooser = new JFileChooser();
            // 设计文件过滤器，接受以jpg、jpeg、gif结尾的文件
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "image files", "jpg", "jpeg", "gif");
            chooser.setFileFilter(filter);

            nameLabel = new JLabel("姓名"); // 构造器参数为标签显示文本
            IdLabel = new JLabel("教师编号");
            collegeLabel = new JLabel("所属学院");
            facultyLabel = new JLabel("所属教研室");
            emailLabel = new JLabel("电子邮箱");

            nameTextField = new JTextField("校长", 15);
            nameTextField.setEditable(false);
            IdTextField = new JTextField("130506", 15);
            IdTextField.setEditable(false);
            emailTextField = new JTextField("xiaozhang@cqupt.edu.cn", 15);
            collegeCombo = new JComboBox<String>();
            facultyCombo = new JComboBox<String>();
            collegeCombo.addItem("软件工程学院");
            collegeCombo.addItem("计算机工程学院");
            collegeCombo.addItem("通信学院");
            facultyCombo.addItem("软件实验中心");
            facultyCombo.addItem("数字媒体技术系");
            facultyCombo.addItem("软件工程系");

            if (queryTeacher != null) {
                nameTextField.setText(queryTeacher.getTeacher_name());
                IdTextField.setText(Integer.toString(queryTeacher.getTeacher_Id()));
                emailTextField.setText(queryTeacher.getTeacher_email());
                CollegeEntity[] college;
                CollegeEntityDAO collegeEntity = new CollegeEntityDAOImpl();
                if ((college = collegeEntity.queryCollegeEntityAll()) != null) {
                    collegeCombo.removeAllItems();
                    for (int i = 0; i < college.length; i++) {
                        collegeCombo.addItem(college[i].getCollege_name());
                    }
                }

                FacultyEntity[] faculty;
                FacultyEntityDAO facultyEntity = new FacultyEntityDAOImpl();
                if ((faculty = facultyEntity.queryFacultyEntityAll()) != null) {
                    facultyCombo.removeAllItems();
                    for (int i = 0; i < faculty.length; i++) {
                        facultyCombo.addItem(faculty[i].getFaculty_name());
                    }
                }
            }

            okButton = new JButton("确认修改"); // 构造器参数为按钮显示文本 = new JButton("修改个人照片");
            returnButton = new JButton("返回上级菜单"); // 构造器参数为按钮显示文本

            infoPanel = new JPanel();
            picPanel = new JPanel();
            buttonPanel = new JPanel();
            stuPanel = new JPanel();

            setLayout(new BorderLayout());

            buttonPanel.add(okButton);
            buttonPanel.add(returnButton);
            add(buttonPanel, BorderLayout.SOUTH);

            picPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            picPanel.add(picInfoLabel);
            picLabel.setSize(140, 110);
            ImageIcon image = new ImageIcon(this.getClass().getResource("/images/003.jpg"));
            image.setImage(image.getImage().getScaledInstance(
                    picLabel.getWidth(), picLabel.getHeight(),
                    Image.SCALE_DEFAULT));
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
            infoPanel.add(collegeLabel);
            infoPanel.add(collegeCombo);
            infoPanel.add(facultyLabel);
            infoPanel.add(facultyCombo);
            infoPanel.add(emailLabel);
            infoPanel.add(emailTextField);
            infoPanel.setBounds(0, 0, 160, 340);
            add(infoPanel);

            stuPanel.setLayout(null);
            stuPanel.add(infoPanel);
            stuPanel.add(picPanel);
            add(stuPanel, BorderLayout.CENTER);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();

            // 事件源注册事件监听器
            modifyPicButton.addActionListener(buttonAction);
            okButton.addActionListener(buttonAction);
            returnButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource().equals(returnButton)) {
                    TchInfoModifyFrame.this.dispose();
                    parentFrame.setVisible(true);
                } else if (e.getSource().equals(okButton)) {
                    TeacherEntity tchUpdate = new TeacherEntity();
                    TeacherEntityDAO tchEntity = new TeacherEntityDAOImpl();

                    tchUpdate.setTeacher_Id(Integer.parseInt(IdTextField
                            .getText().trim()));
                    tchUpdate.setTeacher_name(nameTextField.getText().trim());
                    tchUpdate.setTeacher_colleage(collegeCombo.getItemAt(collegeCombo.getSelectedIndex()));
                    tchUpdate.setTeacher_faculty(facultyCombo.getItemAt(facultyCombo.getSelectedIndex()));
                    tchUpdate.setTeacher_email(emailTextField.getText().trim());

                    if (tchEntity.updateTeacherEntity(tchUpdate) != 0) {
                        JOptionPane.showMessageDialog(null, "个人信息修改成功！",
                                "修改成功", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "无法更新数据库信息，请和管理员联系！", "警告",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource().equals(modifyPicButton)) {
                    // 设置当前目录
                    chooser.setCurrentDirectory(new File("."));
                    // 显示文件选择对话框
                    int result = chooser
                            .showOpenDialog(TchInfoModifyFrame.this);
                    // 如果文件选择，则更新图片信息
                    if (result == JFileChooser.APPROVE_OPTION) {
                        String fileName = chooser.getSelectedFile().getPath();
                        ImageIcon image = new ImageIcon(fileName);
                        image.setImage(image.getImage().getScaledInstance(
                                picLabel.getWidth(), picLabel.getHeight(),
                                Image.SCALE_DEFAULT));
                        picLabel.setIcon(image);
                        TchInfoModifyFrame.this.repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "该功能暂不支持！", "警告",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TchInfoModifyFrame("教师个人信息修改", null);
            }
        });
    }

}
