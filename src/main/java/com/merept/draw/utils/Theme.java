package com.merept.draw.utils;

import java.util.Collections;

public class Theme {
    public static String theLine = "-";
    private static final String shortLine = "-";
    private static final String waveLine = "~";
    private static final String sharpLine = "#";
    private static final String starLine = "*";
    private static final String equalLine = "=";

    public static void setTheLine(char i) {
        switch (i) {
            case '1' -> theLine = shortLine;
            case '2' -> theLine = waveLine;
            case '3' -> theLine = sharpLine;
            case '4' -> theLine = starLine;
            case '5' -> theLine = equalLine;
        }
    }

    public static String getTheLine() {
        return String.join("", Collections.nCopies(36,theLine));
    }

    public static String getTheLine(int n) {
        return String.join("", Collections.nCopies(n,theLine));
    }
}