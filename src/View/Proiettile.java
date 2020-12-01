/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Quick Fix Demons
 */
public class Proiettile {
    //attributi
    private BufferedImage bullet;
    private int posX, posY;
    private int velocita = 20;
    private int potenza; //la carica distruttiva del proiettile
    private Rectangle contornoProiettile; //rettangolo usato per gestire le collisioni
    private String verso;
    
    //private boolean isOnScreen = false;
    
    //costruttore
    public Proiettile(int x, int y, String v) {
        setVerso(v);
        setPosX(x);
        setPosY(y);
        try {
            //System.out.println(v);
            switch(v) {
                case "up":bullet=ImageIO.read(getClass().getResource("../images/proiettile_up.png"));
                        break;
                case "down":bullet=ImageIO.read(getClass().getResource("../images/proiettile_down.png"));
                        break;
                case "sx":bullet=ImageIO.read(getClass().getResource("../images/proiettile_left.png"));
                        break;
                case "dx":bullet=ImageIO.read(getClass().getResource("../images/proiettile_right.png"));
                        break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Proiettile.class.getName()).log(Level.SEVERE, null, ex);
        }
        contornoProiettile = new Rectangle(getPosX(),getPosY(),150,150);
        
    }
    
    //set-get
    private void setPosX(int x) {
        this.posX=x;
    }
    
    private void setPosY(int y) {
        this.posY=y;
    }
    
    public int getPosX() {
        return this.posX;
    }
    
    public int getPosY() {
        return this.posY;
    }
    
    public BufferedImage getBullet() {
        return this.bullet;
    }
    
    public Rectangle getContornoProiettile() {
        return contornoProiettile;
    }

    public int getVelocita() {
        return velocita;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }
    
    //metodi operativi
    public boolean isOnScreen() {
        if ((this.getPosX() > -80) && (this.getPosX() <= PannelloDiGioco.WIDTH) && (this.getPosY() > -80) && (this.getPosY() <= PannelloDiGioco.HEIGHT))
            return true;
        else {
            return false;
        }
    }
    
    public void avanza() {
        if (this.isOnScreen()) {
            switch (getVerso()) {
                case "dx":
                    setPosX(getPosX() + getVelocita());
                    contornoProiettile.setLocation(getPosX(), getPosY());
                    break;
                case "sx":
                    setPosX(getPosX() - getVelocita());
                    contornoProiettile.setLocation(getPosX(), getPosY());
                    break;
                case "up":
                    setPosY(getPosY() - getVelocita());
                    contornoProiettile.setLocation(getPosX(), getPosY());
                    break;
                case "down":
                    setPosY(getPosY() + getVelocita());
                    contornoProiettile.setLocation(getPosX(), getPosY());
                    break;
            }        
            //riposiziono coerentemente il rettangolo che borda il personaggio
            contornoProiettile.setLocation(getPosX(), getPosY());  
        }
    }
    
}
