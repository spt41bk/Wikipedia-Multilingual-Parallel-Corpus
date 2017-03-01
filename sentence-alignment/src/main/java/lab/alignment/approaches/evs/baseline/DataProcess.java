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
import java.nio.file.Files;
import java.util.List;
import lab.alignment.configs.Configs;
import lab.alignment.data.vnu.process.GetFileList;

/**
 *
 * @author s1420211
 */
public class DataProcess {

    public static void mergeTestFiles(String inDir, String srcOutFile, String trgOutFile) throws IOException {

        BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));
        BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

        List<String> fileNameList = GetFileList.getFileList(inDir);

        for (String fileName : fileNameList) {

            String srcInFile = GetFileList.makeFilePath(inDir, fileName, "test", "en", "snt");
            String trgInFile = GetFileList.makeFilePath(inDir, fileName, "test", "vi", "snt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcInFile)), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {
                //
                srcWriter.write(line);
                srcWriter.write("\n");
            }

            reader.close();

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgInFile)), Configs.ENCODING));

            while ((line = reader.readLine()) != null) {
                //
                trgWriter.write(line);
                trgWriter.write("\n");
            }

            reader.close();

        }

        srcWriter.close();
        trgWriter.close();

    }

    public static void mergeRefFiles(String testDir, String inDir, String srcOutFile, String trgOutFile) throws IOException {

        BufferedWriter srcWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(srcOutFile)), Configs.ENCODING));
        BufferedWriter trgWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(trgOutFile)), Configs.ENCODING));

        List<String> fileNameList = GetFileList.getFileList(testDir);

        for (String fileName : fileNameList) {

            String srcInFile = GetFileList.makeFilePath(inDir, fileName, "ref", "en", "snt");
            String trgInFile = GetFileList.makeFilePath(inDir, fileName, "ref", "vi", "snt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcInFile)), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {
                //
                srcWriter.write(line);
                srcWriter.write("\n");
            }

            reader.close();

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(trgInFile)), Configs.ENCODING));

            while ((line = reader.readLine()) != null) {
                //
                trgWriter.write(line);
                trgWriter.write("\n");
            }

            reader.close();

        }

        srcWriter.close();
        trgWriter.close();

    }

    
    public static void copyFile(String inDir, String outDir) throws IOException {

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        for (File inFile : new File(inDir).listFiles()) {

            String fileName = inFile.getName();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outDir, fileName)), Configs.ENCODING));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), Configs.ENCODING));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n");
            }
            writer.close();
            reader.close();

        }

    }

    public static void changeTestingFileName_Test(String inDir, String outDir) throws IOException {

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        for (File file : new File(inDir).listFiles()) {
            String fileName = file.getName();

            String id = fileName.substring(0, fileName.length() - 7);
            String newid = Integer.valueOf(id) + 34000 + "";

            String newName = "";
            if (fileName.contains("a_e")) {
                //                newName = fileName.replace("a_e", ".test_en");
                newName = newid + ".test_en.snt";
            }
            if (fileName.contains("a_v")) {
//                newName = fileName.replace("a_v", ".test_vi.snt");
                newName = newid + ".test_vi.snt";
            }
            Files.copy(file.toPath(), new File(outDir, newName).toPath());

        }

    }

    public static void changeTestingFileName_Ref(String inDir, String outDir) throws IOException {

        if (!new File(outDir).exists()) {
            new File(outDir).mkdirs();
        }

        for (File file : new File(inDir).listFiles()) {
            String fileName = file.getName();

            String id = fileName.substring(0, fileName.length() - 7);
            String newid = Integer.valueOf(id) + 34000 + "";

            String newName = "";
            if (fileName.contains("a_e")) {
                //                newName = fileName.replace("a_e", ".test_en");
                newName = newid + ".ref_en.snt";
            }
            if (fileName.contains("a_v")) {
//                newName = fileName.replace("a_v", ".test_vi.snt");
                newName = newid + ".ref_vi.snt";
            }
            Files.copy(file.toPath(), new File(outDir, newName).toPath());

        }

    }

}
