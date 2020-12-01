
package theultimatevirus;

import Control.Wasd;
import Model.GestoreGioco;
import View.PannelloDiGioco;
import java.net.MalformedURLException;
import javax.swing.JFrame;

/**
 *
 * @author Quick Fix Demons
 */
public class TheUltimateVirus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException {
        // creo Frame principale, pannello di gioco e gestore della partita e poi lo avvio
        JFrame f = new JFrame("Sprite in movimento");
        PannelloDiGioco pGioco = new PannelloDiGioco();
        Wasd inputGioco = new Wasd();
        f.addKeyListener(inputGioco);
        GestoreGioco gestore = new GestoreGioco(pGioco, inputGioco);
        gestore.start();
        
        f.add(pGioco);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(3);
    }
    
}
