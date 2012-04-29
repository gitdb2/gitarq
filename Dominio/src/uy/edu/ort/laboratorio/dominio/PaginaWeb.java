/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.util.Date;

/**
 *
 * @author tanquista
 */
public class PaginaWeb extends Contenido {
    
    private String nombre;
    private byte[] html;

    public PaginaWeb(Date fechaPublicacion) {
        super(fechaPublicacion);
    }
    
}
