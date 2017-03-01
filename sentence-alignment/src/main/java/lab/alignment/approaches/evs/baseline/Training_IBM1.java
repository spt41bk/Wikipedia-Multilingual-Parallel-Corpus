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
import java.util.List;
import java.util.Set;
import static lab.alignment.approaches.evs.baseline.LengthBased_Phase.getFileNameSet;
import lab.alignment.configs.Configs;
import lab.alignment.configs.Configs_evs;
import lab.alignment.data.vnu.process.GetFileList;

/**
 *
 * @author s1420211
 */
public class Training_IBM1 {

    public static void build_IBM1v2(String srcTrainFile, String trgTrainFile, int loop, String outDir) {

        System.out.println("Building IBM1...");
        lab.alignment.approaches.evs.ibm1.IBM1.buildModelOne(
                srcTrainFile,
                trgTrainFile,
                loop,
                outDir+"/ibm1.model"
        );

        System.out.println("success!");
        System.out.println("-----------------------------------------");
        System.out.println("");

    }
    
    public static void build_IBM1(String srcTrainFile, String trgTrainFile, int loop) {

        System.out.println("Building IBM1...");
        lab.alignment.approaches.evs.ibm1.IBM1.buildModelOne(
                srcTrainFile,
                trgTrainFile,
                loop,
                "ibm1.model"
        );

        System.out.println("success!");
        System.out.println("-----------------------------------------");
        System.out.println("");

    }

    public static void build_IBM1(String srcTrainFile, String trgTrainFile, int loop, String modelFile) {

        System.out.println("Building IBM1...");
        lab.alignment.approaches.evs.ibm1.IBM1.buildModelOne(
                srcTrainFile,
                trgTrainFile,
                loop,
                modelFile
        );

        System.out.println("success!");
        System.out.println("-----------------------------------------");
        System.out.println("");

    }

    public static void mergeLengthBasedFiles(String inDir, String lengthBasedDir, String srclang, String trglang, String ibm1Dir) throws IOException {

        System.out.println("IBM1, making training data:");
        System.out.println("");

        if (!new File(ibm1Dir).exists()) {
            new File(ibm1Dir).mkdirs();
        }

        BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(ibm1Dir, "length-based." + srclang)), Configs.ENCODING));
        BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(ibm1Dir, "length-based." + trglang)), Configs.ENCODING));

        Set<String> fileNameSet = getFileNameSet(inDir);
        BufferedReader reader;
        int nSrcSents = 0;
        int nTrgSents = 0;

        for (String prefixName : fileNameSet) {
            String srcLengthOutFile = lengthBasedDir + "/" + String.format("%s_%s.length-based", prefixName, srclang);
            String trgLengthOutFile = lengthBasedDir + "/" + String.format("%s_%s.length-based", prefixName, trglang);

            //
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcLengthOutFile)), Configs.ENCODING));
            String line;
            while ((line = reader.readLine()) != null) {
                srcWriter.write(line);
                srcWriter.write("\n");
                nSrcSents++;
            }
            reader.close();
            //
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgLengthOutFile)), Configs.ENCODING));
            while ((line = reader.readLine()) != null) {
                trgWriter.write(line);
                trgWriter.write("\n");
                nTrgSents++;
            }
            reader.close();
            //

        }
        srcWriter.close();
        trgWriter.close();
        //
        System.out.println("src sents: " + nSrcSents);
        System.out.println("trg sents: " + nTrgSents);
        System.out.println("--------------");
        System.out.println("");
    }

    public static void makeTrainingData(String testDir, String inDir, String outDir, String srcOutFile, String trgOutFile) throws IOException {

        List<String> fileNameList = GetFileList.getFileList(testDir);

        makeTrainingData(fileNameList, inDir, outDir, srcOutFile, trgOutFile);

    }

    public static void makeTrainingData_Big(String testDir, String trainDir, String lengthDir1, String lengthDir2, String outDir, String srcOutFile, String trgOutFile) throws IOException {

        List<String> fileNameList = GetFileList.getFileList(testDir);

        List<String> bigFileNameList = GetFileList.getFileList(trainDir);

//        for (String fileName : bigFileNameList) {
//            fileNameList.add(fileName);
//        }
        makeTrainingData(fileNameList, bigFileNameList, lengthDir1, lengthDir2, outDir, srcOutFile, trgOutFile);

    }

    public static void makeTrainingData(List<String> fileNameList, List<String> bigFileNameList, String lengthDir1, String lengthDir2, String outDir, String srcOutFile, String trgOutFile) throws IOException {

        System.out.println("TRAINING IBM MODEL1:");
        System.out.println("");

        lab.alignment.utils.FileIO.mkdirs(outDir);

        BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));
        BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

