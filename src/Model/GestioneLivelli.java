/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Quick Fix Demons
 */
public class GestioneLivelli {

    private BufferedImage SfondoCorrente;
    public static int RIGHE=6;
    public static int COLONNE=10;
    private int livello;
    private ArrayList<Nemico> arrayNemici = new ArrayList<>();
    private ArrayList<PiccoloVirus> virus = new ArrayList<>();
    private Blocco mappa[][];
    private int ultimoLivello;
    
    // Costruttore
    
    public GestioneLivelli(){
        mappa = new Blocco[COLONNE][RIGHE];
        ultimoLivello = 0;
        livello = 1;
    }
    
    // Set - Get
    
    public Blocco[][] getMappa() {
        return mappa;
    }

    public void setMappa(Blocco[][] mappa) {
        this.mappa = mappa;
    }

    public BufferedImage getSfondoCorrente() {
        return SfondoCorrente;
    }

    public void setSfondoCorrente(BufferedImage SfondoCorrente) {
        this.SfondoCorrente = SfondoCorrente;
    }
    
    public int getUltimoLivello() {
        return ultimoLivello;
    }

    public void setUltimoLivello(int ultimoLivello) {
        this.ultimoLivello = ultimoLivello;
    }

    public ArrayList<Nemico> getArrayNemici() {
        return arrayNemici;
    }

    public void removeNemico(Nemico n) {
        arrayNemici.remove(n);
    }

    public void setArrayNemici(ArrayList<Nemico> arrayNemici) {
        this.arrayNemici = arrayNemici;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public ArrayList<PiccoloVirus> getVirus() {
        return virus;
    }

    public void setVirus(ArrayList<PiccoloVirus> virus) {
        this.virus = virus;
    }
    
    // Metodi Operativi
   
    public void CaricaLivello() throws IOException{
        // Caricamento sfondo

        if(ultimoLivello == 0) {
            SfondoCorrente = ImageIO.read(getClass().getResource("../images/background.png"));
        }
        if(ultimoLivello == 1) {
            SfondoCorrente = ImageIO.read(getClass().getResource("../images/background2.png"));
        }
        if(ultimoLivello == 2) {
            SfondoCorrente = ImageIO.read(getClass().getResource("../images/background3.png"));
        }
        
        // Random Blocchi
        mappa[2][1] = new Blocco("blocco", 128*2, 128*1);
        mappa[7][1] = new Blocco("blocco", 128*7, 128*1);
        mappa[2][4] = new Blocco("blocco", 128*2, 128*4);
        mappa[7][4] = new Blocco("blocco", 128*7, 128*4);
        int casuale = (int) (Math.random() * 2);
        if (casuale == 0) mappa[2][1] = null;
        casuale = (int) (Math.random() * 2);
        if (casuale == 0) mappa[7][1] = null;
        casuale = (int) (Math.random() * 2);
        if (casuale == 0) mappa[2][4] = null;
        casuale = (int) (Math.random() * 2);
        if (casuale == 0) mappa[7][4] = null;
        
        // Randomizzazione Nemici
        int nemici = (int) ((livello * 0.5) + livello);
        //System.out.println(nemici);
        for(int i=0; i<nemici; i++) {
            casuale = (int) (Math.random() * 2);
            int max = 0;
            int min = 0;
            switch (casuale) {
                case 0: max = (int) (Math.random() * (128 * 4));
                    min = 0;
                    break;
                case 1: min = 128 * 7;
                    max = PannelloDiGioco.WIDTH-128;
                    break;
            }

            int x = (int) (Math.random()*((max-min)+1))+min;
            int y = (int) (Math.random()* ((128 - PannelloDiGioco.HEIGHT) + 1) + PannelloDiGioco.HEIGHT-128);
            //System.out.println(x);
            //System.out.println(y);
            arrayNemici.add(new Nemico(x, y));
            virus.add(new PiccoloVirus(x+10,y+10));
        }
    }
    
    public void prossimoLivello() {
        arrayNemici.clear();
        virus.clear();
        ultimoLivello++;
        if(ultimoLivello > 2) {
            ultimoLivello = 0;
        }
        livello++;
        try {
            CaricaLivello();
        } catch (IOException ex) {
            Logger.getLogger(GestioneLivelli.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
