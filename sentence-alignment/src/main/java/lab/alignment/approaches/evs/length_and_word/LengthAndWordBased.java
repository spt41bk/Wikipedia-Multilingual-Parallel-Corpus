package lab.alignment.approaches.evs.length_and_word;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.*;
import java.util.HashMap;
import java.io.*;
import lab.alignment.approaches.evs.length.IOData;
import lab.alignment.approaches.evs.length.LengthBased;
import lab.alignment.approaches.evs.length.SentLengthRatio;
import lab.alignment.configs.Configs;

/**
 * @Trieu Hai Long
 */
public class LengthAndWordBased extends LengthBased {
    
   
  
    private Map<Integer,Double> sentscores =new HashMap<Integer,Double>();
    private Map<Integer,Double> sentscoret =new HashMap<Integer,Double>();
    private Map<Integer,Integer> bead =new HashMap<Integer, Integer>();
    private Map<Integer,Integer> ColIndex =new HashMap<Integer, Integer>();
    private Map<Integer,String> SentE = new HashMap<Integer,String>();
    private Map<Integer,String> SentV = new HashMap<Integer,String>();
    private Map<String,Double> prob =new HashMap<String,Double>();
    
//    Table<String,String,Integer>testPairs=HashBasedTable.create();
    
            

      
    
    public void SentScore(IOData a1,IOData a2, CountWords r){
        SentE = r.getSentE();
        SentV = r.getSentV();
        Map<String,Double> wordprobe = new HashMap<String,Double>();
        Map<String,Double> wordprobv = new HashMap<String,Double>();
        
        wordprobe = r.getWordProbe();
        wordprobv = r.getWordProbv();
        
        int Ns1 = a1.getNs();
        int Ns2 = a2.getNs();
        String line;
        int Nw1 = 0; 
        for(int i = 0;i<Ns1;i++){
            int nw = 0;
            line = SentE.get(i);
            StringTokenizer words;
            words=new StringTokenizer(line);
            double pr = 0;
            while(words.hasMoreTokens()){
                String s = words.nextToken();
                nw++;
                pr += wordprobe.get(s);
            }
            sentscores.put(i+1, pr);
            Nw1 += nw;
        }
        
        int Nw2 = 0;
        for(int i = 0;i<Ns2;i++){
            int nw = 0;
            line = SentV.get(i);
            StringTokenizer words;
            words=new StringTokenizer(line);
            double pr = 0;
            while(words.hasMoreTokens()){
                String s = words.nextToken();
                nw++;
                pr += wordprobv.get(s);
            }
            sentscoret.put(i+1, pr);
            Nw2 += nw;
        } 
    }
    
    
    public void SentScore(IOData a1,IOData a2, CountWords_W2V r){
        SentE = r.getSentE();
        SentV = r.getSentV();
        Map<String,Double> wordprobe = new HashMap<String,Double>();
        Map<String,Double> wordprobv = new HashMap<String,Double>();
        
        wordprobe = r.getWordProbe();
        wordprobv = r.getWordProbv();
        
        int Ns1 = a1.getNs();
        int Ns2 = a2.getNs();
        String line;
        int Nw1 = 0; 
        for(int i = 0;i<Ns1;i++){
            int nw = 0;
            line = SentE.get(i);
            StringTokenizer words;
            words=new StringTokenizer(line);
            double pr = 0;
            while(words.hasMoreTokens()){
                String s = words.nextToken();
                nw++;
                pr += wordprobe.get(s);
            }
            sentscores.put(i+1, pr);
            Nw1 += nw;
        }
        
        int Nw2 = 0;
        for(int i = 0;i<Ns2;i++){
            int nw = 0;
            line = SentV.get(i);
            StringTokenizer words;
            words=new StringTokenizer(line);
            double pr = 0;
            while(words.hasMoreTokens()){
                String s = words.nextToken();
                nw++;
                pr += wordprobv.get(s);
            }
            sentscoret.put(i+1, pr);
            Nw2 += nw;
        } 
    }
    
    
     public double TransScore_W2V(int row,int col,String bead) {
        Map<Integer,String> wordE = new HashMap<Integer,String>();
        Map<Integer,String> wordV = new HashMap<Integer,String>();
        StringTokenizer words1 = null,words2=null;
        if(bead.equals("match")){
            words1=new StringTokenizer(SentE.get(row));
            words2=new StringTokenizer(SentV.get(col));
            
        }
        else if(bead.equals("contract")){
            words1=new StringTokenizer(SentE.get(row)+" "+SentE.get(row-1));
            words2=new StringTokenizer(SentV.get(col));
        }
        else if(bead.equals("expand")){
            words1=new StringTokenizer(SentE.get(row));
            words2=new StringTokenizer(SentV.get(col)+" "+SentV.get(col-1));
        }
        int lengths=0,lengtht=0;
        while(words1.hasMoreTokens()){
            String s = words1.nextToken();
            lengths++;
            
            wordE.put(lengths, s);
            
        }
        
        while(words2.hasMoreTokens()){
            String s = words2.nextToken();
            
            lengtht++;
            wordV.put(lengtht, s);
        }
        
        double score = (double)-Math.log((double) 1 / (lengths + 1)) * (lengtht);
        
        for(int j = 1;j<=lengtht;j++){
            String wordv = wordV.get(j);
            double pr = 0;
            for(int i=1;i<=lengths;i++){
                String worde = wordE.get(i);
                
                if(prob.containsKey(worde+" "+wordv) && prob.get(worde+" "+wordv)>0){
                    pr += prob.get(worde+" "+wordv);
                  
                                        
                }
                else{
                    System.out.println("");
                }
                
                    
            }
            if(prob.containsKey("(empty)"+" "+wordv) && prob.get("(empty)"+" "+wordv)>0){
                pr += prob.get("(empty)"+" "+wordv);
              
            }
            score -= Math.log(pr);
        }
        
        return score;
    }
     
     
    
 
 
