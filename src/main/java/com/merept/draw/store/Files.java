package com.merept.draw.store;

import com.merept.draw.main.Menu;
import com.merept.draw.service.DrawingService;
import com.merept.draw.utils.Utility;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Files {
    private static int numbers = 0;
    private static String path = Menu.inputPath;

    public static String setPath() {
        System.out.print("\n请输入文件路径或拖入文件(直接回车将使用默认路径) >\040");
        path = Utility.readString(path);
        path = InputIds(path);
        if (path.equals("请先设置输入路径!")) return path;
        System.out.println("文件读取完成, 回车继续...");
        Utility.readEnter();
        return path;
    }

    public static String InputIds(String path) {
        Drawing.idIn.clear();
        path = checkFileName(path);
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("\n该文件不存在!回车继续...");
            Utility.readEnter();
            return "请先设置输入路径!";
        }

        System.out.println("\n" + path + "\n");

        input(path);

        if (Drawing.idIn.isEmpty()) {
            System.out.println("该文件内没有信息!");
            emptyFile(path);
        }
        return path;
    }

    public static void beginInput(String path) {
        Drawing.idIn.clear();
        if (path.equals("请先设置输入路径!")) return;
        path = checkFileName(path);
        input(path);
        ReduceRate.readLatest();
    }

    private static void input(String path) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            var count = "";
            while ((count = input.readLine()) != null) Drawing.idIn.add(new DrawingService(count));
            input.close();
        } catch (IOException e) {
            System.out.println("找不到该文件");
            emptyFile(path);
        }
    }

    public static void OutputIds() {
        System.out.print("""
                                
                抽签已完成!
                请输入结果输出文件的文件名(直接回车将使用默认命名) >\040""");
        var name = Utility.readString(null);
        name = fileNameSet(name);
        new DrawingService(name, 2);
        checkRepeatName(name);

        try {
            var path = DrawingService.getOutputPath();
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            for (DrawingService draw : Drawing.idOut) {
                output.write(draw.getId() + "  ");
                numbers++;

                if (numbers == 5) {
                    output.write("\n");
                    numbers = 0;
                }
            }
            ReduceRate.writeLatest();
            output.close();
        } catch (IOException e) {
            System.out.println("找不到该输出文件");
        }

        System.out.println("\n抽签结果已存入 " + DrawingService.getOutputPath() + " 中。\n");
    }

    private static void checkRepeatName(String name) {
        File check = new File(DrawingService.getOutputPath());
        StringBuilder nameBuilder = new StringBuilder(name);
        while (check.exists()) {
            var i = 1;
            nameBuilder.append(" (").append(i).append(")");
            new DrawingService(nameBuilder.toString(), 2);
            check = new File(DrawingService.getOutputPath());
        }
    }

    public static void printResults(String path) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            var count = "";
            while ((count = input.readLine()) != null) {
                System.out.println(count);
            }

            input.close();
        } catch (IOException e) {
            System.out.println("找不到该文件");
        }
    }

    public static String checkFileName(String path) {
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
