/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.approaches.w2v;

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
import lab.alignment.configs.Configs;

/**
 *
 * @author s1420211
 */
public class IBM_W2V {

    public static void ibmModel_W2v(String modelFile, String enSimFile, String viSimFile, String modelOutFile, String newOutFile) throws IOException {

        Table<String, String, Double> enSimTable = readSimilarityFile(enSimFile);
        Table<String, String, Double> viSimTable = readSimilarityFile(viSimFile);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(modelOutFile)), Configs.ENCODING));
        BufferedWriter newWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(newOutFile)), Configs.ENCODING));        

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(modelFile)), Configs.ENCODING));

        String line;
        int nline = 0;
        int newen = 0;
        int newvi = 0;
        while ((line = reader.readLine()) != null) {

            String[] st = line.trim().split(" ");

            String en = st[1];
            String vi = st[2];

            writer.write(line);
            writer.write("\n");
            nline++;

            if (enSimTable.containsRow(en)) {

                double score = Double.parseDouble(st[0]);

                for (String enSim : enSimTable.row(en).keySet()) {
                    double newScore = score * enSimTable.get(en, enSim);
                    writer.write(String.format("%f %s %s\n", newScore, enSim, vi));
                    newWriter.write(String.format("%f %s %s\n", newScore, enSim, vi));                    
                    newen++;
                }
            }

            if (viSimTable.containsRow(vi)) {

                double score = Double.parseDouble(st[0]);
                for (String viSim : viSimTable.row(vi).keySet()) {
                    double newScore = score * viSimTable.get(vi, viSim);
                    writer.write(String.format("%f %s %s\n", newScore, en, viSim));
                    newWriter.write(String.format("%f %s %s\n", newScore, en, viSim));                    
                    newvi++;
                }

            }

        }

        writer.close();
        newWriter.close();
        reader.close();

        System.out.println("old lines: " + nline);
        System.out.println("new en: " + newen);
        System.out.println("new vi: " + newvi);

    }

    public static Table<String, String, Double> readSimilarityFile(String inFile) throws IOException {

        Table<String, String, Double> pairTable = HashBasedTable.create();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {

            String[] st = line.trim().split(" ");

            pairTable.put(st[1], st[0], Double.parseDouble(st[2]));

        }

        reader.close();

        System.out.println("similarity pairs: ");
        System.out.println("pairs: " + pairTable.size());
        System.out.println("src vocab: " + pairTable.rowKeySet().size());
        System.out.println("trg vocab: " + pairTable.columnKeySet().size());

        return pairTable;
    }

}
