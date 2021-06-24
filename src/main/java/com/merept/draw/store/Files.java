package com.merept.draw.store;

import com.merept.draw.service.DrawingService;
import com.merept.draw.utils.Utility;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Files extends Drawing {
    private static int numbers = 0;
    private static final String USER = System.getProperty("user.dir");
    private static String path = USER + "\\sources\\input.txt";

    public static String setPath() {
        System.out.print("\n请输入文件路径或拖入文件(直接回车将使用默认路径) >\040");
        path = Utility.readString(path);
        path = InputIds(path);
        System.out.println("文件读取完成, 回车继续...");
        Utility.readEnter();
        return path;
    }

    public static String InputIds(String path) {
        path = checkFileName(path);

        System.out.println("\n" + path + "\n");

        try {
            var input = new DataInputStream(new FileInputStream(path));
            BufferedReader d  = new BufferedReader(new InputStreamReader(input));

            var count = "";
            while ((count = d.readLine()) != null) {
                idIn.add(new DrawingService(count));
            }
        } catch (IOException e) {
            System.out.println("找不到该文件");
            emptyFile(path);
        }

        if (idIn.isEmpty()) {
            System.out.println("该文件内没有信息!");
            emptyFile(path);
        }
        return path;
    }

    public static void OutputIds() {
        System.out.print("""
                
                抽签已完成!
                请输入结果输出文件的文件名(直接回车将使用默认命名) >\040""");
        var name = Utility.readString(null);
        name = fileNameSet(name);
        History.writeHistory(name);
        new DrawingService(name, 2);

        try {
            var output = new DataOutputStream(new FileOutputStream(DrawingService.getOutputPath()));
            for (DrawingService draw: idOut) {
                output.writeBytes(draw.getId() + "  ");
                numbers++;

                if (numbers == 5) {
                    output.writeBytes("\n");
                    numbers = 0;
                }
            }
        } catch (IOException e) {
            System.out.println("找不到该输出文件");
        }

        System.out.println("\n抽签结果已存入 " + DrawingService.getOutputPath() + " 中。\n");
    }

    public static void printResults(String path) {
        try {
            var results = new DataInputStream(new FileInputStream(path));
            BufferedReader d  = new BufferedReader(new InputStreamReader(results));

            var count = "";
            while ((count = d.readLine()) != null) {
                System.out.println(count);
            }
        } catch (IOException e) {
            System.out.println("找不到该文件");
        }
    }

    private static String checkFileName(String path) {
        if (path.charAt(0) != '\"') return path;
        StringBuilder returnPath = new StringBuilder();
        for (int i = 1; i < path.length() - 1; i++)
            returnPath.append(path.charAt(i));
        return returnPath.toString();
    }

    private static String fileNameSet(String name) {
        var date = new Date();
        var sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        return name == null ? sdf.format(date) : name;
    }

    private static void emptyFile(String path) {
        System.out.print("修改文件并保存后按回车继续(更换文件请在后面输入路径) >\040");
        path = Utility.readString(path);
        InputIds(path);
    }
}
