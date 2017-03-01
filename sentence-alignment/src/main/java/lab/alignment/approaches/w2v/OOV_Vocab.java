/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.approaches.w2v;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import lab.alignment.configs.Configs;
import lab.alignment.utils.Read;

/**
 *
 * @author s1420211
 */
public class OOV_Vocab {

    public static void oovVocab(String modelFile, String testFile, String oovFile) throws IOException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(oovFile)), Configs.ENCODING));

        Set<String> testVocab = Read.read2set(testFile);
        Set<String> modelVocab = Read.read2set(modelFile);

        int noov = 0;

        for (String test : testVocab) {
            if (!modelVocab.contains(test)) {
                noov++;
                writer.write(test);
                writer.write("\n");
            }
        }
        writer.close();
        
        System.out.println("file: "+new File(testFile).getName());
        System.out.println("test vocab: "+testVocab.size());
        System.out.println("model vocab: "+modelVocab.size());
        System.out.println("oov vocab: "+noov);
        System.out.println("");

    }

    public static void vocabFile(String inFile, String vocabFile) throws IOException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(vocabFile)), Configs.ENCODING));

        Set<String> vocabSet = vocab(inFile);

        for (String word : vocabSet) {
            writer.write(word);
            writer.write("\n");
        }
        writer.close();

    }

    public static Set<String> vocab(String inFile) throws IOException {

        Set<String> vocabSet = new HashSet<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;

        while ((line = reader.readLine()) != null) {

            String[] st = line.trim().split(" ");
            for (String word : st) {
                vocabSet.add(word);
            }
        }

        System.out.println("vocab size: " + vocabSet.size());

        return vocabSet;

    }

    public static void ibm1Vocab(String inFile, String srcOutFile, String trgOutFile) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;
        int nline = 0;
        int nsaved = 0;

        Set<String> enVocab = new HashSet<>();
        Set<String> viVocab = new HashSet<>();

        while ((line = reader.readLine()) != null) {
            //
            nline++;

            String st[] = line.trim().split(" ");

            if (st.length == 3) {
                nsaved++;

                enVocab.add(st[1]);
                viVocab.add(st[2]);

            }

        }

        BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));
        BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

        for (String en : enVocab) {

            srcWriter.write(en);
            srcWriter.write("\n");

        }

        for (String vi : viVocab) {
            trgWriter.write(vi);
            trgWriter.write("\n");
        }

        srcWriter.close();
        trgWriter.close();

        reader.close();

        System.out.println("lines: " + nline);
        System.out.println("saved: " + nsaved);
        System.out.println("src vocab: " + enVocab.size());
        System.out.println("trg vocab: " + viVocab.size());

    }

}
