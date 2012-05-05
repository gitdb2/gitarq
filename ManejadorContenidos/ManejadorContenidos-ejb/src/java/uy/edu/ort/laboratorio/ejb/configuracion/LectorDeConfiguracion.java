/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.configuracion;

import java.io.InputStream;
import java.util.Properties;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * Clase encargada de levantatr la configuracion de la aplicacion y brindar
 * metodos para acceder a los datos de la configuracion
 *
 * @author rodrigo
 */
public class LectorDeConfiguracion {

    private static LectorDeConfiguracion instance = new LectorDeConfiguracion();
    private Properties prop = null;
    private final String PROP_FILE = "config-app.properties";

    /**
     * *
     * En el constructor es cuando levantamos el properties desde el file system
     */
    private LectorDeConfiguracion() {
        try {
            InputStream is = LectorDeConfiguracion.class.getResourceAsStream(PROP_FILE);
            prop = new Properties();
            prop.load(is);
            is.close();
            Logger.info(LectorDeConfiguracion.class, "Configuracion " + PROP_FILE + " cargada con exito");
        } catch (Exception e) {
            Logger.error(LectorDeConfiguracion.class, "No ese encontro la configuracion " + PROP_FILE);
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
        return prop.getProperty(clave);
    }

    /**
     * * metodo para acceder a los valores del properties por medio de la clave
     * y se pasa un valor por defecto que en caso de no encontrarse la propiedad
     * se retorna dicho valor
     *
     * @param clave
     * @param valorporDefecto
     * @return
     */
    public String getMensaje(String clave, String valorporDefecto) {
        return prop.getProperty(clave, valorporDefecto);
    }
}
