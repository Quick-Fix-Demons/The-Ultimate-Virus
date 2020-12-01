/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.*;
import View.Nemico;
import View.Personaggio;
import View.PannelloDiGioco;
import View.Proiettile;
import View.PiccoloVirus;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * @author Quick Fix Demons
 */
public class GestoreCollisioni {
    // Blocco
    // Nemici
    // Proiettili
    private Rectangle bloccoNord = new Rectangle();
    private Rectangle bloccoSud = new Rectangle();
    private Rectangle bloccoEst = new Rectangle();
    private Rectangle bloccoOvest = new Rectangle();
    private Rectangle personaggioNord = new Rectangle();
    private Rectangle personaggioSud = new Rectangle();
    private Rectangle personaggioEst = new Rectangle();
    private Rectangle personaggioOvest = new Rectangle();
    private boolean perso=false;
    private boolean invincibile=false;
    
    //private GestorePunteggio gestPunt; DA IMPLEMENTARE


    public void Collisioni(Personaggio hero,Nemico cattivo){

        //if(hero.getScudo() == false){
            if(cattivo.getContornoPersonaggio().intersects(hero.getContornoPersonaggio())){ //se il cattivo tocca l'eroe
                //fai morire l'eroe.
                if(invincibile == true) {
                    if(GestoreGioco.tempoInvincibile <= 0) {
                        invincibile = false;
                        GestoreGioco.tempoInvincibile = 0;
                    }
                }
                if(invincibile == false) {
                    hero.setCurrentY(1100); //lo faccio tornare allo spawn
                    hero.setCurrentX(670); // lo faccio tornare allo spawn perchè è morto

                    hero.setVita(hero.getVita()-1);
                    invincibile = true;
                    GestoreGioco.tempoInvincibile = 60;
                }
                if(hero.getVita()<=0){
                   hero.setVita(0);
                }
            }
        //else{
        //    hero.setScudo(false);  
        //}
    }
    
    public void Collisioni(Personaggio hero,PiccoloVirus cattivo){

        //if(hero.getScudo() == false){
            if(cattivo.getContornoPersonaggio().intersects(hero.getContornoPersonaggio())){ //se il cattivo tocca l'eroe
                if(invincibile == true) {
                    if(GestoreGioco.tempoInvincibile <= 0) {
                        invincibile = false;
                        GestoreGioco.tempoInvincibile = 0;
                    }
                }
                //fai morire l'eroe.
                if(invincibile == false) {
                    hero.setCurrentY(1100); //lo faccio tornare allo spawn
                    hero.setCurrentX(670); // lo faccio tornare allo spawn perchè è morto

                    hero.setVita(hero.getVita()-1);
                    invincibile = true;
                    GestoreGioco.tempoInvincibile = 60;
                }
                if(hero.getVita()<=0){
                   hero.setVita(0);
                }
            }
           
        //}
        //else{
        //    hero.setScudo(false);  
        //}
    }
    
    public Integer collisioneProiettile(Proiettile[] p, Nemico cattivo){
        int j = 0;
        for(Proiettile i : p) {
            //se il proiettile tocca il nemico
            if(i != null) {
                if(cattivo.getMorto() == false) {
                    if(i.getContornoProiettile().intersects(cattivo.getContornoPersonaggio())) { 
                        cattivo.setVita(cattivo.getVita()-1);
                        return j;
                    }
                    if(cattivo.getVita() <= 0) {
                        cattivo.setMorto(true);
                        GestoreGioco.nMorti++;
                    }
                }
                if(cattivo.getMorto()==true){
                    cattivo.setCurrentY(12345);
                    cattivo.setCurrentX(12345);
                }
            }
            j++;
        }
        return null;
    }

    
    public Integer collisioneProiettile(Proiettile[] p, PiccoloVirus cattivo){
        int j = 0;
        for(Proiettile i : p) {
            //se il proiettile tocca il nemico
            if(i != null) {
                if(cattivo.getMorto() == false) {
                    if(i.getContornoProiettile().intersects(cattivo.getContornoPersonaggio())) { 
                        cattivo.setVita(cattivo.getVita()-1);
                        return j;
                    }
                    if(cattivo.getVita() <= 0) {
                        cattivo.setMorto(true);
                        GestoreGioco.nMorti++;
                    }
                }
                if(cattivo.getMorto()==true){
                    cattivo.setCurrentY(12345);
                    cattivo.setCurrentX(12345);
                }
            }
            j++;
        }
        return null;
    }
    

