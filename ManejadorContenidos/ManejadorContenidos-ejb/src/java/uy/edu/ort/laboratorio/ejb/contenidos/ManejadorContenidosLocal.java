package uy.edu.ort.laboratorio.ejb.contenidos;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import uy.edu.ort.laboratorio.dominio.EntradaBlog;
import uy.edu.ort.laboratorio.dominio.PaginaWeb;
import uy.edu.ort.laboratorio.ejb.excepciones.ArquitecturaException;
import uy.edu.ort.laboratorio.datatype.DataEntradaBlog;
import uy.edu.ort.laboratorio.datatype.DataPaginaWeb;

@Local
public interface ManejadorContenidosLocal {

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
    public long crearContenidoEntradaBlog(String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException;
    
    /**
     * actualiza el contenido de una entrada de blog.
     * @param idPaginaWeb
     * @param titulo
     * @param nombreAutor
     * @param fechaPublicacion
     * @param texto
     * @param tags
     * @return
     * @throws ArquitecturaException 
     */
    public long modificarContenidoEntradaBlog(long idEntradaBlog, String titulo, String nombreAutor, Date fechaPublicacion, String texto, List<String> tags) throws ArquitecturaException;
    
     /**
     * crea y persiste una pagina web.
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    public long crearContenidoPaginaWeb(String nombre, Date fechaPublicacion, byte[] html) throws ArquitecturaException;
    
    /**
     * modifica el contenido de una pagina web.
     * @param idPaginaWeb 
     * @param nombre
     * @param fechaPublicacion
     * @param html
     * @return
     * @throws ArquitecturaException
     */
    public long modificarContenidoPaginaWeb(long idPaginaWeb, String nombre, Date fechaPublicacion,
                            byte[] html) throws ArquitecturaException;
    
    /**
     * elimina una entrada de blog
     * @param idEntradaBlog
     * @return
     * @throws ArquitecturaException 
     */
    public boolean eliminarEntradaBlog(long idEntradaBlog) throws ArquitecturaException;
    
    /**
     * elimina una pagina web
     * @param idPaginaWeb
     * @return
     * @throws ArquitecturaException 
     */
    public boolean eliminarPaginaWeb(long idPaginaWeb) throws ArquitecturaException;
    
    /**
     * devuelve la lista de todas las paginas web
     * @return 
     */
    public List<DataPaginaWeb> listarPaginasWeb() throws ArquitecturaException;
    
    /**
     * lista todas las entradas de blog
     * @return 
     */
    public List<DataEntradaBlog> listarEntradasDeBlog() throws ArquitecturaException;
    
     /**
     * devuelve la entrada de blog asociada al identificador
     * @return 
     */
    public EntradaBlog obtenerEntradasBlog(long id) throws ArquitecturaException;
    
     /**
     * devuelve la entrada de blog asociada al identificador
     * @return 
     */
    public PaginaWeb obtenerPaginaWeb(long id) throws ArquitecturaException;
    
    /**
     * lista todas las paginas web segun filtrando por nombre y/o fecha de publicacion
     * @return 
     */
    public List<DataPaginaWeb> listarPaginasWebFiltrando(String nombre, Date fechaPublicacion) throws ArquitecturaException;
    
}
