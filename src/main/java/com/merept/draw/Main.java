package com.merept.draw;

import com.merept.draw.service.DrawingService;
import com.merept.draw.store.Drawing;
import com.merept.draw.store.Files;
import com.merept.draw.utils.Utility;

/**
 * <p>所属包名: com.merept.draw</p>
 * <p>项目名称: RandomDrawSys</p>
 * <p>文件名称: Main</p>
 * <p>创建时间: 2021/6/15</p>
 *
 * @author MerePT
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        Drawing.byFile();
        System.out.println("是否需要现在查看结果?");
        var slt = Utility.readConfirmSelection();
        if (slt == 'Y') Files.printResults(DrawingService.getPath());
        System.out.println("\n回车退出...");
        Utility.readEnter();
    }
}
