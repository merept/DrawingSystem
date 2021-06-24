package com.merept.draw.store;

import com.merept.draw.service.DrawingService;
import com.merept.draw.utils.Utility;

import java.io.*;

/**
 * <p>所属包名: com.merept.draw.store</p>
 * <p>项目名称: DrawingSystem</p>
 * <p>文件名称: OutputPathChange</p>
 * <p>创建时间: 2021/6/24</p>
 *
 * @author MerePT
 * @version 1.0
 */
public class OutputPathChange {
    private static final String USER = System.getProperty("user.dir");
    private static final String LOG = USER + "\\sources\\log.txt";
    public static String PATH;

    public static void enter() {
        readLog();
        System.out.print("更改输出目录将会清空历史记录!\n" +
                "是否继续更改？");
        var slt = Utility.readConfirmSelection();
        if (slt == 'N') return;
        History.clearHistory();
        System.out.print("请输入需要设置的新路径(直接回车将不会重新设置) >\040");
        PATH = Utility.readString(PATH);
        if (PATH.equals("请先设置输出目录!")) return;
        writeLog();
    }

    public static void readLog() {
        try {
            var path = new DataInputStream(new FileInputStream(LOG));
            BufferedReader d = new BufferedReader(new InputStreamReader(path));

            var count = "";
            while ((count = d.readLine()) != null) PATH = count;
        } catch (IOException e) {
            System.out.println("找不到文件!");
        }
        if (PATH == null) PATH = "请先设置输出目录!";
    }

    @SuppressWarnings(value = "all")
    private static void writeLog() {
        try {
            var dir = new File(PATH);
            if (!dir.exists()) dir.mkdirs();
            var output = new DataOutputStream(new FileOutputStream(LOG));
            if (PATH.charAt(PATH.length() - 1) != '\\') PATH += "\\";
            output.writeBytes(PATH);
        } catch (IOException e) {
            System.out.println("找不到该文件!");
        }
    }
}
