/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.persistencia;

import javax.ejb.Local;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;

/**
 *
 * @author rodrigo
 */
@Local
public interface ManejadorPersistenciaLocal {
     /**
     * persiste en disco el objeto recibido por parametro.
     * @param contenido
     * @return identificador unico del objeto.
     * @throws ArquitecturaException
     */
    public Long persistir(Contenido contenido) throws ArquitecturaException;
    
}
