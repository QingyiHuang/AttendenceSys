package com.hqy.views;

/**
 * Created by Administrator on 2017/6/21.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.hqy.bean.AttendanceEntity;
import com.hqy.dao.AttendanceEntityDAO;
import com.hqy.dao.impl.AttendanceEntityDAOImpl;
import com.hqy.util.WindowsHandler;

public class TchQryMdyAttInfoFrame extends JFrame {
    JFrame parentFrame;
    private AttendanceEntity[] attReuryResult;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 380;
    DefaultTableModel tableModel;
    TableColumnModel columnModel;

    public TchQryMdyAttInfoFrame(String title, AttendanceEntity[] attRequryRes) {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle(title);

        Toolkit kit = Toolkit.getDefaultToolkit();
        // 显示位置在屏幕长度和宽度的1/3处。
        Dimension screenSize = kit.getScreenSize();
        int screenWidthpx = screenSize.width;
        int screenHeightpx = screenSize.height;
        setLocation(screenWidthpx / 3, screenHeightpx / 3);
        setLocationByPlatform(false);

        // 设置窗口最小化时显示的图标
        Image img = new ImageIcon(this.getClass().getResource("/images/005.jpg")).getImage();
        setIconImage(img);

        // 装入panel，并显示在中间（默认布局为边框布局borderLayout）
        attReuryResult = attRequryRes;
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
        JButton modifyBotton, returnButton;
        JPanel queryResultPanel, buttonPanel;
        JScrollPane scrollPanel;
        JTable dbInfoTable;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public DBInfoPanel() {
            String[] columnName = { "教学班号","学生ID", "学生姓名", "课程名称", "考勤时间", "考勤记录" };
            Object data[][] = { { "13111505","2015214038", "张磊", "express框架", "2017-6-21", "正常" },
                    { "13111505","2016214038", "夏至", "AngularJS", "2017-6-21", "迟到" },
                    { "13111505","2017214038", "易欢", "AngularJS2", "2017-6-21", "旷课" } };

            if(attReuryResult != null && attReuryResult.length > 0){
                String[] columnName1 = { "教学班号", "课程名称", "学生ID", "姓名", "考勤时间", "考勤记录" };
                AttendanceEntity attTemp = new AttendanceEntity();
                Object[][] data1 = new Object[attReuryResult.length][6];
                for(int i = 0; i < attReuryResult.length; i++){
                    attTemp = (AttendanceEntity)attReuryResult[i];
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

            //create a TableModel
            tableModel = new DefaultTableModel(data,columnName);
            dbInfoTable = new JTable(tableModel){
                public boolean isCellEditable(int row, int column) {
                    if(column == 5)
                    {
                        return true;
                    }
                    return false;
                }
            };
            dbInfoTable.setRowHeight(20);
            columnModel = dbInfoTable.getColumnModel();
            dbInfoTable.setAutoCreateColumnsFromModel(true);
            dbInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            dbInfoTable.setEnabled(false);
            scrollPanel = new JScrollPane(dbInfoTable,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 90));
            // queryBoder = BorderFactory.createEtchedBorder();

            buttonPanel = new JPanel();
            queryResultPanel = new JPanel();

            setLayout(new BorderLayout(10, 10));
            queryResultPanel.add(scrollPanel);
            add(queryResultPanel, BorderLayout.CENTER);

            modifyBotton = new JButton("修改考勤记录");
            modifyBotton.setActionCommand("修改考勤记录");
            returnButton = new JButton("返回上级菜单");
            buttonPanel.add(modifyBotton);
            buttonPanel.add(returnButton);
            add(buttonPanel, BorderLayout.SOUTH);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();
            // 事件源注册事件监听器
            modifyBotton.addActionListener(buttonAction);
            returnButton.addActionListener(buttonAction);
        }

        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource().equals(returnButton)) {
                    TchQryMdyAttInfoFrame.this.dispose();
                    parentFrame.setVisible(true);
                } else {
                    if (e.getActionCommand().equals("修改考勤记录")) {
                        setEditbalColumn();
                        modifyBotton.setText("提交修改记录");
                        modifyBotton.setActionCommand("提交修改记录");
                        pack();
                    } else {
                        int updateTimes = 0;
                        Boolean updateFailedFlag = false;
                        for(int i = 0; i < attReuryResult.length; i++)
                        {
                            if(!attReuryResult[i].getAttendance_status().equals(dbInfoTable.getValueAt(i, 5).toString()))
                            {
                                String tempAttStatus = attReuryResult[i].getAttendance_status();
                                attReuryResult[i].setAttendance_status(dbInfoTable.getValueAt(i, 5).toString());
                                AttendanceEntityDAO attEntity = new AttendanceEntityDAOImpl();
                                int returnVal = attEntity.updateAttendanceEntity(attReuryResult[i]);
                                if(returnVal != 1){
                                    JOptionPane.showMessageDialog(null,
                                            "无法更新考勤记录信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                                    attReuryResult[i].setAttendance_status(tempAttStatus);
                                    updateFailedFlag = true;
                                    break;
                                }
                                updateTimes++;
                            }
                        }

                        if(updateFailedFlag == false)
                        {
                            if(updateTimes > 0){
                                JOptionPane.showMessageDialog(null,
                                        "考勤记录修改成功！", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null,
                                        "您未实际更新学生考勤状态，本次操作未执行！", "注意", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
            }
        }


        private void setEditbalColumn() {
            //columnModel.removeColumn(columnModel.getColumn(0));
            JComboBox<String> attStatusCombo = new JComboBox<String>();
            attStatusCombo.addItem("正常");
            attStatusCombo.addItem("迟到");
            attStatusCombo.addItem("旷课");
            attStatusCombo.addItem("请假");
            columnModel.getColumn(5).setCellEditor(new DefaultCellEditor(attStatusCombo));
            dbInfoTable.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 所有swing组件必须由事件分派线程（event dispatch thread）进行配置，线程将鼠标点击和按键控制转移到用户接口组件。
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TchQryMdyAttInfoFrame("查看学生考勤信息", null);
            }
        });
    }
}
