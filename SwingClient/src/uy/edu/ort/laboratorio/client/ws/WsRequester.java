/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.client.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.ws.BindingProvider;
import uy.edu.ort.laboratorio.client.UsuarioManagerSingleton;
import uy.edu.ort.laboratorio.ejb.cripto.DesEncrypter;
import uy.edu.ort.laboratorio.travellers.datatype.*;
import uy.edu.ort.laboratorio.travellers.utiles.MarsharUnmarshallUtil;
import uy.edu.ort.laboratorio.ws.ManejadorContenidosWebService;
import uy.edu.ort.laboratorio.ws.ManejadorContenidosWebService_Service;
import uy.edu.ort.laboratorio.ws.autenticar.AutenticarWebService;
import uy.edu.ort.laboratorio.ws.autenticar.AutenticarWebService_Service;

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
    
    private void addUserAndPassToHeader(BindingProvider serv) {
        serv.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, UsuarioManagerSingleton.getInstance().getLogin());
        serv.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, UsuarioManagerSingleton.getInstance().getPassword());
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

            String payloadXml = utilPayload.marshall(nuevaEntradaBlog);
            payloadXml = encriptar(payloadXml);

            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);

            String message = utilTraveller.marshall(traveller);
            System.out.println(message);
            String lon = serv.crearEntradaBlogEncripted(message);

            System.out.println(lon);

            Traveller inObject = utilTraveller.unmarshall(Traveller.class, lon);
            System.out.println("ID = " + inObject.getId());

            lon = desencriptar(inObject.getPayload());
            System.out.println("payload = " + lon);

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

    public boolean crearContenidoPaginaWeb(String nombre, Date fecha, String texto) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);

//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//
//        try {
//            Long lon = serv.crearPaginaWeb(nombre, sdf.format(fecha), texto.getBytes());
//
//        } catch (Exception e) {
//            throw e;
//        }
        
          try {

            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarsharUnmarshallUtil<PaginaWebTraveller>();


            PaginaWebTraveller nuevaEntradaBlog = new PaginaWebTraveller(nombre, texto.getBytes(), fecha);

            String payloadXml = utilPayload.marshall(nuevaEntradaBlog);
            payloadXml = encriptar(payloadXml);


            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);

            String message = utilTraveller.marshall(traveller);
            System.out.println(message);
            
            String respuesta = serv.crearPaginaWebEncripted(message);

            System.out.println(respuesta);

            Traveller inObject = utilTraveller.unmarshall(Traveller.class, respuesta);
            System.out.println("ID = " + inObject.getId());

            respuesta = desencriptar(inObject.getPayload());
            System.out.println("payload = " + respuesta);

        } catch (Exception e) {
            throw e;
        }
       
        return true;
    }
    
    public List<ListItemTraveller> todasLasPaginasWeb() throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<ListWrapperTraveller> utilPayload = new MarsharUnmarshallUtil<ListWrapperTraveller>();
            
            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload("");
            String message = utilTraveller.marshall(traveller);
            
            String travellerString = serv.listarPaginasWebEncripted(message);
            
            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);
            
            String payload = traveller.getPayload();
            payload = desencriptar(payload);
            
            ListWrapperTraveller listadoWrapper = utilPayload.unmarshall(ListWrapperTraveller.class, payload);
            return listadoWrapper.getItems();
        } catch (Exception ex) {
            throw ex;
        }     
    }

    public boolean eliminarPaginaWeb(long id) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
         try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            
            String payloadXml = encriptar(""+id);
            
            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);
            String message = utilTraveller.marshall(traveller);
            
            String travellerString = serv.eliminarPaginaWebEncripted(message);
            
            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);
            
            String payload = traveller.getPayload();
            return Boolean.parseBoolean(desencriptar(payload));
        } catch (Exception ex) {
            throw ex;
        }     
    }

    public List<ListItemTraveller> paginasWebFiltrando(String titulo, String fechaPublicacion) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<FilterQueryTraveller> utilPayload = new MarsharUnmarshallUtil<FilterQueryTraveller>();
            
            FilterQueryTraveller queryTraveller = new FilterQueryTraveller();
            queryTraveller.setTitulo(titulo);
            queryTraveller.setFecha(parsearFecha(fechaPublicacion));

            String payloadXml = utilPayload.marshall(queryTraveller);
            payloadXml = encriptar(payloadXml);
            
            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);
            
            String message = utilTraveller.marshall(traveller);
            String travellerString = serv.listarPaginasWebFiltrandoEncripted(message);
            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);
            
            String payload = traveller.getPayload();
            payload = desencriptar(payload);
            MarsharUnmarshallUtil<ListWrapperTraveller> resultUtilPaload = new MarsharUnmarshallUtil<ListWrapperTraveller>();
            ListWrapperTraveller listadoWrapper = resultUtilPaload.unmarshall(ListWrapperTraveller.class, payload);
            return listadoWrapper.getItems();
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private Date parsearFecha(String fecha) throws ParseException {
        if (fecha == null || fecha.trim().isEmpty()) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.parse(fecha);
        }
    }
}
