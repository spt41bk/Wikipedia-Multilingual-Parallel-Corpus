/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import lab.alignment.configs.Configs;
import static lab.alignment.evaluation.Evaluation.evaluation;
import static lab.alignment.evaluation.Evaluation.fmeasure;
import static lab.alignment.evaluation.Evaluation.precision;
import static lab.alignment.evaluation.Evaluation.recall;

/**
 *
 * @author s1420211
 */
public class HunAlign {

    public static void evaluation(String srcOutFile, String trgOutFile, String srcRefFile, String trgRefFile, String outDir, String config) throws IOException {

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, config + ".txt")), Configs.ENCODING));
        writer.write("Evaluation: Hun-Align:\n");
        writer.write(String.format("Config: %s\n", config));
        writer.write("-------------------------\n");

        int[] result = lab.alignment.evaluation.Evaluation.evaluation(srcOutFile, trgOutFile, srcRefFile, trgRefFile);

        int ref_num_local = result[0];
        int total_system_num_local = result[1];
        int correct_system_num_local = result[2];

        double precision = precision(correct_system_num_local, total_system_num_local);
        double recall = recall(correct_system_num_local, ref_num_local);
        double fscore = fmeasure(precision, recall);

        String precision_percentage = String.format("%.2f%%", (double) precision * 100);
        String recall_percentage = String.format("%.2f%%", (double) recall * 100);
        String fscore_percentage = String.format("%.2f%%", (double) fscore * 100);

        writer.write(String.format("\tReference: %d\n", result[0]));
        writer.write(String.format("\tSystem: %d\n", result[1]));
        writer.write(String.format("\tCorrect: %d\n", result[2]));
        writer.write(String.format("\tPrecision: %s\n", precision_percentage));
        writer.write(String.format("\tRecall: %s\n", recall_percentage));
        writer.write(String.format("\tF-score: %s\n", fscore_percentage));

        writer.close();

    }

    public static void postAligned(String inFile, String srcOutFile, String trgOutFile) throws IOException {

        BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));
        BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;
        int nline = 0;
        int nsaved = 0;
        while ((line = reader.readLine()) != null) {
            //
            nline++;

            String st[] = line.trim().split("\t");

            if (st.length == 3) {
                nsaved++;

                srcWriter.write(st[0]);
                srcWriter.write("\n");

                trgWriter.write(st[1]);
                trgWriter.write("\n");
            }

        }
        srcWriter.close();
        trgWriter.close();

        reader.close();

        System.out.println("lines: " + nline);
        System.out.println("saved: " + nsaved);

    }

}
