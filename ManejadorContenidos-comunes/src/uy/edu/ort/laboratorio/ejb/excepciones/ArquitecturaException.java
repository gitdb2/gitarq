package uy.edu.ort.laboratorio.ejb.excepciones;

/**
 * Excepcion creada por nosotros para enviarle al cliente del webservice.
 * @author rodrigo
 */
public class ArquitecturaException extends Exception {

    public ArquitecturaException(Throwable thrwbl) {
        super(thrwbl);
    }

    public ArquitecturaException() {
        super();
    }

    public ArquitecturaException(String string) {
        super(string);
    }

    public ArquitecturaException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    
}
