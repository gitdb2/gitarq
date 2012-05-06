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
    public Long crearContenidoEntradaBlog(@WebParam(name = "titulo") String titulo, 
                                          @WebParam(name = "nombreAutor") String nombreAutor, 
                                          @WebParam(name = "fechaPublicacion")
                                          @XmlJavaTypeAdapter(DateAdapter.class) 
                                          Date fechaPublicacion, 
                                          @WebParam(name = "texto") String texto, 
                                          @WebParam(name = "tags") List<String> tags) throws ArquitecturaException {
        
        checkParamsBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);
        
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
     * crea un Contenido del tipo pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "crearContenidoPaginaWeb")
    public Long crearContenidoPaginaWeb(@WebParam(name = "nombre") String nombre, 
                                        @WebParam(name = "fechaPublicacion") 
                                        @XmlJavaTypeAdapter(DateAdapter.class) 
                                                Date fechaPublicacion, 
                                        @WebParam(name = "html") byte[] html) 
            throws ArquitecturaException {
        
       checkParamsPaginaWeb(nombre, fechaPublicacion, html);
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
     * Chequea los parametros para dar de alta de un blog.
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @throws ArquitecturaException 
     */
    private void checkParamsBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
        
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
    private void checkParamsPaginaWeb(String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        
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

}
