/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.evaluation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lab.alignment.configs.Configs;
import lab.alignment.data.vnu.process.GetFileList;
import lab.alignment.utils.Read;

/**
 *
 * @author s1420211
 */
public class Evaluation {
    
    
        

    public static void evaluation_EVS_length(String testDir, String refDir, String lengthDir, String outDir, String config) throws IOException {

        List<String> fileNameList = lab.alignment.data.vnu.process.GetFileList.getFileList(testDir);

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, config + ".txt")), Configs.ENCODING));
        writer.write("Evaluation: EVS:\n");
        writer.write(String.format("Config: %s\n", config));
        writer.write("-------------------------\n");

        int ref_num = 0;
        int total_system_num = 0;
        int correct_system_num = 0;

        for (String fileName : fileNameList) {

//            String srcRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_fr", "snt");
//            String trgRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_en", "snt");
//
//            String srcSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_fr", "snt.aligned");
//            String trgSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_en", "snt.aligned");
            String srcRefFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(refDir, fileName, "ref", "en", "snt");
            String trgRefFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(refDir, fileName, "ref", "vi", "snt");

//            String srcSystemFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(systemDir, fileName, "test", "en", "snt.aligned");
//            String trgSystemFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(systemDir, fileName, "test", "vi", "snt.aligned");
//            String srcOutFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(alignedDir, fileName, "test", "en", "aligned");
//            String trgOutFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(alignedDir, fileName, "test", "vi", "aligned");
            String srcOutFile = GetFileList.makeFilePath(lengthDir, fileName, "test", "en", "length");
            String trgOutFile = GetFileList.makeFilePath(lengthDir, fileName, "test", "vi", "length");

            int[] result = evaluation(srcOutFile, trgOutFile, srcRefFile, trgRefFile);

            ref_num += result[0];
            total_system_num += result[1];
            correct_system_num += result[2];

            int ref_num_local = result[0];
            int total_system_num_local = result[1];
            int correct_system_num_local = result[2];

            double precision = precision(correct_system_num_local, total_system_num_local);
            double recall = recall(correct_system_num_local, ref_num_local);
            double fscore = fmeasure(precision, recall);

            String precision_percentage = String.format("%.2f%%", (double) precision * 100);
            String recall_percentage = String.format("%.2f%%", (double) recall * 100);
            String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

            writer.write(String.format("File: %s\n", fileName));
            writer.write(String.format("\tReference: %d\n", result[0]));
            writer.write(String.format("\tSystem: %d\n", result[1]));
            writer.write(String.format("\tCorrect: %d\n", result[2]));
            writer.write(String.format("\tPrecision: %s\n", precision_percentage));
            writer.write(String.format("\tRecall: %s\n", recall_percentage));
            writer.write(String.format("\tF-score: %s\n", fscore_percentage));

            writer.write("\n");

        }

        writer.write("-------------------------\n");
        writer.write("Scores: \n");

        double precision = precision(correct_system_num, total_system_num);
        double recall = recall(correct_system_num, ref_num);
        double fscore = fmeasure(precision, recall);

        String precision_percentage = String.format("%.2f%%", (double) precision * 100);
        String recall_percentage = String.format("%.2f%%", (double) recall * 100);
        String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

        writer.write(String.format("\tTotal Reference: %d\n", ref_num));
        writer.write(String.format("\tTotal System: %d\n", total_system_num));
        writer.write(String.format("\tTotal Correct: %d\n", correct_system_num));

        writer.write("\n");

        writer.write(String.format("\tPrecision: %s\n", precision_percentage));
        writer.write(String.format("\tRecall: %s\n", recall_percentage));
        writer.write(String.format("\tF-score: %s\n", fscore_percentage));

        writer.close();

        System.out.println("");
        System.out.println("precision: " + precision_percentage);
        System.out.println("recall: " + recall_percentage);
        System.out.println("fscore: " + fscore_percentage);
        System.out.println("");
        System.out.println("finished!");
    }

    public static void evaluation_EVS(String testDir, String refDir, String alignedDir, String outDir, String config) throws IOException {

        List<String> fileNameList = lab.alignment.data.vnu.process.GetFileList.getFileList(testDir);

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, config + ".txt")), Configs.ENCODING));
        writer.write("Evaluation: EVS:\n");
        writer.write(String.format("Config: %s\n", config));
        writer.write("-------------------------\n");

        int ref_num = 0;
        int total_system_num = 0;
        int correct_system_num = 0;

        for (String fileName : fileNameList) {

//            String srcRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_fr", "snt");
//            String trgRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_en", "snt");
//
//            String srcSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_fr", "snt.aligned");
//            String trgSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_en", "snt.aligned");
            String srcRefFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(refDir, fileName, "ref", "en", "snt");
            String trgRefFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(refDir, fileName, "ref", "vi", "snt");

//            String srcSystemFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(systemDir, fileName, "test", "en", "snt.aligned");
//            String trgSystemFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(systemDir, fileName, "test", "vi", "snt.aligned");
            String srcOutFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(alignedDir, fileName, "test", "en", "aligned");
            String trgOutFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(alignedDir, fileName, "test", "vi", "aligned");

            int[] result = evaluation(srcOutFile, trgOutFile, srcRefFile, trgRefFile);

            ref_num += result[0];
            total_system_num += result[1];
            correct_system_num += result[2];

            int ref_num_local = result[0];
            int total_system_num_local = result[1];
            int correct_system_num_local = result[2];

            double precision = precision(correct_system_num_local, total_system_num_local);
            double recall = recall(correct_system_num_local, ref_num_local);
            double fscore = fmeasure(precision, recall);

            String precision_percentage = String.format("%.2f%%", (double) precision * 100);
            String recall_percentage = String.format("%.2f%%", (double) recall * 100);
            String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

            writer.write(String.format("File: %s\n", fileName));
            writer.write(String.format("\tReference: %d\n", result[0]));
            writer.write(String.format("\tSystem: %d\n", result[1]));
            writer.write(String.format("\tCorrect: %d\n", result[2]));
            writer.write(String.format("\tPrecision: %s\n", precision_percentage));
            writer.write(String.format("\tRecall: %s\n", recall_percentage));
            writer.write(String.format("\tF-score: %s\n", fscore_percentage));

            writer.write("\n");

        }

        writer.write("-------------------------\n");
        writer.write("Scores: \n");

        double precision = precision(correct_system_num, total_system_num);
        double recall = recall(correct_system_num, ref_num);
        double fscore = fmeasure(precision, recall);

        String precision_percentage = String.format("%.2f%%", (double) precision * 100);
        String recall_percentage = String.format("%.2f%%", (double) recall * 100);
        String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

        writer.write(String.format("\tTotal Reference: %d\n", ref_num));
        writer.write(String.format("\tTotal System: %d\n", total_system_num));
        writer.write(String.format("\tTotal Correct: %d\n", correct_system_num));

        writer.write("\n");

        writer.write(String.format("\tPrecision: %s\n", precision_percentage));
        writer.write(String.format("\tRecall: %s\n", recall_percentage));
        writer.write(String.format("\tF-score: %s\n", fscore_percentage));

        writer.close();

        System.out.println("");
        System.out.println("precision: " + precision_percentage);
        System.out.println("recall: " + recall_percentage);
        System.out.println("fscore: " + fscore_percentage);
        System.out.println("");
        System.out.println("finished!");
    }

    public static void evaluation_MALIGN(String testDir, String refDir, String systemDir, String outDir, String config) throws IOException {

        List<String> fileNameList = lab.alignment.data.vnu.process.GetFileList.getFileList(testDir);

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, config + ".txt")), Configs.ENCODING));
        writer.write("Evaluation: M-Align (Moore 2002):\n");
        writer.write(String.format("Config: %s\n", config));
        writer.write("-------------------------\n");

        int ref_num = 0;
        int total_system_num = 0;
        int correct_system_num = 0;

        for (String fileName : fileNameList) {

//            String srcRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_fr", "snt");
//            String trgRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_en", "snt");
//
//            String srcSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_fr", "snt.aligned");
//            String trgSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_en", "snt.aligned");
            String srcRefFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(refDir, fileName, "ref", "en", "snt");
            String trgRefFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(refDir, fileName, "ref", "vi", "snt");

            String srcSystemFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(systemDir, fileName, "test", "en", "snt.aligned");
            String trgSystemFile = lab.alignment.data.vnu.process.GetFileList.makeFilePath(systemDir, fileName, "test", "vi", "snt.aligned");

            int[] result = evaluation(srcSystemFile, trgSystemFile, srcRefFile, trgRefFile);

            ref_num += result[0];
            total_system_num += result[1];
            correct_system_num += result[2];

            int ref_num_local = result[0];
            int total_system_num_local = result[1];
            int correct_system_num_local = result[2];

            double precision = precision(correct_system_num_local, total_system_num_local);
            double recall = recall(correct_system_num_local, ref_num_local);
            double fscore = fmeasure(precision, recall);

            String precision_percentage = String.format("%.2f%%", (double) precision * 100);
            String recall_percentage = String.format("%.2f%%", (double) recall * 100);
            String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

            writer.write(String.format("File: %s\n", fileName));
            writer.write(String.format("\tReference: %d\n", result[0]));
            writer.write(String.format("\tSystem: %d\n", result[1]));
            writer.write(String.format("\tCorrect: %d\n", result[2]));
            writer.write(String.format("\tPrecision: %s\n", precision_percentage));
            writer.write(String.format("\tRecall: %s\n", recall_percentage));
            writer.write(String.format("\tF-score: %s\n", fscore_percentage));

            writer.write("\n");

        }

        writer.write("-------------------------\n");
        writer.write("Scores: \n");

        double precision = precision(correct_system_num, total_system_num);
        double recall = recall(correct_system_num, ref_num);
        double fscore = fmeasure(precision, recall);

        String precision_percentage = String.format("%.2f%%", (double) precision * 100);
        String recall_percentage = String.format("%.2f%%", (double) recall * 100);
        String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

        writer.write(String.format("\tTotal Reference: %d\n", ref_num));
        writer.write(String.format("\tTotal System: %d\n", total_system_num));
        writer.write(String.format("\tTotal Correct: %d\n", correct_system_num));

        writer.write("\n");

        writer.write(String.format("\tPrecision: %s\n", precision_percentage));
        writer.write(String.format("\tRecall: %s\n", recall_percentage));
        writer.write(String.format("\tF-score: %s\n", fscore_percentage));

        writer.close();

        System.out.println("");
        System.out.println("precision: " + precision_percentage);
        System.out.println("recall: " + recall_percentage);
        System.out.println("fscore: " + fscore_percentage);
        System.out.println("");
        System.out.println("finished!");
    }

    public static void evaluation_MALIGN_BAF(String testDir, String refDir, String systemDir, String outDir, String config) throws IOException {

        List<String> fileNameList = lab.alignment.data.vnu.process.GetFileList.getFileList(testDir);

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, config + ".txt")), Configs.ENCODING));
        writer.write("Evaluation: M-Align (Moore 2002):\n");
        writer.write(String.format("Config: %s\n", config));
        writer.write("-------------------------\n");

        int ref_num = 0;
        int total_system_num = 0;
        int correct_system_num = 0;

        for (String fileName : fileNameList) {

            String srcRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_fr", "snt");
            String trgRefFile = makeFilePath(refDir, fileName, "fr", "en", "tok_en", "snt");

            String srcSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_fr", "snt.aligned");
            String trgSystemFile = makeFilePath(systemDir, fileName, "fr", "en", "tok_en", "snt.aligned");

            int[] result = evaluation(srcSystemFile, trgSystemFile, srcRefFile, trgRefFile);

            ref_num += result[0];
            total_system_num += result[1];
            correct_system_num += result[2];

            int ref_num_local = result[0];
            int total_system_num_local = result[1];
            int correct_system_num_local = result[2];

            double precision = precision(correct_system_num_local, total_system_num_local);
            double recall = recall(correct_system_num_local, ref_num_local);
            double fscore = fmeasure(precision, recall);

            String precision_percentage = String.format("%.2f%%", (double) precision * 100);
            String recall_percentage = String.format("%.2f%%", (double) recall * 100);
            String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

            writer.write(String.format("File: %s\n", fileName));
            writer.write(String.format("\tReference: %d\n", result[0]));
            writer.write(String.format("\tSystem: %d\n", result[1]));
            writer.write(String.format("\tCorrect: %d\n", result[2]));
            writer.write(String.format("\tPrecision: %s\n", precision_percentage));
            writer.write(String.format("\tRecall: %s\n", recall_percentage));
            writer.write(String.format("\tF-score: %s\n", fscore_percentage));

            writer.write("\n");

        }

        writer.write("-------------------------\n");
        writer.write("Scores: \n");

        double precision = precision(correct_system_num, total_system_num);
        double recall = recall(correct_system_num, ref_num);
        double fscore = fmeasure(precision, recall);

        String precision_percentage = String.format("%.2f%%", (double) precision * 100);
        String recall_percentage = String.format("%.2f%%", (double) recall * 100);
        String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

        writer.write(String.format("\tTotal Reference: %d\n", ref_num));
        writer.write(String.format("\tTotal System: %d\n", total_system_num));
        writer.write(String.format("\tTotal Correct: %d\n", correct_system_num));

        writer.write("\n");

        writer.write(String.format("\tPrecision: %s\n", precision_percentage));
        writer.write(String.format("\tRecall: %s\n", recall_percentage));
        writer.write(String.format("\tF-score: %s\n", fscore_percentage));

        writer.close();

        System.out.println("");
        System.out.println("precision: " + precision_percentage);
        System.out.println("recall: " + recall_percentage);
        System.out.println("fscore: " + fscore_percentage);
        System.out.println("");
        System.out.println("finished!");
    }

    public static int[] evaluation(String srcSystemFile, String trgSystemFile, String srcRefFile, String trgRefFile) throws IOException {

        int[] result = new int[3];

        List<String> systemPairList = makeList(srcSystemFile, trgSystemFile);
        List<String> refPairList = makeList(srcRefFile, trgRefFile);

        int ref_num = refPairList.size();
        int total_system_num = systemPairList.size();
        int correct_system_num = match(refPairList, systemPairList);

        result[0] = ref_num;
        result[1] = total_system_num;
        result[2] = correct_system_num;

        return result;

//        
    }

    public static List<String> makeList(String srcFile, String trgFile) throws IOException {

        List<String> sentPairList = new ArrayList<>();

        List<String> srcList = Read.read2List(srcFile);
        List<String> trgList = Read.read2List(trgFile);

        if (srcList.size() == trgList.size()) {

            for (int i = 0; i < srcList.size(); i++) {
                String srcSent = srcList.get(i);
                String trgSent = trgList.get(i);

                sentPairList.add(srcSent.trim() + "___" + trgSent.trim());
            }

        }

//        for(String s:sentPairList){
//            System.out.println(s);
//        }
//        System.out.println("");
        return sentPairList;

    }

    public static int match(List<String> refList, List<String> systemList) {

        List<Integer> matchedList = new ArrayList<>();

        for (int id = 0; id < refList.size(); id++) {

            String refSent = refList.get(id);

            if (systemList.contains(refSent)) {
                if (!matchedList.contains(id)) {
                    matchedList.add(id);
//                    System.out.println(id);
//                    System.out.println(refSent);
//                    System.out.println("");
                }
            }

        }

        return matchedList.size();

    }

    public static double recall(int correct_system_num, int ref_num) {

        if (ref_num > 0) {
            return (double) correct_system_num / ref_num;
        } else {
            return 0;
        }

    }

    public static double precision(int correct_system_num, int total_system_num) {

        if (total_system_num > 0) {
            return (double) correct_system_num / total_system_num;
        } else {
            return 0;
        }

    }

    public static double fmeasure(double precision, double recall) {

        if (precision + recall > 0) {
            return (2 * precision * recall) / (precision + recall);
        } else {
            return 0;
        }

    }

    public static String makeFilePath(String inDir, String inputname, String srclang, String trglang, String other, String type) {
        StringBuilder name = new StringBuilder(100);
        name.append(inDir).append("/")
                .append(inputname).append(".")
                .append(srclang).append("-").append(trglang)
                .append(".").append(other).append(".").append(type);
        return name.toString();
    }

}
