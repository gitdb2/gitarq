/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.ort.edu.laboratorio.ejb.datatype;

/**
 *
 * @author tanquista
 */
public class DataPaginaWeb {
    
    private long id;
    private String nombre;

    public DataPaginaWeb(){
    
    }
    
    public DataPaginaWeb(long id, String nombre) {
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

}
