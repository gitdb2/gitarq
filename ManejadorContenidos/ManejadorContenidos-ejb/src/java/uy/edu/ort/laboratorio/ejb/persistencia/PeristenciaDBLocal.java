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
 *
 * @author rodrigo
 */
@Local
public interface PeristenciaDBLocal {
   
    public void persist(Object entity);

    public <T extends Object> T merge(T entity);

    public void remove(Object entity);
    
    public <T extends Object> T find(Class<T> entityClass, Object primaryKey);

    public List<PaginaWeb> findAllPaginasWb();

    public List<EntradaBlog> findAllBlogs();

    public List<PaginaWeb> listarPaginasWebFiltrando(String nombre, Date fechaPublicacion) throws ArquitecturaException;

    public List<EntradaBlog> listarEntradaBlogFiltrando(String titulo, Date fechaPublicacion, String contenido, String autor, String tag) throws ArquitecturaException;
    
}
