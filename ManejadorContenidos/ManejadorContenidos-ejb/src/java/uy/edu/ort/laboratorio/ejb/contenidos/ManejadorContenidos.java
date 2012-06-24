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
    public Long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
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
    public Long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion,
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

}
