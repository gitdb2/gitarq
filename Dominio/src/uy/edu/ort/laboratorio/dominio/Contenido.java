/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author tanquista
 */
public abstract class Contenido implements Serializable {

    protected static final String FORMATO_FECHA = "dd-MM-yyyy";
    protected Date fechaPublicacion;

    public Contenido() {
    }
    
    public Contenido(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public String getFechaPublicacionFormateada() {
        Format formatter = new SimpleDateFormat(FORMATO_FECHA);
        return formatter.format(fechaPublicacion);
    }
    
}
