/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client;

import uy.edu.ort.laboratorio.client.swing.EnviarEntradaBlog;
import uy.edu.ort.laboratorio.client.swing.EnviarPagina;

/**
 *
 * @author rodrigo
 */
public class SwingClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SwingClient().run();
    }

    private void run() {
        //new EnviarPagina().setVisible(true);
        new EnviarEntradaBlog().setVisible(true);
    
    }
    
}
