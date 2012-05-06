/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.ArquitecturaException_Exception;
import ws.ManejadorContenidosWebService;
import ws.ManejadorContenidosWebService_Service;

/**
 *
 * @author rodrigo
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DatatypeConfigurationException {
        try {
            ManejadorContenidosWebService_Service pepe = new ManejadorContenidosWebService_Service();
             ManejadorContenidosWebService serv = pepe.getManejadorContenidosWebServicePort();

             GregorianCalendar c = new GregorianCalendar();
             c.setTime(new Date());
             XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);


                
           // Long lon = serv.crearContenidoPaginaWeb("pepe44","12-01-1999", "hola".getBytes());
             List<String> tags = new ArrayList<String>();
             tags.add("tag1");
             tags.add("tag2");
              Long lon = serv.crearContenidoEntradaBlog("titulos","El pepo", "01-12-2011", "hola", tags);
             System.out.println(lon);
        } catch (ArquitecturaException_Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
