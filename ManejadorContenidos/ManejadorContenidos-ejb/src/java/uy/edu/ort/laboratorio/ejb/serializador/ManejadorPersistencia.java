/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.serializador;

import javax.ejb.Stateless;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorPersistencia implements ManejadorPersistenciaLocal {

    @Override
    public Long persistir(Contenido contenido) throws ArquitecturaException {
        //marsharll y save xml
        return System.currentTimeMillis();
    }

     @Override
    public Long persistir(String contenido) throws ArquitecturaException {
        //marsharll y save xml
        return System.currentTimeMillis();
    }
    
}
