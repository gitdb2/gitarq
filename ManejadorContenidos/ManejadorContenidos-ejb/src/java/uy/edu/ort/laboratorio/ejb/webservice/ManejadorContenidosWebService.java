/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.webservice;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import uy.edu.ort.laboratorio.datatype.DataEntradaBlog;
import uy.edu.ort.laboratorio.datatype.DataPaginaWeb;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.contenidos.ManejadorContenidosLocal;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.webservice.adapters.DateAdapter;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * Fachada de la aplicacion para exponer como webservice.
 * Delega el comportamiento un ejb.
 * @author tanquista
 */
@WebService(serviceName = "ManejadorContenidosWebService")
@Stateless()
@HandlerChain(file = "autenticacion_handler.xml") 
public class ManejadorContenidosWebService {
    
    @EJB
    private ManejadorContenidosLocal manejadorContenidos;
    
    /**
     * crea un Contenido del tipo entrada de blog.
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "crearEntradaBlog")
    public long crearEntradaBlog(@WebParam(name = "titulo") String titulo, 
                                          @WebParam(name = "nombreAutor") String nombreAutor, 
                                          @WebParam(name = "fechaPublicacion") 
                                          @XmlJavaTypeAdapter(DateAdapter.class) 
                                          Date fechaPublicacion, 
                                          @WebParam(name = "texto") String texto, 
                                          @WebParam(name = "tags") List<String> tags) throws ArquitecturaException {
        
        checkParametrosCrearBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);
        
        try {
            return manejadorContenidos.crearContenidoEntradaBlog
                    (titulo, nombreAutor, fechaPublicacion, texto, tags);
        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, "params:"+titulo+", "+nombreAutor+", "+fechaPublicacion+", "+texto+", "+tags);
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearEntradaBlog"));
        }
        
    }
    
     /**
     * actualiza el contenido de una entrada de blog
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "modificarEntradaBlog")
    public long modificarEntradaBlog(@WebParam(name = "idEntradaBlog") long idEntradaBlog,
                                          @WebParam(name = "titulo") String titulo, 
                                          @WebParam(name = "nombreAutor") String nombreAutor, 
                                          @WebParam(name = "fechaPublicacion") 
                                          @XmlJavaTypeAdapter(DateAdapter.class) 
                                          Date fechaPublicacion, 
                                          @WebParam(name = "texto") String texto, 
                                          @WebParam(name = "tags") List<String> tags) throws ArquitecturaException {
        
        checkParametosActualizarEntradaBlog(idEntradaBlog, titulo, nombreAutor, fechaPublicacion, texto, tags);
        
        try {
            return manejadorContenidos.modificarContenidoEntradaBlog(idEntradaBlog, titulo, nombreAutor, fechaPublicacion, texto, tags);
        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, "params:"+idEntradaBlog+", "+titulo+", "+nombreAutor+", "+fechaPublicacion+", "+texto+", "+tags);
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.modificarEntradaBlog"));
        }
        
    }
    
    /**
     * crea un Contenido del tipo pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "crearPaginaWeb")
    public long crearPaginaWeb(@WebParam(name = "nombre") String nombre, 
                                        @WebParam(name = "fechaPublicacion") 
                                        @XmlJavaTypeAdapter(DateAdapter.class) 
                                                Date fechaPublicacion, 
                                        @WebParam(name = "html") byte[] html) 
            throws ArquitecturaException {
        
       checkParametrosPaginaWeb(nombre, fechaPublicacion, html);
       try{
            return manejadorContenidos.crearContenidoPaginaWeb(nombre, fechaPublicacion, html);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+nombre+", "+fechaPublicacion+", "+html);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearPaginaWeb"));
       }
    }
    
    /**
     * crea un Contenido del tipo pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "modificarPaginaWeb")
    public long modificarPaginaWeb(@WebParam(name = "idPaginaWeb") long idPaginaWeb,
                                        @WebParam(name = "nombre") String nombre, 
                                        @WebParam(name = "fechaPublicacion") 
                                        @XmlJavaTypeAdapter(DateAdapter.class) Date fechaPublicacion, 
                                        @WebParam(name = "html") byte[] html) 
            throws ArquitecturaException {
        
       checkParametosActualizarPaginaWeb(idPaginaWeb, nombre, fechaPublicacion, html);
       
       try{
            return manejadorContenidos.modificarContenidoPaginaWeb(idPaginaWeb, nombre, fechaPublicacion, html);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+idPaginaWeb+", "+nombre+", "+fechaPublicacion+", "+html);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.modificarPaginaWeb"));
       }
    }

    /**
     * Chequea los parametros para dar de alta de un blog.
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @throws ArquitecturaException 
     */
    private void checkParametrosCrearBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
        
