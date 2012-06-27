package uy.edu.ort.laboratorio.datatype;

/**
 * Bean usado para retornar las listas de elementos resutlado de las busquedas
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
