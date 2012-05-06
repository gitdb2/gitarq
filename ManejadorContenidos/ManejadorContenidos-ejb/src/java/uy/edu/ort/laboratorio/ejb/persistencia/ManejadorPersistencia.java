/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.persistencia;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.logging.Level;
import javax.ejb.Stateless;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.configuracion.LectorDeConfiguracion;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorPersistencia implements ManejadorPersistenciaLocal {

    /**
     * almacen de archivos XML a persistir.
     */
    private static final String REPOSITORIO_XML = LectorDeConfiguracion.getInstance().getMensaje("save.files.path");
    /**
     * extension de los archivos XML.
     */
    private static final String EXTENSION_ARCHIVO = ".xml";

    /**
     * persiste en disco el objeto recibido por parametro.
     *
     * @param contenido
     * @return identificador unico del objeto.
     * @throws ArquitecturaException
     */
    @Override
    public Long persistir(PaginaWeb contenido) throws ArquitecturaException {
        Long idObjeto = obtenerIdObjeto();
        contenido.setOid(idObjeto);
        String rutaXML = obtenerRuta(contenido);
        try {
            guardarArchivoEnDisco(contenido, rutaXML);
            Logger.info(ManejadorPersistencia.class,
                    "Se guardo correctamente el archivo " + rutaXML);
            return idObjeto;
        } catch (FileNotFoundException ex) {
            Logger.error(ManejadorPersistencia.class,
                    "Error al guardar el archivo " + rutaXML);

            Logger.debug(ManejadorPersistencia.class, Logger.getStackTrace(ex));

            throw new ArquitecturaException(
                    "Error al guardar el archivo " + rutaXML);
        } catch (Exception ex) {
            Logger.error(ManejadorPersistencia.class,
                    "No se pudo guardar el acrchivo " + rutaXML);

            Logger.debug(ManejadorPersistencia.class, Logger.getStackTrace(ex));

            throw new ArquitecturaException(
                    "Error, No se pudo guardar el acrchivo " + rutaXML);
        }
    }

    @Override
    public Long persistir(EntradaBlog contenido) throws ArquitecturaException {
        Long idObjeto = obtenerIdObjeto();
        contenido.setOid(idObjeto);
        String rutaXML = obtenerRuta(contenido);
        try {
            guardarArchivoEnDiscoPlain(contenido, rutaXML);
            Logger.info(ManejadorPersistencia.class,
                    "Se guardo correctamente el archivo " + rutaXML);
            return idObjeto;
        } catch (FileNotFoundException ex) {
            Logger.error(ManejadorPersistencia.class,
                    "Error al guardar el archivo " + rutaXML);

            Logger.debug(ManejadorPersistencia.class, Logger.getStackTrace(ex));

            throw new ArquitecturaException(
                    "Error al guardar el archivo " + rutaXML);
        } catch (Exception ex) {
            Logger.error(ManejadorPersistencia.class,
                    "No se pudo guardar el acrchivo " + rutaXML);

            Logger.debug(ManejadorPersistencia.class, Logger.getStackTrace(ex));

            throw new ArquitecturaException(
                    "Error, No se pudo guardar el acrchivo " + rutaXML);
        }
    }

    /**
     * serializa en formato XML el contenido en la rutaAbsoluta.
     *
     * @param contenido
     * @param rutaAbsoluta
     * @throws FileNotFoundException
     */
    private void guardarArchivoEnDisco(Contenido contenido,
            String rutaAbsoluta) throws FileNotFoundException {
        crearRutaDestino(rutaAbsoluta);
        XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(new FileOutputStream(rutaAbsoluta)));
        encoder.writeObject(contenido);
        encoder.close();
    }

    private void guardarArchivoEnDiscoPlain(Contenido contenido,
            String rutaAbsoluta) throws FileNotFoundException {
        FileWriter out = null;
        try {
            crearRutaDestino(rutaAbsoluta);
            out = new FileWriter(rutaAbsoluta, true);
            out.write(contenido.toString() + "\n");
            out.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ManejadorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ManejadorPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * devuelve la ruta al XML.
     *
     * @param contenido
     * @return ruta absoluta al XML.
     */
    private String obtenerRuta(PaginaWeb contenido) {
        return REPOSITORIO_XML + File.separator
                + contenido.obtenerRutaArchivo() + EXTENSION_ARCHIVO;
    }

    private String obtenerRuta(EntradaBlog contenido) {
        return REPOSITORIO_XML + File.separator
                + contenido.obtenerRutaArchivo() + ".txt";
    }

    /**
     * el id del objeto es el tiempo actual en milisegundos.
     *
     * @return
     */
    private Long obtenerIdObjeto() {
        return System.currentTimeMillis();
    }

    /**
     * crea las carpetas necesarias para persistir el archivo.
     *
     * @param rutaArchivo
     */
    private void crearRutaDestino(String rutaArchivo) {
        File rutaAbsoluta = new File(rutaArchivo);
        File directorio = new File(rutaAbsoluta.getParent());
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }
}
