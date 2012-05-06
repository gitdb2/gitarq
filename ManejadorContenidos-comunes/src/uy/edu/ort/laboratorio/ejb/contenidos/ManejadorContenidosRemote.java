package uy.edu.ort.laboratorio.ejb.contenidos;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import uy.edu.ort.laboratorio.dominio.Contenido;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;

@Remote
public interface ManejadorContenidosRemote {

     /**
     * crea y persiste una entrada de blog.
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @return
     * @throws ArquitecturaException
     */
    public Long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException;
     /**
     * crea y persiste una pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    public Long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException;
}
