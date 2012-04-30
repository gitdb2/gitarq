/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.serializador.ManejadorPersistenciaLocal;


/**
 *
 * @author rodrigo
 */
@Stateless
@WebService
public class ManejadorContenidos implements ManejadorContenidosRemote, ManejadorContenidosLocal {

    @EJB
    private ManejadorPersistenciaLocal manPersist;
    
    @WebMethod
    public Long crearContenido(@WebParam(name="contenido") Contenido contenido) 
                                                          throws ArquitecturaException{
        Long ret = manPersist.persistir(contenido);
        
        
        return ret;
        
    }
    
    
    @WebMethod
    public Long crearContenido2(@WebParam(name="contenido") String contenido)throws ArquitecturaException {
        return manPersist.persistir(contenido);
    }
    
    @Override
    @WebMethod
    public String prueba(String entra) {
        return "pasado por ejb "+entra;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
