package uy.edu.ort.laboratorio.travellers.datatype;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * Clase que representa las entradas de blog.
 * Se le agregaron contstructor por defecto, getters y setters para
 * poder persistirla.
 * @author Rodrigo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class EntradaBlogTraveller implements Serializable {
    
    private long id;
    
    private String titulo;
    
    private String nombreAutor;
    
    private String texto;
    
    private Date fechaPublicacion;
    
    @XmlElementWrapper(name = "tagList")
    @XmlElement(name = "tag")
    private List<String> tags = new ArrayList<String>();

    public EntradaBlogTraveller() {
    }
    
    public EntradaBlogTraveller(String titulo, String nombreAutor, String texto, List<String> tags, Date fechaPublicacion) {
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
