package com.merept.draw.service;

import com.merept.draw.store.OutputPathChange;

public class DrawingService {
    private static String outputPath;
    private String id;

    public DrawingService(String id) {
        this.id = id;
    }

    public DrawingService(String path, int i) {
        if (i == 2) outputPath = path;
        setPath();
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public String getId() {
        return id;
    }

    private void setPath() {
        outputPath = OutputPathChange.PATH + outputPath + ".txt";
    }
}
