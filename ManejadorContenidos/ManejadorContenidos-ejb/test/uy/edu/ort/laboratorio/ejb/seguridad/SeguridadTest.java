/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.seguridad;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;
import uy.edu.ort.laboratorio.ejb.cripto.DesEncrypter;

/**
 *
 * @author rodrigo
 */
public class SeguridadTest {
    
    public SeguridadTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    @Test
    public void testPermiso(){
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
           
           assertTrue(ejbRef.tienePermiso("rodrigo2", "admin"));
           
           assertFalse(ejbRef.tienePermiso("rodrigo2", "pepe"));
           
       } catch (NamingException ex) {
           ex.printStackTrace();
       }
    }
    
     @Test
    public void testAutenticar(){
         Properties props = new Properties();
       props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
       props.put(Context.PROVIDER_URL, "jnp://localhost:3700");
       try {
           
           InitialContext ctx = new InitialContext(props);
           
           BeanSeguridadRemote ejbRef = (BeanSeguridadRemote) ctx.lookup(
                   "java:global/ManejadorContenidos/ManejadorContenidos-ejb/BeanSeguridad!uy.edu.ort.laboratorio.ejb.seguridad.BeanSeguridadRemote");
       
           String md5PassOK         = DesEncrypter.MD5("1234");
           DesEncrypter enc         = new DesEncrypter(md5PassOK);
           String encriptedPass     = enc.encrypt(md5PassOK);
   
           assertTrue(ejbRef.autenticar("rodrigo2", encriptedPass));

           
           assertFalse(ejbRef.autenticar("rodrigo2", "pepe"));
           
       } catch (NamingException ex) {
           ex.printStackTrace();
       }
    }
    
}
