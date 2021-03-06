package com.ssafy.BackEnd.service;

import java.util.ArrayList;
import java.util.List;

public class HashTagAlgorithm {

    public static List<String> strList(String content){
        List<String> strlist = new ArrayList<>();
        String[] strArr = content.split(" ");
        for (String s : strArr){
            if(s.charAt(0) == '#') {
                String key = s.substring(1);
                strlist.add(key);
            }
        }
        return strlist;
    }
}
