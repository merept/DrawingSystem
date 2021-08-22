package com.merept.draw.main;

import com.merept.draw.store.*;
import com.merept.draw.utils.Theme;
import com.merept.draw.utils.Utility;

public class Menu {
    public static String inputPath = "请先设置输入路径!";

    public static void enter() {
        var mode = "";
        for (; ; ) {
            if (Mode.modeCode.equals("1")) mode = "团队模式";
            else if (Mode.modeCode.equals("2")) mode = "单次模式";
            reading();
            System.out.printf("""
                    %s

                    随机抽签菜单
                    当前抽签模式: %s
                    当前结果输出路径: %s
                    当前文件输入路径: %s

                    1.读取文件
                    2.开始抽取
                    3.历史记录
                    4.更改输出目录
                    5.更改模式
                    0.退出程序

                    %s
                    请输入选项 >\040""", Theme.getTheLine(), mode, OutputPathChange.PATH, inputPath, Theme.getTheLine());
            var slt = selecting();
            if (slt == 0) break;
        }
    }

    private static void reading() {
        setInputPath();
        ReduceRate.readLatest();
        Files.beginInput(inputPath);
        OutputPathChange.readLog();
    }

    private static void setInputPath() {
        if (!inputPath.equals(ReduceRate.DATA) && !inputPath.equals("请先设置输入路径!")) return;
        if (Mode.modeCode.equals("1")) inputPath = ReduceRate.DATA;
        else if (Mode.modeCode.equals("2")) inputPath = "请先设置输入路径!";
    }

    private static int selecting() {
        var slt = Utility.readMenuSelection(5);
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
            case 5 -> Mode.changMode();
            case 0 -> slt = exit();
        }
        return slt;
    }

    private static int exit() {
        System.out.printf("""
                                
                %s
                                
                              感谢使用!
                      Copyright 2021 C MerePT
                                
                %s
                是否退出?
                """, Theme.getTheLine(), Theme.getTheLine());
        var slt = Utility.readConfirmSelection();
        if (slt == 'N') return -1;
        System.out.println("回车继续...");
        Utility.readEnter();
        return 0;
    }
}
