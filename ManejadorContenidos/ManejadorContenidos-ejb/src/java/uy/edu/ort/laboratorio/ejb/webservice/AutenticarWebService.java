package uy.edu.ort.laboratorio.ejb.webservice;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.negocio.seguridad.BeanSeguridadLocal;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * Implementacion del webservice de autenticacion
 *
 * @author rodrigo
 */
@WebService(serviceName = "AutenticarWebService")
@Stateless()
public class AutenticarWebService {

    @EJB
    private BeanSeguridadLocal ejbRef;

    /**
     * Metodo para el logueo inicial en el sistema y obtener el idUsuario del 
     * usuario autenticado, en caso de no poder autenticar se tira excepcion
     * no el mensaje usuario o password incorrecta
     * @param login
     * @param passwordEncriptdo
     * @return
     * @throws ArquitecturaException 
     */
    @WebMethod(operationName = "autenticar")
    public Long autenticar(@WebParam(name = "login") String login,
            @WebParam(name = "passwordEncriptdo") String passwordEncriptdo)
            throws ArquitecturaException {
        Logger.error(AutenticarWebService.class, "autenticar: "
                + " login=" + login);
        Long ret = ejbRef.autenticar(login, passwordEncriptdo);;
        if (ret == null) {
            Logger.error(AutenticarWebService.class, "No se pudo autenticar: "
                    + " login=" + login);

            throw new ArquitecturaException(
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.autenticar"));
        }
        return ret;
    }
}
