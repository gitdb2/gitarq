/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.negocio.seguridad;

import java.util.List;
import javax.ejb.Remote;

/**
 * Interface remota del ejb de autenticacion, usada para testin y armar el
 * entorno, ej. dar de alta usuarios
 *
 * @author rodrigo
 */
@Remote
public interface BeanSeguridadRemote {

    /**
     *Operacion para testing
     * autentica un login con una password encriptada y retorna el id de usaurio
     * si la operacion fue exitosa, de lo contrario retorna null
     *
     * @param login
     * @param passwordEncriptdo
     * @return
     */
    Long autenticar(String login, String passwordEncriptdo);

    /**
     * operacion para testing que chuequea si un usuario tiene un rol determinado
     * @param login
     * @param rol
     * @return 
     */
    boolean tienePermiso(String login, String rol);

    /**
     * Da de alta en el sistema un suario con la contrasena pasada por prametro
     * (debe ser el MD5 de la misma) y la lista de roles a los que pertenece.
     * Si el rol no existe lo crea y luego asocia el usuario creado
     * @param login
     * @param pass
     * @param roles 
     */
    void altaUsuario(String login, String pass, List<String> roles);
}
