package com.finance.jframe.search;



import javax.swing.*;
import java.io.File;
import java.io.IOException;

class SearchMainFrame extends JFrame {
    public SearchMainFrame() {
        this.setSize(600, 600);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        new SearchTool();
      //searchFile("F:\\test\\2020081714");

    }

    public static void searchFile(String name) {
        File file = new File(name);
        for (File childFile : file.listFiles()) {
            if (childFile.isDirectory()) {
                searchFile(childFile.getAbsolutePath());
            }
            System.out.println(childFile.getName());
        }
    }
}