    public Blocco BloccoCollisioniSud(Blocco mappa[][], Personaggio hero){
        for(int i=0;i<GestioneLivelli.COLONNE;i++){
            for(int j=0;j<GestioneLivelli.RIGHE;j++){
                //System.out.println(mappa[i][j]);
                if(mappa[i][j] != null) {
                    //System.out.println("Preso Blocco a X: " + mappa[i][j].getContornoBlocco().getX() + " Y: " + mappa[i][j].getContornoBlocco().getY());
                    personaggioSud.setBounds((int) hero.getContornoPersonaggio().getX() + 48,(int) hero.getContornoPersonaggio().getMaxY(), 
                            35, 8); 
                    //con questo comando creo il rettangolo a sud del personaggio, alto 8 pixel e largo quanto il personaggio 
                    //meno 8 pixel (per questioni che non sto a spiegarvi, ma si poteva anche evitare il -8 pixel)
                    bloccoSud.setBounds(mappa[i][j].getX(), mappa[i][j].getY(), 128, 128);
                    if(personaggioSud.intersects(bloccoSud)) {
                        return mappa[i][j];
                    }
                }
            }
        }
        return null;
    }
    
    public Blocco BloccoCollisioniNord(Blocco mappa[][], Personaggio hero){
        for(int i=0;i<GestioneLivelli.COLONNE;i++){
            for(int j=0;j<GestioneLivelli.RIGHE;j++){
                //System.out.println(mappa[i][j]);
                if(mappa[i][j] != null) {
                    //System.out.println("Preso Blocco a X: " + mappa[i][j].getContornoBlocco().getX() + " Y: " + mappa[i][j].getContornoBlocco().getY());
                    personaggioNord.setBounds((int) hero.getContornoPersonaggio().getX()+48,(int) hero.getContornoPersonaggio().getMaxY()+4, 
                            35, 8);
                    
                    bloccoNord.setBounds(mappa[i][j].getX(), mappa[i][j].getY()+132, 128, 128);
                    if(personaggioNord.intersects(bloccoNord)) {
                        return mappa[i][j];
                    }
                }
            }
        }
        return null;
    }
        
    public Blocco BloccoCollisioniEst(Blocco mappa[][], Personaggio hero){
        for(int i=0;i<GestioneLivelli.COLONNE;i++){
            for(int j=0;j<GestioneLivelli.RIGHE;j++){
                //System.out.println(mappa[i][j]);
                if(mappa[i][j] != null) {
                    //System.out.println("Preso Blocco a X: " + mappa[i][j].getContornoBlocco().getX() + " Y: " + mappa[i][j].getContornoBlocco().getY());
                    personaggioEst.setBounds((int) hero.getContornoPersonaggio().getX()+60,(int) hero.getContornoPersonaggio().getMinY()+10, 
                            35, 118); 
                    bloccoEst.setBounds(mappa[i][j].getX(), mappa[i][j].getY(), 128, 128);
                    if(personaggioEst.intersects(bloccoEst)) {
                        return mappa[i][j];
                    }
                }
            }
        }
        return null;
    }
    
    public Blocco BloccoCollisioniOvest(Blocco mappa[][], Personaggio hero){
        for(int i=0;i<GestioneLivelli.COLONNE;i++){
            for(int j=0;j<GestioneLivelli.RIGHE;j++){
                //System.out.println(mappa[i][j]);
                if(mappa[i][j] != null) {
                    //System.out.println("Preso Blocco a X: " + mappa[i][j].getContornoBlocco().getX() + " Y: " + mappa[i][j].getContornoBlocco().getY());
                    personaggioOvest.setBounds((int) hero.getContornoPersonaggio().getX()+24,(int) hero.getContornoPersonaggio().getMinY()+10, 
                            35, 118); 
                    bloccoOvest.setBounds(mappa[i][j].getX(), mappa[i][j].getY(), 128, 128);
                    if(personaggioOvest.intersects(bloccoOvest)) {
                        return mappa[i][j];
                    }
                }
            }
        }
        return null;
    }
    public void CollisioniContorno(Personaggio hero){
        if(hero.getCurrentY()>=590){
           //System.out.println("si fermaaa");
           hero.setCurrentY(590);

        }
        else if (hero.getCurrentY()<=0){
            //System.out.println("si ferma");
            hero.setCurrentY(0);
        }
        else if(hero.getCurrentX()>=1160){
            hero.setCurrentX(1160);
            //System.out.println("si ferma");
        }
        else if(hero.getCurrentX()<=0){
           // System.out.println("si ferma");
            hero.setCurrentX(0);
        }
    }
}