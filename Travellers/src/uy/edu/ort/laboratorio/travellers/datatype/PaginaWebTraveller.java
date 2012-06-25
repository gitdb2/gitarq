/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.travellers.datatype;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa las paginas web.
 * Se le agregaron contstructor por defecto, getters y setters para
 * poder persistirla.
 * @author Rodrigo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class PaginaWebTraveller implements Serializable {
    
    private long id;
    
    private String nombre;
    
    private Date fechaPublicacion;
    
    private byte[] html;

    public PaginaWebTraveller() {
    }

    public PaginaWebTraveller(String nombre, byte[] html, Date fechaPublicacion) {
        this.nombre = nombre;
        this.fechaPublicacion = fechaPublicacion;
        this.html = html;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public byte[] getHtml() {
        return html;
    }

    public void setHtml(byte[] html) {
        this.html = html;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
