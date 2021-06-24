package com.merept.draw.store;

import com.merept.draw.service.DrawingService;
import com.merept.draw.utils.Utility;

import java.util.ArrayList;

public class Drawing {
    protected static final ArrayList<DrawingService> idIn = new ArrayList<>();
    protected static final ArrayList<DrawingService> idOut = new ArrayList<>();

    public static void enter() {
        if (idIn.isEmpty()) {
            System.out.println("\n请先设置输入路径!回车继续");
            Utility.readEnter();
            return;
        }
        Drawing.byFile();
        System.out.println("是否需要现在查看结果?");
        var slt = Utility.readConfirmSelection();
        if (slt == 'Y') Files.printResults(DrawingService.getOutputPath());
        System.out.println("\n回车继续...");
        Utility.readEnter();
    }

    public static void byFile() {
        System.out.println("文件读取完成, 是否有需要删除的?");
        var slt = Utility.readConfirmSelection();
        if (slt == 'Y') removeId();

        System.out.printf("""
                
                共计 %d 人
                输入需要抽的人数 >\040""", idIn.size());
        var i = Utility.readInt();
        for (int j = 0; j < i; j++) drawPeople();

        Files.OutputIds();
    }

    private static void drawPeople() {
        var ran = idIn.size();
        var num = (int) (Math.random() * ran);
        var id = checkRepeatValue(num, ran);
        idOut.add(new DrawingService(id));
    }

    private static String checkRepeatValue(int num, int ran) {
        if (idOut.isEmpty()) return idIn.get(num).getId();
        for (int i = 0; i < idOut.size(); i++) {
            var id = idIn.get(num).getId();
            if (id.equals(idOut.get(i).getId())) {
                num = (int) (Math.random() * ran);
                i = 0;
            }
        }
        return idIn.get(num).getId();
    }

    private static void removeId() {
        var id = " ";
        while (true) {
            System.out.print("\n输入你想删除的信息(直接回车结束删除) >\040");
            id = Utility.readString(null);
            if (id == null) break;

            var i = findId(id);
            if (i == -1) {
                System.out.println("找不到该信息请重新输入!");
            } else {
                idIn.remove(i);
                System.out.println("删除成功!");
            }
        }
    }

    private static int findId(String id) {
        for (int i = 0; i < idIn.size(); i++)
            if (id.equals(idIn.get(i).getId())) return i;
        return -1;
    }
}
