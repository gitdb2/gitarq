/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client.ws;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;
import uy.edu.ort.laboratorio.client.SwingClient;
import uy.edu.ort.laboratorio.client.UsuarioManagerSingleton;
import uy.edu.ort.laboratorio.client.ws.autenticar.ArquitecturaException_Exception;
import uy.edu.ort.laboratorio.client.ws.autenticar.AutenticarWebService;
import uy.edu.ort.laboratorio.client.ws.autenticar.AutenticarWebService_Service;
import uy.edu.ort.laboratorio.ejb.cripto.DesEncrypter;
import uy.edu.ort.laboratorio.travellers.datatype.EntradaBlogTraveller;
import uy.edu.ort.laboratorio.travellers.datatype.Traveller;
import uy.edu.ort.laboratorio.travellers.utiles.MarsharUnmarshallUtil;
import uy.edu.ort.laboratorio.ws.EntradaBlog;
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
    public boolean crearContenidoEntradaBlogOrig(String titulo, String autor, Date fecha, String texto, List<String> tags) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        System.out.println(UsuarioManagerSingleton.getInstance().getPassword());

        try {
            Long lon = serv.crearEntradaBlog(titulo, autor, sdf.format(fecha), texto, tags);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean crearContenidoEntradaBlog(String titulo, String autor, Date fecha, String texto, List<String> tags) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        System.out.println(UsuarioManagerSingleton.getInstance().getPassword());

        try {

            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarsharUnmarshallUtil<EntradaBlogTraveller>();


            EntradaBlogTraveller nuevaEntradaBlog = new EntradaBlogTraveller(titulo, autor, texto, tags, fecha);

//            {
//                    JAXBContext context = JAXBContext.newInstance(EntradaBlogTraveller.class);
//            Marshaller m = context.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            m.marshal(nuevaEntradaBlog, System.out);
//            }
//            
//            
//            JAXBContext contextOut = JAXBContext.newInstance(EntradaBlogTraveller.class);
//            Marshaller m = contextOut.createMarshaller();
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            m.marshal(nuevaEntradaBlog, out);
//            String payloadXml = out.toString();
//            out.close();           
            String payloadXml = utilPayload.marshall(nuevaEntradaBlog);
            payloadXml = encriptar(payloadXml);


            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);





//            contextOut = JAXBContext.newInstance(Traveller.class);
//            m = contextOut.createMarshaller();
//            out = new ByteArrayOutputStream();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            m.marshal(traveller, out);
//            String message = out.toString();
//            out.close();    
            String message = utilTraveller.marshall(traveller);
            System.out.println(message);
            String lon = serv.crearEntradaBlogEncripted(message);




            System.out.println(lon);


//            JAXBContext contextIn = JAXBContext.newInstance(Traveller.class);
//            Unmarshaller u = contextIn.createUnmarshaller();
//            ByteArrayInputStream input = new ByteArrayInputStream(lon.getBytes());
//            Traveller inObject = (Traveller) u.unmarshal(input);

            Traveller inObject = utilTraveller.unmarshall(Traveller.class, lon);
            System.out.println("ID = " + inObject.getId());

            lon = desencriptar(inObject.getPayload());
            System.out.println("payload = " + lon);



//        } catch (ArquitecturaException_Exception ex) {
//            Logger.getLogger(SwingClient.class.getName()).log(Level.SEVERE, null, ex);
//            throw ex;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    private String desencriptar(String payload) {
        DesEncrypter encriptador = new DesEncrypter(UsuarioManagerSingleton.getInstance().getMD5Key());
        return encriptador.decrypt(payload);
    }

    private String encriptar(String payload) {
        DesEncrypter encriptador = new DesEncrypter(UsuarioManagerSingleton.getInstance().getMD5Key());
        return encriptador.encrypt(payload);
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
