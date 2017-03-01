/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.data.vnu.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import lab.alignment.configs.Configs;

/**
 *
 * @author s1420211
 */
public class GetFileList {

    public static void testSet_Statistics(String testDir, String refDir, String outDir, String outFile) throws IOException {

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)), Configs.ENCODING));

        writer.write("Test data statistics:\n");
        writer.write("----------------------------\n");

        int totalnsrcsents = 0;
        int totalntrgsents = 0;
        int totalnrefsents = 0;

        List<String> fileNameList = getFileList(testDir);

        for (String name : fileNameList) {

            String srcTestFile = makeFileName(name, "test", "en", "snt");
            String trgTestFile = makeFileName(name, "test", "vi", "snt");
            String srcRefFile = makeFileName(name, "ref", "en", "snt");
            String trgRefFile = makeFileName(name, "ref", "vi", "snt");

            int nsrcsents = sentNum(testDir, srcTestFile);
            int ntrgsents = sentNum(testDir, trgTestFile);
            int nsrcrefsents = sentNum(refDir, srcRefFile);
            int ntrgrefsents = sentNum(refDir, trgRefFile);

            totalnsrcsents += nsrcsents;
            totalntrgsents += ntrgsents;
            totalnrefsents += nsrcrefsents;

            writer.write(String.format("File: %s\n", name));
            writer.write(String.format("\tsrc sents: %d\n", nsrcsents));
            writer.write(String.format("\ttrg sents: %d\n", ntrgsents));
            writer.write(String.format("\tref sents: %d\n", nsrcrefsents));
            if (nsrcrefsents != ntrgrefsents) {
                System.out.println(name);
                System.out.println("not match");
            }
            if (nsrcrefsents < 2) {
                System.out.println("short: " + name);
            }
            writer.write("\n");

        }

        writer.write("----------------------------\n");
        writer.write("\n");
        writer.write("Total sents: ");
        writer.write(String.format("\ttotal src sents: %d\n", totalnsrcsents));
        writer.write(String.format("\ttotal trg sents: %d\n", totalntrgsents));
        writer.write(String.format("\ttotal ref sents: %d\n", totalnrefsents));

        writer.close();

    }

    public static int sentNum(String inDir, String inFile) throws IOException {

        int sentNum = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inDir, inFile)), Configs.ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                sentNum++;
            }
        }
        reader.close();

        return sentNum;

    }

    public static String makeFileName(String name, String type, String lang, String extension) {

        StringBuilder fileName = new StringBuilder(100);

        fileName.append(name).append(".")
                .append(type).append("_").append(lang)
                .append(".").append(extension);

        return fileName.toString();

    }

    public static String makeFilePath(String inDir, String name, String type, String lang, String extension) {

        StringBuilder fileName = new StringBuilder(100);

        fileName.append(inDir).append("/")
                .append(name).append(".")
                .append(type).append("_").append(lang)
                .append(".").append(extension);

        return fileName.toString();

    }

    public static void refFileName(String inDir, String outDir) throws IOException {

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        for (File file : new File(inDir).listFiles()) {
            String fileName = file.getName();

            String newName = "";
            if (fileName.contains("_clean_e")) {
                newName = fileName.replace("_clean_e", ".ref_en.snt");
            }
            if (fileName.contains("_clean_v")) {
                newName = fileName.replace("_clean_v", ".ref_vi.snt");
            }
            Files.copy(file.toPath(), new File(outDir, newName).toPath());

        }

    }

    public static void testingFileName(String inDir, String outDir) throws IOException {

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        for (File file : new File(inDir).listFiles()) {
            String fileName = file.getName();

            String newName = "";
            if (fileName.contains("_clean_e")) {
                newName = fileName.replace("_clean_e", ".test_en.snt");
            }
            if (fileName.contains("_clean_v")) {
                newName = fileName.replace("_clean_v", ".test_vi.snt");
            }
            Files.copy(file.toPath(), new File(outDir, newName).toPath());

        }

    }

    public static List<String> getFileList(String inDir) {

        List<String> fileList = new ArrayList<>();

        TreeSet<String> fileNameSet = new TreeSet<>();

        for (File file : new File(inDir).listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".test_en.snt")) {
                fileName = fileName.replace(".test_en.snt", "");
                fileNameSet.add(fileName);
            } else if (fileName.endsWith(".test_vi.snt")) {
                fileName = fileName.replace(".test_vi.snt", "");
                fileNameSet.add(fileName);
            }
        }

        for (String fileName : fileNameSet) {
            fileList.add(fileName);
        }

        return fileList;

    }
}
