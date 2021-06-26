package com.merept.draw.store;

import com.merept.draw.utils.Theme;
import com.merept.draw.utils.Utility;

import java.io.File;
import java.util.ArrayList;

public class History {
    private static ArrayList<String> history = new ArrayList<>();

    public static void historyList() {
        System.out.println("\n" + Theme.getTheLine() + "\n");
        var i = 1;
        File file = new File(OutputPathChange.PATH);
        File[] files = file.listFiles();

        if (files == null || files.length == 0) {
            System.out.printf("""
                    当前没有历史记录!
                                        
                    %s
                    回车继续...
                    """, Theme.getTheLine());
            Utility.readEnter();
            return;
        }

        for (File f : files) {
            System.out.println(i + "." + f.getName());
            history.add(f.getName());
            i++;
        }

        System.out.println("0.清空记录\n\n" + Theme.getTheLine() + "\n");
        System.out.print("输入要查看的历史( -1 退出) >\040");
        readHistory();
        System.out.println("回车继续...");
        Utility.readEnter();
    }

    private static void readHistory() throws IndexOutOfBoundsException {
        var slt = Utility.readMenuSelection(history.size());
        if (slt == 0) {
            clearHistory();
            return;
        } else if (slt == -1) return;
        var path = OutputPathChange.PATH + history.get(slt - 1);
        Files.printResults(path);
    }

    @SuppressWarnings (value = "all")
    public static void clearHistory() {
        if (OutputPathChange.PATH.equals("请先设置输出目录!")) return;
        File file = new File(OutputPathChange.PATH);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.getName().equals("history.txt")) continue;
            f.delete();
        }
    }
}
