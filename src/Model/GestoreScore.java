/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alessio
 */
public class GestoreScore {
    public static int score = 1;
    
    public static void salvaFile(int score) throws IOException {
        FileWriter fw = new FileWriter("best.txt");
        fw.write(String.valueOf(score));
        fw.close();
    }
    
        
    public static int leggiScore() throws IOException {
        File f = new File("best.txt");
        FileInputStream fis = new FileInputStream(f);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        
        score = (int) Float.parseFloat(br.readLine());
        br.close();
        
        return score;
    }
}
