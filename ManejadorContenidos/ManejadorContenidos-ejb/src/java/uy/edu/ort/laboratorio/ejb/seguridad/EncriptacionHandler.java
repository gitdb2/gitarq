package uy.edu.ort.laboratorio.ejb.seguridad;


import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author rodrigo
 */
public class EncriptacionHandler implements LogicalHandler<LogicalMessageContext> {

    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleMessage(LogicalMessageContext messageContext) {
        Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty.booleanValue()) {
            System.out.println("\nOutbound message:");
        } else {
            System.out.println("\nInbound message:");
        }

        System.out.println("** Response: " + messageContext.getMessage().toString());
        return true;
    }

    @Override
    public boolean handleFault(LogicalMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {
    }
}
