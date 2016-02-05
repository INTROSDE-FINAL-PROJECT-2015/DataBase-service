package introsde.models;

import introsde.dao.LifeCoachDb_Dao;

import static java.lang.Math.toIntExact;
import java.util.Date;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlTransient;

import java.util.List;
import javax.persistence.*;


@Entity
@Table(name="Measure")
@NamedQueries({
	@NamedQuery(name="Measure.findAll", query="SELECT h FROM Measure h"),
	@NamedQuery(name="Measure.findByMeasureAndPerson", query="SELECT hmh FROM Measure hmh WHERE hmh.person = ?1 AND hmh.measureDefinition = ?2"),
	@NamedQuery(name="Measure.findByPidAndMid", query="SELECT hmh FROM Measure hmh WHERE hmh.person = ?1 AND hmh.idMeasureHistory = ?2"),
	@NamedQuery(name="Measure.findMeasureByDate", query="SELECT hmh FROM Measure hmh WHERE hmh.person = ?1 AND hmh.measureDefinition = ?2 "
	+ "AND hmh.dateRegistered BETWEEN ?4 AND ?3")
})
public class Measure{

    @Id
    @GeneratedValue(
    strategy = GenerationType.AUTO,                      // Indicates that the persistence provider should pick an appropriate strategy for the particular database.
    generator="sqlite_hmh_id_generator")                 // generator="sqlite_measure_definition_id_generator" must match the name of the @TableGenerator tag
    @TableGenerator(name="sqlite_hmh_id_generator",      // the name of the generator
        table="sqlite_sequence",                         // the name of the table where are stored sequences values
          pkColumnName="name",                           // name of the primary key (Optional)
        valueColumnName="seq",                           // the Column containing the actual value of the counter
          pkColumnValue="Measure",          // the name of the Column related to the sequence to autoincrement
        allocationSize=1								 // Increments the cointer by one during the insertion
        )
    @Column(name="idMeasureHistory")
    private int idMeasureHistory;

    @Column(name="measureValue")
    private String measureValue;

		@Temporal(TemporalType.DATE)
    @Column(name="dateRegistered")
    private Date dateRegistered;

		@ManyToOne
		@JoinColumn( name = "idPerson", referencedColumnName = "idPerson")
    private Person person;

    @ManyToOne
	  @JoinColumn(name="idMeasureDefinition", referencedColumnName="idMeasureDefinition")
    private MeasureDefinition measureDefinition;

		public int getIdMeasureHistory() {
			return idMeasureHistory;
		}


		public void setIdMeasureHistory(int idMeasureHistory) {
			this.idMeasureHistory = idMeasureHistory;
		}


		public String getMeasureValue() {
			return measureValue;
		}


		public void setMeasureValue(String measureValue) {
			this.measureValue = measureValue;
		}


		public Date getDateRegistered() {
			return dateRegistered;
		}


		public void setDateRegistered(Date dateRegistered) {
			this.dateRegistered = dateRegistered;
		}

		@XmlTransient
		public Person getPerson() {
			return person;
		}


		public void setPerson(Person person) {
			this.person = person;
		}


		public MeasureDefinition getMeasureDefinition() {
			return measureDefinition;
		}


		public void setMeasureDefinition(MeasureDefinition measureDefinition) {
			this.measureDefinition = measureDefinition;
		}


		public static List<Measure> getAll() {
			EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
	    List<Measure> list = em.createNamedQuery("Measure.findAll", Measure.class).getResultList();
	    LifeCoachDb_Dao.instance.closeConnections(em);
	    return list;
		}


		// Save Or Update
		public static Measure saveMeasure(Measure hmh) {
			EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			hmh=em.merge(hmh);
			tx.commit();
		  LifeCoachDb_Dao.instance.closeConnections(em);
		  return hmh;
		}

		// removes the entity from the DB
		public static void removeMeasure(Measure hmh) {
			EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
	    hmh=em.merge(hmh);
	    em.remove(hmh);
	    tx.commit();
	    LifeCoachDb_Dao.instance.closeConnections(em);
		}


		public static List<Measure> readPersonHistory(int id, String measureType){
			Person p = Person.getPersonById(toIntExact(id));
			MeasureDefinition md = MeasureDefinition.getMeasureDefinitionByName(measureType);
			if(p==null || md == null){
				return null;
			}
			EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
			TypedQuery<Measure> query = em.createNamedQuery("Measure.findByMeasureAndPerson", Measure.class);
			query.setParameter(1, p);
			query.setParameter(2, md);
			List<Measure> list = query.getResultList();
			LifeCoachDb_Dao.instance.closeConnections(em);
			return list;
		}


		public static Measure getMeasureByPidAndMid(int idPerson,String measureType, int mid) {
			EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
			Person p = Person.getPersonById(idPerson);

			TypedQuery<Measure> query = em.createNamedQuery("Measure.findByPidAndMid", Measure.class);
			query.setParameter(1, p);
			query.setParameter(2, mid);

			List<Measure> hmh = query.getResultList();

			LifeCoachDb_Dao.instance.closeConnections(em);

			Measure hmhNonList = hmh.get(0);
			if(hmhNonList!=null && hmhNonList.getMeasureDefinition().getMeasureType().equals(measureType)){
					return hmhNonList;
			} else {
				System.out.println("INCONSISTENCY IN PARAMETERS GIVEN EXACTLY BETWEEN MID AND MEASURE NAME");
				return null;
			}
		}


		public static Measure savePersonMeasure(int id, Measure hmh){
			Person p = Person.getPersonById(id);
			if(p!=null){
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				hmh.setDateRegistered(ts);
				hmh.setPerson(p);
				hmh = Measure.saveMeasure(hmh);
				return hmh;
			}
			return null;
		}


		// actually this method is the same of savePersonMeasure.. because the DB FUNCTION IS THE SAME BOTH FOR SAVING OR UPDATING.. :)
		public static Measure updatePersonMeasure(int id, Measure hmh){
			Person p = Person.getPersonById(id);
			if(p!=null){
				if(hmh.getDateRegistered()==null){
				Date date = new Date();
				Timestamp ts = new Timestamp(date.getTime());
				hmh.setDateRegistered(ts);
				}
				hmh.setPerson(p);
				return Measure.saveMeasure(hmh);
			}
			return null;
		}
	}
