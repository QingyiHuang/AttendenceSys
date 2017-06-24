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

import com.hqy.bean.StudentEntity;
import com.hqy.dao.StudentEntityDAO;
import com.hqy.dao.impl.StudentEntityDAOImpl;
import com.hqy.util.Communal;
import com.hqy.util.WindowsHandler;

public class TchQueryStuInfoFrame extends JFrame {
    JFrame parentFrame;
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 380;

    public TchQueryStuInfoFrame(String title) {
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
        QueryStuInfoPanel dbInfoPanel = new QueryStuInfoPanel();
        getContentPane().add(dbInfoPanel);

        // 注册窗口监听器
        addWindowListener(new WindowsHandler());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private class QueryStuInfoPanel extends JPanel {
        // 定义容器Panel中的控件元素。
        JLabel queryMethodLabel, queryKeyWordsLabel;
        JComboBox<String> queryCombo;
        JTextField queryKeyWordsTextField;
        JButton returnButton, queryButton, reQueryButton;
        JPanel queryPanel, queryMethodPanel, queryKeyPanel, queryResultPanel, buttonPanel;
        JScrollPane scrollPanel;
        JTable dbInfoTable;
        DefaultTableModel tableModel;
        TableColumnModel columnModel;

        // 登陆容器panel构造，将所有控件元素装入容器。
        public QueryStuInfoPanel() {

            String[] columnName = { "姓名", "学号", "所属学院","专业" };
            Object data[][] = { { "鹿岛", "13111505", "计算机学院","软件工程" },
                    { "李四", "13111505", "软件学院" ,"英语+软件"},
                    { "王武", "13111505", "通信学院","通信设计" } };
            buttonPanel = new JPanel();
            queryResultPanel = new JPanel();
            queryMethodPanel = new JPanel();
            queryKeyPanel = new JPanel();
            queryPanel = new JPanel();

            setLayout(new BorderLayout());

            queryMethodLabel = new JLabel("请选择查询方式      ");
            queryKeyWordsLabel = new JLabel("请输入查询关键字 ");
            queryCombo = new JComboBox<String>();
            queryCombo.addItem("按姓名查询");
            queryCombo.addItem("按教学班查询");
            queryCombo.addItem("按专业查询");
            queryCombo.addItem("按学号查询");
            queryCombo.setPreferredSize(new Dimension(140,25));
            queryKeyWordsTextField = new JTextField(20);
            queryButton = new JButton("查询");


            //queryMethodPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            queryMethodPanel.add(queryMethodLabel);
            queryMethodPanel.add(queryCombo);

            //queryKeyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            queryKeyPanel.add(queryKeyWordsLabel);
            queryKeyPanel.add(queryKeyWordsTextField);
            queryKeyPanel.add(queryButton);

            //create a TableModel
            tableModel = new DefaultTableModel(data,columnName);
            dbInfoTable = new JTable(tableModel);
            columnModel = dbInfoTable.getColumnModel();
            dbInfoTable.setRowHeight(20);
            dbInfoTable.setEnabled(false);
            //dbInfoTable.setAutoCreateColumnsFromModel(true);
            scrollPanel = new JScrollPane(dbInfoTable,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 180));
            //queryBoder = BorderFactory.createEtchedBorder();
            queryResultPanel.add(scrollPanel);

            queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            queryPanel.add(queryMethodPanel);
            queryPanel.add(queryKeyPanel);
            queryPanel.add(queryResultPanel);
            add(queryPanel, BorderLayout.CENTER);


            returnButton = new JButton("返回上级菜单");
            reQueryButton = new JButton("重新开始查询");
            buttonPanel.add(reQueryButton);
            buttonPanel.add(returnButton);
            add(buttonPanel, BorderLayout.SOUTH);

            // 生成事件监听器对象
            ButtonAction buttonAction = new ButtonAction();
            // 事件源注册事件监听器
            reQueryButton.addActionListener(buttonAction);
            returnButton.addActionListener(buttonAction);
            queryButton.addActionListener(buttonAction);
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
        }


        // 定义事件监听器类，实现事件监听器接口中的actionPerformed方法，引入一个用户具体的操作。
        private class ButtonAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(e.getSource().equals(returnButton))
                {
                    TchQueryStuInfoFrame.this.dispose();
                    parentFrame.setVisible(true);
                }
                else if(e.getSource().equals(reQueryButton))
                {
                    queryCombo.setSelectedItem(queryCombo.getItemAt(0));
                    queryKeyWordsTextField.setText("");
                }
                else
                {
                    if (queryKeyWordsTextField.getText().trim().equals("")) {
                        JOptionPane.showMessageDialog(null,
                                "查询关键字不能够为空！", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        String queryMethod = queryCombo.getItemAt(queryCombo.getSelectedIndex());
                        StudentEntity student;
                        StudentEntityDAO stuEntity = new StudentEntityDAOImpl();
                        StudentEntity[] queryResult;

                        if(queryMethod.equals("按学号查询")){
                            if(Communal.isInteger(queryKeyWordsTextField.getText().trim()) == false)
                            {
                                JOptionPane.showMessageDialog(null, "无效学号ID，只能为数字形式！",
                                        "警告", JOptionPane.ERROR_MESSAGE);
                                queryKeyWordsTextField.setText("");
                            }else if ((student = stuEntity.queryStudentEntityById(Integer.parseInt(queryKeyWordsTextField.getText().trim()))) != null) {
                                String[] column = {"姓名", "所属学院", "所属专业", "电子邮箱" };
                                Object[][] rowData = new Object[1][4];
                                rowData[0][0] = new String(student.getStudent_name());
                                rowData[0][1] = new String(student.getStudent_colleage());
                                rowData[0][2] = new String(student.getStudent_major());
                                rowData[0][3] = new String(student.getStudent_email());
                                clearRequeryResultAndRepaint(column, rowData);
                            } else{
                                JOptionPane.showMessageDialog(null,
                                        "数据库中没有您要查询的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                            }
                        }else if(queryMethod.equals("按姓名查询")){
                            if ((queryResult = stuEntity.queryStudentEntityByName(queryKeyWordsTextField.getText().trim())) != null
                                    && queryResult.length > 0) {
                                String[] column = {"学号", "所属学院", "所属专业", "电子邮箱" };
                                StudentEntity studentTemp = new StudentEntity();
                                Object[][] rowData = new Object[queryResult.length][4];
                                for(int i = 0; i < queryResult.length; i++){
                                    studentTemp = queryResult[i];
                                    rowData[i][0] = new Integer(studentTemp.getStudent_Id());
                                    rowData[i][1] = new String(studentTemp.getStudent_colleage());
                                    rowData[i][2] = new String(studentTemp.getStudent_major());
                                    rowData[i][3] = new String(studentTemp.getStudent_email());
                                }
                                clearRequeryResultAndRepaint(column, rowData);
                            } else{
                                JOptionPane.showMessageDialog(null,
                                        "数据库中没有您要查询的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                            }
                        }else if(queryMethod.equals("按专业查询")){
                            if ((queryResult = stuEntity.queryStudentEntityByMajor(queryKeyWordsTextField.getText().trim())) != null
                                    && queryResult.length > 0) {
                                String[] column = {"学号", "姓名", "所属学院", "电子邮箱" };
                                StudentEntity studentTemp = new StudentEntity();
                                Object[][] rowData = new Object[queryResult.length][4];
                                for(int i = 0; i < queryResult.length; i++){
                                    studentTemp = queryResult[i];
                                    rowData[i][0] = new Integer(studentTemp.getStudent_Id()) ;
                                    rowData[i][1] = new String(studentTemp.getStudent_name());
                                    rowData[i][2] = new String(studentTemp.getStudent_colleage());
                                    rowData[i][3] = new String(studentTemp.getStudent_email());
                                }
                                clearRequeryResultAndRepaint(column, rowData);
                            } else{
                                JOptionPane.showMessageDialog(null,
                                        "数据库中没有您要查询的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
                            }

                        }else{
                            if(Communal.isInteger(queryKeyWordsTextField.getText().trim()) == false)
                            {
                                JOptionPane.showMessageDialog(null, "无效教学班ID，只能为数字形式！",
                                        "警告", JOptionPane.ERROR_MESSAGE);
                                queryKeyWordsTextField.setText("");
                            }
                            else if ((queryResult = stuEntity.queryStudentEntityByClass(Integer.parseInt(queryKeyWordsTextField.getText().trim()))) != null
                                    && queryResult.length > 0) {
                                String[] column = {"学号", "姓名", "所属学院", "所属专业", "电子邮箱" };
                                StudentEntity studentTemp = new StudentEntity();
                                Object[][] rowData = new Object[queryResult.length][5];
                                for(int i = 0; i < queryResult.length; i++){
                                    studentTemp = queryResult[i];
                                    rowData[i][0] = new Integer(studentTemp.getStudent_Id()) ;
                                    rowData[i][1] = new String(studentTemp.getStudent_name());
                                    rowData[i][2] = new String(studentTemp.getStudent_colleage());
                                    rowData[i][3] = new String(studentTemp.getStudent_major());
                                    rowData[i][4] = new String(studentTemp.getStudent_email());
                                }
                                clearRequeryResultAndRepaint(column, rowData);
                            } else{
                                JOptionPane.showMessageDialog(null,
                                        "数据库中没有您要查询的信息，请和管理员联系！", "警告", JOptionPane.ERROR_MESSAGE);
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
                new TchQueryStuInfoFrame("查看学生信息");
            }
        });
    }
}
