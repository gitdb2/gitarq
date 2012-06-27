package uy.edu.ort.laboratorio.travellers.datatype;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * Bean para serializacion a XML que representa una lista de resutlados de un
 * listado de entidades
 *
 * @author Rodrigo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ListWrapperTraveller {

    @XmlElementWrapper(name = "itemList")
    @XmlElement(name = "listItemTraveller")
    private List<ListItemTraveller> items = new ArrayList<ListItemTraveller>();

    public List<ListItemTraveller> getItems() {
        return items;
    }

    public void setItems(List<ListItemTraveller> items) {
        this.items = items;
    }

    /**
     * Agrega un elemento a la lista de items
     * @param item 
     */
    public void add(ListItemTraveller item) {
        if (this.items == null) {
            this.items = new ArrayList<ListItemTraveller>();
        }
        this.items.add(item);
    }
}
