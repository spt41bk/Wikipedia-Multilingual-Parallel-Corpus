package lab.alignment.approaches.evs.length_and_word;

import java.util.*;
import java.util.Scanner;
import java.io.*;
import lab.alignment.approaches.evs.length.IOData;
import lab.alignment.configs.Configs;

/*
 author Trieu Hai Long
 *K17CS - UET
 */
public class CountWords_W2V extends ReadIBM {
    //----------------------------------

    private Map<String, Double> wordprobe = new HashMap<String, Double>();
    private Map<String, Double> wordprobv = new HashMap<String, Double>();

    private Map<Integer, String> SentE = new HashMap<Integer, String>();
    private Map<Integer, String> SentV = new HashMap<Integer, String>();
    private int Nw1;
    private int Nw2;
    
    private int othern1;
    private int othern2;
    
    private int vocabn1;
    private int vocabn2;
    
    
    private Set<String>enOtherSet=new HashSet<>();

    public Set<String> getEnOtherSet() {
        return enOtherSet;
    }

    public void setEnOtherSet(Set<String> enOtherSet) {
        this.enOtherSet = enOtherSet;
    }

    public Set<String> getViOtherSet() {
        return viOtherSet;
    }

    public void setViOtherSet(Set<String> viOtherSet) {
        this.viOtherSet = viOtherSet;
    }
    private Set<String>viOtherSet=new HashSet<>();    

    //----------------------------------------------------------
    public int getNw1() {
        return Nw1;
    }

    public int getNw2() {
        return Nw2;
    }

    public Map<String, Double> getWordProbe() {
        return wordprobe;
    }

    public Map<String, Double> getWordProbv() {
        return wordprobv;
    }

    public Map<Integer, String> getSentE() {
        return SentE;
    }

    public Map<Integer, String> getSentV() {
        return SentV;
    }

    public int getOthern1() {
        return othern1;
    }

    public void setOthern1(int othern1) {
        this.othern1 = othern1;
    }

    public int getOthern2() {
        return othern2;
    }

    public void setOthern2(int othern2) {
        this.othern2 = othern2;
    }

    public int getVocabn1() {
        return vocabn1;
    }

    public void setVocabn1(int vocabn1) {
        this.vocabn1 = vocabn1;
    }

    public int getVocabn2() {
        return vocabn2;
    }

    public void setVocabn2(int vocabn2) {
        this.vocabn2 = vocabn2;
    }
    
    

    //--------------
    public void SetWord2(String SourceFileName, String TargetFileName) {
        int ns = 0, nw = 0;
        try {
//            FileInputStream is1=new FileInputStream(SourceFileName);
//            FileInputStream is2=new FileInputStream(TargetFileName);
//            
//            Scanner input1=new Scanner(is1);
//            Scanner input2=new Scanner(is2);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(SourceFileName)), Configs.ENCODING));
            String line;
            while ((line = reader.readLine()) != null) {

//            while(input1.hasNextLine()) {
//                String line = input1.nextLine();
                SentE.put(ns, line);
                ns++;

            }

//            is1.close();
//            input1.close();
            reader.close();

            ns = 0;

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TargetFileName)), Configs.ENCODING));
            while ((line = reader.readLine()) != null) {

//            while (input2.hasNextLine()) {
//                String line = input2.nextLine();
                SentV.put(ns, line);
                ns++;

            }

//            is2.close();
//            input2.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CountWord(IOData a, IOData b, ReadIBM r) {
        Map<String, Integer> mworde = new HashMap<String, Integer>();
        Map<String, Integer> mwordv = new HashMap<String, Integer>();

        ModE = r.getModE();
        ModV = r.getModV();
        mp = r.getMp();

        int Ns1 = a.getNs();
        int Ns2 = b.getNs();
        int CountOther = 0;

        Nw1 = 0;
        Nw2 = 0;
        String line;
        int size;
//        Set<String> enOtherSet = new HashSet<>();//test other
        Set<String> enVocabSet = new HashSet<>();

        for (int ns = 0; ns < Ns1; ns++) {
            line = SentE.get(ns);
            StringTokenizer words;
            words = new StringTokenizer(line);

            int nw = 0;
            String line2 = "";
            while (words.hasMoreTokens()) {

                if (line2 != "") {
                    line2 += " ";
                }
                String s = words.nextToken();

                enVocabSet.add(s);

                nw++;

                if (ModE.containsKey(s)) {
                    if (mworde.get(s) != null) {
                        int num = mworde.get(s);
                        mworde.put(s, num + 1);
                    } else {
                        mworde.put(s, 1);
                    }
                } else {
                    CountOther++;
                    enOtherSet.add(s);
//                    s = "(other)";
                }
                line2 += s;
            }

            Nw1 += nw;
            SentE.put(ns, line2);

        }

//        mworde.put("(other)", CountOther);

