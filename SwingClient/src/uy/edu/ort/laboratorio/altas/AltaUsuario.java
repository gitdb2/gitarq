/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.altas;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import uy.edu.ort.laboratorio.travellers.cripto.DesEncrypter;
import uy.edu.ort.laboratorio.ejb.seguridad.BeanSeguridadRemote;

/**
 *
 * @author rodrigo
 */
public class AltaUsuario {
      public static void main(String[] args) {

        Properties props = new Properties();
//        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
//        props.put(Context.PROVIDER_URL, "jnp://localhost:3700");
        
        props.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
        // glassfish's server location
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        
        
        try {

            InitialContext ctx = new InitialContext(props);

            BeanSeguridadRemote  ejbRef = (BeanSeguridadRemote) ctx.lookup(
                    "java:global/ManejadorContenidos/ManejadorContenidos-ejb/BeanSeguridad!uy.edu.ort.laboratorio.ejb.seguridad.BeanSeguridadRemote");
//                  "java:global/ManejadorContenidos/ManejadorContenidos-ejb/BeanSeguridad!uy.edu.ort.laboratorio.ejb.seguridad.BeanSeguridadRemote");

            try {
               List<String> roles = new ArrayList<String>();
               roles.add("admin");
               ejbRef.altaUsuario("admin", DesEncrypter.MD5("admin"), roles);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

//            System.out.print(ejbRef.tienePermiso("rodrigo2", "admin"));

        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }
}
