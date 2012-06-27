/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.webservice;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.negocio.seguridad.BeanSeguridadLocal;

/**
 *
 * @author rodrigo
 */
@WebService(serviceName = "AutenticarWebService")
@Stateless()
public class AutenticarWebService {
    @EJB
    private BeanSeguridadLocal ejbRef;

    @WebMethod(operationName = "autenticar")
    public Long autenticar(@WebParam(name = "login") String login, @WebParam(name = "passwordEncriptdo") String passwordEncriptdo) throws ArquitecturaException {
        Long ret = ejbRef.autenticar(login, passwordEncriptdo);;
        if(ret == null)
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.autenticar"));
        return  ret;
    }

//    @WebMethod(operationName = "tienePermiso")
//    public boolean tienePermiso(@WebParam(name = "login") String login, @WebParam(name = "rol") String rol) {
//        return ejbRef.tienePermiso(login, rol);
//    }
//
//    @WebMethod(operationName = "altaUsuario")
//    @Oneway
//    public void altaUsuario(@WebParam(name = "login") String login, @WebParam(name = "pass") String pass, @WebParam(name = "roles") List<String> roles) {
//        ejbRef.altaUsuario(login, pass, roles);
//    }
    
}
