/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import View.Personaggio;
import View.PiccoloVirus;
import View.Nemico;
/**
 *
 * @author Quick Fix Demons
 */
public class IntelligenzaArtificiale {
    private int contaPassi;
    private String movimento;
    private Personaggio hero;
    private Nemico cattivo;
   

    public IntelligenzaArtificiale() {
        this.contaPassi = 0;
        this.movimento = "no";
    }

    public int getContaPassi() {
        return contaPassi;
    }

    public String Insegui(Personaggio hero,Nemico cattivo) {
        int casuale = (int) (Math.random() * 2);
        if(casuale == 0) {
            if(hero.getCurrentX()>cattivo.getCurrentX()){
                cattivo.setOrientamento("dx");
                return "corridx";
            }
            else if(hero.getCurrentX()<cattivo.getCurrentX()){
                cattivo.setOrientamento("sx");
                return "corrisx";
            }
        }
        else {
            if(hero.getCurrentY()>cattivo.getCurrentY()){
                return "corre up";//corre up
            }
            else
                return "corre down";
        }
        return null;
    }
    public String Insegui(Personaggio hero,PiccoloVirus cattivo) {
        int casuale = (int) (Math.random() * 2);
        if(casuale == 0) {
            if(hero.getCurrentX()>cattivo.getCurrentX()){
                cattivo.setOrientamento("dx");
                return "corridx";
            }
            else if(hero.getCurrentX()<cattivo.getCurrentX()){
                cattivo.setOrientamento("sx");
                return "corrisx";
            }
        }
        else {
            if(hero.getCurrentY()>cattivo.getCurrentY()){
                return "corre up";//corre up
            }
            else
                return "corre down";
        }
        return null;
    }

    public String getMovimento() {
        return movimento;
    }

    public void setMovimento(String movimento) {
        this.movimento = movimento;
    }
}