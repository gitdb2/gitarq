package uy.edu.ort.laboratorio.dominio;

import java.io.Serializable;
import javax.persistence.*;

/**
 *Ebtidad que representa al usaurio en la base de datos
 * @author Rodrigo
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;
    @Column(name = "CONTRASENA", unique = false, nullable = false)
    private String contrasena;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
