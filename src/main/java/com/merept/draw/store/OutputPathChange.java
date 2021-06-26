package com.merept.draw.store;

import com.merept.draw.main.Main;
import com.merept.draw.utils.Utility;

import java.io.*;

public class OutputPathChange {
    private static final String LOG = Main.USER + "\\sources\\log.txt";
    public static String PATH;

    public static void enter() {
        readLog();
        System.out.print("请输入需要设置的新路径(直接回车将不会重新设置) >\040");
        PATH = Utility.readString(PATH);
        if (PATH.equals("请先设置输出目录!")) return;
        writeLog();
    }

    public static void readLog() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(LOG)));

            var count = "";
            while ((count = input.readLine()) != null) PATH = count;

            input.close();
        } catch (IOException e) {
            System.out.println("找不到文件!");
        }
        if (PATH == null) PATH = "请先设置输出目录!";
    }

    @SuppressWarnings (value = "all")
    private static void writeLog() {
        try {
            var dir = new File(PATH);
            if (!dir.exists()) dir.mkdirs();
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOG)));
            if (PATH.charAt(PATH.length() - 1) != '\\') PATH += "\\";
            output.write(PATH);

            output.close();
        } catch (IOException e) {
            System.out.println("找不到该文件!");
        }
    }
}
