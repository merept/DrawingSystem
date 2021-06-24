package com.merept.draw.service;

import com.merept.draw.store.OutputPathChange;

/**
 * <p>所属包名: com.merept.draw.service</p>
 * <p>项目名称: RandomDrawSys</p>
 * <p>文件名称: DrawingService</p>
 * <p>创建时间: 2021/6/15</p>
 *
 * @author MerePT
 * @version 1.0
 */
public class DrawingService {
    private static final String USER = System.getProperty("user.dir");
    private String id;
    private static String outputPath;
    private static String inputPath;

    public DrawingService(String id) {
        this.id = id;
    }

    public DrawingService(String path, int i) {
        if (i == 1) inputPath = path;
        else if (i == 2) outputPath = path;
        setPath();
    }

    public String getId() {
        return id;
    }

    private void setPath() {
        outputPath = OutputPathChange.PATH + outputPath + ".txt";
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static String getInputPath() {
        return inputPath;
    }
}
