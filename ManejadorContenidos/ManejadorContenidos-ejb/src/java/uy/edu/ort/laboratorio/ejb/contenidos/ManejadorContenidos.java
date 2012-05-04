/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;


import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;

import uy.edu.ort.laboratorio.ejb.persistencia.ManejadorPersistenciaLocal;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorContenidos implements ManejadorContenidosRemote, ManejadorContenidosLocal {
    private static Logger logger = Logger.getLogger(ManejadorContenidos.class);
    @EJB
    private ManejadorPersistenciaLocal manejadorPersistencia;
    
    @Override
    public Long crearContenido(Contenido contenido) throws ArquitecturaException {
        logger.info(contenido);
        Long ret = manejadorPersistencia.persistir(contenido);
        return ret;
    }
    


    @Override
    public Long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
	logger.info( titulo );
        return manejadorPersistencia.persistir(new EntradaBlog(titulo, nombreAutor, texto, tags, fechaPublicacion));
    }

    @Override
    public Long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        logger.info(nombre );
        return manejadorPersistencia.persistir(new PaginaWeb(nombre, html, fechaPublicacion));
    }

}
