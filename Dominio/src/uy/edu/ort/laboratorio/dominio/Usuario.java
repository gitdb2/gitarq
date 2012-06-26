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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.login == null) ? (other.login != null) : !this.login.equals(other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 11 * hash + (this.login != null ? this.login.hashCode() : 0);
        hash = 11 * hash + (this.contrasena != null ? this.contrasena.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", login=" + login + ", contrasena=" + contrasena + '}';
    }
    
}
