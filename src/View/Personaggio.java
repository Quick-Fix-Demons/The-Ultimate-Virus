 package View;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Quick Fix Demons
 */
public class Personaggio {
    
    //ATTRIBUTI
    private int velocitaX = 40;
    private int vita=5;
    private double velocitaY = 40;
//    private double forzaGravita = 0.5;
    private int energia;
    private String orientamento; //da che parte è girato, mi serve per sparare coerentemente
                                 //con l'orientamento del personaggio
    double velYtemp = getVelocitaY();
    //setto una direzione iniziale fissa (dx)
    private int ultimaDirezione=KeyEvent.VK_RIGHT;

    //la soglia di mosse, la uso in setFrameNumber
    private static final int SOGLIA_CONTATORE_MOSSE=5;

    //conta il numero di cambi di immagine nell'animazione
    private int contatoreMosse=0;

    //e' un rettangolo che circonda il protagonista e si sposta con lui, serve
    //per gestire facilmente gli scontri con gli altri oggetti del gioco
    private Rectangle contornoPersonaggio;

    //SPOSTAMENTO e' lo spazio percorso sull'asse x dal personaggio con un passo
    private static final int SPOSTAMENTO=9;

    //frame corrente nell'animazione del personaggio
    private BufferedImage immagineCorrente;

    //dimensione dei buffer dell'animazione della corsa una cella per ogni disegno che
    //compone l'animazione
    private static final int DIMENSIONE_BUFFER_CORSA=7;

    //tutte le bufferedImages usate nell'animazione NB: due sono immagini singole
    //e due sono vettori di immagini
    private BufferedImage fermo_a_DX;
    private BufferedImage fermo_a_SX;
    
    private BufferedImage[] corri_a_Dx;
    private BufferedImage[] corri_a_Sx;
    
    private BufferedImage fermo_a_UP;
    private BufferedImage fermo_a_DOWN;
    
    private BufferedImage[] corri_a_Up;
    private BufferedImage[] corri_a_Down;

    //determina l'immagine corrente da usare nell'animazione di corsa
    private int numeroImmagineCorrente=0;

    //posizione iniziale del personaggio
    public static int BOY_START_X=640;
    public static int BOY_START_Y=360;

    //altezza del personaggio (usata anche per il rettangolo di contorno)
    private final int BOY_HEIGHT=128;

    //larghezza del personaggio (usata anche per il rettangolo di contorno)
    private final int BOY_WIDTH=128;

    //posizione attuale del personaggio sull'asse X (all'inizio corrisponde a 
    //BOY_START_X)
    private int currentX=BOY_START_X;

    //posizione attuale del personaggio sull'asse Y (all'inizio corrisponde a 
    //BOY_START_Y)
    private int currentY=BOY_START_Y;
    private boolean morte = false;
    
    //COSTRUTTORE
    public Personaggio(){
        //inizializzo i buffer di immagini (sono come dei vettori di immagini)
        //che contengono i disegni per l'animazione della corsa vs dx e sx
        orientamento = ("dx");
        corri_a_Sx=new BufferedImage[DIMENSIONE_BUFFER_CORSA];
        corri_a_Dx=new BufferedImage[DIMENSIONE_BUFFER_CORSA];
        corri_a_Up = new BufferedImage[DIMENSIONE_BUFFER_CORSA];
        corri_a_Down = new BufferedImage[DIMENSIONE_BUFFER_CORSA];

        //carico tutte le immagini che mi serviranno nel gioco
        loadFrames();

        //setto come posizione iniziale "fermo girato vs destra"
        immagineCorrente=fermo_a_DX;

        //inizializzo un rettangolo che sara' il "perimetro del personaggio, 
        //questo sara' molto utile quando vorro' gestire gli scontri
        //con altri oggetti sulla scena
        contornoPersonaggio=new Rectangle(currentX,currentY,BOY_WIDTH,BOY_HEIGHT);
    }
    
    //SET-GET
    public int getVelocitaX() {
        return velocitaX;
    }

    public void setVelocitaX(int velocita) {
        this.velocitaX = velocita;
    }
        
    public double getVelocitaY() {
        return velocitaY;
    }

    public void setVelocitaY(double velocitaY) {
        this.velocitaY = velocitaY;
    }