    public double TransScore(int row,int col,String bead) {
        Map<Integer,String> wordE = new HashMap<Integer,String>();
        Map<Integer,String> wordV = new HashMap<Integer,String>();
        StringTokenizer words1 = null,words2=null;
        if(bead.equals("match")){
            words1=new StringTokenizer(SentE.get(row));
            words2=new StringTokenizer(SentV.get(col));
            
        }
        else if(bead.equals("contract")){
            words1=new StringTokenizer(SentE.get(row)+" "+SentE.get(row-1));
            words2=new StringTokenizer(SentV.get(col));
        }
        else if(bead.equals("expand")){
            words1=new StringTokenizer(SentE.get(row));
            words2=new StringTokenizer(SentV.get(col)+" "+SentV.get(col-1));
        }
        int lengths=0,lengtht=0;
        while(words1.hasMoreTokens()){
            String s = words1.nextToken();
            lengths++;
            
            wordE.put(lengths, s);
            
        }
        
        while(words2.hasMoreTokens()){
            String s = words2.nextToken();
            
            lengtht++;
            wordV.put(lengtht, s);
        }
        
        double score = (double)-Math.log((double) 1 / (lengths + 1)) * (lengtht);
        for(int j = 1;j<=lengtht;j++){
            String wordv = wordV.get(j);
            double pr = 0;
            for(int i=1;i<=lengths;i++){
                String worde = wordE.get(i);
                if(prob.containsKey(worde+" "+wordv) && prob.get(worde+" "+wordv)>0){
                    pr += prob.get(worde+" "+wordv);
                    
                                      
                }   
                
                
                    
            }
            if(prob.containsKey("(empty)"+" "+wordv) && prob.get("(empty)"+" "+wordv)>0){
                pr += prob.get("(empty)"+" "+wordv);
              
            }
            score -= Math.log(pr);
        }
        
        return score;
    }
    
