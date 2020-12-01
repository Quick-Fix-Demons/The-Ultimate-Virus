/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Quick Fix Demons
 */
public class Blocco {
        
    //ATTRIBUTI
    private String img;
    private int x;
    private int y;
    private BufferedImage texture;
    private Rectangle contornoBlocco;
    
    //COSTRUTTORE
    public Blocco(String img, int x, int y) {
        this.contornoBlocco = new Rectangle(x,y,224,224);
        try {
            texture = ImageIO.read(getClass().getResource("../images/blocco.png"));
        } catch (IOException ex) {
            Logger.getLogger(Blocco.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = x;
        this.y = y;
    }
    
    //SET-GET

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public Rectangle getContornoBlocco() {
        return contornoBlocco;
    }

    public void setContornoBlocco(Rectangle contornoBlocco) {
        this.contornoBlocco = contornoBlocco;
    }
    
}
