package lab.alignment.approaches.evs.length_and_word;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import lab.alignment.configs.Configs;

/**
 *
 * @author long
 */
public class ReadIBM {

    protected Map<String, Integer> ModE = new HashMap<String, Integer>();
    protected Map<String, Integer> ModV = new HashMap<String, Integer>();
    protected Map<String, Double> mp = new HashMap<String, Double>();

    public void readIBM1(String modelFile) {
        try {
//            FileInputStream is=new FileInputStream(Model+"/"+FileName);
//            Scanner input=new Scanner(is);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(modelFile)), Configs.ENCODING));

            int ns1 = 0;

            String line;
            while ((line = reader.readLine()) != null) {

//            while(input.hasNextLine()) {
//                String line=input.nextLine();
                StringTokenizer words;
                words = new StringTokenizer(line);
                {
                    ns1++;
                    String s1 = words.nextToken();
                    double pr = Double.parseDouble(s1);
                    String s2 = words.nextToken();
                    String s3 = words.nextToken();

                    ModE.put(s2, null);
                    ModV.put(s3, null);

                    mp.put(s2 + " " + s3, pr);

//                    if (s2.matches("(other)") && s3.matches("(other)")) {
//                        System.out.println("other-other: " + pr);
//                        mp.put(s2 + " " + s3, 0.1);
//                    }

                }

            }
            System.out.println("IBM Model 1 - So cap " + ns1);
            ModE.remove("'s");
            ModE.remove("'t");
            ModE.remove("s");

//            is.close();
//            input.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Double> getMp() {
        return mp;
    }

    public Map<String, Integer> getModE() {
        return ModE;
    }

    public Map<String, Integer> getModV() {
        return ModV;
    }
}