//        List<String> fileNameList = GetFileList.getFileList(testDir);
        int srcsents = 0;
        int trgsents = 0;

        for (String fileName : fileNameList) {

            String srcLengthFile = GetFileList.makeFilePath(lengthDir1, fileName, "test", "en", "length");
            String trgLengthFile = GetFileList.makeFilePath(lengthDir1, fileName, "test", "vi", "length");

            //write source file
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcLengthFile)), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {

                srcWriter.write(line);
                srcWriter.write("\n");
                srcsents++;
            }

            reader.close();

            //write target file
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgLengthFile)), Configs.ENCODING));

            while ((line = reader.readLine()) != null) {
                trgWriter.write(line);
                trgWriter.write("\n");
                trgsents++;
            }

            reader.close();
            //

        }

        for (String fileName : bigFileNameList) {

            String srcLengthFile = GetFileList.makeFilePath(lengthDir2, fileName, "test", "en", "length");
            String trgLengthFile = GetFileList.makeFilePath(lengthDir2, fileName, "test", "vi", "length");

            //write source file
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcLengthFile)), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {

                srcWriter.write(line);
                srcWriter.write("\n");
                srcsents++;
            }

            reader.close();

            //write target file
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgLengthFile)), Configs.ENCODING));

            while ((line = reader.readLine()) != null) {
                trgWriter.write(line);
                trgWriter.write("\n");
                trgsents++;
            }

            reader.close();
            //

        }

        srcWriter.close();
        trgWriter.close();

        System.out.println("Make training file to train ibm1:");
        System.out.println("src sents: " + srcsents);
        System.out.println("trg sents: " + trgsents);
        System.out.println("success!");
        System.out.println("-----------------------------------------");
        System.out.println("");
    }

    public static void makeTrainingData(List<String> fileNameList, String lengthDir, String outDir, String srcOutFile, String trgOutFile) throws IOException {

        System.out.println("TRAINING IBM MODEL1:");
        System.out.println("");

        lab.alignment.utils.FileIO.mkdirs(outDir);

        BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));
        BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

//        List<String> fileNameList = GetFileList.getFileList(testDir);
        int srcsents = 0;
        int trgsents = 0;

        for (String fileName : fileNameList) {

            String srcLengthFile = GetFileList.makeFilePath(lengthDir, fileName, "test", "en", "length");
            String trgLengthFile = GetFileList.makeFilePath(lengthDir, fileName, "test", "vi", "length");

            //write source file
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcLengthFile)), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {

                srcWriter.write(line);
                srcWriter.write("\n");
                srcsents++;
            }

            reader.close();

            //write target file
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgLengthFile)), Configs.ENCODING));

            while ((line = reader.readLine()) != null) {
                trgWriter.write(line);
                trgWriter.write("\n");
                trgsents++;
            }

            reader.close();
            //

        }

        srcWriter.close();
        trgWriter.close();

        System.out.println("Make training file to train ibm1:");
        System.out.println("src sents: " + srcsents);
        System.out.println("trg sents: " + trgsents);
        System.out.println("success!");
        System.out.println("-----------------------------------------");
        System.out.println("");
    }

}
