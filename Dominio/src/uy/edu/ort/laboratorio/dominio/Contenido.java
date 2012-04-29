/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author tanquista
 */
public abstract class Contenido implements Serializable{
    
    private Date fechaPublicacion;

    public Contenido(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }
    
}
