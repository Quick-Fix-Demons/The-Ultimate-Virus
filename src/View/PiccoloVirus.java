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
public class PiccoloVirus {
    //ATTRIBUTI
    private int velocitaX = 6;
    private double velocitaY = 12;
    private double forzaGravita = 0.5;
    private int energia;
    private boolean morto=false;
    private boolean piediPerTerra = true;
    private String orientamento; //da che parte è girato, mi serve per sparare coerentemente
                                 //con l'orientamento del personaggio
    double velYtemp = getVelocitaY();
    //setto una direzione iniziale fissa (dx)
    private int ultimaDirezione=KeyEvent.VK_RIGHT;
    private int vita;

    //la soglia di mosse, la uso in setFrameNumber
    private static final int SOGLIA_CONTATORE_MOSSE=5;

    //conta il numero di cambi di immagine nell'animazione
    private int contatoreMosse=0;

    //e' un rettangolo che circonda il protagonista e si sposta con lui, serve
    //per gestire facilmente gli scontri con gli altri oggetti del gioco
    private Rectangle contornoPersonaggio;

    //SPOSTAMENTO e' lo spazio percorso sull'asse x dal personaggio con un passo
    private static final int SPOSTAMENTO=4;

    //frame corrente nell'animazione del personaggio
    private BufferedImage immagineCorrente;

    //dimensione dei buffer dell'animazione della corsa una cella per ogni disegno che
    //compone l'animazione
    private static final int DIMENSIONE_BUFFER_CORSA=4;

    //tutte le bufferedImages usate nell'animazione NB: due sono immagini singole
    //e due sono vettori di immagini
    private BufferedImage fermo_a_DX;
    private BufferedImage fermo_a_SX;
    private BufferedImage[] corri_a_Dx;
    private BufferedImage[] corri_a_Sx;
    private BufferedImage[] corri_a_Up;
    private BufferedImage[] corri_a_Down;
    private BufferedImage fermo_a_UP;
    private BufferedImage fermo_a_DOWN;

    //determina l'immagine corrente da usare nell'animazione di corsa
    private int numeroImmagineCorrente=0;

    //posizione iniziale del personaggio
    public static int BOY_START_X=200;
    public static int BOY_START_Y=380;

    //altezza del personaggio (usata anche per il rettangolo di contorno)
    private final int BOY_HEIGHT=64;

    //larghezza del personaggio (usata anche per il rettangolo di contorno)
    private final int BOY_WIDTH=40;

    //posizione attuale del personaggio sull'asse X (all'inizio corrisponde a 
    //BOY_START_X)
    private int currentX=BOY_START_X;

    //posizione attuale del personaggio sull'asse Y (all'inizio corrisponde a 
    //BOY_START_Y)
    private int currentY=BOY_START_Y;
    
