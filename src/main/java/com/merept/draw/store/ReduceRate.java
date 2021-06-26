package com.merept.draw.store;

import com.merept.draw.main.Main;
import com.merept.draw.service.DrawingService;
import com.merept.draw.utils.Utility;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReduceRate {
    public static ArrayList<DrawingService> idData = new ArrayList<>();
    public static ArrayList<DrawingService> idRate = new ArrayList<>();
    public static String DATA = Main.USER + "\\sources\\data.txt";
    public static String RATE = Main.USER + "\\sources\\rate.txt";

    public static void readLatest() {
        clearCache();
        if (DATA == null) return;
        try {
            BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(DATA)));
            BufferedReader rate = new BufferedReader(new InputStreamReader(new FileInputStream(RATE)));

            String dataString;
            String rateString;

            while ((dataString = data.readLine()) != null) idData.add(new DrawingService(dataString));
            while ((rateString = rate.readLine()) != null) idRate.add(new DrawingService(rateString));

            data.close();
            rate.close();
        } catch (IOException e) {
            System.out.println("找不到该文件");
        }

        if (idData.isEmpty()) addData();
    }

    public static void addData() {
        System.out.print("""
                                
                还未添加团队数据
                输入文件路径(或拖入文件)以添加团队数据 >\040""");
        var path = Utility.readString();
        path = Files.checkFileName(path);
        ArrayList<DrawingService> inputArray = new ArrayList<>();
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            BufferedWriter data = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATA)));
            BufferedWriter rate = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RATE)));

            var datas = "";
            while ((datas = input.readLine()) != null) {
                inputArray.add(new DrawingService(datas));
                data.write(datas + "\n");
                rate.write(datas + ":0\n");
            }

            input.close();
            data.close();
            rate.close();
        } catch (IOException e) {
            System.out.println("找不到该文件");
            readLatest();
        }

        if (inputArray.isEmpty()) {
            System.out.print("该文件内没有信息!回车继续...");
            Utility.readEnter();
            readLatest();
        }
    }

    public static void writeLatest() {
        if (DATA == null) return;
        for (int i = 0; i < idRate.size(); i++) {
            var id = idRate.get(i).getId();
            if (id.charAt(id.length() - 1) == '9') idRate.set(i, new DrawingService(resetRate(id, id.length())));
        }
        try {
            BufferedWriter data = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DATA)));
            BufferedWriter rate = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RATE)));

            for (DrawingService Data : idData)
                data.write(Data.getId() + "\n");
            for (DrawingService Rate : idRate)
                rate.write(Rate.getId() + "\n");

            data.close();
            rate.close();
        } catch (IOException e) {
            System.out.println("找不到该文件");
        }
    }

    private static String resetRate(String id, int length) {
        var idChar = id.toCharArray();
        idChar[length - 1] = '1';
        return Arrays.toString(idChar).replaceAll("[\\[\\]\\s,]", "");
    }

    public static void resetRateAll() {
        try {
            BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(DATA)));
            BufferedWriter rate = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RATE)));

            var count = "";
            while ((count = data.readLine()) != null)
                rate.write(count + ":0\n");

            data.close();
            rate.close();
        } catch (IOException e) {
            System.out.println("找不到该文件");
        }
        System.out.println("重置成功!回车继续...");
        Utility.readEnter();
    }

    public static String reducing(String id, int ran) {
        if (DATA == null) return id;
        int i;
        for (i = 0; i < idData.size(); i++) {
            if (id.equals(idData.get(i).getId())) {
                id = drawAgain(i, ran);
                break;
            }
        }
        return id;
    }

    private static String drawAgain(int i, int ran) {
        var idR = idRate.get(i).getId();
        var id = idData.get(i).getId();
        if (idR.charAt(idR.length() - 1) == '0') {
            addRate(idR, i);
            return id;
        }
        var rat = Integer.parseInt(String.valueOf(idR.charAt(idR.length() - 1))) + 1;
        var raw = (int) (Math.random() * (Math.pow(4.0, rat)));
        if (raw == 1) {
            addRate(idR, i);
            return id;
        }
        var num = (int) (Math.random() * ran);
        id = Drawing.checkRepeatValue(num, ran);
        if (id.equals(idData.get(i).getId())) addRate(idR, i);
        return id;
    }

    @SuppressWarnings(value = "all")
    private static void addRate(String idR, int i) {
        var idRChar = idR.toCharArray();
        var idRInt = (int) idRChar[idR.length() - 1];
        idRChar[idR.length() - 1] = (char) (idRInt += 1);
        idRate.set(i, new DrawingService(Arrays.toString(idRChar).replaceAll("[\\[\\]\\s,]", "")));
    }

    private static void clearCache() {
        idData.clear();
        idRate.clear();
    }
}
