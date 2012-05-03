package uy.edu.ort.laboratorio.ejb.excepciones;

/**
 *
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
