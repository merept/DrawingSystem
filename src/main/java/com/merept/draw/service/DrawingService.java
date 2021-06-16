package com.merept.draw.service;

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
    public static String path;

    public DrawingService(String id) {
        this.id = id;
    }

    public DrawingService(String path, char c) {
        DrawingService.path = path;
        setPath();
    }

    public String getId() {
        return id;
    }

    private void setPath() {
        path = USER + "\\results\\" + path + ".txt";
    }

    public static String getPath() {
        return path;
    }
}
