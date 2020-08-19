package com.finance.jframe.search;

import com.finance.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;
import java.util.List;

public class SearchTool extends JFrame implements ActionListener {
    private JLabel excelLa;
    private JLabel searchLa;
    private JLabel recordLa;
    private JLabel resultLa;

    private JTextField excelTxt;
    private JTextField recordTxt;
    private JTextField searchTxt;

    private JButton recordBt;
    private JButton excelBt;
    private JButton searchBt;
    private JButton transBt;
    private JButton quitBt;
    private static JFrame frmIpa;
    private static Integer successCount = 0;
    private static Integer failCount = 0;
    /**
     * 构造方法
     */
    public SearchTool() {
        Init();
    }

    public void Init() {
        frmIpa = new JFrame();
        frmIpa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        //录音文件路径
        recordLa = new JLabel();
        recordLa.setText("录音文件:");
        recordLa.setSize(110, 50);
        recordLa.setLocation(3, 70);

        //录音文件路径
        recordTxt = new JTextField();
        recordTxt.setSize(370, 20);
        recordTxt.setLocation(80, 85);
        recordTxt.setEditable(false);

        //请选择录音文件路径按钮
        recordBt = new JButton("请选择录音文件夹");
        recordBt.setSize(200, 20);
        recordBt.setLocation(80, 110);


        //excel文件路径
        excelLa = new JLabel();
        excelLa.setText("excel文件:");
        excelLa.setSize(110, 50);
        excelLa.setLocation(3, 130);


        //excel文件路径输入框
        excelTxt = new JTextField();
        excelTxt.setSize(370, 20);
        excelTxt.setLocation(80, 145);
        excelTxt.setEditable(false);

        //请选择excel文件路径按钮
        excelBt = new JButton("请选择excel文件(xls格式)");
        excelBt.setSize(200, 20);
        excelBt.setLocation(80, 170);

        //搜索存入的文件路径
        searchLa = new JLabel();
        searchLa.setText("保存到:");
        searchLa.setSize(110, 50);
        searchLa.setLocation(3, 190);


        //搜索存入的文件路径输入框
        searchTxt = new JTextField();
        searchTxt.setSize(370, 20);
        searchTxt.setLocation(80, 205);
        searchTxt.setEditable(false);

        //搜索存入的文件路径按钮
        searchBt = new JButton("请选择保存的位置");
        searchBt.setSize(200, 20);
        searchBt.setLocation(80, 230);


        //转换按钮
        transBt = new JButton("开始搜索");
        transBt.setSize(110, 20);
        transBt.setLocation(140, 350);

        //退出按钮
        quitBt = new JButton("退出");
        quitBt.setSize(80, 20);
        quitBt.setLocation(330, 350);


        this.setLayout(null);
        this.setSize(500, 500);
        this.add(excelLa);
        this.add(searchLa);
        this.add(recordLa);
        this.add(excelTxt);
        this.add(recordTxt);
        this.add(searchTxt);
        this.add(recordBt);
        this.add(searchBt);
        this.add(excelBt);
        this.add(transBt);
        this.add(quitBt);
        this.setTitle("录音文件搜索工具");
        this.setLocation(width / 2 - 200, height / 2 - 230);
        recordBt.addActionListener(this);
        excelBt.addActionListener(this);
        searchBt.addActionListener(this);
        transBt.addActionListener(this);
        quitBt.addActionListener(this);
        this.setVisible(true);
    }

