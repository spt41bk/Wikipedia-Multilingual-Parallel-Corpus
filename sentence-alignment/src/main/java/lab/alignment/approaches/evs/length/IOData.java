package lab.alignment.approaches.evs.length;

import java.util.*;
import java.util.Scanner;
import java.io.*;
import lab.alignment.configs.Configs;

/*
 author Trieu Hai int
 *K17CS - UET
 */
public class IOData {

    private int ns;
    private Map<Integer, Integer> sentLength = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> countLength = new HashMap<Integer, Integer>();

    private Map<String, Integer> countVocab = new HashMap<String, Integer>();
    private int nword;
    private int nvocab;

    public void setNs(int n) {
        ns = n;
    }

    public void setNword(int n) {
        nword = n;
    }

    public int getNword() {
        return nword;
    }

    public int getNs() {
        return ns;
    }

    public Map<Integer, Integer> getSentLength() {
        return sentLength;
    }

    public Map<Integer, Integer> getCountLength() {
        return countLength;
    }

    public Map<String, Integer> getCountVocab() {
        return countVocab;
    }

    public void setCountVocab(Map<String, Integer> countVocab) {
        this.countVocab = countVocab;
    }

    public int getNvocab() {
        return nvocab;
    }

    public void setNvocab(int nvocab) {
        this.nvocab = nvocab;
    }
    
    

    public void SetIODataComplex(String inFile) {
//        System.out.println("Reading files for length-based:");
//        System.out.println("------------------------------");
        int ns = 0, nw = 0;
        try {
//            FileInputStream is=new FileInputStream(inFile);
//            Scanner input=new Scanner(is);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));
            String line;
            while ((line = reader.readLine()) != null) {

//            while(input.hasNextLine()) {
//                String line=input.nextLine();
                StringTokenizer words;
                words = new StringTokenizer(line);
                int count = 0;
                while (words.hasMoreTokens()) {
                    String s = words.nextToken();
                    count++;
                    nw++;
                    countVocab.put(s, null);
                }
                ns++;
                sentLength.put(ns, count);
                countLength.put(count, null);
            }
            this.setNs(ns);
            this.setNword(nw);
//            System.out.println(inFile + " so cau " + this.getNs());
//            System.out.println(inFile + " so tu " + this.getNword());
//            System.out.println(inFile + " so tu khac nhau " + countVocab.size());

            System.out.println("File: "+new File(inFile).getName());
            System.out.println("#sents: "+this.getNs());
            System.out.println("#words: "+this.getNword());
            System.out.println("#vocab: "+countVocab.size());
            System.out.println("");
            

//            is.close();
//            input.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Initial(String NewFolder, String Directory, String FileName) {
        try {
            FileInputStream is = new FileInputStream(Directory + "\\" + FileName);
            Scanner input = new Scanner(is);
            FileOutputStream outf = new FileOutputStream(NewFolder + "\\" + FileName);
            PrintWriter output = new PrintWriter(outf, true);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                //line = line.replace('_',' ');
                line = line.toLowerCase();
                output.println(line);
            }
            is.close();
            input.close();
            outf.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
