package Model;

import Control.Wasd;
import View.Blocco;
import View.Nemico;
import View.PannelloDiGioco;
import View.Personaggio;
import View.Proiettile;

import static View.Personaggio.BOY_START_Y;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import View.PiccoloVirus;
/**
 *
 * @author Quick Fix Demons
 */

//riceve il pannello di Gioco dal main, crea il personaggio e gestisce il gameloop
public class GestoreGioco extends Thread {
    
    //attributi
    public static String direzione = "stop";
    public static ArrayList movimenti = new ArrayList();
    private boolean azione;
    public static String direction= "fermo";
    private Personaggio hero;
    private ArrayList<Nemico> nemici;
    private ArrayList<PiccoloVirus> virus;
    private IntelligenzaArtificiale nuv;
    private PannelloDiGioco panGioco;
    private Wasd inputTastiera;
    private GestioneLivelli gl;
    private GestoreCollisioni c;
    //imposto il tempo tra un frame e l'altro
    private static final int TEMPO_PAUSA_LOOP=20;
    public static int nMorti;
    public static int tempoInvincibile = 0;
    
    //costruttore
    public GestoreGioco(PannelloDiGioco p, Wasd i) {
        
        inputTastiera = i; //
        nuv = new IntelligenzaArtificiale();
        hero = new Personaggio();
        gl = new GestioneLivelli();
        c = new GestoreCollisioni();
        nMorti = 0;

        try {
            gl.CaricaLivello();
        } catch (IOException ex) {
            Logger.getLogger(GestoreGioco.class.getName()).log(Level.SEVERE, null, ex);
        }
        //salvo il parametro pannello di gioco e ci aggiungo il personaggio appena creato
        panGioco = p;
        panGioco.impostaLivelli(gl);
        panGioco.aggiungiPersonaggio(hero);
        //l'animazione puo' iniziare
        azione  = true;
    }

    public int getnMorti() {
        return nMorti;
    }

    public void setnMorti(int nMorti) {
        this.nMorti = nMorti;
    }
    
    //il gameloop lo metto nel metodo run che e' caratteristico dei thread e viene
    //invocato automaticamente quando invoco il metodo start() su un oggetto della
    //classe GestoreGioco
    @Override
    public void run() {
        while(azione){
            if(tempoInvincibile > 0) {
                tempoInvincibile--;
            }
            
            ArrayList<Nemico> nemici = gl.getArrayNemici(); 
            for(Nemico n : nemici) {
                if(n.getMorto() == false) {
                    direction=nuv.Insegui(hero, n);  //gestione della IA per l'inseguimento
                
                    // Da modificare implementando movimento verticale
                    if(direction != null) {
                        switch(direction){
                            case "corridx":
                                n.correDx();
                                break;
                            case "corrisx":
                                n.correSx();
                                break;
                            case "fermosx":
                                n.fermoSx();
                                break;
                            case "fermoDx":
                                n.fermoDx();
                                break;
                            case "fermox":
                                n.fermoUp();
                                break;
                            case "corre down":
                                n.correDown();
                                break;
                            case "corre up":
                                n.correUp();
                                break;
                        }
                    }
                    c.Collisioni(hero, n);
                    Integer collisione = c.collisioneProiettile(panGioco.getVettProiettili(), n);
                    if(collisione != null) {
                        panGioco.cancellaProiettile(collisione);
                    }
                }
            }
            ArrayList<PiccoloVirus> virus = gl.getVirus();
            for(PiccoloVirus v : virus) {
                if(v.getMorto() == false) {
                    direction=nuv.Insegui(hero, v);  //gestione della IA per l'inseguimento
                
                    // Da modificare implementando movimento verticale
                    if(direction != null) {
                        switch(direction){
                            case "corridx":
                                v.correDx();
                                break;
                            case "corrisx":
                                v.correSx();
                                break;
                            case "fermosx":
                                v.fermoSx();
                                break;
                            case "fermoDx":
                                v.fermoDx();
                                break;
                            case "fermox":
                                v.fermoUp();
                                break;
                            case "corre down":
                                v.correDown();
                                break;
                            case "corre up":
                                v.correUp();
                                break;
                        }
                    }
                    c.Collisioni(hero, v);
                    Integer collisione = c.collisioneProiettile(panGioco.getVettProiettili(), v);
                    if(collisione != null) {
                        panGioco.cancellaProiettile(collisione);
                    }
                }
            }
            if(nMorti == nemici.size() + virus.size()) {
                nMorti=0;
                gl.prossimoLivello();
                panGioco.setSfondo(gl.getSfondoCorrente());
                panGioco.updateScore(gl.getLivello());
            }

            c.CollisioniContorno(hero);
            

            switch (direzione) {
                case "dx":
                    if(c.BloccoCollisioniEst(gl.getMappa(), hero) == null)
                        hero.correDx();
                    else
                        hero.fermoDx();
                    break;
                case "sx":
                    if(c.BloccoCollisioniOvest(gl.getMappa(), hero) == null)
                        hero.correSx();
                    else
                        hero.fermoSx();
                    break;
                case "up":
                    if(c.BloccoCollisioniSud(gl.getMappa(), hero) == null)
                        hero.correUp();
                    else
                        hero.fermoDown();
                    break;
                case "down":
                    if(c.BloccoCollisioniNord(gl.getMappa(), hero) == null)
                        hero.correDown();
                    else
                        hero.fermoUp();
                    break;
                case "fermoDx":
                    hero.fermoDx();
                    break;
                case "fermoSx":
                    hero.fermoSx();
                    break;
                case "fermoUp":
                    hero.fermoUp();
                    break;
                case "fermoDown":
                    hero.fermoDown();
                    break;
            }
            
            
            
            //gestione sparo
            if (movimenti.contains("spara")) {
                panGioco.aggiungiProiettile(hero.spara()); //hero.spara() mi ritorna un oggetto proiettile
                movimenti.remove("spara");
            }
            
            //gestite tutte le possibili azioni del protagonista ridisegno
            //la scena
            panGioco.repaint();

            try {
                Thread.sleep(TEMPO_PAUSA_LOOP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}