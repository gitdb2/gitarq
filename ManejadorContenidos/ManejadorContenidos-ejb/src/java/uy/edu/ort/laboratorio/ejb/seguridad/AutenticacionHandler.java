package uy.edu.ort.laboratorio.ejb.seguridad;

import com.sun.jersey.core.util.Base64;
import java.util.*;
import javax.ejb.EJB;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.negocio.seguridad.BeanSeguridadLocal;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * Interceptor de los llamados a los webservices, se ejecutan cuando entra y
 * cuando sale un mensaje
 *
 * @author rodrigo
 */
public class AutenticacionHandler implements SOAPHandler<SOAPMessageContext> {

    @EJB
    BeanSeguridadLocal seguridad;

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
        Logger.debug(AutenticacionHandler.class,
                "handleMessage");
        Boolean outbound = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outbound.booleanValue()) {
            Logger.debug(AutenticacionHandler.class, "Outbound message:");
        } else {
            Logger.debug(AutenticacionHandler.class, "Inbound message:");

            SOAPMessage msg = messageContext.getMessage();
            Long usuarioAutenticado = checkAutenticacion(messageContext, msg);
            if (usuarioAutenticado != null) {
                checkRoles(messageContext, msg, usuarioAutenticado);
            }
        }
        return true;
    }

    /**
     * Saca del mensaje Soap el nombre de la operacion y controla si el usaurio
     * tiene permiso de ejecucion de la operacion En caso de no tener permiso se
     * genera un SOAPFaultMessage para terminar la invocacio y retornar error.
     * Si se produce cualquier error no se verifica al usuario, y no se le da
     * permiso
     *
     * @param messageContext
     * @param msg
     * @param idUser
     * @return
     */
    private boolean checkRoles(SOAPMessageContext messageContext, SOAPMessage msg, Long idUser) {
        Logger.debug(AutenticacionHandler.class,
                "checkRoles idUser=" + idUser);
        boolean verificado = false;
        String operacion = null;
        String role = null;
        try {
            operacion = ((QName) messageContext.get(MessageContext.WSDL_OPERATION)).getLocalPart();
            role = LectorDeConfiguracion.getInstance().getMensaje("permiso." + operacion, "NONE");

            List<String> roles = Arrays.asList(role.split("\\|"));

            verificado = seguridad.tienePermiso(idUser, roles);

            if (!verificado) {
                generateSOAPErrMessage(msg, LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.permiso") + " " + operacion);
            }
        } catch (Exception e) {
            Logger.error(AutenticacionHandler.class, "checkRoles: "
                    + " idUser=" + idUser
                    + " verificado=" + verificado
                    + " operacion=" + operacion
                    + " role=" + role + "\n" + e.getMessage());
            Logger.debug(AutenticacionHandler.class, Logger.getStackTrace(e));
        }
        return verificado;
    }

    /**
     * Metodo para extraer del header las credenciales del usuario y
     * autenticarlo. En caso de ocurrir cualquier problema no se autentica y se
     * loguea el error
     *
     * @param messageContext
     * @param msg
     * @return
     */
    private Long checkAutenticacion(SOAPMessageContext messageContext, SOAPMessage msg) {
        Logger.debug(AutenticacionHandler.class,
                "checkAutenticacion msg=" + msg);
        Long autenticado;
        String login = null;
        String pass = null;
        try {
            Map<String, Object> header = (Map<String, Object>) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);

            List<String> data = (List<String>) header.get(("authorization"));

            String aut = data.get(0);
            pass = aut.split(" ")[1];
            pass = Base64.base64Decode(pass);
            login = pass.split(":")[0];
            pass = pass.split(":")[1];

            autenticado = seguridad.autenticar(login, pass);

        } catch (Exception e) {//si algo revienta, entoces no se mando con seguridad o algo raro
            autenticado = null;
            Logger.error(AutenticacionHandler.class, "checkAutenticacion: "
                    + " autenticado=" + autenticado
                    + " login=" + login
                    + " pass=" + pass
                    + "\nex=" + e.getMessage());
            Logger.debug(AutenticacionHandler.class, Logger.getStackTrace(e));
        }
        if (null == autenticado) {
            generateSOAPErrMessage(msg, LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.autenticar"));
        }
        return autenticado;
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    /**
     * Genera el mensaje de error SOAP que corta la ejecucion 
     * y es retornado al clietne
     * Este segmento de codigo fue obtenido de internet
     * @param msg
     * @param reason 
     */
    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            throw new SOAPFaultException(soapFault);
        } catch (SOAPException e) {
        }
    }
}
