/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.utils;

//import com.google.common.collect.HashBasedTable;
//import com.google.common.collect.Table;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lab.alignment.configs.Configs;
//import lab.corpora.configs.Configs;
//import lab.pivot.configurations.Configs;

/**
 *
 * @author Long
 */
public class Sample {

    public static void read_write() throws IOException {
        String inFile = "";
        String outFile = "";

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)), Configs.ENCODING));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {
            //
        }
        writer.close();
        
        reader.close();
    }

    public static void read_write_binary() throws IOException {
        String inFile = "";
        String outFile = "";
        DataInputStream binReader = new DataInputStream(new BufferedInputStream(new FileInputStream(inFile)));
        DataOutputStream binWriter = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outFile)));

        while (binReader.available() > 0) {
            binReader.readInt();
            binWriter.writeInt(0);
        }
    }

    public static void map() {
        Map<String, String> map = new HashMap<>();
        for (String key : map.keySet()) {
            String value = map.get(key);
            map.put(key, value);
        }
    }

    public static void set() {
        Set<String> set = new HashSet<>();
        for (String key : set) {
            set.add(key);
        }
    }

    public static void list() {
        List<String> list = new ArrayList<>();
        for (String key : list) {
            list.add(key);
        }
    }

//    public static void table() {
//        Table<String, String, String> table = HashBasedTable.create();
//        //===================================
//        for (String rowKey : table.rowKeySet()) {
//            for (String columnKey : table.columnKeySet()) {
//                String value = table.get(rowKey, columnKey);
//                table.put(rowKey, columnKey, value);
//            }
//        }
//        //===================================
//        for(String rowKey:table.rowKeySet()){
//            Map<String,String>columnMap=table.row(rowKey);
//        }
//        //===================================        
//        for(String columnKey:table.columnKeySet()){
//            Set<String>rowKeySet=table.column(columnKey).keySet();
//        }
//    }

}
