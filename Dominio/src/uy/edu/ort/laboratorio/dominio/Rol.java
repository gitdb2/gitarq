/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tanquista
 */
@Entity
@Table(name="ROL")
public class Rol implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    @OneToMany 
    @JoinTable(name="ROL_USUARIO", 
         joinColumns=@JoinColumn(name="ROL_ID"),
          inverseJoinColumns=@JoinColumn(name="USUARIO_ID"))
    private List<Usuario> usuario;

    @Column(name="NOMBRE", unique=true, nullable=false)
    private String nombre;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    public void addUsuario(Usuario usaurio){
        
        if(this.usuario == null)
            this.usuario = new ArrayList<Usuario>();
        this.usuario.add(usaurio);
    }

    @Override
    public String toString() {
        return "Rol{" + "id=" + id + ", usuario=" + usuario + ", nombre=" + nombre + '}';
    }
    
    
}
