
package introsde.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "readPersonResponse", namespace = "http://ws.introsde/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "readPersonResponse", namespace = "http://ws.introsde/")
public class ReadPersonResponse {

    @XmlElement(name = "return", namespace = "")
    private introsde.models.Person _return;

    /**
     * 
     * @return
     *     returns Person
     */
    public introsde.models.Person getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(introsde.models.Person _return) {
        this._return = _return;
    }

}
