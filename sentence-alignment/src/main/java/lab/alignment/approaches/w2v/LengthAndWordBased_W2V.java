/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.approaches.w2v;

import lab.alignment.approaches.evs.baseline.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import lab.alignment.approaches.evs.length.IOData;
import lab.alignment.approaches.evs.length.SentLengthRatio;
import lab.alignment.approaches.evs.length_and_word.CountWords;
import lab.alignment.approaches.evs.length_and_word.CountWords_W2V;
import lab.alignment.approaches.evs.length_and_word.LengthAndWordBased;
//import lab.alignment.approaches.evs.length_and_word.CountWords_W2;
//import lab.alignment.approaches.w2v.CountWords;
//import lab.alignment.approaches.evs.length_and_word.LengthAndWordBased;
import lab.alignment.approaches.evs.length_and_word.ReadIBM;
import lab.alignment.configs.Configs;
import lab.alignment.configs.Configs_evs;
import lab.alignment.data.vnu.process.GetFileList;

/**
 *
 * @author s1420211
 */
public class LengthAndWordBased_W2V {

    public static void oovVocab(
            String testDir,
            String lengthDir,
            String ibmModelFile,
            //            double threshold,
            //            String alignedDir
            //            String srcInFile,
            //            String trgInFile,
            //            String nodeFile,
            //            String srcOutFile,
            //            String trgOutFile
            String outDir,
            String oovEnFile,
            String oovViFile
    ) throws IOException{

        ReadIBM ibm1Model = new ReadIBM();
        ibm1Model.readIBM1(ibmModelFile);

        Set<String> ibmEnSet = new HashSet<>();
        Set<String> ibmViSet = new HashSet<>();

        for (String en : ibm1Model.getModE().keySet()) {
            ibmEnSet.add(en);
        }

        for (String vi : ibm1Model.getModV().keySet()) {
            ibmViSet.add(vi);
        }

        System.out.println("ibm: en vocab=" + ibmEnSet.size());
        System.out.println("ibm: vi vocab=" + ibmViSet.size());

        System.out.println("LENGTH BASED PHASE:");
        System.out.println("");

        List<String> fileNameList = lab.alignment.data.vnu.process.GetFileList.getFileList(testDir);

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

//        int othern1=0;
//        int othern2=0;
//        double oov1=0;
//        double oov2=0;
        int nfile = 0;

        Set<String> oovEnSet = new HashSet<>();
        Set<String> oovViSet = new HashSet<>();

        for (String fileName : fileNameList) {

            nfile++;

            String srcInFile = GetFileList.makeFilePath(testDir, fileName, "test", "en", "snt");
            String trgInFile = GetFileList.makeFilePath(testDir, fileName, "test", "vi", "snt");

            String nodeFile = GetFileList.makeFilePath(lengthDir, fileName, "length-based", "en-vi", "node");

//            String srcOutFile = GetFileList.makeFilePath(alignedDir, fileName, "test", "en", "aligned");
//            String trgOutFile = GetFileList.makeFilePath(alignedDir, fileName, "test", "vi", "aligned");
            CountWords w = new CountWords();

//            lengthAndWordBased(ibm1Model, srcInFile, trgInFile, w, nodeFile, threshold, srcOutFile, trgOutFile);
            getOtherSet(ibm1Model, srcInFile, trgInFile, w);

            for (String en : w.getEnOtherSet()) {
                oovEnSet.add(en);
            }

            for (String vi : w.getViOtherSet()) {
                oovViSet.add(vi);
            }

//            int othern1=w.getOthern1();
//            int othern2=w.getOthern2();                        
//            int vocabn1=w.getVocabn1();
//            int vocabn2=w.getVocabn2();
//            
//            oov1+=(double)othern1/vocabn1;
//            oov2+=(double)othern2/vocabn2;  
//            
//            System.out.println("");
//            System.out.println("other 1: "+othern1);
//            System.out.println("vocab 1: "+vocabn1);
//            System.out.println("oov1: "+(double)othern1/vocabn1);
//            System.out.println("other 2: "+othern2);
//            System.out.println("vocab 2: "+vocabn2);
//            System.out.println("oov2: "+(double)othern2/vocabn2);            
//            System.out.println("");
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(oovEnFile)), Configs.ENCODING));

        for (String en : oovEnSet) {
            writer.write(en);
            writer.write("\n");
        }
        
        writer.close();
        
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(oovViFile)), Configs.ENCODING));

        for (String vi : oovViSet) {
            writer.write(vi);
            writer.write("\n");
        }
        
        writer.close();
        
        
        

