/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.travellers.datatype;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodrigo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class FilterQueryTraveller {
    private String nombre;
    private Date fecha;
    private String titulo;
    private String texto;
    private String tag;
}
