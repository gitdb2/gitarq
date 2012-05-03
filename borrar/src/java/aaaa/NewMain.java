/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aaaa;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import pepepe.ArquitecturaException_Exception;
import pepepe.ManejadorContenidosWebService;
import pepepe.ManejadorContenidosWebService_Service;

/**
 *
 * @author rodrigo
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DatatypeConfigurationException, ArquitecturaException_Exception {
        ManejadorContenidosWebService_Service pepe = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = pepe.getManejadorContenidosWebServicePort();

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);



        Long lon = serv.crearContenidoPaginaWeb("rodrigo",date2, "hola".getBytes());
        System.out.println(lon);
    }
}
