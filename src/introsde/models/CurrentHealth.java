package introsde.models;

import introsde.dao.LifeCoachDb_Dao;

import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "CurrentHealth")
@NamedQueries({
	@NamedQuery(name = "CurrentHealth.findAll", query = "SELECT l FROM CurrentHealth l"),
	//@NamedQuery(name="CurrentHealth.findCurrentHealthByMeasureDefinitionAndPerson", query="SELECT ls FROM CurrentHealth ls WHERE ls.person = ?1 AND ls.measureDefinition = ?2")
})
public class CurrentHealth{

	@Id
	@ManyToOne
	@JoinColumn(name="idMeasureHistory", referencedColumnName="idMeasureHistory")
	private Measure healthMeasureHistory;

  @ManyToOne
  @JoinColumn(name="idPerson", referencedColumnName="idPerson")
  private Person person;

  @ManyToOne
  @JoinColumn(name="idMeasureDefinition", referencedColumnName="idMeasureDefinition")
  private MeasureDefinition measureDefinition;

  @Column(name="measureType")
  private String measureType;

	@Column(name="measureValue")
  private String measureValue;

	@Column(name="measureValueType")
	private String measureValueType;

	@Temporal(TemporalType.DATE)  							// indicates to the persistency manager that this is a Date field
  @Column(name="dateRegistered")
  private Date dateRegistered;

  public static List<CurrentHealth> getAll() {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
	  List<CurrentHealth> list = em.createNamedQuery("CurrentHealth.findAll", CurrentHealth.class).getResultList();
	  LifeCoachDb_Dao.instance.closeConnections(em);
	  return list;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}

	//@XmlTransient
	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}


	public void setMeasureDefinition(MeasureDefinition measureDefinition) {
		this.measureDefinition = measureDefinition;
	}


	public String getMeasureType() {
		return measureType;
	}


	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}


	public String getMeasureValue() {
		return measureValue;
	}


	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public String getMeasureValueType() {
		return measureValueType;
	}


	public void setMeasureValueType(String measureValueType) {
		this.measureValueType = measureValueType;
	}


	public Date getDateRegistered() {
		return dateRegistered;
	}


	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
}
