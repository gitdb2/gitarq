package uy.edu.ort.laboratorio.travellers.datatype;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Bean usado para serializar a XML los parametros de los listados parametricos, 
 * tanto de paginas web ocmo de entradas de blog
 * @author rodrigo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class FilterQueryTraveller {
    private String nombre;
    private Date fecha;
    private String titulo;
    private String texto;
    private String tag;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