//        System.out.println("");
//        System.out.println("en vocab set: " + enVocabSet.size());
//        System.out.println("(other): en=" + CountOther);//test
//        System.out.println("(other): distinct en=" + enOtherSet.size());//test      
//        System.out.println("nw1: " + Nw1);
//        System.out.println("words: " + a.getNword());
//        System.out.println("vocab: " + a.getCountVocab().size());
//        System.out.println("");
        
        othern1=enOtherSet.size();
        vocabn1=enVocabSet.size();
        
        

        CountOther = 0;
//        Set<String> viOtherSet = new HashSet<>();        //test other
        Set<String>viVocabSet=new HashSet<>();
        for (int ns = 0; ns < Ns2; ns++) {
            line = SentV.get(ns);
            StringTokenizer words;
            words = new StringTokenizer(line);

            int nw = 0;
            String line2 = "";
            while (words.hasMoreTokens()) {
                if (line2 != "") {
                    line2 += " ";
                }
                String s = words.nextToken();
                
                viVocabSet.add(s);
                
                nw++;
                if (ModV.containsKey(s)) {
                    if (mwordv.get(s) != null) {
                        int num = mwordv.get(s);
                        mwordv.put(s, num + 1);
                    } else {
                        mwordv.put(s, 1);
                    }
                } else {
                    CountOther++;
                    viOtherSet.add(s);
//                    s = "(other)";
                }
                line2 += s;

            }

            Nw2 += nw;
            SentV.put(ns, line2);
        }

//        mwordv.put("(other)", CountOther);
        
//        System.out.println("vi vocab set: "+viVocabSet.size());
//        System.out.println("(other): vi=" + CountOther);//test
//        System.out.println("(other): distinct vi=" + viOtherSet.size());//test         
//        System.out.println("nw1: " + Nw2);
//        System.out.println("words: " + b.getNword());
//        System.out.println("vocab: "+b.getCountVocab().size());
//        System.out.println("");
        
        othern2=viOtherSet.size();
        vocabn2=viVocabSet.size();

        Iterator it = mworde.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry m = (Map.Entry) it.next();
            int value = (Integer) m.getValue();
            String key = (String) m.getKey();
            wordprobe.put(key, -Math.log((double) value / Nw1));

        }

        it = mwordv.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry m = (Map.Entry) it.next();
            int value = (Integer) m.getValue();
            String key = (String) m.getKey();
            wordprobv.put(key, -Math.log((double) value / Nw2));

        }
    }
    
    public void CountWord_origin(IOData a, IOData b, ReadIBM r) {
        Map<String, Integer> mworde = new HashMap<String, Integer>();
        Map<String, Integer> mwordv = new HashMap<String, Integer>();

        ModE = r.getModE();
        ModV = r.getModV();
        mp = r.getMp();

        int Ns1 = a.getNs();
        int Ns2 = b.getNs();
        int CountOther = 0;

        Nw1 = 0;
        Nw2 = 0;
        String line;
        int size;

        for (int ns = 0; ns < Ns1; ns++) {
            line = SentE.get(ns);
            StringTokenizer words;
            words = new StringTokenizer(line);

            int nw = 0;
            String line2 = "";
            while (words.hasMoreTokens()) {

                if (line2 != "") {
                    line2 += " ";
                }
                String s = words.nextToken();


                nw++;

                if (ModE.containsKey(s)) {
                    if (mworde.get(s) != null) {
                        int num = mworde.get(s);
                        mworde.put(s, num + 1);
                    } else {
                        mworde.put(s, 1);
                    }
                } else {
                    CountOther++;
                    s = "(other)";
                }
                line2 += s;
            }

            Nw1 += nw;
            SentE.put(ns, line2);

        }

        mworde.put("(other)", CountOther);


        CountOther = 0;
        for (int ns = 0; ns < Ns2; ns++) {
            line = SentV.get(ns);
            StringTokenizer words;
            words = new StringTokenizer(line);

            int nw = 0;
            String line2 = "";
            while (words.hasMoreTokens()) {
                if (line2 != "") {
                    line2 += " ";
                }
                String s = words.nextToken();
                
                
                nw++;
                if (ModV.containsKey(s)) {
                    if (mwordv.get(s) != null) {
                        int num = mwordv.get(s);
                        mwordv.put(s, num + 1);
                    } else {
                        mwordv.put(s, 1);
                    }
                } else {
                    CountOther++;
                    s = "(other)";
                }
                line2 += s;

            }

            Nw2 += nw;
            SentV.put(ns, line2);
        }

        mwordv.put("(other)", CountOther);
        

        Iterator it = mworde.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry m = (Map.Entry) it.next();
            int value = (Integer) m.getValue();
            String key = (String) m.getKey();
            wordprobe.put(key, -Math.log((double) value / Nw1));

        }

        it = mwordv.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry m = (Map.Entry) it.next();
            int value = (Integer) m.getValue();
            String key = (String) m.getKey();
            wordprobv.put(key, -Math.log((double) value / Nw2));

        }
    }
}
