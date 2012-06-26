/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.negocio.seguridad;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author rodrigo
 */
@Remote
public interface BeanSeguridadRemote {
      Long autenticar(String login, String passwordEncriptdo);
      boolean tienePermiso(String login, String rol);
      void altaUsuario(String login, String pass, List<String> roles );
}
