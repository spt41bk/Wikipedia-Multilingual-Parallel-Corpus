/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import lab.alignment.approaches.evs.baseline.*;
import lab.alignment.configs.Configs_evs;
import lab.alignment.configs.Configs_evs_big;
import lab.alignment.configs.Configs_vnu;

/**
 *
 * @author s1420211
 */
public class Sentence_Aligner {

    public static void main(String[] args) throws IOException {

        String config = "";

        if (args.length > 0) {
            config = args[0];
        }
        //help-----------------------------------------------------------------
        if (config.matches("help")) {

            help();
        } //train-full
        else if (config.matches("train-full")) {
            String srcFile = args[1];
            String trgFile = args[2];
            double threshold1 = Double.parseDouble(args[3]);
            double threshold2 = Double.parseDouble(args[4]);
            int searchinglimit = Integer.parseInt(args[5]);
            train_full(srcFile, trgFile, threshold1, threshold2, searchinglimit);

        } //length-based------------------------------------------------------
        else if (config.matches("length-based")) {
            String srcFile = args[1];
            String trgFile = args[2];
            double threshold = Double.parseDouble(args[3]);
            lengthBasedPhase(srcFile, trgFile, threshold);

        } //ibm1--------------------------------------------------------------
        else if (config.matches("ibm1")) {
            String srcFile = args[1];
            String trgFile = args[2];
            int loop = Integer.parseInt(args[3]);
            train_IBM1(srcFile, trgFile, loop);
        } //
        else if (config.matches("length-and-word-based")) {
            String srcFile = args[1];
            String trgFile = args[2];
            double threshold = Double.parseDouble(args[3]);
            int searchinglimit = Integer.parseInt(args[4]);
            lengthAndWordBasedPhase(srcFile, trgFile, threshold, searchinglimit);
        } //
        else if (config.matches("length-based-folder")) {
            String inDir = args[1];
            String srclang = args[2];
            String trglang = args[3];
            double threshold = Double.parseDouble(args[4]);
            lengthBasedPhase_Folder(inDir, srclang, trglang, threshold);
        } //
        else if (config.matches("ibm1-folder")) {
            int loop = Integer.parseInt(args[1]);
            ibm1_Folder(loop);
        } //
        else if (config.matches("length-and-word-based-folder")) {
            double threshold = Double.parseDouble(args[1]);
            int searchinglimit = Integer.parseInt(args[2]);
            String outDir = args[3];
            lengthAndWordBasedPhase_Folder(threshold, searchinglimit, outDir);
        } //
        //train-full-folder
        else if (config.matches("train-full-folder")) {
            String inDir = args[1];
            String srclang = args[2];
            String trglang = args[3];
            double threshold1 = Double.parseDouble(args[4]);
            double threshold2 = Double.parseDouble(args[5]);
            int searchinglimit = Integer.parseInt(args[6]);
            String outDir = args[7];
            train_full_folder(inDir, srclang, trglang, threshold1, threshold2, searchinglimit, outDir);

        } //
        else {
            help();
        }

    }

    public static void ibm1_Folder(int loop) throws IOException {

        String inDir = "." + "/data";
        String lengthBasedDir = "." + "/length-based-phase";
        String ibm1Dir = "." + "/ibm1";
        String srclang = "en";
        String trglang = "vi";
        //merge---
        lab.alignment.approaches.evs.baseline.Training_IBM1.mergeLengthBasedFiles(inDir, lengthBasedDir, srclang, trglang, ibm1Dir);
        //train---
        String srcTrainFile = ibm1Dir + "/length-based." + srclang;
        String trgTrainFile = ibm1Dir + "/length-based." + trglang;
        train_IBM1(srcTrainFile, trgTrainFile, loop, ibm1Dir);
    }

    public static void lengthBasedPhase_Folder(String inDir, String srclang, String trglang, double threshold) {

        String outDir = "." + "/length-based-phase";

        lab.alignment.approaches.evs.baseline.LengthBased_Phase.length_based2(
                inDir,
                srclang,
                trglang,
                threshold,
                outDir
        );

    }

    public static void lengthAndWordBasedPhase_Folder(double threshold2, int searchinglimit, String outDir) {

        String ibm1File = "./ibm1/ibm1.model";
        String lengthBasedDir = "." + "/length-based-phase";
        String srclang = "en";
        String trglang = "vi";
        String inDir = "." + "/data";

        lab.alignment.approaches.evs.baseline.LengthAndWordBased_Phase.lengthAndWordBased2(inDir, ibm1File, srclang, trglang, lengthBasedDir, threshold2, searchinglimit, outDir);
    }

    public static void train_full(String srcFile, String trgFile, double threshold1, double threshold2, int searchinglimit) {

        String srcLengthOutFile = new File(srcFile).getName() + ".length-based";
        String trgLengthOutFile = new File(trgFile).getName() + ".length-based";
        int loop = 3;

        lab.alignment.approaches.evs.baseline.LengthBased_Phase.length_based(srcFile, trgFile, threshold1);
        lab.alignment.approaches.evs.baseline.Training_IBM1.build_IBM1v2(srcLengthOutFile, trgLengthOutFile, loop, ".");
        lab.alignment.approaches.evs.baseline.LengthAndWordBased_Phase.lengthAndWordBased2(srcFile, trgFile, threshold2, searchinglimit);

    }

    public static void train_full_folder(String inDir, String srclang, String trglang, double threshold1, double threshold2, int searchinglimit, String outDir) throws IOException {

        //length-based
        String lengthBasedDir = "." + "/length-based-phase";
        lab.alignment.approaches.evs.baseline.LengthBased_Phase.length_based2(inDir, srclang, trglang, threshold1, lengthBasedDir);
        //ibm1
        int loop = 3;
        String ibm1Dir = "." + "/ibm1";
        String srcTrainFile = ibm1Dir + "/length-based." + srclang;
        String trgTrainFile = ibm1Dir + "/length-based." + trglang;
        //
        lab.alignment.approaches.evs.baseline.Training_IBM1.mergeLengthBasedFiles(inDir, lengthBasedDir, srclang, trglang, ibm1Dir);
        train_IBM1(srcTrainFile, trgTrainFile, loop, ibm1Dir);
        //
        String ibm1File = ibm1Dir + "/ibm1.model";
        lab.alignment.approaches.evs.baseline.LengthAndWordBased_Phase.lengthAndWordBased2(inDir, ibm1File, srclang, trglang, lengthBasedDir, threshold2, searchinglimit, outDir);

    }

    public static void lengthAndWordBasedPhase(String srcFile, String trgFile, double threshold, int searchinglimit) {

        lab.alignment.approaches.evs.baseline.LengthAndWordBased_Phase.lengthAndWordBased2(srcFile, trgFile, threshold, searchinglimit);

    }

    public static void train_IBM1(String srcFile, String trgFile, int loop, String outDir) {
        lab.alignment.approaches.evs.baseline.Training_IBM1.build_IBM1v2(
                srcFile, trgFile, loop, outDir
        );
    }

    public static void train_IBM1(String srcFile, String trgFile, int loop) {
        lab.alignment.approaches.evs.baseline.Training_IBM1.build_IBM1(
                srcFile, trgFile, loop
        );
    }

    public static void lengthBasedPhase(String srcFile, String trgFile, double threshold) {
        lab.alignment.approaches.evs.baseline.LengthBased_Phase.length_based(srcFile, trgFile, threshold);
    }

    public static void help() throws IOException {

        Help.help("readme.txt");

    }

}
