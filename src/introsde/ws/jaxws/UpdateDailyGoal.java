
package introsde.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "updateDailyGoal", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateDailyGoal", namespace = "http://ws.introsde/")
public class UpdateDailyGoal {

    @XmlElement(name = "arg0", namespace = "")
    private introsde.models.DailyGoal arg0;

    /**
     * 
     * @return
     *     returns DailyGoal
     */
    public introsde.models.DailyGoal getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(introsde.models.DailyGoal arg0) {
        this.arg0 = arg0;
    }

}