//        System.out.println("avg oov1: "+(oov1/nfile));
//        System.out.println("avg oov2: "+(oov2/nfile));        
    }

    public static void getOtherSet(
            ReadIBM r,
            String srcInFile,
            String trgInFile,
            CountWords w
    //            String nodeFile,
    //            double threshold,
    //            String srcOutFile,
    //            String trgOutFile
    ) {

//        LengthAndWordBased Ass = new LengthAndWordBased();
        IOData a1 = new IOData();
        IOData a2 = new IOData();
        SentLengthRatio b1 = new SentLengthRatio();
//        CountWords w = new CountWords();

        a1.SetIODataComplex(srcInFile);
        a2.SetIODataComplex(trgInFile);

        b1.ComputeSentLengthRatio(a1, a2);
        w.SetWord2(srcInFile, trgInFile);
        w.CountWord(a1, a2, r);

//        Ass.SentScore(a1, a2, w);
//        Ass.ReadNode(nodeFile, a1.getNs(), a2.getNs());
//        Ass.InitPlus(a1, a2, b1, r);
//
//        Ass.Forward_Backward(
//                Configs_evs.LENGTH_WORD_BASED.SEARCHING_LIMIT,
////                Configs_evs.LENGTH_WORD_BASED.THRESHOLD
//                threshold
//        );
//
//        writeComplex(Ass.getBead1(), Ass.getBead2(), srcInFile, trgInFile, srcOutFile, trgOutFile);
    }

    public static void lengthAndWordBased(
            ReadIBM r,
            String srcInFile,
            String trgInFile,
            CountWords_W2V w,
            String nodeFile,
            double threshold,
            String srcOutFile,
            String trgOutFile
    ) {

        LengthAndWordBased Ass = new LengthAndWordBased();
        IOData a1 = new IOData();
        IOData a2 = new IOData();
        SentLengthRatio b1 = new SentLengthRatio();
//        CountWords w = new CountWords();

        a1.SetIODataComplex(srcInFile);
        a2.SetIODataComplex(trgInFile);

        b1.ComputeSentLengthRatio(a1, a2);

        w.SetWord2(srcInFile, trgInFile);
        w.CountWord(a1, a2, r);

        Ass.SentScore(a1, a2, w);
        Ass.ReadNode(nodeFile, a1.getNs(), a2.getNs());
        Ass.InitPlus(a1, a2, b1, r);

        Ass.Forward_Backward(
                Configs_evs.LENGTH_WORD_BASED.SEARCHING_LIMIT,
                //                Configs_evs.LENGTH_WORD_BASED.THRESHOLD
                threshold
        );

        writeComplex(Ass.getBead1(), Ass.getBead2(), srcInFile, trgInFile, srcOutFile, trgOutFile);
    }

    public static void writeComplex(Map<Integer, Double> bead1, Map<Integer, Double> bead2,
            //            String InputDirectory, String Aligned,
            //            String InputSourceFile, String InputTargetFile, String Out

            String srcInFile, String trgInFile,
            String srcOutFile, String trgOutFile
    ) {

        int ns1 = 0;
        try {
//            FileInputStream is = new FileInputStream(srcInFile);
//            Scanner input = new Scanner(is);

//            FileOutputStream outf = new FileOutputStream(srcOutFile);
//            PrintWriter output = new PrintWriter(outf, true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcInFile)), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {

//                while (input.hasNextLine()) {
//                    String line = input.nextLine();
                if (bead1.containsKey(ns1)) {

//                    output.println(line);
                    writer.write(line);
                    writer.write("\n");

                }
                ns1++;
            }

//            is.close();
//            input.close();
//            outf.close();
//            output.close();
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Write to Target File
        int ns2 = 0;
        try {

//            FileInputStream is = new FileInputStream(trgInFile);
//            Scanner input = new Scanner(is);
//            FileOutputStream outf = new FileOutputStream(trgOutFile);
//            PrintWriter output = new PrintWriter(outf, true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgInFile)), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {

//            while (input.hasNextLine()) {
//                String line = input.nextLine();
                if (bead2.containsKey(ns2)) {

//                    output.println(line);
                    writer.write(line);
                    writer.write("\n");
                }
                ns2++;
            }
//            is.close();
//            input.close();
//            outf.close();
//            output.close();
            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
