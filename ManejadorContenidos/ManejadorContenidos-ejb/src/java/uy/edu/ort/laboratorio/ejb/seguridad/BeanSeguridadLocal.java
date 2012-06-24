/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.seguridad;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rodrigo
 */
@Local
interface BeanSeguridadLocal {
     public boolean autenticar(String login, String passwordEncriptdo);
     public boolean tienePermiso(String login, String rol);
     public void altaUsuario(String login, String pass, List<String> roles );
}
