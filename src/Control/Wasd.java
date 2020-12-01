/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Quick Fix Demons
 */
public class Wasd implements KeyListener {
    
    //attributi
    private int c;
    //costruttore
    public Wasd() {
    }
    
    //metodi interfaccia
    @Override
    public void keyTyped(KeyEvent e) {  //si accorge se il tasto è stato premuto E rilasciato
    }

    @Override
    public void keyPressed(KeyEvent e) { //scatta quando il tasto viene premuto 

        try{
            c=e.getKeyCode();
        }
        catch(Exception ee) {}
        
        // →↓↑← //
        if(c==39) {
            GestoreGioco.direzione=("dx");
//                System.out.println("premuto -->");
        }
        if(c==37) {
            GestoreGioco.direzione=("sx");
//                System.out.println("premuto <--");
        }
        if(c==38) {
            GestoreGioco.direzione=("down");
//                System.out.println("premuto <--");
        }
        if(c==40) {
            GestoreGioco.direzione=("up");
//                System.out.println("premuto <--");
        }
        
        if(c==32) {
            GestoreGioco.movimenti.add("spara");
        }
        
        // Wasd //
        if(c==68) {
            GestoreGioco.direzione=("dx");
//                System.out.println("premuto -->");
        }
        if(c==65) {
            GestoreGioco.direzione=("sx");
//                System.out.println("premuto <--");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { //scatta quando il tasto viene rilasciato
        try{
            c=e.getKeyCode();
        }
        catch(Exception ee) {}
        
        if(c==39) {
            GestoreGioco.direzione=("fermoDx");
//                System.out.println("rilasciato -->");
        }
        if(c==37) {
            GestoreGioco.direzione=("fermoSx");
//                System.out.println("rilasciato <--");
        }
        if(c==68) {
            GestoreGioco.direzione=("fermoDx");
//                System.out.println("rilasciato -->");
        }
        if(c==65) {
            GestoreGioco.direzione=("fermoSx");
//                System.out.println("rilasciato <--");
        }
        if(c==38) {
            GestoreGioco.direzione=("fermoUp");
//                System.out.println("premuto <--");
        }
        if(c==40) {
            GestoreGioco.direzione=("fermoDown");
//                System.out.println("premuto <--");
        }
            
    }
    
}
