package com.merept.draw;

import com.merept.draw.store.Drawing;
import com.merept.draw.store.Files;
import com.merept.draw.store.History;
import com.merept.draw.store.OutputPathChange;
import com.merept.draw.utils.Theme;
import com.merept.draw.utils.Utility;

public class Main {
    public static String inputPath = "请先设置输入路径!";

    public static void main(String[] args) {
        OutputPathChange.readLog();
        for (; ; ) {
            System.out.printf("""
                %s
                
                随机抽签菜单
                当前结果输出路径: %s
                当前文件输入路径: %s
                
                1.读取文件
                2.开始抽取
                3.历史记录
                4.更改输出目录
                0.退出程序
                
                %s
                请输入选项 >\040""", Theme.getTheLine(), OutputPathChange.PATH, inputPath, Theme.getTheLine());
            var slt = selecting();
            if (slt == 0) break;
        }
    }

    private static int selecting() {
        var slt = Utility.readMenuSelection(4);
        if (OutputPathChange.PATH.equals("请先设置输出目录!")) {
            if (slt != 4 && slt != 0) {
                System.out.println("\n请先设置输出目录!\n回车继续...");
                Utility.readEnter();
                return -1;
            }
        }
        switch (slt) {
            case 1 -> inputPath = Files.setPath();
            case 2 -> Drawing.enter();
            case 3 -> History.historyList();
            case 4 -> OutputPathChange.enter();
            case 0 -> exit();
        }
        return slt;
    }

    private static void exit() {
        System.out.printf("""
                
                %s
                
                              感谢使用!
                      Copyright 2021 C MerePT
                
                %s
                回车继续...""", Theme.getTheLine(), Theme.getTheLine());
        Utility.readEnter();
    }
}
