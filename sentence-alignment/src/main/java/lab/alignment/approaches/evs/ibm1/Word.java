/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.approaches.evs.ibm1;

/**
 *
 * @author Am
 */
public class Word implements Comparable {
    private String sword; //noi dung tu
    private int nword; //so lan xuat hien
    private int total;
 
    public Word (String sword,int nword,int total){
        
        this.sword = sword;
        this.nword = nword;
        this.total = total;
        
        
    }
    public Word (){

        this.sword = null;
        this.nword = 0;
        this.total=0;



    }
    
   
    
   

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the sword
     */
    public String getSword() {
        return sword;
    }

    /**
     * @param sword the sword to set
     */
    public void setSword(String sword) {
        this.sword = sword;
    }

    /**
     * @return the nword
     */
    public int getNword() {
        return nword;
    }

    /**
     * @param nword the nword to set
     */
    public void setNword(int nword) {
        this.nword = nword;
    }



    @Override
public int compareTo(Object otherword){

        if(otherword instanceof Word){

            throw new ClassCastException("Not a valid Word object!!");

        }

        Word tempCar = (Word)otherword;
        if(this.getNword() > tempCar.getNword()){

            return 1;

        }else if(this.getNword() < tempCar.getNword()){

            return -1;

        }else{

            return 0;

        }

    }
    
     
}

