/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.webservice;

import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import uy.edu.ort.laboratorio.ejb.seguridad.BeanSeguridadLocal;

/**
 *
 * @author rodrigo
 */
@WebService(serviceName = "AutenticarWebService")
@Stateless()
public class AutenticarWebService {
    @EJB
    private BeanSeguridadLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "autenticar")
    public boolean autenticar(@WebParam(name = "login") String login, @WebParam(name = "passwordEncriptdo") String passwordEncriptdo) {
        return ejbRef.autenticar(login, passwordEncriptdo);
    }

    @WebMethod(operationName = "tienePermiso")
    public boolean tienePermiso(@WebParam(name = "login") String login, @WebParam(name = "rol") String rol) {
        return ejbRef.tienePermiso(login, rol);
    }

    @WebMethod(operationName = "altaUsuario")
    @Oneway
    public void altaUsuario(@WebParam(name = "login") String login, @WebParam(name = "pass") String pass, @WebParam(name = "roles") List<String> roles) {
        ejbRef.altaUsuario(login, pass, roles);
    }
    
}
