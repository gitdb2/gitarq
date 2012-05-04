/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import prueba.ArquitecturaException_Exception;
import prueba.ManejadorContenidosWebService;
import prueba.ManejadorContenidosWebService_Service;

/**
 *
 * @author rodrigo
 */
public class Cliente {

  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DatatypeConfigurationException, ArquitecturaException_Exception {
        ManejadorContenidosWebService_Service pepe = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = pepe.getManejadorContenidosWebServicePort();

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);



        Long lon = serv.crearContenidoPaginaWeb("pepe",date2, "hola".getBytes());
        System.out.println(lon);
    }
}
