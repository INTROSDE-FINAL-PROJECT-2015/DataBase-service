
package introsde.ws.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getPersonGoalsResponse", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPersonGoalsResponse", namespace = "http://ws.introsde/")
public class GetPersonGoalsResponse {

    @XmlElement(name = "return", namespace = "")
    private List<introsde.models.Goal> _return;

    /**
     * 
     * @return
     *     returns List<Goal>
     */
    public List<introsde.models.Goal> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<introsde.models.Goal> _return) {
        this._return = _return;
    }

}
