/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.persistencia;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.ejb.Stateless;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorPersistencia implements ManejadorPersistenciaLocal {

    private static final String REPOSITORIO_XML = "C:\\arqsoft\\";
    
    private static Long idObjeto = 0l;
    
    @Override
    public Long persistir(Contenido contenido) throws ArquitecturaException {
        String rutaXML = obtenerRutaXML(contenido);
        try {
            guardarArchivoEnDisco(contenido, rutaXML);
            Logger.info(ManejadorPersistencia.class, "Se guardo correctamente el archivo " + rutaXML);
            return idObjeto++;
        } catch (FileNotFoundException ex) {
            Logger.error(ManejadorPersistencia.class, "Error al guardar el archivo " + rutaXML);
            return null;
        }
    }
    
    private void guardarArchivoEnDisco(Contenido contenido, String rutaAbsoluta) throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(rutaAbsoluta)));
        encoder.writeObject(contenido);
        encoder.close();
    }
    
    private String obtenerRutaXML(Contenido contenido) {
        return REPOSITORIO_XML + idObjeto + "\\" + contenido.toString() + ".xml";
    }

}
