/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.contenidos;

import javax.ejb.Stateless;


/**
 *
 * @author rodrigo
 */
@Stateless
public class ManejadorConenidos implements ManejadorContenidosRemote, ManejadorContenidosLocal {

    @Override
    public String prueba(String entra) {
        return "pasado por ejb "+entra;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}