/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client;

import uy.edu.ort.laboratorio.client.swing.FrameLogin;

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
        //new FramePrincipal().setVisible(true);
//        new FramePrincipal().setVisible(true);
        new FrameLogin().setVisible(true);
    
    }
    
}
