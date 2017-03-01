package lab.alignment.approaches.evs.length;
import java.io.*;
import java.util.*;
import java.util.Scanner;
/**
 *Trieu Hai Long
 **/
public class SentLengthRatio {
    
    protected Map<Integer,Double> Rs =new HashMap<Integer, Double>();
    protected Map<Integer,Double> Rt =new HashMap<Integer, Double>();
    protected Map<Integer,Double> Pair =new HashMap<Integer, Double>();
    protected double Rpoi;

    
    public void setRpoi(double r){
        Rpoi = r;
    }
    
    public double getRpoi(){
        return Rpoi;
    }
    public Map<Integer,Double> getRs(){
        return Rs;
    }
    public Map<Integer,Double> getRt(){
        return Rt;
    }
    public Map<Integer,Double> getPair(){
        return Pair;
    }
    
    public void ComputeSentLengthRatio(IOData a,IOData b){
        Map<Integer,Integer> SentLengthE =new HashMap<Integer,Integer>();
        Map<Integer,Integer> CountLengthE =new HashMap<Integer,Integer>();
        Map<Integer,Integer> SentLengthV =new HashMap<Integer,Integer>();
        Map<Integer,Integer> CountLengthV =new HashMap<Integer,Integer>();
        long start = System.currentTimeMillis();
        SentLengthE = a.getSentLength();
        SentLengthV = b.getSentLength();
        int ns1 = a.getNs();
        int ns2 = b.getNs();
        int nw1 = a.getNword();
        int nw2 = b.getNword();
        double r1 = (double)nw1/ns1;
        double r2 = (double)nw2/ns2;
        double r = r2/r1;
        this.setRpoi(r);
        
        int length,num;
        CountLengthE = a.getCountLength();
        CountLengthV = b.getCountLength();
        for(int i=1;i<=ns1;i++){
            length = SentLengthE.get(i);
            if(CountLengthE.get(length)!=null){
                num = CountLengthE.get(length);
                CountLengthE.put(length, num+1);
            }
            else CountLengthE.put(length, 1);
        }
      
        double tp;
        int count;
        
        Iterator it = CountLengthE.entrySet().iterator();
        //System.out.println("source file");
        while(it.hasNext()){
            Map.Entry m =(Map.Entry)it.next();
            count = (Integer)m.getValue();
            length = (Integer)m.getKey();
            //System.out.println(count);
            tp = (double)count/ns1;        
            Rs.put(length, (double)-Math.log(tp));
        }
        
     
        for(int i=1;i<=ns2;i++){
            length = SentLengthV.get(i);
            if(CountLengthV.get(length)!=null){
                num = CountLengthV.get(length);
                CountLengthV.put(length, num+1);
            }
            else CountLengthV.put(length, 1);
        }
        
        it = CountLengthV.entrySet().iterator();
        //System.out.println("target file");
        while(it.hasNext()){
            Map.Entry m =(Map.Entry)it.next();
            count = (Integer)m.getValue();
            length = (Integer)m.getKey();
            //System.out.println(count);
            tp = (double)count/ns2;        
            Rt.put(length, (double)-Math.log(tp));
        }
        //------pair
        double []Norm = new double [100000];
        int l,l1,l2;
        for (int k=1;k<ns2;k++){
            l  = SentLengthV.get(k) + SentLengthV.get(k+1);
            double temp = 0;
            for (int i = 1; i< l; i++){
                if((Rt.get(i)!=null)&&(Rt.get(l-i)!=null)){
                    temp += Math.exp(-Rt.get(i)-Rt.get(l-i));
                }
            }
            Norm[l] = (double)- Math.log(temp);
        }
        
        double Pairk;
        double []P = new double [ns2+1];
        for (int k = 1; k<ns2; k++){
            l1 = SentLengthV.get(k);
            l2 = SentLengthV.get(k+1);
            l = l1+l2;
            P[k] = Rt.get(l1) + Rt.get(l2);
            int bool=1;
            int j=2;
            while((j<=ns2) &&(bool==1)){
                if (SentLengthV.get(j)==l2){
                    P[k] -= Norm[l];
                    bool=0;
                }       
                j++;
            }
        }
        for (int k = 1; k<ns2; k++){
            Pair.put(k, P[k]);
        }
        
    }
 
}
