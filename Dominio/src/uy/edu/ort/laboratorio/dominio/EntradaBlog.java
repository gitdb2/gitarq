/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.util.Date;
import java.util.List;

/**
 *
 * @author tanquista
 */
public class EntradaBlog extends Contenido {
    
    private String titulo;
    private String nombreAutor;
    private String texto;
    private List<String> tags;

    public EntradaBlog(Date fechaPublicacion) {
        super(fechaPublicacion);
    }
    
}
