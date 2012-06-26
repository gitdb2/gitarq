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
import uy.edu.ort.laboratorio.travellers.cripto.DesEncrypter;
import uy.edu.ort.laboratorio.travellers.datatype.*;
import uy.edu.ort.laboratorio.travellers.utiles.MarshallUnmarshallUtil;
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

            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarshallUnmarshallUtil<EntradaBlogTraveller>();

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
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarshallUnmarshallUtil<PaginaWebTraveller>();

            PaginaWebTraveller nuevaEntradaBlog = new PaginaWebTraveller(nombre, texto.getBytes(), fecha);

            String payloadXml = utilPayload.marshall(nuevaEntradaBlog);
            payloadXml = encriptar(payloadXml);
            
            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);
            
            String message = utilTraveller.marshall(traveller);
            String respuesta = serv.crearPaginaWebEncripted(message);
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
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<ListWrapperTraveller> utilPayload = new MarshallUnmarshallUtil<ListWrapperTraveller>();
            
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
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();

            String payloadXml = encriptar("" + id);

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
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<FilterQueryTraveller> utilPayload = new MarshallUnmarshallUtil<FilterQueryTraveller>();
            
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
            MarshallUnmarshallUtil<ListWrapperTraveller> resultUtilPaload = new MarshallUnmarshallUtil<ListWrapperTraveller>();
            ListWrapperTraveller listadoWrapper = resultUtilPaload.unmarshall(ListWrapperTraveller.class, payload);
            return listadoWrapper.getItems();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public PaginaWebTraveller obtenerPaginaWeb(long id) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();

            String payloadXml = encriptar("" + id);

            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);
            String message = utilTraveller.marshall(traveller);

            String travellerString = serv.obtenerPaginaWebEncripted(message);

            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);
            
            payloadXml = desencriptar(traveller.getPayload());
            
            MarshallUnmarshallUtil<PaginaWebTraveller> returnTraveller = new MarshallUnmarshallUtil<PaginaWebTraveller>();
            
            return returnTraveller.unmarshall(PaginaWebTraveller.class, payloadXml);
            
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

    public boolean modificarPaginaWeb(long id, String titulo, Date fecha, String html) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {

            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarshallUnmarshallUtil<PaginaWebTraveller>();

            PaginaWebTraveller nuevaPaginaWeb = new PaginaWebTraveller(titulo, html.getBytes(), fecha);
            nuevaPaginaWeb.setId(id);

            String payloadXml = utilPayload.marshall(nuevaPaginaWeb);
            payloadXml = encriptar(payloadXml);

            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);

            String message = utilTraveller.marshall(traveller);
            String respuesta = serv.modificarPaginaWebEncripted(message);
            Traveller inObject = utilTraveller.unmarshall(Traveller.class, respuesta);
            respuesta = desencriptar(inObject.getPayload());
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public List<ListItemTraveller> entradasDeBlogFiltrando(String titulo, String fechaPublicacion, 
            String contenido, String autor, String tags) throws Exception {

        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<FilterQueryTraveller> utilPayload = new MarshallUnmarshallUtil<FilterQueryTraveller>();
            
            FilterQueryTraveller queryTraveller = new FilterQueryTraveller();
            queryTraveller.setTitulo(titulo);
            queryTraveller.setFecha(parsearFecha(fechaPublicacion));
            queryTraveller.setTag(tags);
            queryTraveller.setNombre(autor);
            queryTraveller.setTexto(contenido);

            String payloadXml = utilPayload.marshall(queryTraveller);
            payloadXml = encriptar(payloadXml);
            
            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);
            
            String message = utilTraveller.marshall(traveller);
            String travellerString = serv.listarEntradaBlogFiltrandoEncripted(message);
            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);
            
            String payload = traveller.getPayload();
            payload = desencriptar(payload);
            MarshallUnmarshallUtil<ListWrapperTraveller> resultUtilPaload = new MarshallUnmarshallUtil<ListWrapperTraveller>();
            ListWrapperTraveller listadoWrapper = resultUtilPaload.unmarshall(ListWrapperTraveller.class, payload);
            return listadoWrapper.getItems();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<ListItemTraveller> todasLasEntradasDeBlog() throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<ListWrapperTraveller> utilPayload = new MarshallUnmarshallUtil<ListWrapperTraveller>();
            
            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload("");
            String message = utilTraveller.marshall(traveller);
            
            String travellerString = serv.listarEntradasDeBlogEncripted(message);
            
            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);
            
            String payload = traveller.getPayload();
            payload = desencriptar(payload);
            
            ListWrapperTraveller listadoWrapper = utilPayload.unmarshall(ListWrapperTraveller.class, payload);
            return listadoWrapper.getItems();
        } catch (Exception ex) {
            throw ex;
        }     
    }

    public EntradaBlogTraveller obtenerEntradaDeBlog(long id) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();

            String payloadXml = encriptar("" + id);

            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);
            String message = utilTraveller.marshall(traveller);

            String travellerString = serv.obtenerEntradaBlogEncripted(message);

            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);
            
            payloadXml = desencriptar(traveller.getPayload());
            
            MarshallUnmarshallUtil<EntradaBlogTraveller> returnTraveller = new MarshallUnmarshallUtil<EntradaBlogTraveller>();
            
            return returnTraveller.unmarshall(EntradaBlogTraveller.class, payloadXml);
            
        } catch (Exception ex) {
            throw ex;
        }
    }

    public boolean modificarPaginaWeb(long id, String nombreAutor, 
            String titulo, String contenido, Date fechaPublicacion, 
            List<String> tags) throws Exception {
        
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarshallUnmarshallUtil<EntradaBlogTraveller>();

            EntradaBlogTraveller nuevaEntradaBlog = new EntradaBlogTraveller(titulo, nombreAutor, contenido, tags, fechaPublicacion);
            nuevaEntradaBlog.setId(id);

            String payloadXml = utilPayload.marshall(nuevaEntradaBlog);
            payloadXml = encriptar(payloadXml);

            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);

            String message = utilTraveller.marshall(traveller);
            String respuesta = serv.modificarEntradaBlogEncripted(message);
            Traveller inObject = utilTraveller.unmarshall(Traveller.class, respuesta);
            respuesta = desencriptar(inObject.getPayload());
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public boolean eliminarEntradaBlog(long id) throws Exception {
        ManejadorContenidosWebService_Service service = new ManejadorContenidosWebService_Service();
        ManejadorContenidosWebService serv = service.getManejadorContenidosWebServicePort();
        addUserAndPassToHeader((BindingProvider) serv);
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();

            String payloadXml = encriptar("" + id);

            Traveller traveller = new Traveller();
            traveller.setId(UsuarioManagerSingleton.getInstance().getIdUser());
            traveller.setPayload(payloadXml);
            String message = utilTraveller.marshall(traveller);

            String travellerString = serv.eliminarEntradaBlogEncripted(message);

            traveller = utilTraveller.unmarshall(Traveller.class, travellerString);

            String payload = traveller.getPayload();
            return Boolean.parseBoolean(desencriptar(payload));
        } catch (Exception ex) {
            throw ex;
        }
    }

}
