package com.merept.draw.store;

import com.merept.draw.service.DrawingService;
import com.merept.draw.utils.Utility;

import java.util.ArrayList;

import static com.merept.draw.store.Files.*;

/**
 * <p>所属包名: com.merept.draw.store</p>
 * <p>项目名称: RandomDrawSys</p>
 * <p>文件名称: Drawing</p>
 * <p>创建时间: 2021/6/15</p>
 *
 * @author MerePT
 * @version 1.0
 */
public class Drawing {
    protected static final ArrayList<DrawingService> idIn = new ArrayList<>();
    protected static final ArrayList<DrawingService> idOut = new ArrayList<>();
    private static String path = USER + "\\sources\\input.txt";

    public static void byFile() {
        System.out.print("请输入文件路径(直接回车将使用默认路径) > ");
        path = Utility.readString(path);

        InputIds(path);

        System.out.println("文件读取完成, 是否有需要删除的?");
        var slt = Utility.readConfirmSelection();
        if (slt == 'Y') removeId();

        System.out.printf("""
                
                共计 %d 人
                输入需要抽的人数 >\040""", idIn.size());
        var i = Utility.readInt();
        for (int j = 0; j < i; j++) drawPeople();

        OutputIds();
    }

    private static void drawPeople() {
        var ran = idIn.size();
        var num = (int) (Math.random() * ran);
        var id = checkRepeatValue(num, ran);
        idOut.add(new DrawingService(id));

    }

    private static String checkRepeatValue(int num, int ran) {
        if (idOut.size() == 0) return idIn.get(num).getId();
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
            System.out.print("\n输入你想删除的信息(直接回车结束删除) > ");
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
        for (int i = 0; i < idIn.size(); i++) {
            if (id.equals(idIn.get(i).getId())) return i;
        }
        return -1;
    }
}
