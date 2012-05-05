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
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.ejb.persistencia.ManejadorPersistenciaLocal;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorContenidos implements ManejadorContenidosRemote, ManejadorContenidosLocal {

    @EJB
    private ManejadorPersistenciaLocal manejadorPersistencia;
    
    @Override
    public Long crearContenido(Contenido contenido) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, contenido);
        return manejadorPersistencia.persistir(contenido);
    }

    @Override
    public Long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException {
	Logger.info(ManejadorContenidos.class, titulo );
        return manejadorPersistencia.persistir(new EntradaBlog(titulo, nombreAutor, texto, tags, fechaPublicacion));
    }

    @Override
    public Long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException {
        Logger.info(ManejadorContenidos.class, nombre );
        return manejadorPersistencia.persistir(new PaginaWeb(nombre, html, fechaPublicacion));
    }

}
