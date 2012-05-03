/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.contenidos.ManejadorContenidosLocal;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 *
 * @author tanquista
 */
@WebService(serviceName = "ManejadorContenidosWebService")
@Stateless()
public class ManejadorContenidosWebService {
    
    @EJB
    private ManejadorContenidosLocal manejadorContenidos;
/*
    @WebMethod(operationName = "crearContenido")
      public Long crearContenido(@WebParam(name = "contenido") Contenido contenido) throws ArquitecturaException {
           return manejadorContenidos.crearContenido(contenido);
      }
    */
    
    @WebMethod(operationName = "crearContenidoEntradaBlog")
    public Long crearContenidoEntradaBlog(@WebParam(name = "titulo") String titulo, 
                                          @WebParam(name = "nombreAutor") String nombreAutor, 
                                          @WebParam(name = "fechaPublicacion") Date fechaPublicacion, 
                                          @WebParam(name = "texto") String texto, 
                                          @WebParam(name = "tags") List<String> tags) throws ArquitecturaException {
        
        return manejadorContenidos.crearContenidoEntradaBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);
    }
 

    @WebMethod(operationName = "crearContenidoPaginaWeb")
    public Long crearContenidoPaginaWeb(@WebParam(name = "nombre") String nombre, 
                                        @WebParam(name = "fechaPublicacion") Date fechaPublicacion, 
                                        @WebParam(name = "html") byte[] html) throws ArquitecturaException {
        
        return manejadorContenidos.crearContenidoPaginaWeb(nombre, fechaPublicacion, html);
    }

    @WebMethod(operationName = "crearContenidoPaginaWeb2")
    public Long crearContenidoPaginaWeb2(@WebParam(name = "nombre") String nombre, 
                                        @WebParam(name = "fechaPublicacion") String fechaPublicacion, 
                                        @WebParam(name = "html") String html) throws ArquitecturaException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
             
            return manejadorContenidos.crearContenidoPaginaWeb(nombre, sdf.parse(fechaPublicacion), html.getBytes());
        } catch (ParseException ex) {
            Logger.error(ManejadorContenidosWebService.class, ex.getMessage());
            throw new ArquitecturaException(ex);
        }
    }
}
