/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.approaches.evs.baseline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
//import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.Scanner;
import lab.alignment.approaches.evs.length.IOData;
import lab.alignment.approaches.evs.length.SentLengthRatio;
import lab.alignment.configs.Configs;
import lab.alignment.configs.Configs_vnu;
import lab.alignment.data.vnu.process.GetFileList;

/**
 *
 * @author s1420211
 */
public class LengthBased_Phase {

    public static void length_based(String srcFile, String trgFile, double threshold) {

        System.out.println("LENGTH BASED PHASE:");
        System.out.println("");

        String fileName = new File(srcFile).getName();
        String prefixName = fileName.substring(0, fileName.length() - 7);
        String nodeFile = prefixName + ".node";
        String srcOutFile = new File(srcFile).getName() + ".length-based";
        String trgOutFile = new File(trgFile).getName() + ".length-based";

        length_based(srcFile, trgFile, threshold, nodeFile, srcOutFile, trgOutFile);

    }

    public static void length_based2(String inDir, String srclang, String trglang, double threshold, String outDir) {

        System.out.println("LENGTH BASED PHASE:");
        System.out.println("");

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        Set<String> fileNameSet = getFileNameSet(inDir);

        for (String prefixName : fileNameSet) {
            

            String srcInFile = inDir+"/"+String.format("%s_%s.snt", prefixName, srclang);
            String trgInFile = inDir+"/"+String.format("%s_%s.snt", prefixName, trglang);
            String nodeFile = outDir+"/"+prefixName + ".node";
            String srcOutFile = outDir+"/"+String.format("%s_%s.length-based", prefixName, srclang);
            String trgOutFile = outDir+"/"+String.format("%s_%s.length-based", prefixName, trglang);
            
            length_based(srcInFile, trgInFile, threshold, nodeFile, srcOutFile, trgOutFile);

        }

    }

    public static Set<String> getFileNameSet(String inDir) {

        Set<String> fileNameSet = new HashSet<>();

        for (File file : new File(inDir).listFiles()) {
            String fileName = file.getName();
            String prefixName = fileName.substring(0, fileName.length() - 7);
            fileNameSet.add(prefixName);
        }

        return fileNameSet;

    }

    public static void length_based(String testDir, double threshold, String outDir) {

        System.out.println("LENGTH BASED PHASE:");
        System.out.println("");

        List<String> fileNameList = lab.alignment.data.vnu.process.GetFileList.getFileList(testDir);

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        for (String fileName : fileNameList) {

            String srcInFile = GetFileList.makeFilePath(testDir, fileName, "test", "en", "snt");
            String trgInFile = GetFileList.makeFilePath(testDir, fileName, "test", "vi", "snt");

            String nodeFile = GetFileList.makeFilePath(outDir, fileName, "length-based", "en-vi", "node");

            String srcOutFile = GetFileList.makeFilePath(outDir, fileName, "test", "en", "length");
            String trgOutFile = GetFileList.makeFilePath(outDir, fileName, "test", "vi", "length");

            length_based(srcInFile, trgInFile, threshold, nodeFile, srcOutFile, trgOutFile);

        }

    }

    public static void length_based(
            String srcInFile,
            String trgInFile,
            double threshold,
            String nodeOutFile,
            String srcOutFile,
            String trgOutFile
    //            ,
    //            double threshold1, 
    //            int limit
    ) {

        lab.alignment.approaches.evs.length.LengthBased c = new lab.alignment.approaches.evs.length.LengthBased();
        IOData a1 = new IOData();
        IOData a2 = new IOData();
        SentLengthRatio b1 = new SentLengthRatio();
        //a1.Initial(NewFolder,Directory,SourceFile);
        //a2.Initial(NewFolder,Directory,TargetFile);

//        a1.SetIODataComplex(inDir + "/" + SourceFile);
//        a2.SetIODataComplex(inDir + "/" + TargetFile);
        a1.SetIODataComplex(srcInFile);
        a2.SetIODataComplex(trgInFile);

        b1.ComputeSentLengthRatio(a1, a2);

        c.Init(a1, a2, b1);
//        c.Forward_Backward(limit, threshold1);
        c.Forward_Backward(Configs_vnu.EVS.LENGTH_BASED.SEARCHING_LIMIT, threshold);

        c.writeNode(nodeOutFile);

        writeLengthBased(c.getBead1(), c.getBead2(), srcInFile, trgInFile, srcOutFile, trgOutFile);

    }

    public static void writeLengthBased(
            Map<Integer, Double> bead1,
            Map<Integer, Double> bead2,
            //            String InputDirectory, String ResultDirectory, String Aligned,
            //            String InputSourceFile, String InputTargetFile, String Out
            String srcInFile,
            String trgInFile,
            String srcOutFile,
            String trgOutFile
    ) {

        int ns1 = 0;
        try {
//            FileInputStream is = new FileInputStream(InputDirectory + "/" + InputSourceFile);
//            Scanner input = new Scanner(is);

//            FileOutputStream outf = new FileOutputStream(Aligned + "/" + InputSourceFile + Out);
//            PrintWriter output = new PrintWriter(outf, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcInFile)), Configs.ENCODING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));

            String line;

            while ((line = reader.readLine()) != null) {

//            while (input.hasNextLine()) {
//                String line = input.nextLine();
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
//            FileInputStream is = new FileInputStream(InputDirectory + "/" + InputTargetFile);
//            Scanner input = new Scanner(is);
//            FileOutputStream outf = new FileOutputStream(Aligned + "/" + InputTargetFile + Out);
//            PrintWriter output = new PrintWriter(outf, true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgInFile)), Configs.ENCODING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

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
