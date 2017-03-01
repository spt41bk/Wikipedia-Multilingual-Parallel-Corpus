/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author s1420211
 */
public class FileIO {
    
//    public static void mkdir(String dir){
//        if(!new File(dir).exists()) new File(dir).mkdir();
//    }
    
    public static void mkdirs(String dir){
        if(!new File(dir).exists()) new File(dir).mkdirs();
    }
    
    public static List<String> fileNameList(String inDir) {

        List<String> fileList = new ArrayList<>();

        TreeSet<String> fileNameSet = new TreeSet<>();

        for (File file : new File(inDir).listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".test_en.snt")) {
                fileName = fileName.replace(".test_en.snt", "");
            } else if (fileName.endsWith(".test_vi.snt")) {
                fileName = fileName.replace(".test_vi.snt", "");
            }
            fileNameSet.add(fileName);
        }

        for (String fileName : fileNameSet) {
            fileList.add(fileName);
        }

        return fileList;

    }
    
}
