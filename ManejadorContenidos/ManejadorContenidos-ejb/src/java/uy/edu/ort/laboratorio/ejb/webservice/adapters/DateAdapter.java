/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.ejb.webservice.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter para controlar el formato de la fecha ingresada
 * a traves del webservice de alta de contenido.
 * @author rodrigo
 */
public class DateAdapter extends XmlAdapter<String, Date> {
	 
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
 
    /**
     * intenta formatear la fecha pasada por parametro al formato definido.
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public String marshal(Date date) throws Exception {
        return dateFormat.format(date);
    }
 
    /**
     * intenta crear un objeto Date a partir de un string con cierto formato.
     * @param date
     * @return
     * @throws Exception
     */
    @Override
    public Date unmarshal(String date) throws Exception {
        return dateFormat.parse(date);
    }
}