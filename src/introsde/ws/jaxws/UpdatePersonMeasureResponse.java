
package introsde.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "updatePersonMeasureResponse", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePersonMeasureResponse", namespace = "http://ws.introsde/")
public class UpdatePersonMeasureResponse {

    @XmlElement(name = "return", namespace = "")
    private introsde.models.Measure _return;

    /**
     * 
     * @return
     *     returns Measure
     */
    public introsde.models.Measure getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(introsde.models.Measure _return) {
        this._return = _return;
    }

}
