/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.seguridad;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class BeanSeguridad implements BeanSeguridadLocal, BeanSeguridadRemote{

    @PersistenceContext
    private EntityManager manejadorPersistenciaDB;

    @Override
    public Long autenticar(String login, String passwordEncriptdo) {
        
        Usuario usuario = null;
        Long ret= null;
         try{
                usuario = findUserByLogin(login);
                DesEncrypter encriptador = new DesEncrypter(usuario.getContrasena());
        
                String desencriptado = encriptador.decrypt(passwordEncriptdo);
        
                if(usuario!= null && usuario.getContrasena()!= null && usuario.getContrasena().equals(desencriptado)){
                    ret = usuario.getId();
                }

            }catch(javax.persistence.NoResultException e){
                //el rol no fue dado de alta por lo que sugo
                Logger.info(this.getClass(), "El login "+ login + "no fue dado de alta aun en la db");
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
        CriteriaBuilder qb = manejadorPersistenciaDB.getCriteriaBuilder();
        CriteriaQuery<Rol> query = qb.createQuery(Rol.class);
        Root<Rol> rolRoot = query.from(Rol.class);
        query.where(qb.equal(rolRoot.get("nombre"), rolName));
        Rol rol = manejadorPersistenciaDB.createQuery(query).getSingleResult();
        return rol;
    }
    
    @Override
    public boolean tienePermiso(String login, String rolStr){
        Usuario usuario = findUserByLogin(login);
        boolean ret = false;
        Rol rol = null;
        try{
                rol = findRolByName(rolStr);
            }catch(javax.persistence.NoResultException e){
                //el rol no fue dado de alta por lo que sugo
                Logger.info(this.getClass(), "El rol "+ rolStr + "no fue dado de alta aun en la db");
            }
        
        if(rol!= null){
//        Query q = manejadorPersistenciaDB.createQuery(
//                "select r from Rol r where r.nombre=:rol and :usuario in (r.usuario)");//.setString("rol", rol).uniqueResult();
//        q.setParameter("rol", rol).setParameter("usuario", usuario);
            //        return roles!=null && roles.size() > 0;    

            List<Usuario> usuarios = rol.getUsuario();
            ret = usuarios != null && usuarios.contains(usuario);
        }
        
        return ret;
    }
    
    
    @Override
    public void altaUsuario(String login, String pass, List<String> roles ){
        
        Usuario user = new Usuario();
        user.setLogin(login);
        user.setContrasena(pass);
        
        for (String rolStr : roles) {
            Rol rol = null;
            try{
                rol = findRolByName(rolStr);
            }catch(javax.persistence.NoResultException e){
                //el rol no fue dado de alta por lo que sugo
                Logger.info(this.getClass(), "El rol "+ rolStr + "no fue dado de alta aun en la db");
            }
            
            if(rol == null){
               rol = new Rol();
               rol.setNombre(rolStr);
            }
            rol.addUsuario(user);
            manejadorPersistenciaDB.persist(rol);
        }
        manejadorPersistenciaDB.persist(user);
        
    }
    
    
    
     public static void main(String[] args){
        
       Properties props = new Properties();
       props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
       props.put(Context.PROVIDER_URL, "jnp://localhost:3700");
       try {
           
           InitialContext ctx = new InitialContext(props);
           
           BeanSeguridadRemote ejbRef = (BeanSeguridadRemote) ctx.lookup(
                   "java:global/ManejadorContenidos/ManejadorContenidos-ejb/BeanSeguridad!uy.edu.ort.laboratorio.ejb.seguridad.BeanSeguridadRemote");
       
           try {
               
               
               
//               List<String> roles = new ArrayList<String>();
//               roles.add("admin");
//               ejbRef.altaUsuario("rodrigo2", DesEncrypter.MD5("1234"), roles);
           } catch (Exception ex) {
               ex.printStackTrace();
           }
           
           System.out.print(ejbRef.tienePermiso("rodrigo2", "admin"));
           
       } catch (NamingException ex) {
           ex.printStackTrace();
       }
     }
    
}