    //COSTRUTTORE
    public PiccoloVirus(int x, int y){
        currentX = x;
        currentY = y;
        //inizializzo i buffer di immagini (sono come dei vettori di immagini)
        //che contengono i disegni per l'animazione della corsa vs dx e sx
        corri_a_Sx=new BufferedImage[DIMENSIONE_BUFFER_CORSA];
        corri_a_Dx=new BufferedImage[DIMENSIONE_BUFFER_CORSA];
        corri_a_Up = new BufferedImage[DIMENSIONE_BUFFER_CORSA];
        corri_a_Down = new BufferedImage[DIMENSIONE_BUFFER_CORSA];
        vita = 2;
        //setto come posizione iniziale "fermo girato vs destra"
        immagineCorrente=fermo_a_DX;
        loadFrames();

        //inizializzo un rettangolo che sara' il "perimetro del personaggio, 
        //questo sara' molto utile quando vorro' gestire gli scontri
        //con altri oggetti sulla scena
        contornoPersonaggio=new Rectangle(currentX,currentY,BOY_WIDTH,BOY_HEIGHT);
        this.setPiediPerTerra(true);
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

    public int getVita() {
        return vita;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }
    
    public void setVelocitaY(double velocitaY) {
        this.velocitaY = velocitaY;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public double getForzaGravita() {
        return forzaGravita;
    }
    public boolean getMorto() {
        return morto;
    }
    
    public void setMorto(boolean morto) {
        this.morto = morto;
    }

    public void setForzaGravita(int forzaGravita) {
        this.forzaGravita = forzaGravita;
    }

    public boolean isPiediPerTerra() {
        return piediPerTerra;
    }
    
    private void setFrameNumber() {
        //devo inventarmi un meccanismo per ruotare le sei imaagini dell'animazione
        //e ricominciare da capo ciclicamente, non posso farlo con un "for" perche'
        //il giocatore potrebbe cambiare direzione in qualsiasi momento quindi
        //devo passare attraverso un calcolo matematico
		numeroImmagineCorrente  = contatoreMosse/SOGLIA_CONTATORE_MOSSE;
//                System.out.println("contatore mosse: " + contatoreMosse );
//                System.out.println("FrameCorrente (moveCounter/5): " + numeroImmagineCorrente );
		numeroImmagineCorrente %= 4;
//		System.out.println("FrameCorrente (currentFrame%=6): " + numeroImmagineCorrente );
                
                //azzero moveCounter prima di avere come resto della divisione valori maggiori 
                //di 6 che mi farebbero sbarellare l'animazione
		if(contatoreMosse>SOGLIA_CONTATORE_MOSSE*6){
			contatoreMosse=0;
		}
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

    public void setPiediPerTerra(boolean piediPerTerra) {
        this.piediPerTerra = piediPerTerra;
    }
        
    //METODI OPERATIVI
    
    public void correDx() {
        setOrientamento("dx");
        if (this.isPiediPerTerra()) {
            //aggiorno la posizione del personaggio che si sta spostando
            currentX=currentX+SPOSTAMENTO/2;

            //riposiziono coerentemente il rettangolo che borda il personaggio
            contornoPersonaggio.setLocation(currentX, currentY);

            //cambio l'immagine corrente dell'animazione
            setFrameNumber();
            immagineCorrente=corri_a_Dx[numeroImmagineCorrente];

            //incremento il contatore dei movimenti
            contatoreMosse++;
        }
        else {
            //aggiorno la posizione del personaggio che si sta spostando in volo
            currentX=currentX+velocitaX;

            //riposiziono coerentemente il rettangolo che borda il personaggio
            contornoPersonaggio.setLocation(currentX, currentY);
        }
    }
    
    public void correSx() {
        setOrientamento("sx");
        if (this.isPiediPerTerra()) {
            //aggiorno la posizione del personaggio che si sta spostando
            currentX=currentX-SPOSTAMENTO/2;

            //riposiziono coerentemente il rettangolo che borda il personaggio
            contornoPersonaggio.setLocation(currentX, currentY);

            //cambio l'immagine corrente dell'animazione
            setFrameNumber();
            immagineCorrente=corri_a_Sx[numeroImmagineCorrente];

            //incremento il contatore dei movimenti
            contatoreMosse++;
        }
        else {
            //aggiorno la posizione del personaggio che si sta spostando in volo
            currentX=currentX+velocitaX;

            //riposiziono coerentemente il rettangolo che borda il personaggio
            contornoPersonaggio.setLocation(currentX, currentY);
        }
        
    }
    public void correUp() {
        // Da mettere up
        //aggiorno la posizione del personaggio che si sta spostando
        currentY=currentY+SPOSTAMENTO/2;

        //riposiziono coerentemente il rettangolo che borda il personaggio
        contornoPersonaggio.setLocation(currentX, currentY);

        //cambio l'immagine corrente dell'animazione
        setFrameNumber();
        immagineCorrente=corri_a_Down[numeroImmagineCorrente]; // Corri_a_Up

        //incremento il contatore dei piskel
        contatoreMosse++;
        
    }
    
    public void correDown() {
        // Da mettere down
        //aggiorno la posizione del personaggio che si sta spostando
        currentY=currentY-SPOSTAMENTO/2;

        //riposiziono coerentemente il rettangolo che borda il personaggio
        contornoPersonaggio.setLocation(currentX, currentY);

        //cambio l'immagine corrente dell'animazione
        setFrameNumber();
        immagineCorrente=corri_a_Up[numeroImmagineCorrente]; // Corri_a_Down

        //incremento il contatore dei piskel
        contatoreMosse++;
        
    }
    public void fermoDx() {
        setOrientamento("dx");
        immagineCorrente=fermo_a_DX;
    }
    public void fermoUp() {
        //setOrientamento("up");
        immagineCorrente=fermo_a_UP;
    }
    
    public void fermoDown() {
        //setOrientamento("down");
        immagineCorrente=fermo_a_DOWN;
    }
    
   
    
    public void fermoSx() {
        setOrientamento("sx");
        immagineCorrente=fermo_a_SX;
    }
    
 
    
   
    
    //carico tutte le immagini necessarie per l'animazione
    private void loadFrames() {
            try {
                    fermo_a_DX=ImageIO.read(getClass().getResource("../images/idle_R.png"));
                    fermo_a_SX=ImageIO.read(getClass().getResource("../images/idle_L.png"));     
                    
                    
                    corri_a_Dx[0]=ImageIO.read(getClass().getResource("../images/virus_R1.png"));
                    corri_a_Sx[0]=ImageIO.read(getClass().getResource("../images/virus_L1.png"));
                    
                    corri_a_Dx[1]=ImageIO.read(getClass().getResource("../images/virus_R2.png"));
                    corri_a_Sx[1]=ImageIO.read(getClass().getResource("../images/virus_L2.png"));
                    
                    corri_a_Dx[2]=ImageIO.read(getClass().getResource("../images/virus_R3.png"));
                    corri_a_Sx[2]=ImageIO.read(getClass().getResource("../images/virus_L3.png"));
                    
                    corri_a_Dx[3]=ImageIO.read(getClass().getResource("../images/virus_R4.png"));
                    corri_a_Sx[3]=ImageIO.read(getClass().getResource("../images/virus_L4.png"));
                    
                    corri_a_Up[0]=ImageIO.read(getClass().getResource("../images/virus_R1.png"));
                    corri_a_Down[0]=ImageIO.read(getClass().getResource("../images/virus_R1.png"));
                    
                    corri_a_Up[1]=ImageIO.read(getClass().getResource("../images/virus_R2.png"));
                    corri_a_Down[1]=ImageIO.read(getClass().getResource("../images/virus_R2.png"));

                    corri_a_Up[2]=ImageIO.read(getClass().getResource("../images/virus_R3.png"));
                    corri_a_Down[2]=ImageIO.read(getClass().getResource("../images/virus_R3.png"));

                    corri_a_Up[3]=ImageIO.read(getClass().getResource("../images/virus_R4.png"));
                    corri_a_Down[3]=ImageIO.read(getClass().getResource("../images/virus_R4.png"));

            } catch (IOException e) {
                    e.printStackTrace();
            }
    }
}
