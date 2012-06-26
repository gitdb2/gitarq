/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.seguridad;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import uy.edu.ort.laboratorio.dominio.Rol;
import uy.edu.ort.laboratorio.dominio.Usuario;
import uy.edu.ort.laboratorio.ejb.cripto.DesEncrypter;
import uy.edu.ort.laboratorio.logger.Logger;

/**
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
            Logger.info(this.getClass(), "El login " + login + "no fue dado de alta aun en la db");
        }



        return ret;
        //List<paginaWeb> result = manejadorPersistenciaDB.createQuery(query).getResultList();
    }

    private Usuario findUserByLogin(String login) {
        CriteriaBuilder qb = manejadorPersistenciaDB.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = qb.createQuery(Usuario.class);
        Root<Usuario> usuarioRoot = query.from(Usuario.class);
        query.where(qb.equal(usuarioRoot.get("login"), login));
        Usuario usuario = manejadorPersistenciaDB.createQuery(query).getSingleResult();
        return usuario;
    }

    private Rol findRolByName(String rolName) {
        

        Query query = manejadorPersistenciaDB.createQuery("Select r from Rol r where upper(r.nombre) = upper(:rolName)");
        query.setParameter("rolName", rolName);
        Rol rol = (Rol) query.getSingleResult();;
        
        
//        CriteriaBuilder qb = manejadorPersistenciaDB.getCriteriaBuilder();
//        CriteriaQuery<Rol> query = qb.createQuery(Rol.class);
//        Root from = query.from(Rol.class);
//        
//        Expression<String> literal = qb.upper(qb.literal((String) rolName));
//        Predicate predicate = qb.equal(qb.upper(from.get("nombre")), literal);
//        query.where(predicate);
//        
//        
////        query.where(qb.equal(from.get("nombre"), rolName));
//        Rol rol = manejadorPersistenciaDB.createQuery(query).getSingleResult();
        return rol;
        
        
        
//        String arg1 = "name";
//        Query query = entityManager.createQuery("from SimpleBean s where upper(s.pstring) like upper(:arg1)");
//        query.setParameter("arg1", arg1);
//        List<SimpleBean> list = query.getResultList();
//         
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
//        Root from = criteriaQuery.from(SimpleBean.class);
//        CriteriaQuery<Object> select = criteriaQuery.select(from);
//         
//        Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal((String) arg1));
//        Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(from.get("pstring")), literal);
//         
//        criteriaQuery.where(predicate);
//         
//        TypedQuery<Object> typedQuery = entityManager.createQuery(select);
//        List<Object> resultList = typedQuery.getResultList();
//        assertEqualsList(list, resultList);
        
        
        
        
        
        
        
    }

    @Override
    public boolean tienePermiso(String login, String rolStr) {
        Usuario usuario = findUserByLogin(login);

        return tienePermiso(rolStr, usuario);
    }

    private boolean tienePermiso(String rolStr, Usuario usuario) {
        boolean ret = false;
        Rol rol = null;
        try {
            rol = findRolByName(rolStr);
            manejadorPersistenciaDB.refresh(rol);
            Logger.info(this.getClass(), rol);
        } catch (javax.persistence.NoResultException e) {
            //el rol no fue dado de alta por lo que sugo
            Logger.info(this.getClass(), "El rol " + rolStr + "no fue dado de alta aun en la db");
        }

        if (rol != null) {
            List<Usuario> usuarios = rol.getUsuario();
             Logger.info(this.getClass(),"usuarios "+usuarios);
            ret = usuarios != null && usuarios.contains(usuario);
             Logger.info(this.getClass(),"usuarios.contains(usuario)= "+ usuarios.contains(usuario));
        }

        return ret;
    }

    @Override
    public void altaUsuario(String login, String pass, List<String> roles) {

        Usuario user = new Usuario();
        user.setLogin(login);
        user.setContrasena(pass);

        for (String rolStr : roles) {
            Rol rol = null;
            try {
                rol = findRolByName(rolStr);
            } catch (javax.persistence.NoResultException e) {
                //el rol no fue dado de alta por lo que sugo
                Logger.info(this.getClass(), "El rol " + rolStr + "no fue dado de alta aun en la db");
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

        Usuario usuario = manejadorPersistenciaDB.find(Usuario.class, id);
        payload = desencriptar(usuario.getContrasena(), payload);

        return payload;
    }

     @Override
    public String encriptar(Long id, String payload) {

        Usuario usuario = manejadorPersistenciaDB.find(Usuario.class, id);
        payload = encriptar(usuario.getContrasena(), payload);
        return payload;
    }
    private String desencriptar(String passphrase, String payload) {
        DesEncrypter encriptador = new DesEncrypter(passphrase);
        return encriptador.decrypt(payload);
    }
    
    private String encriptar(String passphrase, String payload) {
        DesEncrypter encriptador = new DesEncrypter(passphrase);
        return encriptador.encrypt(payload);
    }

    @Override
    public boolean tienePermiso(Long idUser, List<String> roles) {
        boolean ret = false;
        Usuario  usuario = manejadorPersistenciaDB.find(Usuario.class, idUser);
        if(usuario != null){
            for (String rol : roles) {
                ret = ret || tienePermiso(rol, usuario);
                if(ret) break;
            }
        }
        Logger.info(this.getClass(), "tiene permiso = "+ret + " ("+ usuario + " - " +roles +")");
        return ret;
    }

}
