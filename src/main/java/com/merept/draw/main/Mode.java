package com.merept.draw.main;

import com.merept.draw.store.ReduceRate;
import com.merept.draw.utils.Theme;
import com.merept.draw.utils.Utility;

import java.io.*;

public class Mode {
    private static final String MODE = Main.USER + "\\sources\\mode.txt";
    public static String modeCode;

    public static void enter() {
        checkMode();
        if (modeCode.equals("0")) changMode();
    }

    public static void changMode() {
        System.out.printf("""
                %s
                                
                1.团队模式(可以降低多次被抽中的概率)
                2.单次模式
                3.重置爆率
                                
                %s
                输入选项更改模式( -1 退出) >\040""", Theme.getTheLine(), Theme.getTheLine());
        var slt = Utility.readMenuSelection(3);
        switch (slt) {
            case 1 -> {
                modeCode = "1";
                changModeCode();
                ReduceRate.DATA = Main.USER + "\\sources\\data.txt";
                ReduceRate.RATE = Main.USER + "\\sources\\rate.txt";
            }
            case 2 -> {
                modeCode = "2";
                changModeCode();
                ReduceRate.DATA = null;
                ReduceRate.RATE = null;
            }
            case 3 -> reset();
            case -1 -> enter();
            case 0 -> System.exit(0);
        }
    }

    private static void reset() {
        if (modeCode.equals("2") || modeCode.equals("0")) enter();
        ReduceRate.resetRateAll();
    }

    private static void checkMode() {
        try {
            BufferedReader mode = new BufferedReader(new InputStreamReader(new FileInputStream(MODE)));
            modeCode = mode.readLine();
        } catch (IOException e) {
            System.out.println("找不到该文件");
        }
    }

    private static void changModeCode() {
        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(MODE)));
            output.write(modeCode);
            output.close();
        } catch (IOException e) {
            System.out.println("找不到该文件");
        }
    }
}
