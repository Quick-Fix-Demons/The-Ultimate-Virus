package View;

import Model.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Quick Fix Demons
 */
public class PannelloDiGioco extends JPanel {
    public static final int HEIGHT=730;
    public static final int WIDTH=1290;
    private final int MAXPROIETTILI=3; 
    private BufferedImage Sfondo;
    private Personaggio hero;
    private ArrayList listaNemici; //uso una lista che e' meglio di un vettore in questo caso
    private Proiettile[] vettProiettili = new Proiettile[MAXPROIETTILI];
    private int numProiettili = 0; //totale dei proiettili sullo schermo
    private Blocco[][] mappa = new Blocco[GestioneLivelli.RIGHE][GestioneLivelli.COLONNE];
    private GestioneLivelli gl;
    private String livello = "Livello: 1";
    private String best = "Best Score: 1";
    private int lvlMigliore = 1;
    
    //costruttore
    public PannelloDiGioco() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //ho letto che doublebufferare un JPanel dovrebbe migliorare la qualita'
        //delle animazioni, se volete approfondire http://www.anandtech.com/show/2794/2
        this.setDoubleBuffered(true);
        try {
            lvlMigliore = GestoreScore.leggiScore();
        } catch (IOException ex) {
            Logger.getLogger(PannelloDiGioco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Metodi operativi
    public void impostaLivelli(GestioneLivelli gl) {
        this.gl = gl;
        Sfondo = gl.getSfondoCorrente();
    }
    
    public void aggiungiPersonaggio(Personaggio p) {
        this.hero = p;
    }
    
    public void addNemico(Personaggio p) {
        listaNemici.add(p);
    }

    public void updateScore(int lvl) {
        livello = "Livello: " + lvl;
        if(lvl > this.lvlMigliore) {
            lvlMigliore = lvl;
        }
    }
    
    public void removeNemico(Nemico n) {
        
    }

    public void cancellaProiettile(Integer i){
        for (int j=i; j<MAXPROIETTILI-1; j++) {
            vettProiettili[j]=vettProiettili[j+1];
        }
        vettProiettili[MAXPROIETTILI-1] = null;
        numProiettili--;
    }

    public Proiettile[] getVettProiettili() {
        return vettProiettili;
    }
    
    public void aggiungiProiettile(Proiettile p) {
        if (numProiettili<MAXPROIETTILI) {
            vettProiettili[numProiettili] = p;
            numProiettili++;
        }
    }

    public void setSfondo(BufferedImage sfondo) {
        this.Sfondo = sfondo;
    }
        
    //override del metodo paintComponent fondamentale per disegnare tutta la scena
    @Override
    protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //lo casto in un oggetto Graphics2D in modo da poter usare l'antialiasing
            Graphics2D g2=(Graphics2D)g;
            
            mappa = gl.getMappa();
            
            //uso l'antialiasing per disegnare linee e immagini maggiormente definite
            //posso farlo xke uso un oggetto Graphics2D
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //disegno lo sfondo del "livello" precedentemente caricato
            g2.drawImage(this.Sfondo,0,0, null);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g2.setColor(Color.white);
            g2.drawString(livello, 10, 40);
            g2.drawString("Vita Giocatore: " + hero.getVita(), 10, 80);
            g2.drawString("Best Score: " + lvlMigliore, 10, 120);
            if(hero.morto(hero)==true){
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
                g2.setColor(Color.white);
                g2.drawString("U DEATH", 500, 400);
                try {
                    GestoreScore.salvaFile(lvlMigliore);
                } catch (IOException ex) {
                    Logger.getLogger(PannelloDiGioco.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            /*Blocco blocchetto = new Blocco("blocco", 30, 60);
            g2.drawImage(blocchetto.getTexture(), blocchetto.getX(), blocchetto.getY(), null);*/
           
            for(int i = 0; i<GestioneLivelli.COLONNE; i++) {
                for(int j = 0; j<GestioneLivelli.RIGHE; j++) {
                    if(mappa[i][j] != null)
                        g2.drawImage(mappa[i][j].getTexture(), mappa[i][j].getX(), mappa[i][j].getY(), null);
                }
            }
            
            
            ArrayList<Nemico> nemici = gl.getArrayNemici();
            for(int i = 0; i<nemici.size(); i++) {
                g2.drawImage(nemici.get(i).getImmagineCorrente(), nemici.get(i).getCurrentX(), nemici.get(i).getCurrentY(), null);
            }

            ArrayList<PiccoloVirus> virus = gl.getVirus();
            for(int i = 0; i<virus.size(); i++) {
                g2.drawImage(virus.get(i).getImmagineCorrente(), virus.get(i).getCurrentX(), virus.get(i).getCurrentY(), null);
            }
            //disegno una griglia di celle giusto per dare un'idea del mondo in cui si muove il personaggio
            /*for(int i=0; i<20; i++){
                g2.drawLine(0, i*128, WIDTH, i*128);
                g2.drawLine(i*128, 0, i*128, HEIGHT);
            }*/

            //disegno il personaggio
            g2.drawImage(hero.getImmagineCorrente(),hero.getCurrentX(),hero.getCurrentY(),null);

            //disegno i proiettili
            if (numProiettili>0) {
                for (int i=0; i<numProiettili; i++) {
                    if(vettProiettili[i] != null) {
                        if (vettProiettili[i].isOnScreen()) { //se il proiettile e' ancora sullo schermo lo faccio avanzare
                            vettProiettili[i].avanza();
                            g2.drawImage(vettProiettili[i].getBullet(),vettProiettili[i].getPosX(),vettProiettili[i].getPosY(),null);
                        }
                        else {
                            //altrimenti rimuovo il proiettile dal vettore che poi shifto a sx di 1
                            //liberando l'ultima posizione per un nuovo proiettile
                            //inoltre decremento il numProiettili
                            cancellaProiettile(i);
                        }
                    }
                }
            }
    }
}