/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.persistencia.ManejadorPersistenciaLocal;
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
    @EJB
    private ManejadorPersistenciaLocal manejadorPersistencia;
    
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
	Logger.info(ManejadorContenidos.class, "Entra en crearContenidoEntradaBlog" );
        Logger.debug(ManejadorContenidos.class, "params:"+titulo+", "+nombreAutor+", "+fechaPublicacion+", "+texto+", "+tags);
        try {
            return manejadorPersistencia.persistir(new EntradaBlog(titulo, nombreAutor, texto, tags, fechaPublicacion));
        } catch (ArquitecturaException ex) {
            Logger.error(ManejadorContenidos.class,
                    "No se pudo persistir crearContenidoEntradaBlog");
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw ex;
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
            return manejadorPersistencia.persistir(new PaginaWeb(nombre, html, fechaPublicacion));
        } catch (ArquitecturaException ex) {
            Logger.error(ManejadorContenidos.class,
                    "No se pudo persistir crearContenidoPaginaWeb");
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw ex;
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "No se pudo persistir crearContenidoPaginaWeb: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

}
