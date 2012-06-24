/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client.ws;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uy.edu.ort.laboratorio.client.SwingClient;
import uy.edu.ort.laboratorio.ws.ArquitecturaException_Exception;
import uy.edu.ort.laboratorio.ws.ManejadorContenidosWebService;
import uy.edu.ort.laboratorio.ws.ManejadorContenidosWebService_Service;

/**
 *
 * @author rodrigo
 */
public class WsRequester {
    
    
    
    /***
     * Dad e alta  una entrada de blog
     * @param titulo
     * @param autor
     * @param fecha
     * @param texto
     * @param tags 
     */
         public boolean crearContenidoEntradaBlog(String titulo, String autor, Date fecha, String texto, List<String> tags) throws ArquitecturaException_Exception{
            ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
            ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

           try{
                Long lon = serv.crearContenidoEntradaBlog(titulo, autor, sdf.format(fecha), texto, tags);
                
            } catch (ArquitecturaException_Exception ex) {
                Logger.getLogger(SwingClient.class.getName()).log(Level.SEVERE, null, ex);
                throw  ex;
            }
           return true;
        }
    
      /**
       * da de alta una pagina web
       * @param nombre
       * @param fecha
       * @param texto 
       */
        public boolean crearContenidoPaginaWeb(String nombre, Date fecha, String texto) throws ArquitecturaException_Exception{
            ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
            ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            try {
                Long lon = serv.crearContenidoPaginaWeb(nombre,sdf.format(fecha), texto.getBytes());
            } catch (ArquitecturaException_Exception ex) {
                Logger.getLogger(SwingClient.class.getName()).log(Level.SEVERE, null, ex);
                throw  ex;
            }
            return true;
        }
}
