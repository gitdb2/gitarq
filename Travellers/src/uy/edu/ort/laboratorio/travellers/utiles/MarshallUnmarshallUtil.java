package uy.edu.ort.laboratorio.travellers.utiles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import uy.edu.ort.laboratorio.logger.Logger;

/**
 * Clase utilidad para facilitar la serializacion y deserializacion a XML
 *
 * @author rodrigo
 */
public class MarshallUnmarshallUtil<T> {

    /**
     * a partir de un XML genera un objeto del tipo classname
     *
     * @param className
     * @param payload
     * @return
     * @throws JAXBException
     */
    public T unmarshall(Class<T> className, String payload) throws JAXBException {

        JAXBContext contextIn = JAXBContext.newInstance(className);
        Unmarshaller u = contextIn.createUnmarshaller();
        ByteArrayInputStream input = new ByteArrayInputStream(payload.getBytes());
        T ret = (T) u.unmarshal(input);
        try {
            input.close();
        } catch (IOException ex) {
            Logger.error(MarshallUnmarshallUtil.class, ex.getMessage());
            Logger.error(MarshallUnmarshallUtil.class, Logger.getStackTrace(ex));
        }
        return ret;
    }

    /**
     * Transforma un objeto generico T (que debe tener las anotaciones de jaxb)
     * al string correspondiente xml
     *
     * @param object
     * @return
     * @throws JAXBException
     * @throws IOException
     */
    public String marshall(T object) throws JAXBException {
        JAXBContext contextOut = JAXBContext.newInstance(object.getClass());
        Marshaller m = contextOut.createMarshaller();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(object, out);
        String ret = out.toString();
        try {
            out.close();
        } catch (IOException ex) {
            Logger.error(this.getClass(), ex.getMessage());
            Logger.error(this.getClass(), Logger.getStackTrace(ex));
        }
        return ret;
    }
}
