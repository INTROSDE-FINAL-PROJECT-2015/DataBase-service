
package introsde.ws.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getPersonDailyGoalsResponse", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPersonDailyGoalsResponse", namespace = "http://ws.introsde/")
public class GetPersonDailyGoalsResponse {

    @XmlElement(name = "return", namespace = "")
    private List<introsde.models.DailyGoal> _return;

    /**
     * 
     * @return
     *     returns List<DailyGoal>
     */
    public List<introsde.models.DailyGoal> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<introsde.models.DailyGoal> _return) {
        this._return = _return;
    }

}
