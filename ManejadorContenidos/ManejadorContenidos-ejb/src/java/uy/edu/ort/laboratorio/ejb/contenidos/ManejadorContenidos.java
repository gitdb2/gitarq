/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import uy.edu.ort.laboratorio.datatype.DataEntradaBlog;
import uy.edu.ort.laboratorio.datatype.DataPaginaWeb;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.persistencia.PeristenciaLocal;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * bean encargado de recibir las peticiones de creacion de Contenidos.
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorContenidos implements ManejadorContenidosRemote, ManejadorContenidosLocal {

    /**
     * ejb encargado de la persistencia.
     */
    //  @EJB
//    private PeristenciaLocal manejadorPersistenciaDB;

    private PeristenciaLocal getPersistencia() throws ArquitecturaException{

        String ejbName = LectorDeConfiguracion.getInstance()
                            .getMensaje("persistance.implementation.ejb.lookup.name", 
                            "java:global/ManejadorContenidos/ManejadorContenidos-ejb/PeristenciaDB!uy.edu.ort.laboratorio.ejb.persistencia.PeristenciaLocal");
        PeristenciaLocal manejadorPersistenciaDB = null;
        try {
            InitialContext context = new InitialContext();
            manejadorPersistenciaDB = (PeristenciaLocal) context.lookup(ejbName);
             Logger.info(this.getClass(), "getPersistencia: persistencia encontrada \""+ejbName+"\"");
        } catch (NamingException ex) {
            Logger.error(this.getClass(), ex);
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.persistencia.notfound", "No se pudo encontrar el ejb de persistencia"), ex);
        }
        return manejadorPersistenciaDB;

    }

    /**
     * crea y persiste una entrada de blog.
     *
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
        Logger.debug(ManejadorContenidos.class, "params:" + titulo + ", " + nombreAutor + ", " + fechaPublicacion + ", " + texto + ", " + tags);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            EntradaBlog nuevaEntradaBlog = new EntradaBlog(titulo, nombreAutor, texto, tags, fechaPublicacion);
            manejadorPersistenciaDB.persist(nuevaEntradaBlog);
            return nuevaEntradaBlog.getId();
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.crearContenidoEntradaBlog") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * crea y persiste una pagina web.
     *
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @Override
    public long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion,
            byte[] html) throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "params:" + nombre + ", " + fechaPublicacion + ", " + html);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            PaginaWeb nuevaPagina = new PaginaWeb(nombre, html, fechaPublicacion);
            manejadorPersistenciaDB.persist(nuevaPagina);
            return nuevaPagina.getId();
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.crearContenidoPaginaWeb") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    @Override
    public long modificarContenidoEntradaBlog(long idEntradaBlog, String titulo,
            String nombreAutor, Date fechaPublicacion, String texto,
            List<String> tags) throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "params:" + idEntradaBlog + "," + titulo + ", " + nombreAutor + ", " + fechaPublicacion + ", " + texto + ", " + tags);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            EntradaBlog entradaBlog = manejadorPersistenciaDB.find(EntradaBlog.class, idEntradaBlog);
            entradaBlog.setTitulo(titulo);
            entradaBlog.setNombreAutor(nombreAutor);
            entradaBlog.setTexto(texto);
            entradaBlog.setTags(tags);
            manejadorPersistenciaDB.merge(entradaBlog);
            return entradaBlog.getId();
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.modificarContenidoEntradaBlog") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * modifica una pagina web.
     *
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @Override
    public long modificarContenidoPaginaWeb(long idPaginaWeb, String nombre, Date fechaPublicacion,
            byte[] html) throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "params:" + idPaginaWeb + "," + nombre + ", " + fechaPublicacion + ", " + html);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            PaginaWeb paginaWeb = manejadorPersistenciaDB.find(PaginaWeb.class, idPaginaWeb);
            paginaWeb.setNombre(nombre);
            paginaWeb.setFechaPublicacion(fechaPublicacion);
            paginaWeb.setHtml(html);
            manejadorPersistenciaDB.merge(paginaWeb);
            return paginaWeb.getId();
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.modificarContenidoPaginaWeb") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * elimina una entrada de blog
     *
     * @param idEntradaBlog
     * @return
     * @throws ArquitecturaException
     */
    @Override
    public boolean eliminarEntradaBlog(long idEntradaBlog) throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "params:" + idEntradaBlog);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            EntradaBlog aBorrar = manejadorPersistenciaDB.find(EntradaBlog.class, idEntradaBlog);
            manejadorPersistenciaDB.remove(aBorrar);
            return true;
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.eliminarEntradaBlog") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * elimina una pagina web
     *
     * @param idPaginaWeb
     * @return
     * @throws ArquitecturaException
     */
    @Override
    public boolean eliminarPaginaWeb(long idPaginaWeb) throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "params:" + idPaginaWeb);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            PaginaWeb aBorrar = manejadorPersistenciaDB.find(PaginaWeb.class, idPaginaWeb);
            manejadorPersistenciaDB.remove(aBorrar);
            return true;
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.eliminarPaginaWeb") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * lista todas las paginas web
     *
     * @return
     */
    @Override
    public List<DataPaginaWeb> listarPaginasWeb() throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "No params");
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            List<DataPaginaWeb> resultado = new ArrayList<DataPaginaWeb>();
            List<PaginaWeb> todasLasPaginasWeb = manejadorPersistenciaDB.findAllPaginasWb();
            for (PaginaWeb p : todasLasPaginasWeb) {
                resultado.add(new DataPaginaWeb(p.getId(), p.getNombre()));
            }
            return resultado;
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.listarPaginasWeb") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * lista todas las entradas de blog
     *
     * @return
     */
    @Override
    public List<DataEntradaBlog> listarEntradasDeBlog() throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "No params");
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            List<DataEntradaBlog> resultado = new ArrayList<DataEntradaBlog>();
            List<EntradaBlog> todasLasEntradasDeBlog = manejadorPersistenciaDB.findAllBlogs();
            for (EntradaBlog e : todasLasEntradasDeBlog) {
                resultado.add(new DataEntradaBlog(e.getId(), e.getNombreAutor()));
            }
            return resultado;
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.listarEntradasDeBlog") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    @Override
    public EntradaBlog obtenerEntradasBlog(long idEntradaBlog) throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "params:" + idEntradaBlog);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            return manejadorPersistenciaDB.find(EntradaBlog.class, idEntradaBlog);
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.obtenerEntradasBlog") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    @Override
    public PaginaWeb obtenerPaginaWeb(long idPaginaWeb) throws ArquitecturaException {
        Logger.debug(ManejadorContenidos.class, "params:" + idPaginaWeb);
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            return manejadorPersistenciaDB.find(PaginaWeb.class, idPaginaWeb);
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.obtenerPaginaWeb") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * lista todas las paginas web segun filtrando por nombre y/o fecha de
     * publicacion
     *
     * @return
     */
    @Override
    public List<DataPaginaWeb> listarPaginasWebFiltrando(String nombre, Date fechaPublicacion) throws ArquitecturaException {
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            List<DataPaginaWeb> resultado = new ArrayList<DataPaginaWeb>();
            List<PaginaWeb> queryResult = manejadorPersistenciaDB.listarPaginasWebFiltrando(nombre, fechaPublicacion);
            for (PaginaWeb paginaWeb : queryResult) {
                resultado.add(new DataPaginaWeb(paginaWeb.getId(), paginaWeb.getNombre()));
            }
            return resultado;
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.listarPaginasWebFiltrando") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }

    /**
     * lista todas las paginas web segun filtrando por nombre y/o fecha de
     * publicacion
     *
     * @return
     */
    @Override
    public List<DataEntradaBlog> listarEntradaBlogFiltrando(String titulo, Date fechaPublicacion,
            String contenido, String autor, String tag) throws ArquitecturaException {
        PeristenciaLocal manejadorPersistenciaDB = getPersistencia();
        try {
            List<DataEntradaBlog> resultado = new ArrayList<DataEntradaBlog>();
            List<EntradaBlog> queryResult = manejadorPersistenciaDB.listarEntradaBlogFiltrando(titulo, fechaPublicacion,
                    contenido, autor, tag);
            for (EntradaBlog entradaBlog : queryResult) {
                resultado.add(new DataEntradaBlog(entradaBlog.getId(), entradaBlog.getNombreAutor()));
            }
            return resultado;
        } catch (Exception ex) {
            Logger.error(ManejadorContenidos.class,
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.listarEntradaBlogFiltrando") + ex.getClass().getName());
            Logger.debug(ManejadorContenidos.class, Logger.getStackTrace(ex));
            throw new ArquitecturaException(ex.getMessage());
        }
    }
}