    public void InitPlus(IOData a1,IOData a2,SentLengthRatio b1, ReadIBM r){
        
        prob = r.getMp();
        Pair = b1.getPair();
        Ns1 = a1.getNs();
        Ns2 = a2.getNs();
        Ls = a1.getSentLength();
        Lt = a2.getSentLength();
        Rs = b1.getRs();
        Rt = b1.getRt();
        Rpoi = b1.getRpoi();
    }
    //------------------------------------------------------------------------------------------   
    public void Forward_Backward(int limit,double threshold2){
        //System.out.println("Start Forward Plus");
       // long start = System.currentTimeMillis();
        
        Map<Integer,Double> fd =new HashMap<Integer,Double>();
        Map<Integer,Double> fi =new HashMap<Integer,Double>();
        Map<Integer,Double> fm =new HashMap<Integer,Double>();
        Map<Integer,Double> fc =new HashMap<Integer,Double>();
        Map<Integer,Double> fe =new HashMap<Integer,Double>();
        
        Map<Integer,Integer> NodeFor1 =new HashMap<Integer, Integer>();
        Map<Integer,Integer> NodeFor2 =new HashMap<Integer, Integer>();
        
        double delete_score,insert_score,match_score,contract_score,expand_score,max;
        double nbs,score,MatchScore=0;
        double del_base,ins_base,mat_base,con_base,exp_base;
        
        int stt,end;
        int del,ins,mat,exp,con,rowcol;
        
        Stack log = new Stack();
        double s,pop;
        
        del_base = ins_base = Math.log(0.01);
        mat_base = Math.log(0.94);
        con_base = exp_base = Math.log(0.02);
        
        int nodefor=1;
        NodeFor1.put(nodefor, 0);
        NodeFor2.put(nodefor, 0);
        
        int ns=1;
        fw.put(0, 0.0);
        for(int row = 0;row<=Ns1;row++){
         
            while(ns<=node && Node1.get(ns)==row){
 
               int col = Node2.get(ns);
   
               max = Double.NEGATIVE_INFINITY;
               del = (row-1)*Ns2+col + (row-1);
               if(col>=1){
                   mat = (row-1)*Ns2 + (col-1) + (row-1);
                   con = (row-2)*Ns2 + col-1 + (row-2);
                   ins = row*Ns2+col-1 + (row);
               }
               else{
                   mat = -1;
                   con = -1;
                   ins = -1;
               }
               if(col>=2)exp = (row-1)*Ns2 + (col-2) + (row-1);
               else exp = -1;
               rowcol = row*Ns2 + col + row;
              
            //xu ly
                //delete
               if(fw.get(del)!=null){
                   nbs = sentscores.get(row) + Rs.get(Ls.get(row));
                   fd.put(rowcol, nbs);
                   delete_score = fw.get(del) - nbs + del_base;
                   log.push(delete_score);
               }
               
              
                //insert
                
               if(fw.get(ins) !=null){
                   nbs = sentscoret.get(col) + Rt.get(Lt.get(col));
                   fi.put(rowcol, nbs);
                   insert_score = fw.get(ins) - nbs + ins_base;
                   log.push(insert_score);
               }
               //match
               if(fw.get(mat)!=null){
                   score = this.TransScore(row-1, col-1, "match");
                   nbs = sentscores.get(row) + Rs.get(Ls.get(row)) + this.poi(Lt.get(col),Ls.get(row)) + score;
                   fm.put(rowcol, nbs);
                   match_score = fw.get(mat) - nbs + mat_base;
                   log.push(match_score);
                  
                 }
                
                //contract
                if(fw.get(con)!=null){
                    score = this.TransScore(row-1, col-1, "contract");
                    nbs = sentscores.get(row) + Rs.get(Ls.get(row)) + sentscores.get(row-1) + Rs.get(Ls.get(row-1)) + score + this.poi(Lt.get(col), Ls.get(row-1) + Ls.get(row));    
                    fc.put(rowcol, nbs);
                    contract_score = fw.get(con) - nbs + con_base;
                    log.push(contract_score);
                }
                
                //expand
               if(fw.get(exp)!=null){
                   score = this.TransScore(row-1, col-1, "expand");
                   nbs = sentscores.get(row) + Rs.get(Ls.get(row)) + Pair.get(col-1) + score + this.poi(Lt.get(col-1)+Lt.get(col), Ls.get(row));
                   fe.put(rowcol, nbs);
                   expand_score = fw.get(exp) - nbs + exp_base;
                   log.push(expand_score);

               }
               
               //log-add-list
               s = 1;
               Stack log2 = new Stack();
               if(!log.empty()){
                    max = (Double)log.pop();
                    while(!log.empty()){
                        pop = (Double)log.pop();
                        if(pop>max){
                            log2.add(max);
                            max = pop;
                        }
                        else log2.add(pop);
                    }
                    while(!log2.empty()){
                        pop = (Double)log2.pop();
                        s+= (double)Math.exp(pop - max);
                    }
                    fw.put(rowcol, max + (double)Math.log(s));
                    nodefor++;
                    NodeFor1.put(nodefor, row);
                    NodeFor2.put(nodefor, col);
                 
               }

               ns++;
            }
            //System.out.println(row);
        }
        //long elapsedTimeMillis = System.currentTimeMillis()-start;
       // System.out.println("Forward Plus pass time "+(elapsedTimeMillis/1000F)+" seconds");
        
        
       //-----------BACKWARD------------------------
        
      // System.out.println("Start Backward Plus");
       //start = System.currentTimeMillis();
       double delete_back,insert_back,match_back,contract_back,expand_back;           
       double bead_prob;
       int highbead = 0;
       
       double conf_threshold = -20;
       bw.put(Ns1*Ns2+Ns2+Ns1, 0.0);
      
       ns = nodefor;
       double fwNs1Ns2 = fw.get(Ns1*Ns2 + Ns2+Ns1);
       
       for(int row = Ns1;row>=0; row--){
        
           while(ns>0 && NodeFor1.get(ns)==row){
               int col = NodeFor2.get(ns);
               rowcol = row*Ns2 + col+row;
             
               double norm = fw.get(rowcol) - fwNs1Ns2;
               max = Double.NEGATIVE_INFINITY;
               
               del = (row+1)*Ns2+col+(row+1);
               if(col<=Ns2-1){
                   mat = (row+1)*Ns2 + (col+1)+(row+1);
                   con = (row+2)*Ns2 + col+1+(row+2);
                   ins = row*Ns2+col+1+row;
               }
               else{
                   mat = -1;
                   con = -1;
                   ins = -1;
               }
               if(col<=Ns2-2)
                   exp = (row+1)*Ns2 + (col+2) + (row+1);
               else exp = -1;           
                              
               //delete--------
               if(bw.get(del)!=null){
                    delete_back = bw.get(del) + del_base - fd.get(del);
                    log.push(delete_back);
                
               }
               //insert--------
               if(bw.get(ins)!=null){
                    insert_back = bw.get(ins) + ins_base - fi.get(ins);
                    log.push(insert_back);
               
               }
               
               //match---------
               if(bw.get(mat)!=null){
                   
                   match_back = bw.get(mat) + mat_base - fm.get(mat);
                   bead_prob = Math.exp(match_back + norm);
                   if(bead_prob>=threshold2){
                        double pow = bead_prob;
                        bead1.put(row, pow);
                        bead2.put(col, pow);
                        bead.put(row, col);
//                        System.out.println(row+" "+col+" "+pow+" "+"match");//print out here
                        highbead++;
                   }
                   log.push(match_back);
                   
               }
               //contract------
               if(bw.get(con)!=null){
                   contract_back = bw.get(con) + con_base - fc.get(con);
                   log.push(contract_back);
                 
               }
               //expand--------
               if(bw.get(exp)!=null){
                   expand_back = bw.get(exp) + exp_base - fe.get(exp);
                   log.push(expand_back);
                
               }
               //log-add-list--
                s = 1;
               Stack log2 = new Stack();
               if(!log.empty()){
                    max = (Double)log.pop();
                    while(!log.empty()){
                        pop = (Double)log.pop();
                        if(pop>max){
                            log2.add(max);
                            max = pop;
                        }
                        else log2.add(pop);
                    }
                    while(!log2.empty()){
                        pop = (Double)log2.pop();
                        s+= (double)Math.exp(pop - max);
                    }               
                    double prob = max + (double)Math.log(s);
                    double total = prob + norm;
                    if(total>conf_threshold){
                        bw.put(rowcol, prob);         
                    }
                    
                }
               //if(bw.get(rowcol)!=null)
                    //System.out.println(row+" "+col+" "+bw.get(rowcol));
               ns--;
           }
       }
       
       
       System.out.println("Total of Association High beads  "+highbead);
       System.out.println("-----------------------------------------");
       System.out.println("");
               
      // elapsedTimeMillis = System.currentTimeMillis()-start;
      // System.out.println("Backward Plus pass time "+(elapsedTimeMillis/1000F)+" seconds");
    }
    
    
    public void ReadNode(String FileName, int Ns1, int Ns2) {
        int ns=0;
        int n1 = -1;
        int n2=0;
        try {
//            FileInputStream is1=new FileInputStream(FileName);
//            Scanner input1=new Scanner(is1);
            
BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FileName)), Configs.ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {
            
//            while(input1.hasNextLine()) {
//                String line=input1.nextLine();
                
                
                ns++;
                StringTokenizer words;
                words=new StringTokenizer(line);
                {
                    String s1 = words.nextToken();
                    int node1 = Integer.parseInt(s1);
                    String s2 = words.nextToken();
                    int node2 = Integer.parseInt(s2);
                    Node1.put(ns, node1);
                    Node2.put(ns, node2);
                   
                }
                
            }
          
            node = ns;
            
//            is1.close();
//            input1.close();

reader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
          }
    }
}