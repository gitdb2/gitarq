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
import uy.edu.ort.laboratorio.ejb.contenidos.ManejadorContenidosLocal;

/**
 *
 * @author tanquista
 */
@WebService(serviceName = "ManejadorContenidosWebService")
@Stateless()
public class ManejadorContenidosWebService {
    
    @EJB
    private ManejadorContenidosLocal ejbRef;

    @WebMethod(operationName = "prueba")
    public String prueba(@WebParam(name = "entra") String entra) {
        return ejbRef.prueba(entra);
    }

    @WebMethod(operationName = "crearContenidoEntradaBlog")
    public Long crearContenidoEntradaBlog(@WebParam(name = "titulo") String titulo, 
                                          @WebParam(name = "nombreAutor") String nombreAutor, 
                                          @WebParam(name = "fechaPublicacion") Date fechaPublicacion, 
                                          @WebParam(name = "texto") String texto, 
                                          @WebParam(name = "tags") List tags) {
        
        return ejbRef.crearContenidoEntradaBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);
    }

    @WebMethod(operationName = "crearContenidoPaginaWeb")
    public Long crearContenidoPaginaWeb(@WebParam(name = "nombre") String nombre, 
                                        @WebParam(name = "fechaPublicacion") Date fechaPublicacion, 
                                        @WebParam(name = "html") String html) {
        
        return ejbRef.crearContenidoPaginaWeb(nombre, fechaPublicacion, html);
    }
    
}
