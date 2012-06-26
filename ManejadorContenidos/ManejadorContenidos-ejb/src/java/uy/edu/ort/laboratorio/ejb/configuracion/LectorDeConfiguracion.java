/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.configuracion;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * 
 * Clase encargada de levantar la configuracion de la aplicacion y brindar
 * metodos para acceder a los datos de la configuracion.
 *
 * @author rodrigo
 */
public final class LectorDeConfiguracion {

    /**
     * singleton.
     */
    private static LectorDeConfiguracion instance = new LectorDeConfiguracion();
    /**
     * property cargado.
     */
    private Properties prop = null;
    /**
     * Nombre del property a cargar.
     */
    private final String PROP_FILE = "config-app.properties";

    /**
     * .
     * En el constructor es cuando levantamos el properties desde el file system
     */
    private LectorDeConfiguracion() {
	try {
	    prop = new Properties();
	    URL url = Thread.currentThread().getContextClassLoader()
						     .getResource(PROP_FILE);

	    if (url == null) {
		throw new IOException();
	    } else {
		prop.load(url.openStream());
	    }

	    Logger.info(LectorDeConfiguracion.class, "Configuracion "
		    + PROP_FILE + " cargada con exito");
	} catch (IOException e) {
	    Logger.error(LectorDeConfiguracion.class, "No se pudo cargar la "
		    + "configuracion configuracion " + PROP_FILE);
	    Logger.debug(LectorDeConfiguracion.class, Logger.getStackTrace(e));
	}
    }
    
    public static LectorDeConfiguracion getInstance() {
	return instance;
    }

    /**
     * metodo para acceder a los valores del properties por medio de la clave
     *
     * @param clave
     * @return
     */
    public String getMensaje(String clave) {
        
	return prop.getProperty(clave, clave);
    }

    /**
     * * metodo para acceder a los valores del properties por medio de la clave.
     * se pasa un valor por defecto que en caso de no encontrarse la propiedad
     * se retorna dicho valor.
     *
     * @param clave
     * @param valorporDefecto
     * @return valor de la propiedad o valor por defecto.
     */
    public String getMensaje(String clave, String valorporDefecto) {
	return prop.getProperty(clave, valorporDefecto);
    }
}
