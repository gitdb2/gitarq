/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.webservice;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
    @WebMethod(operationName = "crearContenidoEntradaBlog")
    public long crearContenidoEntradaBlog(@WebParam(name = "titulo") String titulo, 
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
            throw new ArquitecturaException("Ocurrio un error al crearContenidoEntradaBlog");
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
    @WebMethod(operationName = "actualizarContenidoEntradaBlog")
    public long modificarContenidoEntradaBlog(@WebParam(name = "idEntradaBlog") long idEntradaBlog,
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
            throw new ArquitecturaException("Ocurrio un error al modificarContenidoEntradaBlog");
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
    @WebMethod(operationName = "crearContenidoPaginaWeb")
    public long crearContenidoPaginaWeb(@WebParam(name = "nombre") String nombre, 
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
           throw new ArquitecturaException( "Ocurrio un error al crearContenidoPaginaWeb");
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
    @WebMethod(operationName = "modificarContenidoPaginaWeb")
    public long modificarContenidoPaginaWeb(@WebParam(name = "idPaginaWeb") long idPaginaWeb,
                                        @WebParam(name = "nombre") String nombre, 
                                        @WebParam(name = "fechaPublicacion") 
                                        @XmlJavaTypeAdapter(DateAdapter.class) 
                                                Date fechaPublicacion, 
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
           throw new ArquitecturaException( "Ocurrio un error al modificarContenidoPaginaWeb");
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
            throw new ArquitecturaException("El formato de la fecha no es correcto o no se paso");
        }
        if(nombreAutor == null  || nombreAutor.isEmpty()){
            throw new ArquitecturaException("El autor no puede ser nulo ni vacio");
        }
         if(titulo == null  || titulo.isEmpty()){
            throw new ArquitecturaException("El titulo no puede ser nulo ni vacio");
        }
        if(tags == null  ){
            throw new ArquitecturaException("la lista de tags no puede ser nula");
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
            throw new ArquitecturaException("El formato de la fecha no es correcto o no se paso");
        }
        if(nombre == null  || nombre.isEmpty()){
            throw new ArquitecturaException("El nombre no puede ser nulo ni vacio");
        }
       
        if(html == null  ){
            throw new ArquitecturaException("El payload de la pagina no puede ser nulo");
        }
     }

    private void checkParametosActualizarEntradaBlog(long idEntradaBlog, String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
        if (idEntradaBlog == 0)
            throw new ArquitecturaException("El identificador no puede ser nulo ni vacio");
        
        checkParametrosCrearBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);
        
    }
    
    private void checkParametosActualizarPaginaWeb(long idPaginaWeb, String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        if (idPaginaWeb == 0)
            throw new ArquitecturaException("El identificador no puede ser nulo ni vacio");
        
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
            throw new ArquitecturaException("El identificador no puede ser nulo ni vacio");
        try{
            return manejadorContenidos.eliminarEntradaBlog(idEntradaBlog);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class,  e.getClass().getName() + e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "params:"+idEntradaBlog);
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException( "Ocurrio un error al eliminarEntradaBlog");
       }
    }

}
