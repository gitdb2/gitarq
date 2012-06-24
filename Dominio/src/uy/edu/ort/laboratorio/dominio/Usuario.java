/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tanquista
 */
@Entity
@Table(name="USUARIO")
public class Usuario implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @Column(name="LOGIN", unique=true, nullable=false)
    private String login;
    
    @Column(name="CONTRASENA", unique=false, nullable=false)
    private String contrasena;
    
    @OneToMany(fetch=FetchType.LAZY)
    private List<EntradaBlog> entradasDeBlog;
    
    @OneToMany(fetch=FetchType.LAZY)
    private List<PaginaWeb> paginasWeb;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<EntradaBlog> getEntradasDeBlog() {
        return entradasDeBlog;
    }

    public void setEntradasDeBlog(List<EntradaBlog> entradasDeBlog) {
        this.entradasDeBlog = entradasDeBlog;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<PaginaWeb> getPaginasWeb() {
        return paginasWeb;
    }

    public void setPaginasWeb(List<PaginaWeb> paginasWeb) {
        this.paginasWeb = paginasWeb;
    }
    
}
