
package introsde.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "saveGoalResponse", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveGoalResponse", namespace = "http://ws.introsde/")
public class SaveGoalResponse {

    @XmlElement(name = "return", namespace = "")
    private introsde.models.Goal _return;

    /**
     * 
     * @return
     *     returns Goal
     */
    public introsde.models.Goal getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(introsde.models.Goal _return) {
        this._return = _return;
    }

}
