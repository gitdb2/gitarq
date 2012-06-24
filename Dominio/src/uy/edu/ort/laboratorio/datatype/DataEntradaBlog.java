/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.datatype;

/**
 *
 * @author tanquista
 */
public class DataEntradaBlog {
    
    private long id;
    private String nombreAutor;

    public DataEntradaBlog(){
    
    }
    
    public DataEntradaBlog(long id, String nombreAutor) {
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
