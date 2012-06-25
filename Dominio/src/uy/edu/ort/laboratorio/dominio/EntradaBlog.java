/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa las entradas de blog.
 * Se le agregaron contstructor por defecto, getters y setters para
 * poder persistirla.
 * @author tanquista
 */
@Entity
@Table(name="ENTRADA_BLOG")
@NamedQueries({
    @NamedQuery(name="EntradaBlog.findAll", query="SELECT e FROM EntradaBlog e")
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class EntradaBlog implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(name="TITULO", length=100, unique=false, nullable=false)
    private String titulo;
    
    @Column(name="NOMBRE_AUTOR", length=50, unique=false, nullable=false)
    private String nombreAutor;
    
    @Column(name="TEXTO", unique=false, nullable=false)
    private String texto;
    
    @Temporal(TemporalType.DATE)
    @Column(name="FECHA_PUBLICACION", unique=false, nullable=false)
    private Date fechaPublicacion;
    
    @ElementCollection
    @CollectionTable(
        name="ENTRADA_BLOG_TAGS",
        joinColumns=@JoinColumn(name="ENTRADA_BLOG_ID")
    )
    @XmlElementWrapper(name = "tagList")
    @XmlElement(name = "tag")
    private List<String> tags = new ArrayList<String>();

    public EntradaBlog() {
    }
    
    public EntradaBlog(String titulo, String nombreAutor, String texto, List<String> tags, Date fechaPublicacion) {
        this.titulo = titulo;
        this.nombreAutor = nombreAutor;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
        this.tags = tags;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
