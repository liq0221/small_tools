package com.finance;

import com.finance.jframe.transfer.TransferTool;

import javax.swing.*;


class MainFrame extends JFrame {
    public MainFrame() {
        this.setSize(600, 600);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new TransferTool();
        //    System.out.println( Week.Monday.getStr());
    }
}

