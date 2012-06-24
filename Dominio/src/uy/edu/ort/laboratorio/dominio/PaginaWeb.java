/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Clase que representa las paginas web.
 * Se le agregaron contstructor por defecto, getters y setters para
 * poder persistirla.
 * @author tanquista
 */
@Entity
@Table(name="PAGINA_WEB")
@NamedQueries({
    @NamedQuery(name="PaginaWeb.findAll", query="SELECT p FROM PaginaWeb p")
})
public class PaginaWeb implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(name="NOMBRE", length=50, unique=false, nullable=false)
    private String nombre;
    
    @Temporal(TemporalType.DATE)
    @Column(name="FECHA_PUBLICACION", unique=false, nullable=false)
    private Date fechaPublicacion;
    
    @Column(name="HTML", unique=false, nullable=false)
    private byte[] html;

    public PaginaWeb() {
    }

    public PaginaWeb(String nombre, byte[] html, Date fechaPublicacion) {
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
