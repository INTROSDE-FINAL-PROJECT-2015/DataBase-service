
package introsde.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "saveGoal", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveGoal", namespace = "http://ws.introsde/")
public class SaveGoal {

    @XmlElement(name = "arg0", namespace = "")
    private introsde.models.Goal arg0;

    /**
     * 
     * @return
     *     returns Goal
     */
    public introsde.models.Goal getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(introsde.models.Goal arg0) {
        this.arg0 = arg0;
    }

}
