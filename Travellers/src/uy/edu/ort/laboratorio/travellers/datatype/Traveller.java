package uy.edu.ort.laboratorio.travellers.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Contenedor para envio y recepcion de datos entre el cliente y el servidor 
 * para la serializacion a XML.
 * Lleva el id del usuario  y la carga del mensaje (debe encriptarse)
 * @author rodrigo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Traveller {

    /**
     * Id del usaurio
     */
    private Long id;
    /**
     * mensaje a enviar, debe encriptarse
     */
    private String payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
