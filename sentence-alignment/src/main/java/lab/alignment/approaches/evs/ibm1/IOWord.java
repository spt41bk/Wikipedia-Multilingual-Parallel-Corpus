package lab.alignment.approaches.evs.ibm1;

import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab.alignment.configs.Configs;

/*
 */
public class IOWord {

    private int ns;//tong so sentence trong file 1
    private Sentences[] fs;
    private Word[] word;

    public void setNs(int ns1) {
        ns = ns1;
    }

    public void setWord(Word w[]) {
        word = w;
    }

    public void setFs(Sentences s, int i) {
        fs[i] = s;

    }

    public Word[] getWord() {
        return word;
    }

    public int getNs() {
        return ns;
    }

    public Sentences getFs(int i) {
        return fs[i];
    }

    public IOWord ReadFile(String FileName) {
        IOWord a = new IOWord();
        word = new Word[5000];

        for (int i = 0; i < 5000; i++) {
            word[i] = new Word();
        }
        // Sentences s = new Sentences();
        try {
            //tạo luồng nối đến tập tin
//            FileInputStream is = new FileInputStream(FileName);
//            //Dùng phương tiện Scanner để đọc
//            Scanner input = new Scanner(is);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FileName)), Configs.ENCODING));

            //trong khi còn dòng để đọc
            int Ns = 0;

            String line;
            while ((line = reader.readLine()) != null) {
//            while (input.hasNextLine()) {
                //Đọc 1 dòng
//                String line = input.nextLine();

                StringTokenizer words;
                words = new StringTokenizer(line);
                int nw = 0;
                String st[] = new String[1000];

                while (words.hasMoreTokens()) {
                    String w = words.nextToken();
                    //xu ly xoa dau cach,dau cham,phay
                    //if((w.equals(";")&&w.equals("{")&&w.equals("}")&&w.equals(".")&&w.equals(",")&&w.equals("(")&&w.equals(")"))==false){
                    // if((!w.equals(",")) && (!w.equals("."))&& (!w.equals("..."))&& (!w.equals("'"))&& (!w.equals("(")) && (!w.equals(")"))&& (!w.equals("}"))&& (!w.equals("{"))){
                    st[nw] = w;
                    int i = 0;

                    nw++;
                    // }
                }

                Sentences sp = new Sentences();
                sp.setNumberOfWord(nw);
                // sp.setVecto(st);
                a.setFs(sp, Ns);
                Ns++;

            }

            a.setNs(Ns);

//            is.close();
//            input.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        a.setWord(word);

        return a;
    }

    //--------------------------Ghi file-----------------------------
    public void WriteFile(String filename, String content) {
        try {
//        FileOutputStream out=new FileOutputStream(filename);
//        PrintWriter output=new PrintWriter(out,true);
//        output.println(content);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filename)), Configs.ENCODING));

            writer.write(content);
            writer.write("\n");

//            out.close();
//            output.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------Main------------------------------
    /*   public static void main(String[] args){
        
        IOData a = new IOData();
        IOData b = new IOData();
        a.ReadFile("Word1.txt");
        b.ReadFile("Word2.txt");
    }
     
     */
}
