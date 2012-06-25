/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.laboratorio.travellers.datatype;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
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

   public void add(ListItemTraveller item){
       if(this.items == null){
           this.items = new ArrayList<ListItemTraveller>();
       }
       this.items.add(item);
   }

}
