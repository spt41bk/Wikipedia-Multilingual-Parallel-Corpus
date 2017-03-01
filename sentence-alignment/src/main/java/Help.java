
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s1420211
 */
public class Help {
    public static void help(String readmeFile)throws IOException{
        
         BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(readmeFile)), "utf-8"));

        String line;
        while ((line = reader.readLine()) != null) {
            //
            System.out.println(line);
        }
        
        reader.close();
        
    }
}
