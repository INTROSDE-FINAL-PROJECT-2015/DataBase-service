
package introsde.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "createPersonDailyGoalResponse", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createPersonDailyGoalResponse", namespace = "http://ws.introsde/")
public class CreatePersonDailyGoalResponse {

    @XmlElement(name = "return", namespace = "")
    private introsde.models.DailyGoal _return;

    /**
     * 
     * @return
     *     returns DailyGoal
     */
    public introsde.models.DailyGoal getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(introsde.models.DailyGoal _return) {
        this._return = _return;
    }

}
