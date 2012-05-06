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
                                          @WebParam(name = "fechaPublicacion") Date fechaPublicacion, 
                                          @WebParam(name = "texto") String texto, 
                                          @WebParam(name = "tags") List<String> tags) throws ArquitecturaException {
        
        return manejadorContenidos.crearContenidoEntradaBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);
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
       try{
            return manejadorContenidos.crearContenidoPaginaWeb(nombre, fechaPublicacion, html);
       }
       catch(Exception e){
           Logger.error(ManejadorContenidosWebService.class, e.getMessage());
           Logger.debug(ManejadorContenidosWebService.class, "---->"+ e.getClass().getName());
           Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
           throw new ArquitecturaException( "Ocurrio un error al crearContenidoPaginaWeb, solicite asistencia tecnica a su michi de confianza");
       }
    }

}
