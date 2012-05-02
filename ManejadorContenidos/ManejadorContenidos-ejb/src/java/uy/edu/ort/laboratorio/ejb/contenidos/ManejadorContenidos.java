/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;


import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.serializador.ManejadorPersistenciaLocal;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorContenidos implements ManejadorContenidosRemote, ManejadorContenidosLocal {

    @EJB
    private ManejadorPersistenciaLocal manejadorPersistencia;
    
    public Long crearContenido(Contenido contenido) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, contenido);
        Long ret = manejadorPersistencia.persistir(contenido);
        return ret;
    }
    
    @Override
    public String prueba(String entra) {
         Logger.info(ManejadorContenidos.class, entra);
        return "pasado por ejb "+entra;
    }

    @Override
    public Long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List tags) {
        Logger.info(ManejadorContenidos.class, titulo );
        return System.currentTimeMillis();
    }

    @Override
    public Long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion, String html) {
        return System.currentTimeMillis();
    }

}
