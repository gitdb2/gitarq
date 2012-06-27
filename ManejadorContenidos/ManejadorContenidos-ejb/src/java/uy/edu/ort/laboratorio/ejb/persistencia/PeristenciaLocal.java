/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.persistencia;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;

/**
 * Interface para poder proveer distintas implementaciones de persistencia 
 * y que la logica de negocios no se vea afecata por el cambio
 * @author rodrigo
 */
@Local
public interface PeristenciaLocal {
   
    /**
     * Persiste el objeto
     * @param entity 
     */
    public void persist(Object entity);

    /**
     * realiza el update de un objeto
     * @param <T>
     * @param entity
     * @return 
     */
    public <T extends Object> T merge(T entity);

    /**
     * Elimina un objeto
     * @param entity 
     */
    public void remove(Object entity);
    
    /**
     * Busca un objeto por la Pk
     * @param <T>
     * @param entityClass
     * @param primaryKey
     * @return 
     */
    public <T extends Object> T find(Class<T> entityClass, Object primaryKey);

    /**
     * retorna todas las paginas web en el sistema
     * @return 
     */
    public List<PaginaWeb> findAllPaginasWb();

    /**
     * retorna todas las entradas de blog en el sistema
     * @return 
     */
    public List<EntradaBlog> findAllBlogs();

    /**
     *  retorna todas las paginas web en el sistema que matcheen el criterio
     * @param nombre
     * @param fechaPublicacion
     * @return
     * @throws ArquitecturaException 
     */
    public List<PaginaWeb> listarPaginasWebFiltrando(String nombre, Date fechaPublicacion) throws ArquitecturaException;

    /**
     * retorna todas las entradas de blog en el sistema que matcheen el criterio
     * @param titulo
     * @param fechaPublicacion
     * @param contenido
     * @param autor
     * @param tag
     * @return
     * @throws ArquitecturaException 
     */
    public List<EntradaBlog> listarEntradaBlogFiltrando(String titulo, Date fechaPublicacion, String contenido, String autor, String tag) throws ArquitecturaException;
    
}