    /**
     * 具体事件的处理
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取产生事件的事件源强制转换
        JButton bt = (JButton) e.getSource();
        JFileChooser jfc = null;
        //获取按钮上显示的文本
        String str = bt.getText();
        if (str.equals("请选择录音文件夹")) {
            jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // 显示打开的文件对话框
            jfc.showSaveDialog(frmIpa);
            File file = null;
            try {
                // 使用文件类获取选择器选择的文件
                file = jfc.getSelectedFile();
            } catch (Exception e2) {
                JPanel panel3 = new JPanel();
                JOptionPane.showMessageDialog(panel3, "没有选中任何文件", "提示", JOptionPane.WARNING_MESSAGE);
                bt.setEnabled(true);
            }
            recordTxt.setText(file.getAbsolutePath());
        } else if (str.equals("请选择excel文件(xls格式)")) {
            jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // 显示打开的文件对话框
            jfc.showSaveDialog(frmIpa);
            File file = null;
            try {
                // 使用文件类获取选择器选择的文件
                file = jfc.getSelectedFile();
            } catch (Exception e2) {
                JPanel panel3 = new JPanel();
                JOptionPane.showMessageDialog(panel3, "没有选中任何文件", "提示", JOptionPane.WARNING_MESSAGE);

            }
            excelTxt.setText(file.getAbsolutePath());
        } else if (str.equals("请选择保存的位置")) {
            jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            // 显示打开的文件对话框
            jfc.showSaveDialog(frmIpa);
            File file = null;
            try {
                // 使用文件类获取选择器选择的文件
                file = jfc.getSelectedFile();
            } catch (Exception e2) {
                JPanel panel3 = new JPanel();
                JOptionPane.showMessageDialog(panel3, "没有选中任何文件", "提示", JOptionPane.WARNING_MESSAGE);
            }
            searchTxt.setText(file.getAbsolutePath());
        } else if (str.equals("开始搜索")) {
            bt.setEnabled(false);
            if (!checkIsNull()) {
                try {
                    File file = new File(excelTxt.getText());
                    if (!"xls".equals(file.getName().substring(file.getName().indexOf(".") + 1)) ){
                        JPanel panel3 = new JPanel();
                        JOptionPane.showMessageDialog(panel3, "选择的excel格式为xls", "提示", 0);
                        bt.setEnabled(true);
                        return;
                    }
                    ExcelSheetData sheetData = ExcelUtil.readMultiSheetExcel(excelTxt.getText()).getSheetData().get(0);
                    List<ExcelLineData> lineData = sheetData.getLineData();
                    if (!"电话号码".equals(lineData.get(0).getColData().get(0)) || !"催记时间".equals(lineData.get(0).getColData().get(1))) {
                        JPanel panel3 = new JPanel();
                        JOptionPane.showMessageDialog(panel3, "excel文件的表头必须为[电话号码]和[催记时间]", "提示", 0);
                        bt.setEnabled(true);
                        return;
                    }
                    searchFile(recordTxt.getText(),lineData,searchTxt.getText());
                    JPanel panel3 = new JPanel();
                    JOptionPane.showMessageDialog(panel3, "搜索完成...", "提示", JOptionPane.OK_CANCEL_OPTION);
                    bt.setEnabled(true);
                    int total = lineData.size();
                    //保存的文件路径输入框
                    String text = "<html><body> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "excel总行数：" + (total - 1) + "<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;搜索成功条数：" + successCount + "<br>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未搜索到条数：" + (total - 1 - successCount) + "</body></html>";
                    resultLa = new JLabel();
                    resultLa.setSize(450, 70);
                    resultLa.setLocation(3, 250);
                    add(this.resultLa);
                    resultLa.setText(text);
                } catch (Exception ee) {
                    JPanel panel3 = new JPanel();
                    JOptionPane.showMessageDialog(panel3, "搜索失败,请检查电话号码或催记时间格式是否正确。", "提示", 0);
                    bt.setEnabled(true);
                    ee.printStackTrace();
                }
            } else {
                JPanel panel3 = new JPanel();
                JOptionPane.showMessageDialog(panel3, "必须选择路径", "提示", 0);
                bt.setEnabled(true);
            }
        } else {
            //调用系统类中的一个正常退出
            System.exit(0);
        }
    }

    /**
     * 检查文件路径是否为空
     */
    private boolean checkIsNull() {
        boolean flag = false;
        if (recordTxt.getText() == null || recordTxt.getText().trim().equals("")) {
            flag = true;
        }
        if (excelTxt.getText() == null || excelTxt.getText().trim().equals("")) {
            flag = true;
        }
        if (searchTxt.getText() == null || searchTxt.getText().trim().equals("")) {
            flag = true;
        }
        return flag;
    }

    /**
     *  搜索文件的规则
     */
    private void searchFile(String recordTxt,List<ExcelLineData> lineData,String searchTxt) {
        File file = new File(recordTxt);
        for (File childFile : file.listFiles()) {
            if (childFile.isDirectory()) {
                searchFile(childFile.getAbsolutePath(),lineData,searchTxt);
            }
            try {
                successCount += matching(lineData, searchTxt, childFile);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }

    }

    public int matching(List<ExcelLineData> lineData ,String searchTxt,File file) throws IOException {

        String phoneNum = null;
        String collectionDateStr = null;

        Date collectionDate = null,fileDate = null;
        int success = 0;
        if (!file.isDirectory()) {
            String fileName = file.getName();
            String filePhoneNum = fileName.substring(fileName.lastIndexOf("_") + 1,fileName.indexOf("."));
            String fileDateStr = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("_"));

            for (int i = 1; i < lineData.size() ; i++) {
                phoneNum = lineData.get(i).getColData().get(0);
                collectionDateStr =  lineData.get(i).getColData().get(1);
                Date date = new Date(Long.parseLong(fileDateStr));
                collectionDate = DateUtil.convert2Date(collectionDateStr,"yyyy-MM-dd");
                fileDate = DateUtil.convert2Date(DateUtil.convert2String(date,"yyyy-MM-dd"),"yyyy-MM-dd");
                if (phoneNum.equals(filePhoneNum) && DateUtil.diffDay(fileDate,collectionDate) == 0) {
                    copyFile(file,searchTxt);
                    success = 1;
                }
            }
        }
        return success;


    }

    public static void copyFile(File sourceFile, String targetFilePath)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(new File(targetFilePath + "/" + sourceFile.getName()));
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

}


