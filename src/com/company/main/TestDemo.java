package com.company.main;

import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

public class TestDemo {

    public static void main(String[] args) {

//        String str = "test";
//        String gbk = null;
//        try {
//            gbk = new String(str.getBytes("ISO-8859-1"), "GBK");
//            gbk = gbk.trim();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(gbk);


        TreeMap<Integer, String> treeMap = new TreeMap<>();

        treeMap.put(10, "十");
        treeMap.put(9, "九");

        System.out.println(treeMap);


    }

}
