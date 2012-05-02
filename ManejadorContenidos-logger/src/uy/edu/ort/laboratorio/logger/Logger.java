/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.logger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;



/**
 *
 * @author rodrigo
 */
public class Logger {
   // DEBUG, INFO, WARN, ERROR and FATAL
    static private boolean inicializado = false;
    public static void init(String fullPathToLog4JConfig){
        if(!inicializado){
            inicializado = true;
            if(fullPathToLog4JConfig == null){
                BasicConfigurator.configure();
            }else{
                PropertyConfigurator.configure(fullPathToLog4JConfig);
            }
        }
    }
    
    public static void debug(Class<?> objectClass, Object message) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(objectClass);
        logger.debug(message);
    }
    public static void info(Class<?> objectClass, Object message) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(objectClass);
        logger.info(message);
    }
    public static void warn(Class<?> objectClass, Object message) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(objectClass);
        logger.warn(message);
    }
    public static void error(Class<?> objectClass, Object message) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(objectClass);
        logger.error(message);
    }
    public static void fatal(Class<?> objectClass, Object message) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(objectClass);
        logger.fatal(message);
    }
    public static void trace(Class<?> objectClass, Object message) {
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(objectClass);
        logger.trace(message);
    }

    
    
    
}
