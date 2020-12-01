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
public class PowerUp {
    private String img;
    private int x;
    private int y;
    private BufferedImage cura, difesa, velocita, forza;
    private Rectangle contornoCura, contornoVel, contornoDif, contornoFor;

    private int i=0;

    public PowerUp(int x, int y){
        this.contornoCura = new Rectangle(x,y,224,224);
        this.contornoDif = new Rectangle(x,y,224,224);
        this.contornoFor = new Rectangle(x,y,224,224);
        this.contornoVel = new Rectangle(x,y,224,224); 

        try {
            cura = ImageIO.read(getClass().getResource("../images/cura.png"));
            difesa = ImageIO.read(getClass().getResource("../images/difesa.png"));
            velocita = ImageIO.read(getClass().getResource("../images/velocita.png"));
            forza = ImageIO.read(getClass().getResource("../images/forza.png"));
        } catch (IOException ex) {
            Logger.getLogger(Blocco.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        this.x = x;
        this.y = y;
    }
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

    public BufferedImage getCura() {
        return cura;
    }

    public BufferedImage getDifesa() {
        return difesa;
    }

    public BufferedImage getForza() {
        return forza;
    }

    public BufferedImage getVelocita() {
        return velocita;
    }

    public void setCura(BufferedImage cura) {
        this.cura = cura;
    }

    public void setDifesa(BufferedImage difesa) {
        this.difesa = difesa;
    }

    public void setForza(BufferedImage forza) {
        this.forza = forza;
    }

    public void setVelocita(BufferedImage velocita) {
        this.velocita= velocita;
    }

    public Rectangle getContornoCura() {
        return contornoCura;
    }

    public Rectangle getContornoDifesa() {
        return contornoDif;
    }

    public Rectangle getContornoVelocita() {
        return contornoVel;
    }
    public Rectangle getContornoForza() {
        return contornoFor;
    }

    public void setContornoCura(Rectangle contornoCura) {
        this.contornoCura = contornoCura;
    }

    public void setContornoDifesa(Rectangle contornoDif) {
        this.contornoDif = contornoDif;
    }

    public void setContornoVelocita(Rectangle contornoVel) {
        this.contornoVel = contornoVel;
    }
    public void setContornoForza(Rectangle contornoFor) {
        this.contornoFor = contornoFor;
    }

    public void speedUp(Personaggio hero){
        
        System.out.println("SPeed");
            do{
                //hero.setSpostamento(20);
                i++;
                System.out.println(i);
            }while(i<10);
            

        
    }

    public void scudoAMe(Personaggio hero){
        System.out.println("Ciccio passami uno shieldino");
        //hero.setScudo(true);
        //System.out.println(hero.getScudo());
    }

    public void removePower(PowerUp powerup){
        setX(1000);
        
    }
}