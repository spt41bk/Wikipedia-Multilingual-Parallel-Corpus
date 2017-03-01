/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.approaches.evs.baseline;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lab.alignment.configs.Configs;
import lab.alignment.utils.Read;

/**
 *
 * @author s1420211
 */
public class Statistics {

    public static void similarity_SentPairs(String goodPairsFile, String enRefFile, String viRefFile, String outFile) throws IOException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)), Configs.ENCODING));

        Table<String, String, Integer> goodPairsTable = readGoodPairs(goodPairsFile);

        List<String> enRefSentList = Read.read2List(enRefFile);
        List<String> viRefSentList = Read.read2List(viRefFile);

        for (int id = 0; id < enRefSentList.size(); id++) {

            String ensent = enRefSentList.get(id);
            String visent = viRefSentList.get(id);

            boolean ok = false;
            for (String en : ensent.split(" ")) {
                for (String vi : visent.split(" ")) {
//                    if (goodPairsTable.contains(en, vi)) {
                    if (goodPairsTable.rowKeySet().contains(en)) {

                        writer.write(String.format("[%s %s]\n", en, vi));
//                        writer.write(ensent);
//                        writer.write("\n");
//                        writer.write(visent);
//                        writer.write("\n\n");

//                        break;
                        ok = true;
                    }
                }
            }
            if (ok) {
                writer.write(ensent);
                writer.write("\n");
                writer.write(visent);
                writer.write("\n\n");
            }

//            System.out.println(ensent);
//            System.out.println(visent);
//            if (id == 20) {
//                break;
//            }
        }

        System.out.println("en ref sents: " + enRefSentList.size());
        System.out.println("vi ref sents: " + viRefSentList.size());

        writer.close();

    }

    public static Table<String, String, Integer> readGoodPairs(String inFile) throws IOException {

        Table<String, String, Integer> goodPairsTable = HashBasedTable.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {
            String words[] = line.split(" ");

            goodPairsTable.put(words[1], words[2], 0);

        }

        reader.close();
        System.out.println("good word pairs: " + goodPairsTable.size());

        return goodPairsTable;

    }

    public static void lengthStatistics(String inFile) throws IOException {

//        Set<String>sentSet=Read.read2set(inFile);
        List<String> sentList = Read.read2List(inFile);

        int len_total = 0;

        for (String sent : sentList) {
            len_total += sent.split(" ").length;
        }

        double avglen = len_total / sentList.size();

        System.out.println("Length Statistics:");
        System.out.println("File: " + inFile);
        System.out.println("# sents: " + sentList.size());
        System.out.println("avg len: " + avglen);
        System.out.println("");

    }

    public static void countVocab(String inFile) throws IOException {

        Set<String> vocabSet = getVocab(inFile);

        System.out.println("File: " + new File(inFile).getName());
        System.out.println("Vocab size: " + vocabSet.size());
        System.out.println("");

    }

    public static Set<String> getVocab(String inFile) throws IOException {

        Set<String> vocabSet = new HashSet<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] words = line.trim().split(" ");

            for (String word : words) {
                vocabSet.add(word);
            }
        }

        reader.close();

        return vocabSet;

    }

}
