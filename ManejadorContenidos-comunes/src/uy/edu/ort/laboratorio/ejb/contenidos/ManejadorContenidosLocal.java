/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rodrigo
 */
@Local
public interface ManejadorContenidosLocal {
    
    public String prueba(String entra);
    
    public Long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List tags);
    
    public Long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion, String html);
    
}
