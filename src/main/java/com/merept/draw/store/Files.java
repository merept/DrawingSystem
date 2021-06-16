package com.merept.draw.store;

import com.merept.draw.service.DrawingService;
import com.merept.draw.utils.Utility;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>所属包名: com.merept.draw.store</p>
 * <p>项目名称: RandomDrawSys</p>
 * <p>文件名称: Files</p>
 * <p>创建时间: 2021/6/15</p>
 *
 * @author MerePT
 * @version 1.0
 */
public class Files extends Drawing {
    private static int lines = 0;
    protected static final String USER = System.getProperty("user.dir");

    public static void InputIds(String path) {
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
        }

        if (idIn.size() == 0) {
            System.out.println("该文件内没有信息!");
            emptyFile(path);
        }
    }

    public static void OutputIds() {
        System.out.print("""
                
                抽签已完成!
                请输入结果输出文件的文件名(直接回车将使用默认命名) >\040""");
        var name = Utility.readString(null);
        name = fileNameSet(name);
        new DrawingService(name, 'c');

        try {
            var output = new DataOutputStream(new FileOutputStream(DrawingService.getPath()));
            for (DrawingService draw: idOut) {
                output.writeBytes(draw.getId() + "  ");
                lines++;

                if (lines == 5) {
                    output.writeBytes("\n");
                    lines = 0;
                }
            }
        } catch (IOException e) {
            System.out.println("找不到该输出文件");
        }

        System.out.println("\n抽签结果已存入 " + DrawingService.getPath() + " 中。\n");
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

    public static String fileNameSet(String name) {
        var date = new Date();
        var sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        return name == null ? sdf.format(date) : name;
    }

    private static void emptyFile(String path) {
        System.out.print("修改文件并保存后按回车继续(更换文件请在后面输入路径) > ");
        path = Utility.readString(path);
        InputIds(path);
    }
}