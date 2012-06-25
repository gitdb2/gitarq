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
import javax.xml.ws.BindingProvider;
import uy.edu.ort.laboratorio.client.SwingClient;
import uy.edu.ort.laboratorio.client.UsuarioManagerSingleton;
import uy.edu.ort.laboratorio.client.ws.autenticar.AutenticarWebService;
import uy.edu.ort.laboratorio.client.ws.autenticar.AutenticarWebService_Service;
import uy.edu.ort.laboratorio.ws.ArquitecturaException_Exception;
import uy.edu.ort.laboratorio.ws.ManejadorContenidosWebService;
import uy.edu.ort.laboratorio.ws.ManejadorContenidosWebService_Service;

/**
 *
 * @author rodrigo
 */
public class WsRequester {

    public Long autenticar() throws Exception {
        AutenticarWebService_Service service = new AutenticarWebService_Service();
        AutenticarWebService serv = service.getAutenticarWebServicePort();

        return serv.autenticar(UsuarioManagerSingleton.getInstance().getLogin(),
                                UsuarioManagerSingleton.getInstance().getPassword());
    }

    /**
     * *
     * Dad e alta una entrada de blog
     *
     * @param titulo
     * @param autor
     * @param fecha
     * @param texto
     * @param tags
     */
    public boolean crearContenidoEntradaBlog(String titulo, String autor, Date fecha, String texto, List<String> tags) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

       System.out.println(UsuarioManagerSingleton.getInstance().getPassword());
        
        try {
            Long lon = serv.crearEntradaBlog(titulo, autor, sdf.format(fecha), texto, tags);

        } catch (ArquitecturaException_Exception ex) {
            Logger.getLogger(SwingClient.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    /**
     * da de alta una pagina web
     *
     * @param nombre
     * @param fecha
     * @param texto
     */
    public boolean crearContenidoPaginaWeb(String nombre, Date fecha, String texto) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Long lon = serv.crearPaginaWeb(nombre, sdf.format(fecha), texto.getBytes());
        } catch (ArquitecturaException_Exception ex) {
            Logger.getLogger(SwingClient.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    private void addUserAndPassToHeader(BindingProvider serv) {
        
        serv.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, UsuarioManagerSingleton.getInstance().getLogin());
        serv.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, UsuarioManagerSingleton.getInstance().getPassword());
//        serv.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "sss");//UsuarioManagerSingleton.getInstance().getPassword());
    }
}
