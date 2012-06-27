package uy.edu.ort.laboratorio.travellers.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean para serializacion a XML que representa un elemento de un listado
 * de entidades
 * @author tanquista
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ListItemTraveller {
    
    private long id;
    private String nombre;

    public ListItemTraveller(){
    
    }
    
    public ListItemTraveller(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    
    @Override
    public String toString(){
        return nombre + " (" + id + ")";
    }

}