    public int getEnergia() {
        return energia;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }

    public int getVita() {
        return vita;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }
    
    private void setFrameNumber() {
        //devo inventarmi un meccanismo per ruotare le sei imaagini dell'animazione
        //e ricominciare da capo ciclicamente, non posso farlo con un "for" perche'
        //il giocatore potrebbe cambiare direzione in qualsiasi momento quindi
        //devo passare attraverso un calcolo matematico
		numeroImmagineCorrente  = contatoreMosse/SOGLIA_CONTATORE_MOSSE;
//                System.out.println("contatore mosse: " + contatoreMosse );
//                System.out.println("FrameCorrente (moveCounter/5): " + numeroImmagineCorrente );
		numeroImmagineCorrente %= 7;
//		System.out.println("FrameCorrente (currentFrame%=6): " + numeroImmagineCorrente );
                
                //azzero moveCounter prima di avere come resto della divisione valori maggiori 
                //di 6 che mi farebbero sbarellare l'animazione
		if(contatoreMosse>SOGLIA_CONTATORE_MOSSE*7){
			contatoreMosse=0;
		}
	}
    public boolean morto(Personaggio hero){
        morte=false;
        if(hero.getVita()==0){
            morte=true;
            return morte;
        }
        return morte;
    }

    //il frame dell'animazione visualizzato in questo istante
    public BufferedImage getImmagineCorrente(){
            return immagineCorrente;
    }

    //x-position del personaggio
    public int getCurrentX(){
        return currentX;
    }

    //y-position del personaggio
    public int getCurrentY(){
        return currentY;
    }
    
    public void setCurrentX(int x) {
        this.currentX=x;
    }
    
    public void setCurrentY(int y) {
        this.currentY=y;
    }

    //il rettangolo che circonda il personaggio
    public Rectangle getContornoPersonaggio() {
            return contornoPersonaggio;
    }

    public String getOrientamento() {
        return orientamento;
    }

    public void setOrientamento(String orientamento) {
        this.orientamento = orientamento;
    }
        
    //METODI OPERATIVI
    
    public void correDx() {
        if(morte == true) return;
        setOrientamento("dx");
        //aggiorno la posizione del personaggio che si sta spostando
        currentX=currentX+SPOSTAMENTO;

        //riposiziono coerentemente il rettangolo che borda il personaggio
        contornoPersonaggio.setLocation(currentX, currentY);

        //cambio l'immagine corrente dell'animazione
        setFrameNumber();
        immagineCorrente=corri_a_Dx[numeroImmagineCorrente];

        //incremento il contatore dei movimenti
        contatoreMosse++;
        
    }
    
    public void correUp() {
        if(morte == true) return;
        setOrientamento("down"); // Da mettere up
        //aggiorno la posizione del personaggio che si sta spostando
        currentY=currentY+SPOSTAMENTO;

        //riposiziono coerentemente il rettangolo che borda il personaggio
        contornoPersonaggio.setLocation(currentX, currentY);

        //cambio l'immagine corrente dell'animazione
        setFrameNumber();
        immagineCorrente=corri_a_Down[numeroImmagineCorrente]; // Corri_a_Up

        //incremento il contatore dei movimenti
        contatoreMosse++;
        
    }
    
    public void correDown() {
        if(morte == true) return;
        setOrientamento("up"); // Da mettere down
        //aggiorno la posizione del personaggio che si sta spostando
        currentY=currentY-SPOSTAMENTO;

        //riposiziono coerentemente il rettangolo che borda il personaggio
        contornoPersonaggio.setLocation(currentX, currentY);

        //cambio l'immagine corrente dell'animazione
        setFrameNumber();
        immagineCorrente=corri_a_Up[numeroImmagineCorrente]; // Corri_a_Down

        //incremento il contatore dei movimenti
        contatoreMosse++;
        
    }
    
    
    public void correSx() {
        if(morte == true) return;
        setOrientamento("sx");
        //aggiorno la posizione del personaggio che si sta spostando
        currentX=currentX-SPOSTAMENTO;

        //riposiziono coerentemente il rettangolo che borda il personaggio
        contornoPersonaggio.setLocation(currentX, currentY);

        //cambio l'immagine corrente dell'animazione
        setFrameNumber();
        immagineCorrente=corri_a_Sx[numeroImmagineCorrente];

        //incremento il contatore dei movimenti
        contatoreMosse++;
        
    }
    