        if(fechaPublicacion == null){
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoFechaIncorrecto"));
        }
        if(nombreAutor == null  || nombreAutor.isEmpty()){
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoAutorIncorrecto"));
        }
         if(titulo == null  || titulo.isEmpty()){
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoTituloIncorrecto"));
        }
        if(tags == null  ){
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoTagsIncorrecto"));
        }
     }
    
    /**
     * Chequea los parametros para dar de alta una pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @throws ArquitecturaException 
     */
    private void checkParametrosPaginaWeb(String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        
        if(fechaPublicacion == null){
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoFechaIncorrecto"));
        }
        
        if(nombre == null  || nombre.isEmpty()){
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoNombreIncorrecto"));
        }
       
        if(html == null  ){
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoHTMLIncorrecto"));
        }
     }

    private void checkParametosActualizarEntradaBlog(long idEntradaBlog, String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
        if (idEntradaBlog == 0)
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        
        checkParametrosCrearBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);
        
    }
    
    private void checkParametosActualizarPaginaWeb(long idPaginaWeb, String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        if (idPaginaWeb == 0)
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        
        checkParametrosPaginaWeb(nombre, fechaPublicacion, html);
    }
    
    /**
     * elimina una entrada de blog
     * @param idEntradaBlog
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "eliminarEntradaBlog")
    public boolean eliminarEntradaBlog(@WebParam(name = "idEntradaBlog") long idEntradaBlog) throws ArquitecturaException {
        if (idEntradaBlog == 0)
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        try{
            return manejadorContenidos.eliminarEntradaBlog(idEntradaBlog);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+idEntradaBlog);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.eliminarEntradaBlog"));
       }
    }
    
    /**
     * elimina una pagina web
     * @param idPaginaWeb
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "eliminarPaginaWeb")
    public boolean eliminarPaginaWeb(@WebParam(name = "idPaginaWeb") long idPaginaWeb) throws ArquitecturaException {
        if (idPaginaWeb == 0)
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        try{
            return manejadorContenidos.eliminarPaginaWeb(idPaginaWeb);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+idPaginaWeb);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.eliminarPaginaWeb"));
       }
    }
    
    /**
     * lista todas las paginas web
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "listarPaginasWeb")
    public List<DataPaginaWeb> listarPaginasWeb() throws ArquitecturaException {
       try{
            return manejadorContenidos.listarPaginasWeb();
       }catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarPaginaWeb"));
       }
    }
    
    /**
     * lista todas las entradas de blog
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "listarEntradasDeBlog")
    public List<DataEntradaBlog> listarEntradasDeBlog() throws ArquitecturaException {
       try{
            return manejadorContenidos.listarEntradasDeBlog();
       }catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarEntradaBlog"));
       }
    }
    
    /**
     * devuelve la pagina web asociada al identificador
     * @param idPaginaWeb
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "obtenerPaginaWeb")
    public PaginaWeb obtenerPaginaWeb(@WebParam(name = "idPaginaWeb") long idPaginaWeb) throws ArquitecturaException {
       if (idPaginaWeb == 0)
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
       try{
            return manejadorContenidos.obtenerPaginaWeb(idPaginaWeb);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+idPaginaWeb);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.obtenerPaginaWeb"));
       }
    }
    
    /**
     * devuelve la pagina web asociada al identificador
     * @param idEntradaBlog
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "obtenerEntradaBlog")
    public EntradaBlog obtenerEntradaBlog(@WebParam(name = "idEntradaBlog") long idEntradaBlog) throws ArquitecturaException {
       if (idEntradaBlog == 0)
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
       try{
            return manejadorContenidos.obtenerEntradasBlog(idEntradaBlog);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+idEntradaBlog);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.obtenerEntradaBlog"));
       }
    }
    
    /**
     * 
     * @param nombre
     * @param fechaPublicacion
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "listarPaginasWebFiltrando")
    public List<DataPaginaWeb> listarPaginasWebFiltrando(@WebParam(name = "nombre") String nombre, 
                                               @WebParam(name = "fechaPublicacion") 
                                               @XmlJavaTypeAdapter(DateAdapter.class) Date fechaPublicacion) 
                                               throws ArquitecturaException {
       try{
            return manejadorContenidos.listarPaginasWebFiltrando(nombre, fechaPublicacion);
       }catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+nombre+","+fechaPublicacion);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarFiltrandoPaginaWeb"));
       }
    }
    
    @WebMethod(operationName = "listarEntradaBlogFiltrando")
    public List<DataEntradaBlog> listarEntradaBlogFiltrando(
                                          @WebParam(name = "titulo") String titulo, 
                                          @WebParam(name = "nombreAutor") String nombreAutor, 
                                          @WebParam(name = "fechaPublicacion") @XmlJavaTypeAdapter(DateAdapter.class) Date fechaPublicacion, 
                                          @WebParam(name = "texto") String texto, 
                                          @WebParam(name = "tag") String tag)
                                               throws ArquitecturaException {
       try{
            return manejadorContenidos.listarEntradaBlogFiltrando(titulo, fechaPublicacion, texto, nombreAutor, tag);
       }catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+titulo+", "+nombreAutor+", "+fechaPublicacion+", "+texto+", "+tag);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarFiltrandoEntradaBlog"));
       }
    }


}
