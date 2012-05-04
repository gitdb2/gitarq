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
import org.apache.log4j.Logger;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.contenidos.ManejadorContenidos;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
//import uy.edu.ort.laboratorio.logger.Logger;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorPersistencia implements ManejadorPersistenciaLocal {

    private static final String REPOSITORIO_XML = "/storage/ORT/";
     private static Logger logger = Logger.getLogger(ManejadorPersistencia.class);
    
    @Override
    public Long persistir(Contenido contenido) throws ArquitecturaException {
        try {
            
            String rutaXML = REPOSITORIO_XML + contenido.toString() + ".xml";
            logger.info( rutaXML);
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(rutaXML)));
            encoder.writeObject(contenido);
            encoder.close();
            return System.currentTimeMillis();
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

}
