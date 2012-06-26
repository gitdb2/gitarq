/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import uy.edu.ort.laboratorio.ejb.contenidos.ManejadorContenidosLocal;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.seguridad.BeanSeguridadLocal;
import uy.edu.ort.laboratorio.logger.Logger;
import uy.edu.ort.laboratorio.travellers.datatype.*;
import uy.edu.ort.laboratorio.travellers.utiles.MarsharUnmarshallUtil;

/**
 * Fachada de la aplicacion para exponer como webservice. Delega el
 * comportamiento un ejb.
 *
 * @author tanquista
 */
@WebService(serviceName = "ManejadorContenidosWebService")
@Stateless()
@HandlerChain(file = "autenticacion_handler.xml")
public class ManejadorContenidosWebService {

    @EJB
    private BeanSeguridadLocal seguridad;
    @EJB
    private ManejadorContenidosLocal manejadorContenidos;

    /**
     * crea un Contenido del tipo entrada de blog.
     *
     * @param dataIn
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "crearEntradaBlogEncripted")
    public String crearEntradaBlogEncripted(
            @WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;
        try {

            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarsharUnmarshallUtil<EntradaBlogTraveller>();


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
                Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
                Logger.debug(ManejadorContenidosWebService.class, "params:" + titulo + ", " + nombreAutor + ", " + fechaPublicacion + ", " + texto + ", " + tags);
                Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearEntradaBlog"), e);
            }

        } catch (JAXBException e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));

            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.crearEntradaBlog"), e);
        }

        return ret;

    }

    /**
     * actualiza el contenido de una entrada de blog
     *
     * @param dataIn
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "modificarEntradaBlogEncripted")
    public String modificarEntradaBlogEncripted(
            @WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;
        try {

            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarsharUnmarshallUtil<EntradaBlogTraveller>();


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
     */
    @WebMethod(operationName = "crearPaginaWebEncripted")
    public String crearContenidoPaginaWebEncripted(@WebParam(name = "data") String dataIn)
            throws ArquitecturaException {
        String ret = null;

        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarsharUnmarshallUtil<PaginaWebTraveller>();


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
                throw new ArquitecturaException("Ocurrio un error al crearContenidoPaginaWeb", e);
            }
        } catch (JAXBException e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException("Ocurrio un error al crearContenidoPaginaWeb", e);
        }

        return ret;
    }

    /**
     * crea un Contenido del tipo pagina web.
     *
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "modificarPaginaWebEncripted")
    public String modificarPaginaWebEncripted(@WebParam(name = "data") String dataIn)
            throws ArquitecturaException {
        String ret = null;

        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarsharUnmarshallUtil<PaginaWebTraveller>();


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
                throw new ArquitecturaException("Ocurrio un error al modificarPaginaWeb", e);
            }
        } catch (JAXBException e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException("Ocurrio un error al modificarPaginaWeb", e);
        }

        return ret;

    }

    /**
     * elimina una entrada de blog
     *
     * @param idEntradaBlog
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "eliminarEntradaBlogEncripted")
    public String eliminarEntradaBlogEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;

        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarsharUnmarshallUtil<ListWrapperTraveller>();

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
     * @param idPaginaWeb
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "eliminarPaginaWebEncripted")
    public String eliminarPaginaWebEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;

        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarsharUnmarshallUtil<ListWrapperTraveller>();

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
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "listarPaginasWebEncripted")
    public String listarPaginasWebEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;

        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarsharUnmarshallUtil<ListWrapperTraveller>();

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
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "listarEntradasDeBlogEncripted")
    public String listarEntradasDeBlogEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;

        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarsharUnmarshallUtil<ListWrapperTraveller>();

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
     * @param idPaginaWeb
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "obtenerPaginaWebEncripted")
    public String obtenerPaginaWebEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        String ret = null;
        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<PaginaWebTraveller> utilPayload = new MarsharUnmarshallUtil<PaginaWebTraveller>();


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
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
            }

        } catch (ArquitecturaException e) {
            throw e;
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
     * @param idEntradaBlog
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "obtenerEntradaBlogEncripted")
    public String obtenerEntradaBlogEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {
        //(@WebParam(name = "idEntradaBlog") long idEntradaBlog) throws ArquitecturaException {
        String ret = null;

        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<EntradaBlogTraveller> utilPayload = new MarsharUnmarshallUtil<EntradaBlogTraveller>();


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
                throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
            }

        } catch (ArquitecturaException e) {
            throw e;
        } catch (Exception e) {
            Logger.error(ManejadorContenidosWebService.class, e.getClass().getName() + e.getMessage());
            Logger.debug(ManejadorContenidosWebService.class, Logger.getStackTrace(e));
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.obtenerEntradaBlog"), e);
        }

        return ret;
    }

    /**
     *
     * @param nombre
     * @param fechaPublicacion
     * @return
     * @throws ArquitecturaException
     */
    @WebMethod(operationName = "listarPaginasWebFiltrandoEncripted")
    public String listarPaginasWebFiltrandoEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;
        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<FilterQueryTraveller> utilPayload = new MarsharUnmarshallUtil<FilterQueryTraveller>();
            MarsharUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarsharUnmarshallUtil<ListWrapperTraveller>();


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

    @WebMethod(operationName = "listarEntradaBlogFiltrandoEncripted")
    public String listarEntradaBlogFiltrandoEncripted(@WebParam(name = "data") String dataIn) throws ArquitecturaException {

        String ret = null;
        try {
            MarsharUnmarshallUtil<Traveller> utilTraveller = new MarsharUnmarshallUtil<Traveller>();
            MarsharUnmarshallUtil<FilterQueryTraveller> utilPayload = new MarsharUnmarshallUtil<FilterQueryTraveller>();
            MarsharUnmarshallUtil<ListWrapperTraveller> utilReturner = new MarsharUnmarshallUtil<ListWrapperTraveller>();


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

    private void checkParametosActualizarEntradaBlog(long idEntradaBlog, String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
        if (idEntradaBlog == 0) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        }

        checkParametrosCrearBlog(titulo, nombreAutor, fechaPublicacion, texto, tags);

    }

    private void checkParametosActualizarPaginaWeb(long idPaginaWeb, String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        if (idPaginaWeb == 0) {
            throw new ArquitecturaException(LectorDeConfiguracion.getInstance().getMensaje("errors.ejb.webservice.formatoIdentificadorIncorrecto"));
        }

        checkParametrosPaginaWeb(nombre, fechaPublicacion, html);
    }
}
