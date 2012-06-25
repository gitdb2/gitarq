/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.seguridad;

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


        SOAPMessage msg = messageContext.getMessage();


//        System.err.println("---------___>>"+seguridad.autenticar("rodrigo", "SjCRdR+deE4Js+zG72wWOuwulj5rgiFoYWPl5oo7tE+Wba7JWV3lLw=="));
        if (outbound.booleanValue()) {
            System.err.println("\nOutbound message:");
        } else {

            System.err.println("\nInbound message:");
            checkAutenticacion(messageContext, msg);

        }

        System.err.println(messageContext);
//        SOAPMessage msg = messageContext.getMessage();
        log(messageContext);

        return true;
    }

    private void checkAutenticacion(SOAPMessageContext messageContext, SOAPMessage msg) {
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

        boolean autenticado = seguridad.autenticar(login, pass);
        System.err.println("---" + autenticado);
        if(!autenticado){
            generateSOAPErrMessage(msg, "Usuario o password incorrectos");
        }
        //                msg.getSOAPBody();
    }

    @Override
    public Set<QName> getHeaders() {

        System.err.println("---SET_HEADERS");
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
        System.err.println("---HANDLEFAULT");
        return true;
    }

    @Override
    public void close(MessageContext context) {
        System.err.println("---CLOSE");

    }

    private void log(SOAPMessageContext messageContext) {
        SOAPMessage msg = messageContext.getMessage(); //Line 1
        try {
            msg.writeTo(System.out);  //Line 3
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
