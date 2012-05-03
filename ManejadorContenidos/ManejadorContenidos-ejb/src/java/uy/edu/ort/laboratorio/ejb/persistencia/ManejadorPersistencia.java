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

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorPersistencia implements ManejadorPersistenciaLocal {

    private static final String REPOSITORIO_XML = "C:\\xmls\\";
    
    @Override
    public Long persistir(Contenido contenido) throws ArquitecturaException {
        try {
            String rutaXML = REPOSITORIO_XML + contenido.toString() + ".xml";
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(rutaXML)));
            encoder.writeObject(contenido);
            encoder.close();
            return System.currentTimeMillis();
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

}
