package com.merept.draw.store;

import com.merept.draw.utils.Theme;
import com.merept.draw.utils.Utility;

import java.io.*;
import java.util.ArrayList;

/**
 * <p>所属包名: com.merept.draw.store</p>
 * <p>项目名称: DrawingSystem</p>
 * <p>文件名称: History</p>
 * <p>创建时间: 2021/6/24</p>
 *
 * @author MerePT
 * @version 1.0
 */
public class History {
    private static final String path = OutputPathChange.PATH + "history.txt";
    private static ArrayList<String> history = new ArrayList<>();

    public static void historyList() {
        System.out.println("\n" + Theme.getTheLine() + "\n");
        var i = 1;
        try {
            var read = new DataInputStream(new FileInputStream(path));
            BufferedReader d = new BufferedReader(new InputStreamReader(read));

            var count = "";
            while ((count = d.readLine()) != null) {
                System.out.println(i + "." + count);
                history.add(count);
                i++;
            }
        } catch (IOException e) {
            System.out.println("找不到文件!");
        }
        System.out.println("\n" + Theme.getTheLine() + "\n");
        System.out.print("输入要查看的历史 >\040");
        readHistory();
        System.out.println("回车继续...");
        Utility.readEnter();
    }

    public static void writeHistory(String name) {
        try {
            var output = new DataOutputStream(new FileOutputStream(path, true));
            output.writeBytes(name + "\n");
        } catch (IOException e) {
            System.out.println("找不到该文件!");
        }
    }

    private static void readHistory() throws IndexOutOfBoundsException {
        var slt = Utility.readMenuSelection(history.size());
        var path = OutputPathChange.PATH + history.get(slt - 1) + ".txt";
        Files.printResults(path);
    }

    @SuppressWarnings(value = "all")
    public static void clearHistory() {
        if (OutputPathChange.PATH.equals("请先设置输出目录!")) return;
        try {
            var output = new DataOutputStream(new FileOutputStream(path));
            output.writeBytes("");
        } catch (IOException e) {
            System.out.println("找不到该文件!");
        }

    }
}
