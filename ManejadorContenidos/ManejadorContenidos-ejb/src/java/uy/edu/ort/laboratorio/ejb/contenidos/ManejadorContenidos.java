/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import uy.edu.ort.laboratorio.datatype.DataEntradaBlog;
import uy.edu.ort.laboratorio.datatype.DataPaginaWeb;
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
    @Override
    public boolean eliminarEntradaBlog(long idEntradaBlog) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, idEntradaBlog);
        Logger.debug(ManejadorContenidos.class, "params:"+idEntradaBlog);
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
    
    /**
     * elimina una pagina web
     * @param idPaginaWeb
     * @return
     * @throws ArquitecturaException 
     */
    @Override
    public boolean eliminarPaginaWeb(long idPaginaWeb) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, idPaginaWeb);
        Logger.debug(ManejadorContenidos.class, "params:"+idPaginaWeb);
        try {
            PaginaWeb aBorrar = manejadorPersistenciaDB.find(PaginaWeb.class, idPaginaWeb);
            manejadorPersistenciaDB.remove(aBorrar);
            return true;
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "No se pudo elminar eliminarPaginaWeb: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }
    
    /**
     * lista todas las paginas web
     * @return 
     */
    @Override
    public List<DataPaginaWeb> listarPaginasWeb() throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "No params");
        try {
            List<DataPaginaWeb> resultado = new ArrayList<DataPaginaWeb>();
            List<PaginaWeb> todasLasPaginasWeb = manejadorPersistenciaDB.createNamedQuery("PaginaWeb.findAll", PaginaWeb.class).getResultList();
            for (PaginaWeb p : todasLasPaginasWeb) {
                resultado.add(new DataPaginaWeb(p.getId(), p.getNombre()));
            }
            return resultado;
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "Error listando Paginas Web: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }
    
     /**
     * lista todas las entradas de blog
     * @return 
     */
    @Override
    public List<DataEntradaBlog> listarEntradasDeBlog() throws ArquitecturaException{
        Logger.debug(ManejadorContenidos.class, "No params");
        try {
            List<DataEntradaBlog> resultado = new ArrayList<DataEntradaBlog>();
            List<EntradaBlog> todasLasEntradasDeBlog = manejadorPersistenciaDB.createNamedQuery("EntradaBlog.findAll", EntradaBlog.class).getResultList();
            for (EntradaBlog e : todasLasEntradasDeBlog) {
                resultado.add(new DataEntradaBlog(e.getId(), e.getNombreAutor()));
            }
            return resultado;
        }catch(Exception ex){
             Logger.error(ManejadorContenidos.class,
                    "Error listando Entradas de Blog: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    @Override
    public EntradaBlog obtenerEntradasBlog(long idEntradaBlog) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, idEntradaBlog);
        Logger.debug(ManejadorContenidos.class, "params:"+idEntradaBlog);
        try {
            return manejadorPersistenciaDB.find(EntradaBlog.class, idEntradaBlog);
        }catch(Exception ex){
            Logger.error(ManejadorContenidos.class,
                    "Error devolviendo la Entrada de Blog: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    @Override
    public PaginaWeb obtenerPaginaWeb(long idPaginaWeb) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, idPaginaWeb);
        Logger.debug(ManejadorContenidos.class, "params:"+idPaginaWeb);
        try {
            return manejadorPersistenciaDB.find(PaginaWeb.class, idPaginaWeb);
        }catch(Exception ex){
            Logger.error(ManejadorContenidos.class,
                    "Error devolviendo la Pagina Web: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }
    
    /**
     * lista todas las paginas web segun filtrando por nombre y/o fecha de publicacion
     * @return 
     */
    @Override
    public List<DataPaginaWeb> listarPaginasWebFiltrando(String nombre, Date fechaPublicacion) throws ArquitecturaException {
        try {
            List<DataPaginaWeb> resultado = new ArrayList<DataPaginaWeb>();
            CriteriaQuery query = obtenerNamedQueryPaginaWeb(nombre, fechaPublicacion);
            List<PaginaWeb> queryResult = manejadorPersistenciaDB.createQuery(query).getResultList();
            for (PaginaWeb paginaWeb : queryResult) {
                resultado.add(new DataPaginaWeb(paginaWeb.getId(), paginaWeb.getNombre()));
            }
            return resultado;
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    "Error listando Paginas Web: Otra exception :->" + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }
    
    private CriteriaQuery obtenerNamedQueryPaginaWeb(String nombre, Date fechaPublicacion) throws ArquitecturaException{
        if (stringEsVacio(nombre) && fechaPublicacion == null) {
            throw new ArquitecturaException("Tiene que estar seteado al menos uno de los filtros.");
        } else {
            CriteriaBuilder qb = manejadorPersistenciaDB.getCriteriaBuilder();
            CriteriaQuery<PaginaWeb> query = qb.createQuery(PaginaWeb.class);
            Root<PaginaWeb> paginaWeb = query.from(PaginaWeb.class);
            
            if (!stringEsVacio(nombre))
                query.where(qb.equal(paginaWeb.get("nombre"), nombre));
            
            if (fechaPublicacion != null)
                query.where(qb.equal(paginaWeb.get("fechaPublicacion"), fechaPublicacion));
            
            return query;
        }
    }
    
    private boolean stringEsVacio(String parametro) {
        return parametro == null || parametro.trim().equals("");
    }

}
