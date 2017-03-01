/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.utils;

//import lab.pivot.utils.chars.CharactersProcess;
//import lab.pivot.utils.chars.VietnameseCharacters;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import lab.alignment.configs.Configs;

/**
 *
 * @author New user
 */
public class Read {

    public static Set<String> read2set(String inFile) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                set.add(line.trim());
            }
        }

//        System.out.println("SIZE OF SET:            " + set.size());
        System.out.println(String.format("file: %s;      size of set: %d", new File(inFile).getName(), set.size()));
        return set;
    }
    
    public static List<String> read2List(String inFile) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
//                set.add(line);
                list.add(line);
            }
        }

//        System.out.println("SIZE OF SET:            " + set.size());
        System.out.println(String.format("file: %s;      size of set: %d", new File(inFile).getName(), list.size()));
        return list;
    }

    public static List<Integer> read2ListInt(String inFile) throws IOException {
//        Set<String> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
//                set.add(line);
                list.add(Integer.parseInt(line));
            }
        }

//        System.out.println("SIZE OF SET:            " + set.size());
        System.out.println(String.format("file: %s;      size of set: %d", new File(inFile).getName(), list.size()));
        return list;
    }

    public static Set<Integer> read2set_int(String inFile) throws IOException {
        Set<Integer> set = new HashSet<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                set.add(Integer.parseInt(line));
            }
        }

//        System.out.println("SIZE OF SET:            " + set.size());
        System.out.println(String.format("file: %s;      size of set: %d", new File(inFile).getName(), set.size()));
        return set;
    }   

}
