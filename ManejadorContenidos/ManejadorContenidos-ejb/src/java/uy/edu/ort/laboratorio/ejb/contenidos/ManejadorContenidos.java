/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * bean encargado de recibir las peticiones de creacion de Contenidos.
 * @author rodrigo
 */
@Stateless
public class ManejadorContenidos implements ManejadorContenidosRemote, ManejadorContenidosLocal {

    /**
     * ejb encargado de la persistencia.
     */
    @PersistenceContext
    private EntityManager manejadorPersistenciaDB;
    
    /**
     * crea y persiste una entrada de blog.
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @return
     * @throws ArquitecturaException
     */
    @Override
    public long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
	Logger.info(ManejadorContenidos.class, nombreAutor );
        Logger.debug(ManejadorContenidos.class, "params:"+titulo+", "+nombreAutor+", "+fechaPublicacion+", "+texto+", "+tags);
        try {
            EntradaBlog nuevaEntradaBlog = new EntradaBlog(titulo, nombreAutor, texto, tags, fechaPublicacion);
            manejadorPersistenciaDB.persist(nuevaEntradaBlog);
            return nuevaEntradaBlog.getId();
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "No se pudo persistir crearContenidoEntradaBlog: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * crea y persiste una pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @Override
    public long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion,
                            byte[] html) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, nombre);
        Logger.debug(ManejadorContenidos.class, "params:"+nombre+", "+fechaPublicacion+", "+html);
        try {
            PaginaWeb nuevaPagina = new PaginaWeb(nombre, html, fechaPublicacion);
            manejadorPersistenciaDB.persist(nuevaPagina);
            return nuevaPagina.getId();
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "No se pudo persistir crearContenidoPaginaWeb: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    @Override
    public long modificarContenidoEntradaBlog(long idEntradaBlog, String titulo, 
                    String nombreAutor, Date fechaPublicacion, String texto, 
                    List<String> tags) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, nombreAutor);
        Logger.debug(ManejadorContenidos.class, "params:"+idEntradaBlog+","+titulo+", "+nombreAutor+", "+fechaPublicacion+", "+texto+", "+tags);
        try{
            EntradaBlog entradaBlog = manejadorPersistenciaDB.find(EntradaBlog.class, idEntradaBlog);
            entradaBlog.setTitulo(titulo);
            entradaBlog.setNombreAutor(nombreAutor);
            entradaBlog.setTexto(texto);
            entradaBlog.setTags(tags);
            manejadorPersistenciaDB.merge(entradaBlog);
            return entradaBlog.getId();
        }catch(Exception ex){
            Logger.error(ManejadorContenidos.class,
                    "No se pudo modificar modificarContenidoEntradaBlog: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }
    
     /**
     * modifica una pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @Override
    public long modificarContenidoPaginaWeb(long idPaginaWeb, String nombre, Date fechaPublicacion,
                            byte[] html) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, nombre);
        Logger.debug(ManejadorContenidos.class, "params:"+idPaginaWeb+","+nombre+", "+fechaPublicacion+", "+html);
        try {
            PaginaWeb paginaWeb = manejadorPersistenciaDB.find(PaginaWeb.class, idPaginaWeb);
            paginaWeb.setNombre(nombre);
            paginaWeb.setFechaPublicacion(fechaPublicacion);
            paginaWeb.setHtml(html);
            manejadorPersistenciaDB.merge(paginaWeb);
            return paginaWeb.getId();
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "No se pudo modificar modificarContenidoPaginaWeb: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }
    
    /**
     * elimina una entrada de blog
     * @param idEntradaBlog
     * @return
     * @throws ArquitecturaException 
     */
    public boolean eliminarEntradaBlog(long idEntradaBlog) throws ArquitecturaException {
        try {
            EntradaBlog aBorrar = manejadorPersistenciaDB.find(EntradaBlog.class, idEntradaBlog);
            manejadorPersistenciaDB.remove(aBorrar);
            return true;
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "No se pudo elminar eliminarEntradaBlog: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

}
