/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.logger;

/**
 *
 * @author rodrigo
 */
public class Logger {
    
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
