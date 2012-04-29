/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.content;

import javax.ejb.Local;

/**
 *
 * @author rodrigo
 */
@Local
public interface ContentHandlerLocal {
    public String prueba(String entra);
}
