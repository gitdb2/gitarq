package uy.edu.ort.laboratorio.ejb.webservice;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;
import uy.edu.ort.laboratorio.datatype.DataEntradaBlog;
import uy.edu.ort.laboratorio.datatype.DataPaginaWeb;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.negocio.contenidos.ManejadorContenidosLocal;
import uy.edu.ort.laboratorio.ejb.negocio.seguridad.BeanSeguridadLocal;
import uy.edu.ort.laboratorio.logger.Logger;
import uy.edu.ort.laboratorio.travellers.datatype.*;
import uy.edu.ort.laboratorio.travellers.utiles.MarshallUnmarshallUtil;

/**
 * Fachada de la aplicacion para exponer como webservice. Delega el
 * comportamiento un ejb.
 *
 * @author tanquista
 */
@WebService(serviceName = "ManejadorContenidosWebService")
@Stateless()
@HandlerChain(file = "autenticacion_handler.xml")//interceptor del webservice
public class ManejadorContenidosWebService {

    @EJB
    private BeanSeguridadLocal seguridad;
    @EJB
    private ManejadorContenidosLocal manejadorContenidos;

    /**
     * crea un Contenido del tipo entrada de blog.
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera un xml (encriptado) que se corresponda con el objeto
     * serializado: EntradaBlogTraveller
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a: El id de la
     * entrada de blog creada
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "crearEntradaBlogEncripted")
    public String crearEntradaBlogEncripted(
            @WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;
        try {

            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarshallUnmarshallUtil<EntradaBlogTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            EntradaBlogTraveller payloadObject = utilPayload.unmarshall(EntradaBlogTraveller.class, payload);


            String titulo = payloadObject.getTitulo();
            String nombreAutor = payloadObject.getNombreAutor();
            Date fechaPublicacion = payloadObject.getFechaPublicacion();
            String texto = payloadObject.getTexto();
            List<String> tags = payloadObject.getTags();

            checkParametrosCrearBlog(payloadObject.getTitulo(),
                    payloadObject.getNombreAutor(),
                    payloadObject.getFechaPublicacion(),
                    payloadObject.getTexto(),
                    payloadObject.getTags());

            try {
                Long idc = manejadorContenidos.crearContenidoEntradaBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);


                inObject.setPayload(seguridad.encriptar(id, "" + idc));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class,
                        e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, "params:"
                        + titulo + ", " + nombreAutor + ", " + fechaPublicacion + ", "
                        + texto + ", " + tags);
                Logger.debug(ManejadorContenidosWebService.class,
                        Logger.getStackTrace(e));
                throw new ArquitecturaException(
                        LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearEntradaBlog"), e);
            }

        } catch (JAXBException e) {
            Logger.error(ManejadorContenidosWebService.class,
                    e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));

            throw new ArquitecturaException(
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearEntradaBlog"), e);
        }

        return ret;

    }

    /**
     * actualiza el contenido de una entrada de blog
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera un xml (encriptado) que se corresponda con el objeto
     * serializado: EntradaBlogTraveller
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a: El id de la
     * entrada de blog creada
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "modificarEntradaBlogEncripted")
    public String modificarEntradaBlogEncripted(
            @WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;
        try {

            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarshallUnmarshallUtil<EntradaBlogTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            EntradaBlogTraveller payloadObject = utilPayload.unmarshall(EntradaBlogTraveller.class, payload);


            String titulo = payloadObject.getTitulo();
            String nombreAutor = payloadObject.getNombreAutor();
            Date fechaPublicacion = payloadObject.getFechaPublicacion();
            String texto = payloadObject.getTexto();
            List<String> tags = payloadObject.getTags();

            checkParametosActualizarEntradaBlog(
                    payloadObject.getId(),
                    payloadObject.getTitulo(),
                    payloadObject.getNombreAutor(),
                    payloadObject.getFechaPublicacion(),
                    payloadObject.getTexto(),
                    payloadObject.getTags());

            try {
                Long idc = manejadorContenidos.modificarContenidoEntradaBlog(payloadObject.getId(), titulo, nombreAutor, fechaPublicacion, texto, tags);


                inObject.setPayload(seguridad.encriptar(id, "" + idc));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, "params:" + titulo + ", " + nombreAutor + ", " + fechaPublicacion + ", " + texto + ", " + tags);
                Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.modificarEntradaBlog"), e);
            }

        } catch (JAXBException e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));

            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.modificarEntradaBlog"), e);
        }

        return ret;

    }

    /**
     * crea un Contenido del tipo pagina web.
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera un xml (encriptado) que se corresponda con el objeto
     * serializado: PaginaWebTraveller
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a: El id de la
     * Pagina web creada
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "crearPaginaWebEncripted")
    public String crearContenidoPaginaWebEncripted(@WebParam(name = "data") String dataIn)
            throws ArquitecturaException {
        String ret = null;

        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarshallUnmarshallUtil<PaginaWebTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            PaginaWebTraveller payloadObject = utilPayload.unmarshall(PaginaWebTraveller.class, payload);



            String nombre = payloadObject.getNombre();
            Date fechaPublicacion = payloadObject.getFechaPublicacion();
            byte[] html = payloadObject.getHtml();



            checkParametrosPaginaWeb(nombre, fechaPublicacion, html);
            try {
                Long idc = manejadorContenidos.crearContenidoPaginaWeb(nombre, fechaPublicacion, html);
                inObject.setPayload(seguridad.encriptar(id, "" + idc));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, "params:" + nombre + ", " + fechaPublicacion + ", " + html);
                Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
                throw new ArquitecturaException(
                        LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearPaginaWeb"), e);
            }
        } catch (JAXBException e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearPaginaWeb"), e);
        }

        return ret;
    }

    /**
     * Modifica el contenido de una pagina web
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera un xml (encriptado) que se corresponda con el objeto
     * serializado: PaginaWebTraveller
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a: El id de la
     * Pagina web creada
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "modificarPaginaWebEncripted")
    public String modificarPaginaWebEncripted(@WebParam(name = "data") String dataIn)
            throws ArquitecturaException {
        String ret = null;

        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarshallUnmarshallUtil<PaginaWebTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            PaginaWebTraveller payloadObject = utilPayload.unmarshall(PaginaWebTraveller.class, payload);



            String nombre = payloadObject.getNombre();
            Date fechaPublicacion = payloadObject.getFechaPublicacion();
            byte[] html = payloadObject.getHtml();


            checkParametosActualizarPaginaWeb(payloadObject.getId(), nombre, fechaPublicacion, html);
            checkParametrosPaginaWeb(nombre, fechaPublicacion, html);
            try {
                Long idc = manejadorContenidos.modificarContenidoPaginaWeb(payloadObject.getId(), nombre, fechaPublicacion, html);
                inObject.setPayload(seguridad.encriptar(id, "" + idc));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, "params:" + nombre + ", " + fechaPublicacion + ", " + html);
                Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.modificarPaginaWeb"), e);
            }
        } catch (JAXBException e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(
                    LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.modificarPaginaWeb"), e);
        }

        return ret;

    }

    /**
     * elimina una entrada de blog
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera un String (encriptado) que se corresponda con el id de
     * la entrada de blog a eliminar
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a: un boolean
     * que indica true si se elimino o false de lo contrario
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "eliminarEntradaBlogEncripted")
    public String eliminarEntradaBlogEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;

        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();

            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();
            payload = seguridad.desencriptar(id, payload);

            Long idEntradaBlog = null;
            try {
                idEntradaBlog = Long.valueOf(payload);

                boolean result = manejadorContenidos.eliminarEntradaBlog(idEntradaBlog);

                inObject.setPayload(seguridad.encriptar(id, result + ""));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, "params:" + idEntradaBlog);
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"), e);
            }

        } catch (ArquitecturaException e) {
            throw e;
        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.eliminarEntradaBlog"), e);
        }
        return ret;
    }

    /**
     * elimina una pagina web
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera un String (encriptado) que se corresponda con el id de
     * la Pagina web a eliminar
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a: un boolean
     * que indica true si se elimino o false de lo contrario
     *
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "eliminarPaginaWebEncripted")
    public String eliminarPaginaWebEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;

        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();

            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();
            payload = seguridad.desencriptar(id, payload);

            Long idPaginaWeb = null;
            try {
                idPaginaWeb = Long.valueOf(payload);

                boolean result = manejadorContenidos.eliminarPaginaWeb(idPaginaWeb);

                inObject.setPayload(seguridad.encriptar(id, result + ""));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, "params:" + idPaginaWeb);
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"), e);
            }

        } catch (ArquitecturaException e) {
            throw e;
        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.eliminarPaginaWeb"), e);
        }
        return ret;

    }

    /**
     * lista todas las paginas web
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload no se espera nada, por lo que ni se desencripta
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a XML de
     * serializar el objeto ListWrapperTraveller, conteniendo el id de la pagina
     * y el nombre
     *
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "listarPaginasWebEncripted")
    public String listarPaginasWebEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;

        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarshallUnmarshallUtil<ListWrapperTraveller>();

            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            List<DataPaginaWeb> paginas = manejadorContenidos.listarPaginasWeb();

            ListWrapperTraveller retObj = new ListWrapperTraveller();
            for (DataPaginaWeb pag : paginas) {
                retObj.add(new ListItemTraveller(pag.getId(), pag.getNombre()));
            }

            String tmp = utilReturner.marshall(retObj);
            inObject.setPayload(seguridad.encriptar(id, tmp));
            ret = utilTraveller.marshall(inObject);

        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarPaginaWeb"), e);
        }

        return ret;
    }

    /**
     * lista todas las entradas de blog
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload no se espera nada, por lo que ni se desencripta
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a XML de
     * serializar el objeto ListWrapperTraveller, conteniendo el id de la
     * entrade de blog y el nombre del autor
     *
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "listarEntradasDeBlogEncripted")
    public String listarEntradasDeBlogEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;

        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarshallUnmarshallUtil<ListWrapperTraveller>();

            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            List<DataEntradaBlog> paginas = manejadorContenidos.listarEntradasDeBlog();

            ListWrapperTraveller retObj = new ListWrapperTraveller();
            for (DataEntradaBlog pag : paginas) {
                retObj.add(new ListItemTraveller(pag.getId(), pag.getNombreAutor()));
            }

            String tmp = utilReturner.marshall(retObj);
            inObject.setPayload(seguridad.encriptar(id, tmp));
            ret = utilTraveller.marshall(inObject);

        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarEntradaBlog"));
        }

        return ret;
    }

    /**
     * devuelve la pagina web asociada al identificador
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera el string encriptado correspondiente al id de la pagina
     * web a obtener
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a XML de
     * serializar el objeto PaginaWebTraveller, conteniendo la informacion de la
     * pagina web
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "obtenerPaginaWebEncripted")
    public String obtenerPaginaWebEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarshallUnmarshallUtil<PaginaWebTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            Long idPaginaWeb = null;
            try {
                idPaginaWeb = Long.valueOf(payload);
                PaginaWeb pagina = manejadorContenidos.obtenerPaginaWeb(idPaginaWeb);

                PaginaWebTraveller retorno = new PaginaWebTraveller(pagina.getNombre(), pagina.getHtml(), pagina.getFechaPublicacion());
                retorno.setId(pagina.getId());

                payload = utilPayload.marshall(retorno);
                inObject.setPayload(seguridad.encriptar(id, payload));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"), e);
            }

        } catch (ArquitecturaException e) {
            throw e;
            //en caso que se produzca una excepcion de las nuestras se vuelve a trirar para que llegue al cliente
        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.obtenerPaginaWeb"), e);
        }
        return ret;
    }

    /**
     * devuelve la pagina web asociada al identificador
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera el string encriptado correspondiente al id de la
     * entrada de blog a obtener
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a XML de
     * serializar el objeto EntradaBlogTraveller, conteniendo la informacion de
     * la entrada de blog pedida
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "obtenerEntradaBlogEncripted")
    public String obtenerEntradaBlogEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;

        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarshallUnmarshallUtil<EntradaBlogTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            Long idEntradaBlog = null;
            try {
                idEntradaBlog = Long.valueOf(payload);
                EntradaBlog blog = manejadorContenidos.obtenerEntradasBlog(idEntradaBlog);

                EntradaBlogTraveller retorno = new EntradaBlogTraveller(
                        blog.getTitulo(),
                        blog.getNombreAutor(),
                        blog.getTexto(),
                        blog.getTags(),
                        blog.getFechaPublicacion());
                retorno.setId(blog.getId());

                payload = utilPayload.marshall(retorno);
                inObject.setPayload(seguridad.encriptar(id, payload));

                ret = utilTraveller.marshall(inObject);

            } catch (Exception e) {
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));

                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"), e);
            }

        } catch (ArquitecturaException e) {
            throw e;
            //en caso que se produzca una excepcion de las nuestras se vuelve a trirar para que llegue al cliente

        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.obtenerEntradaBlog"), e);
        }

        return ret;
    }

    /**
     * Realiza una busqueda parametrizada sobre las paginas web, retornando los
     * <id, nombre> que matcheen
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera el string encriptado correspondiente al xml de
     * serializar el objeto FilterQueryTraveller con los filtros de busqueda
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a XML de
     * serializar el objeto ListWrapperTraveller, conteniendo la lista de las
     * tuplas <id ,nombre>
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "listarPaginasWebFiltrandoEncripted")
    public String listarPaginasWebFiltrandoEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<FilterQueryTraveller> utilPayload = new MarshallUnmarshallUtil<FilterQueryTraveller>();
            MarshallUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarshallUnmarshallUtil<ListWrapperTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            FilterQueryTraveller filter = utilPayload.unmarshall(FilterQueryTraveller.class, payload);

            List<DataPaginaWeb> paginas = manejadorContenidos.listarPaginasWebFiltrando(filter.getTitulo(), filter.getFecha());

            ListWrapperTraveller retObj = new ListWrapperTraveller();
            for (DataPaginaWeb pag : paginas) {
                retObj.add(new ListItemTraveller(pag.getId(), pag.getNombre()));
            }

            String tmp = utilReturner.marshall(retObj);
            inObject.setPayload(seguridad.encriptar(id, tmp));
            ret = utilTraveller.marshall(inObject);


        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarFiltrandoPaginaWeb"), e);
        }
        return ret;
    }

    /**
     * Realiza una busqueda parametrizada sobre las entradas de blog, retornando
     * los <id, nombre> que matcheen
     *
     * @param dataIn contiene el xml del objeto Traveller: en el id debe venir
     * el idDelusuario obtenido al llamar al ws de autenticacionen. En el
     * payload se espera el string encriptado correspondiente al xml de
     * serializar el objeto FilterQueryTraveller con los filtros de busqueda
     * @return Se retorna el xml correspondiente a un objeto Traveller, con el
     * payload cargado con el string encriptado correspondiente a XML de
     * serializar el objeto ListWrapperTraveller, conteniendo la lista de las
     * tuplas <id ,nombre>
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "listarEntradaBlogFiltrandoEncripted")
    public String listarEntradaBlogFiltrandoEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;
        try {
            MarshallUnmarshallUtil<Traveller> utilTraveller = new MarshallUnmarshallUtil<Traveller>();
            MarshallUnmarshallUtil<FilterQueryTraveller> utilPayload = new MarshallUnmarshallUtil<FilterQueryTraveller>();
            MarshallUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarshallUnmarshallUtil<ListWrapperTraveller>();


            Traveller inObject = utilTraveller.unmarshall(Traveller.class, dataIn);
            Long id = inObject.getId();

            String payload = inObject.getPayload();

            payload = seguridad.desencriptar(id, payload);

            FilterQueryTraveller filter = utilPayload.unmarshall(FilterQueryTraveller.class, payload);

            List<DataEntradaBlog> paginas = manejadorContenidos.listarEntradaBlogFiltrando(
                    filter.getTitulo(),
                    filter.getFecha(),
                    filter.getTexto(),
                    filter.getNombre(),
                    filter.getTag());

            ListWrapperTraveller retObj = new ListWrapperTraveller();
            for (DataEntradaBlog pag : paginas) {
                retObj.add(new ListItemTraveller(pag.getId(), pag.getNombreAutor()));
            }

            String tmp = utilReturner.marshall(retObj);
            inObject.setPayload(seguridad.encriptar(id, tmp));
            ret = utilTraveller.marshall(inObject);


        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.listarFiltrandoEntradaBlog"), e);
        }
        return ret;
    }

    /**
     * Chequea los parametros para dar de alta de un blog.
     *
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @throws ArquitecturaException
     */
    private void checkParametrosCrearBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {

        if (fechaPublicacion == null) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoFechaIncorrecto"));
        }
        if (nombreAutor == null || nombreAutor.isEmpty()) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoAutorIncorrecto"));
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoTituloIncorrecto"));
        }
        if (tags == null) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoTagsIncorrecto"));
        }

        //TODO chequear que texto no sea null ni vacio
    }

    /**
     * Chequea los parametros para dar de alta una pagina web.
     *
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @throws ArquitecturaException
     */
    private void checkParametrosPaginaWeb(String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {

        if (fechaPublicacion == null) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoFechaIncorrecto"));
        }

        if (nombre == null || nombre.isEmpty()) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoNombreIncorrecto"));
        }

        //TODO chqeuar que no sea vacio
        if (html == null) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoHTMLIncorrecto"));
        }
    }

    /**
     * Chequea la correctitud de los parametros para actualizar una entrada de blog
     *
     * @param idEntradaBlog
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @throws ArquitecturaException
     */
    private void checkParametosActualizarEntradaBlog(long idEntradaBlog, String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
        if (idEntradaBlog == 0) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        }

        checkParametrosCrearBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);

    }

    /**
     * Chequea la correctitud de los parametros para actualizar una pagina web
     *
     * @param idPaginaWeb
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @throws ArquitecturaException
     */
    private void checkParametosActualizarPaginaWeb(long idPaginaWeb, String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        if (idPaginaWeb == 0) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        }

        checkParametrosPaginaWeb(nombre, fechaPublicacion, html);
    }
}
