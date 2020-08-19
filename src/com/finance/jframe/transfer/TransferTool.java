package com.finance.jframe.transfer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TransferTool extends JFrame implements ActionListener {
    private JLabel userLa;
    private JLabel pwdLa;
    private JLabel codeLa;

    private JTextField userTxt;
    private JTextField codeTxt;

    private JButton sourceBt;
    private JButton destBt;
    private JButton transBt;
    private JButton quitBt;
    private static JFrame frmIpa;

    //构造方法
    public TransferTool() {
        Init();
    }

    public void Init() {
        frmIpa = new JFrame();
        frmIpa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        //机构编码
        codeLa = new JLabel();
        codeLa.setText("机构编码:");
        codeLa.setSize(110, 50);
        codeLa.setLocation(40, 50);

        //机构编码
        codeTxt = new JTextField();
        codeTxt.setSize(370, 20);
        codeTxt.setLocation(105, 65);
        codeTxt.setEditable(true);

        //转换的文件路径
        userLa = new JLabel();
        userLa.setText("转换的文件路径:");
        userLa.setSize(110, 50);
        userLa.setLocation(3, 100);


        //转换的文件路径输入框
        userTxt = new JTextField();
        userTxt.setSize(370, 20);
        //this.setSize(width, height)
        userTxt.setLocation(105, 115);
        userTxt.setEditable(false);

        //请选择初始路径按钮
        sourceBt = new JButton("请选择文件路径");
        sourceBt.setSize(130, 20);
        sourceBt.setLocation(105, 150);


       /*   //请选择目标路径按钮
        destBt = new JButton("请选择目标路径");
        destBt.setSize(130, 20);
        destBt.setLocation(105, 200);*/

        //转换按钮
        transBt = new JButton("转换");
        transBt.setSize(80, 20);
        transBt.setLocation(140, 190);

        //退出按钮
        quitBt = new JButton("退出");
        quitBt.setSize(80, 20);
        quitBt.setLocation(240, 190);


        this.setLayout(null);
        this.setSize(500, 500);
        this.add(userLa);
        this.add(codeLa);
        this.add(userTxt);
        this.add(codeTxt);
        this.add(sourceBt);
        //this.add(destBt);
        this.add(transBt);
        this.add(quitBt);
        this.setTitle("文件名转换工具");
        this.setLocation(width / 2 - 200, height / 2 - 230);
        sourceBt.addActionListener(this);
        // destBt.addActionListener(this);
        transBt.addActionListener(this);
        quitBt.addActionListener(this);
        this.setVisible(true);
    }

    //具体事件的处理
    public void actionPerformed(ActionEvent e) {
        //获取产生事件的事件源强制转换
        JButton bt = (JButton) e.getSource();
        JFileChooser jfc = null;
        //获取按钮上显示的文本
        String str = bt.getText();
        if (str.equals("请选择文件路径")) {
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
            userTxt.setText(file.getAbsolutePath());
        } else if (str.equals("请选择目标路径")) {
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
           // pwdTxt.setText(file.getAbsolutePath());
        } else if (str.equals("转换")) {
            bt.setEnabled(false);
            if (!CheckIsNull()) {
                try {
                    Map<String, Object> resultMap = transferFile(codeTxt.getText(),userTxt.getText());
                    if ((boolean) resultMap.get("flag")) {
                        JPanel panel3 = new JPanel();
                        JOptionPane.showMessageDialog(panel3, "转换完成...", "提示", JOptionPane.OK_CANCEL_OPTION);
                        bt.setEnabled(true);
                        Object fileCount = resultMap.get("fileCount");
                        Object wavFileCount = resultMap.get("wavFileCount");
                        Object ogaFileCount = resultMap.get("ogaFileCount");
                        //保存的文件路径输入框
                        String text = "<html><body> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                                "文件总数：" + fileCount + "<br>" +
                                "重命名.wav文件数量：" + wavFileCount + "<br>" +
                                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;删除.oga文件数量：" + ogaFileCount + "</body></html>";
                        pwdLa = new JLabel();
                        pwdLa.setSize(450, 70);
                        pwdLa.setLocation(3, 250);
                        add(this.pwdLa);
                        pwdLa.setText(text);

                    } else {
                        JPanel panel3 = new JPanel();
                        JOptionPane.showMessageDialog(panel3, "转换失败...", "提示", 0);
                        bt.setEnabled(true);
                    }
                } catch (Exception ee) {
                    JPanel panel3 = new JPanel();
                    JOptionPane.showMessageDialog(panel3, "转换失败,请检查文件名称格式或文件路径是否正确。", "提示", 0);
                    bt.setEnabled(true);
                    ee.printStackTrace();
                }
            } else {
                JPanel panel3 = new JPanel();
                JOptionPane.showMessageDialog(panel3, "目标路径没有选择或机构编码没有填写", "提示", 0);
                bt.setEnabled(true);
            }
        } else {
            //调用系统类中的一个正常退出
            System.exit(0);
        }
    }

    private boolean CheckIsNull() {
        boolean flag = false;
        if (userTxt.getText() == null || userTxt.getText().trim().equals("")) {
            flag = true;
        }
        if (codeTxt.getText() == null || codeTxt.getText().trim().equals("")) {
            flag = true;
        }
        return flag;
    }

	//文件名转换的方法，如果需要修改转化规则的话在这里修改
    private Map<String, Object> transferFile(String codeTxt,String source) {
        File sourceFile = new File(source);
        String noExtFileName = null;
        String destFilePath = null;
        int fileCount = 0;
        int ogaFileCount = 0;
        Map<String, Object> resultMap = new HashMap<>();
        boolean flag = false;
        if (sourceFile.isDirectory()) {
            File[] files = sourceFile.listFiles();
            fileCount = files.length;
            for (File file : files) {
                String sourceFileName = file.getName();
                int extIndex = sourceFileName.lastIndexOf(".");
                String extName = sourceFileName.substring(extIndex);
                if (".oga".equals(extName)) {
                    ogaFileCount++;
                    file.delete();
                    continue;
                }
                int noExtNameIndex = sourceFileName.indexOf(extName);
                //Matcher matcher = Pattern.compile("-").matcher(sourceFileName);
                int start = 0;
                //if (matcher.find())   start = matcher.start();
                noExtFileName = sourceFileName.substring(start , noExtNameIndex);
                String[] split = noExtFileName.split("-");
                String[] timeSplit = split[4].split("_");
                String second = null;
                //没有秒数，补为00
                if (timeSplit.length < 4)  {
                    second= "00";
                } else {
                    second = timeSplit[3];
                }
                //转换的格式为 机构编码 +"_" + 外呼时间戳 + "_" +手机号.wav
                String destFileName = codeTxt + "_" + split[2] + split[3] + timeSplit[0] + timeSplit[1] + timeSplit[2] + second + "_" + split[0] + extName;
                destFilePath = source + "/" + destFileName;
                flag = file.renameTo(new File(destFilePath));
            }
        }
        int wavFileCount = fileCount - ogaFileCount;
        resultMap.put("flag", flag);
        resultMap.put("ogaFileCount", ogaFileCount);
        resultMap.put("fileCount", fileCount);
        resultMap.put("wavFileCount", wavFileCount);
        return resultMap;
    }

    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
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
