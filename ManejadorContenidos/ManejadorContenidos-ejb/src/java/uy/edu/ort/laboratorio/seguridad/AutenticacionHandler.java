package uy.edu.ort.laboratorio.seguridad;

import com.sun.jersey.core.util.Base64;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author rodrigo
 */
public class AutenticacionHandler implements SOAPHandler<SOAPMessageContext> {

    @EJB
    BeanSeguridadLocal seguridad;

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {

        Boolean outbound = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        System.err.println(messageContext.get(MessageContext.HTTP_REQUEST_HEADERS));
    
        if (outbound.booleanValue()) {
            System.err.println("\nOutbound message:");
        } else {
            SOAPMessage msg = messageContext.getMessage();
            System.err.println("\nInbound message:");
            Long usuarioAutenticado = checkAutenticacion(messageContext, msg);
            if(usuarioAutenticado != null){
                checkRoles(messageContext, msg, usuarioAutenticado);
            }
        }

        System.err.println(messageContext);
        log(messageContext);

        return true;
    }

    private boolean checkRoles(SOAPMessageContext messageContext, SOAPMessage msg, Long idUser) {
        String operacion = ((QName)messageContext.get(MessageContext.WSDL_OPERATION)).getLocalPart();
        String role = LectorDeConfiguracion.getInstance().getMensaje("permiso."+operacion, "NONE");
        
        List<String> roles = Arrays.asList(role.split("\\|"));
        
        
        boolean verificado = seguridad.tienePermiso(idUser, roles);
        
         if(!verificado){
             generateSOAPErrMessage(msg, LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.permiso") + " "+operacion);
        }
        return verificado;
    }

    private Long checkAutenticacion(SOAPMessageContext messageContext, SOAPMessage msg) {
        // now we can access the header, body, attachments etc ..
        Map<String, Object> header = (Map<String, Object>) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        System.err.println("---" + header);
        List<String> data = (List<String>) header.get(("authorization"));
        System.err.println("---" + data);
        String aut = data.get(0);
        String pass = aut.split(" ")[1];
        pass = Base64.base64Decode(pass);
        String login = pass.split(":")[0];
        pass = pass.split(":")[1];

        Long autenticado = seguridad.autenticar(login, pass);
        System.err.println("---" + autenticado);
        if(null == autenticado){
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

    private void log(SOAPMessageContext messageContext) {
        SOAPMessage msg = messageContext.getMessage(); 
        try {
            msg.writeTo(System.out);  
        } catch (SOAPException ex) {
            Logger.getLogger(AutenticacionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AutenticacionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
       try {
          SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
          SOAPFault soapFault = soapBody.addFault();
          soapFault.setFaultString(reason);
          throw new SOAPFaultException(soapFault); 
       }
       catch(SOAPException e) { }
    }
}
