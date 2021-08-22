package com.merept.draw.service;

import com.merept.draw.store.OutputPathChange;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawingService that = (DrawingService) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
