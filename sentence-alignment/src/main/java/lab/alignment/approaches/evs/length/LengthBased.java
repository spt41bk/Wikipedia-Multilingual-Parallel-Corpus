package lab.alignment.approaches.evs.length;

import java.io.*;
import java.util.*;
import java.util.HashMap;
import lab.alignment.configs.Configs;

/**
 * Trieu Hai Long
 */
public class LengthBased extends SentLengthRatio {

    protected Map<Integer, Double> fw = new HashMap<Integer, Double>();
    protected Map<Integer, Double> bw = new HashMap<Integer, Double>();

    protected Map<Integer, Double> bead1 = new HashMap<Integer, Double>();
    protected Map<Integer, Double> bead2 = new HashMap<Integer, Double>();
    protected Map<Integer, Integer> Node1 = new HashMap<Integer, Integer>();
    protected Map<Integer, Integer> Node2 = new HashMap<Integer, Integer>();
    protected Map<Integer, Integer> Ls = new HashMap<Integer, Integer>();
    protected Map<Integer, Integer> Lt = new HashMap<Integer, Integer>();
    protected int Ns1;
    protected int Ns2;
    protected int node = 0;

    public Map<Integer, Double> getBead1() {
        return bead1;
    }

    public Map<Integer, Double> getBead2() {
        return bead2;
    }

    public void Init(IOData a1, IOData a2, SentLengthRatio b1) {
        Ls = a1.getSentLength();
        Lt = a2.getSentLength();
        Rs = b1.getRs();
        Rt = b1.getRt();
        Pair = b1.getPair();
        Rpoi = b1.getRpoi();
        Ns1 = a1.getNs();
        Ns2 = a2.getNs();

    }

    public double poi(int lt, int ls) {
        double p = 0;
        double r = Rpoi;
        double p0 = (double) Math.log(ls * r);
        for (int i = lt; i > 0; i--) {
            double t1 = (double) Math.log(i);
            p += t1 - p0;
        }
        p += (ls * r);
        return p;
    }

