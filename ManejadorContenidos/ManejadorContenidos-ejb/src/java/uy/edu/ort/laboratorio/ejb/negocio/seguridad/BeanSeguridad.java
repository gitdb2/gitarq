package uy.edu.ort.laboratorio.ejb.negocio.seguridad;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import uy.edu.ort.laboratorio.dominio.Rol;
import uy.edu.ort.laboratorio.dominio.Usuario;
import uy.edu.ort.laboratorio.logger.Logger;
import uy.edu.ort.laboratorio.travellers.cripto.DesEncrypter;

/**
 * Bean que implementa las operaciones referidas a seguridad: Autenticacion,
 * autorizacion y encriptacion/ decriptacion
 *
 * @author rodrigo
 */
@Stateless
@LocalBean
public class BeanSeguridad implements BeanSeguridadLocal, BeanSeguridadRemote {

    @PersistenceContext
    private EntityManager manejadorPersistenciaDB;

    @Override
    public Long autenticar(String login, String passwordEncriptdo) {
        Logger.debug(BeanSeguridad.class, "autenticar login=" + login
                + " passwordEncriptdo=" + passwordEncriptdo);
        Usuario usuario = null;
        Long ret = null;
        try {
            usuario = findUserByLogin(login);
            DesEncrypter encriptador = new DesEncrypter(usuario.getContrasena());

            String desencriptado = encriptador.decrypt(passwordEncriptdo);

            if (usuario != null && usuario.getContrasena() != null && usuario.getContrasena().equals(desencriptado)) {
                ret = usuario.getId();
            }

        } catch (javax.persistence.NoResultException e) {
            //el rol no fue dado de alta por lo que sugo
            Logger.info(this.getClass(), "El login "
                    + login + "no fue dado de alta aun en la db");
        }



        return ret;
    }

    private Usuario findUserByLogin(String login) {
        Logger.debug(BeanSeguridad.class, "findUserByLogin login=" + login);
        CriteriaBuilder qb = manejadorPersistenciaDB.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = qb.createQuery(Usuario.class);
        Root<Usuario> usuarioRoot = query.from(Usuario.class);
        query.where(qb.equal(usuarioRoot.get("login"), login));
        Usuario usuario = manejadorPersistenciaDB.createQuery(query).getSingleResult();
        return usuario;
    }

    private Rol findRolByName(String rolName) {
        Logger.debug(BeanSeguridad.class, "findRolByName rolName=" + rolName);
        Query query = manejadorPersistenciaDB.createQuery(
                "Select r from Rol r where upper(r.nombre) = upper(:rolName)");
        query.setParameter("rolName", rolName);
        Rol rol = (Rol) query.getSingleResult();
        return rol;
    }

    @Override
    public boolean tienePermiso(String login, String rolStr) {
        Logger.debug(BeanSeguridad.class, "tienePermiso login=" + login + "rolStr=" + rolStr);
        Usuario usuario = findUserByLogin(login);

        return tienePermiso(rolStr, usuario);
    }

    private boolean tienePermiso(String rolStr, Usuario usuario) {
        Logger.debug(BeanSeguridad.class,
                "tienePermiso rolStr=" + rolStr + "usuario=" + usuario.getLogin());
        boolean ret = false;
        Rol rol = null;
        try {
            rol = findRolByName(rolStr);
            manejadorPersistenciaDB.refresh(rol);
            Logger.info(this.getClass(), rol);
        } catch (javax.persistence.NoResultException e) {
            //el rol no fue dado de alta por lo que sugo
            Logger.info(this.getClass(), "El rol "
                    + rolStr + "no fue dado de alta aun en la db");
        }

        if (rol != null) {
            List<Usuario> usuarios = rol.getUsuario();
            Logger.info(this.getClass(), "usuarios " + usuarios);
            ret = usuarios != null && usuarios.contains(usuario);
            Logger.info(this.getClass(), "usuarios.contains(usuario)= " + usuarios.contains(usuario));
        }

        return ret;
    }

    @Override
    public void altaUsuario(String login, String pass, List<String> roles) {
        Logger.debug(BeanSeguridad.class,
                "altaUsuario login=" + login + " pass=**** roles=" + roles);
        Usuario user = new Usuario();
        user.setLogin(login);
        user.setContrasena(pass);

        for (String rolStr : roles) {
            Rol rol = null;
            try {
                rol = findRolByName(rolStr);
            } catch (javax.persistence.NoResultException e) {
                //el rol no fue dado de alta por lo que sigo
                Logger.info(this.getClass(), "El rol "
                        + rolStr + "no fue dado de alta aun en la db");
            }

            if (rol == null) {
                rol = new Rol();
                rol.setNombre(rolStr);
            }
            rol.addUsuario(user);
            manejadorPersistenciaDB.persist(rol);
        }
        manejadorPersistenciaDB.persist(user);

    }

    @Override
    public String desencriptar(Long id, String payload) {
        Logger.debug(BeanSeguridad.class,
                "desencriptar id=" + id + " payload=" + payload);
        Usuario usuario = manejadorPersistenciaDB.find(Usuario.class, id);
        payload = desencriptar(usuario.getContrasena(), payload);

        return payload;
    }

    @Override
    public String encriptar(Long id, String payload) {
        Logger.debug(BeanSeguridad.class,
                "encriptar id=" + id + " payload=" + payload);
        Usuario usuario = manejadorPersistenciaDB.find(Usuario.class, id);
        payload = encriptar(usuario.getContrasena(), payload);
        return payload;
    }

    /**
     * Desencripta una carga
     * @param passphrase
     * @param payload
     * @return 
     */
    private String desencriptar(String passphrase, String payload) {
        DesEncrypter encriptador = new DesEncrypter(passphrase);
        return encriptador.decrypt(payload);
    }

    /**
     * Encripta una carga
     * @param passphrase
     * @param payload
     * @return 
     */
    private String encriptar(String passphrase, String payload) {
        DesEncrypter encriptador = new DesEncrypter(passphrase);
        return encriptador.encrypt(payload);
    }

    @Override
    public boolean tienePermiso(Long idUser, List<String> roles) {
        Logger.debug(BeanSeguridad.class,
                "tienePermiso idUser=" + idUser + " roles=" + roles);
        boolean ret = false;
        Usuario usuario = manejadorPersistenciaDB.find(Usuario.class, idUser);
        if (usuario != null) {
            for (String rol : roles) {
                ret = ret || tienePermiso(rol, usuario);
                if (ret) {
                    break;
                }
            }
        }
        Logger.debug(BeanSeguridad.class,
                "ret.tienePermiso = " + ret + " (" + usuario + " - " + roles + ")");
        return ret;
    }
}
