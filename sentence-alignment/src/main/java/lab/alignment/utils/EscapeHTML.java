/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import lab.alignment.configs.Configs;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author s1420211
 */
public class EscapeHTML {
    public static void escapeHTML(String inFile, String outFile)throws IOException{
    
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFile)), Configs.ENCODING));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inFile)), Configs.ENCODING));

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(StringEscapeUtils.escapeHtml(line));
            writer.write(System.lineSeparator());
        }
        writer.close();
        
        reader.close();
    
    }
}