    public void fermoUp() {
        setOrientamento("up");
        immagineCorrente=fermo_a_UP;
    }
    
    public void fermoDown() {
        setOrientamento("down");
        immagineCorrente=fermo_a_DOWN;
    }
    
    public void fermoDx() {
        setOrientamento("dx");
        immagineCorrente=fermo_a_DX;
    }
    
    public void fermoSx() {
        setOrientamento("sx");
        immagineCorrente=fermo_a_SX;
    }
    
    public Proiettile spara() {
        //creo un proiettile che viaggera' nel verso dell'orientamento del personaggio
        if(morte == true) return null;
        Proiettile p = new Proiettile(this.getCurrentX(), (this.getCurrentY()+20), this.getOrientamento());
        return p;
    }
    
    //carico tutte le immagini necessarie per l'animazione
    private void loadFrames() {
        try {
            fermo_a_DX=ImageIO.read(getClass().getResource("../images/idle_R.png"));
            fermo_a_SX=ImageIO.read(getClass().getResource("../images/idle_L.png"));
            fermo_a_UP=ImageIO.read(getClass().getResource("../images/idle_UP.png"));
            fermo_a_DOWN=ImageIO.read(getClass().getResource("../images/idle_DOWN.png"));

            corri_a_Dx[0]=ImageIO.read(getClass().getResource("../images/run_R0.png"));
            corri_a_Sx[0]=ImageIO.read(getClass().getResource("../images/run_L0.png"));

            corri_a_Dx[1]=ImageIO.read(getClass().getResource("../images/run_R1.png"));
            corri_a_Sx[1]=ImageIO.read(getClass().getResource("../images/run_L1.png"));

            corri_a_Dx[2]=ImageIO.read(getClass().getResource("../images/run_R2.png"));
            corri_a_Sx[2]=ImageIO.read(getClass().getResource("../images/run_L2.png"));

            corri_a_Dx[3]=ImageIO.read(getClass().getResource("../images/run_R3.png"));
            corri_a_Sx[3]=ImageIO.read(getClass().getResource("../images/run_L3.png"));

            corri_a_Dx[4]=ImageIO.read(getClass().getResource("../images/run_R4.png"));
            corri_a_Sx[4]=ImageIO.read(getClass().getResource("../images/run_L4.png"));

            corri_a_Dx[5]=ImageIO.read(getClass().getResource("../images/run_R5.png"));
            corri_a_Sx[5]=ImageIO.read(getClass().getResource("../images/run_L5.png"));

            corri_a_Dx[6]=ImageIO.read(getClass().getResource("../images/run_R6.png"));
            corri_a_Sx[6]=ImageIO.read(getClass().getResource("../images/run_L6.png"));

            corri_a_Up[0]=ImageIO.read(getClass().getResource("../images/run_U0.png"));
            corri_a_Down[0]=ImageIO.read(getClass().getResource("../images/run_D0.png"));

            corri_a_Up[1]=ImageIO.read(getClass().getResource("../images/run_U1.png"));
            corri_a_Down[1]=ImageIO.read(getClass().getResource("../images/run_D1.png"));

            corri_a_Up[2]=ImageIO.read(getClass().getResource("../images/run_U2.png"));
            corri_a_Down[2]=ImageIO.read(getClass().getResource("../images/run_D2.png"));

            corri_a_Up[3]=ImageIO.read(getClass().getResource("../images/run_U3.png"));
            corri_a_Down[3]=ImageIO.read(getClass().getResource("../images/run_D3.png"));

            corri_a_Up[4]=ImageIO.read(getClass().getResource("../images/run_U4.png"));
            corri_a_Down[4]=ImageIO.read(getClass().getResource("../images/run_D4.png"));

            corri_a_Up[5]=ImageIO.read(getClass().getResource("../images/run_U5.png"));
            corri_a_Down[5]=ImageIO.read(getClass().getResource("../images/run_D5.png"));

            corri_a_Up[6]=ImageIO.read(getClass().getResource("../images/run_U6.png"));
            corri_a_Down[6]=ImageIO.read(getClass().getResource("../images/run_D6.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