    //ForWard------------------
    public void Forward_Backward(int limit, double threshold1) {
        //long start = System.currentTimeMillis();
        //System.out.println("Start Forward");

        double delete_score, insert_score, match_score, contract_score, expand_score, max;
        double del_base, ins_base, mat_base, con_base, exp_base;
        double conf_threshold = -20;

        Stack log = new Stack();
        double s, pop;

        int stt, end;
        int del, ins, mat, exp, con;
        int rowcol;

        del_base = ins_base = Math.log(0.01);
        mat_base = Math.log(0.94);
        con_base = exp_base = Math.log(0.02);

        node = 0;

        //-------FORWARD-----------------------
        fw.put(0, 0.0);
        for (int row = 0; row <= Ns1; row++) {

            stt = row - limit;
            if (stt < 0) {
                stt = 0;
            }
            end = row + limit;
            if (end > Ns2) {
                end = Ns2;
            }

            for (int col = stt; col <= end; col++) {

                max = Double.NEGATIVE_INFINITY;
                del = (row - 1) * Ns2 + col + (row - 1);
                if (col >= 1) {
                    mat = (row - 1) * Ns2 + (col - 1) + (row - 1);
                    con = (row - 2) * Ns2 + col - 1 + (row - 2);
                    ins = row * Ns2 + col - 1 + row;

                } else {
                    mat = -1;
                    con = -1;
                    ins = -1;
                }
                if (col >= 2) {
                    exp = (row - 1) * Ns2 + (col - 2) + (row - 1);
                } else {
                    exp = -1;
                }
                rowcol = row * Ns2 + col + row;
                //xu ly
                //delete
                if (fw.get(del) != null) {
                    delete_score = fw.get(del) + del_base - Rs.get(Ls.get(row));
                    log.push(delete_score);
                }
                //insert
                if (fw.get(ins) != null) {
                    insert_score = fw.get(ins) + ins_base - Rt.get(Lt.get(col));
                    log.push(insert_score);
                }

                if (fw.get(mat) != null) {
                    match_score = fw.get(mat) + mat_base - Rs.get(Ls.get(row)) - this.poi(Lt.get(col), Ls.get(row));
                    log.push(match_score);
                }

                //contract
                if (fw.get(con) != null) {
                    contract_score = fw.get(con) + con_base - Rs.get(Ls.get(row)) - Rs.get(Ls.get(row - 1)) - this.poi(Lt.get(col), Ls.get(row - 1) + Ls.get(row));
                    log.push(contract_score);
                }

                //expand   
                if (fw.get(exp) != null) {
                    expand_score = fw.get(exp) + exp_base - Rs.get(Ls.get(row)) - Pair.get(col - 1) - this.poi(Lt.get(col - 1) + Lt.get(col), Ls.get(row));
                    log.push(expand_score);
                }

                s = 1;
                Stack log2 = new Stack();
                if (!log.empty()) {
                    max = (Double) log.pop();
                    while (!log.empty()) {
                        pop = (Double) log.pop();
                        if (pop > max) {
                            log2.add(max);
                            max = pop;
                        } else {
                            log2.add(pop);
                        }
                    }
                    while (!log2.empty()) {
                        pop = (Double) log2.pop();
                        s += (double) Math.exp(pop - max);
                    }

                    double prob = max + (double) Math.log(s);
                    fw.put(rowcol, prob);
                }
                //if(row==Ns1)System.out.println(row+" "+col+" "+prob);

            }
        }

        //System.out.println("Finished Forward");
        //long elapsedTimeMillis = System.currentTimeMillis()-start;
        //System.out.println("Forward pass time "+(elapsedTimeMillis/1000F)+" seconds");
        //-----------------BACKWARD----------------
        //start = System.currentTimeMillis();
        double delete_back, insert_back, match_back, contract_back, expand_back;

        del_base = ins_base = Math.log(0.01);
        mat_base = Math.log(0.94);
        con_base = exp_base = Math.log(0.02);

        double bead_prob;
        int highbead = 0;

        bw.put(Ns1 * Ns2 + Ns2 + Ns1, 0.0);
        node++;
        Node1.put(node, Ns1);
        Node2.put(node, Ns2);

        for (int row = Ns1; row >= 0; row--) {
            stt = row - limit;
            if (stt < 0) {
                stt = 0;
            }
            end = row + limit;
            if (end > Ns2) {
                end = Ns2;
            }

            for (int col = end; col >= stt; col--) {

                double norm = fw.get(row * Ns2 + col + row) - fw.get(Ns1 * Ns2 + Ns2 + Ns1);

                max = Double.NEGATIVE_INFINITY;
                rowcol = row * Ns2 + col + row;
                del = (row + 1) * Ns2 + col + (row + 1);

                if (col <= end - 1) {
                    mat = (row + 1) * Ns2 + (col + 1) + (row + 1);
                    con = (row + 2) * Ns2 + col + 1 + (row + 2);
                    ins = row * Ns2 + col + 1 + row;
                } else {
                    mat = -1;
                    con = -1;
                    ins = -1;
                }
                if (col <= end - 2) {
                    exp = (row + 1) * Ns2 + (col + 2) + (row + 1);
                } else {
                    exp = -1;
                }
                //delete--------
                if (bw.get(del) != null) {
                    delete_back = bw.get(del) + del_base - Rs.get(Ls.get(row + 1));
                    log.push(delete_back);

                }
                //insert--------

                if (bw.get(ins) != null) {
                    insert_back = bw.get(ins) + ins_base - Rt.get(Lt.get(col + 1));
                    log.push(insert_back);

                }

                if (bw.get(mat) != null) {
                    match_back = bw.get(mat) + mat_base - Rs.get(Ls.get(row + 1)) - this.poi(Lt.get(col + 1), Ls.get(row + 1));
                    bead_prob = match_back + norm;

                    if (Math.exp(bead_prob) > threshold1) {
                        double pow = Math.exp(bead_prob);
                        bead1.put(row, pow);
                        bead2.put(col, pow);
                        //System.out.println(row+" "+col+" "+pow+" "+"match");
                        highbead++;
                    }
                    log.push(match_back);
                }
                //contract------
                if (bw.get(con) != null) {
                    contract_back = bw.get(con) + con_base - Rs.get(Ls.get(row + 1)) - Rs.get(Ls.get(row + 2)) - this.poi(Lt.get(col + 1), Ls.get(row + 1) + Ls.get(row + 2));
                    log.push(contract_back);
                }
                //expand--------

                if (bw.get(exp) != null) {
                    expand_back = bw.get(exp) + exp_base - Rs.get(Ls.get(row + 1)) - Pair.get(col + 1) - this.poi(Lt.get(col + 1) + Lt.get(col + 2), Ls.get(row + 1));
                    log.push(expand_back);
                }
                s = 1;
                Stack log2 = new Stack();
                if (!log.empty()) {
                    max = (Double) log.pop();
                    while (!log.empty()) {
                        pop = (Double) log.pop();
                        if (pop > max) {
                            log2.add(max);
                            max = pop;
                        } else {
                            log2.add(pop);
                        }
                    }
                    while (!log2.empty()) {
                        pop = (Double) log2.pop();
                        s += (double) Math.exp(pop - max);
                    }

                    double prob = max + (double) Math.log(s);
                    double total = prob + norm;

                    if (total > conf_threshold) {
                        bw.put(rowcol, prob);
                        node++;
                        Node1.put(node, row);
                        Node2.put(node, col);
                    }
                }
            }
        }

        System.out.println("Total of High beads by length    " + highbead);
        System.out.println("-----------------------------------------");
        System.out.println("");
        //elapsedTimeMillis = System.currentTimeMillis()-start;
        //System.out.println("Backward pass time "+(elapsedTimeMillis/1000F)+" seconds");

    }

//    public void WriteNode(String InputDirectory, String ResultDirectory, String InputSourceFile, String InputTargetFile, String Out) {
    public void writeNode(String outFile) {
        
        try {
//            if (!new File(ResultDirectory).exists()) {
//                new File(ResultDirectory).mkdirs();
//            }
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(ResultDirectory, InputSourceFile + "-" + InputTargetFile + Out)), Configs.ENCODING));

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)), Configs.ENCODING));


//            FileOutputStream outf = new FileOutputStream(ResultDirectory + "/" + InputSourceFile + "." + InputTargetFile + Out);
//            PrintWriter output = new PrintWriter(outf, true);
            for (int i = node; i > 0; i--) {
//                output.println(Node1.get(i) + " " + Node2.get(i));

                writer.write(String.format("%d %d\n", Node1.get(i), Node2.get(i)));

            }
//            outf.close();
//            output.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
