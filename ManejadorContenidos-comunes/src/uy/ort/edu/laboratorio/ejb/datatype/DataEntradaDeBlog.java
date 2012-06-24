/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.ort.edu.laboratorio.ejb.datatype;

/**
 *
 * @author tanquista
 */
public class DataEntradaDeBlog {
    
    private long id;
    private String nombreAutor;

    public DataEntradaDeBlog(){
    
    }
    
    public DataEntradaDeBlog(long id, String nombreAutor) {
        this.id = id;
        this.nombreAutor = nombreAutor;
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

}
