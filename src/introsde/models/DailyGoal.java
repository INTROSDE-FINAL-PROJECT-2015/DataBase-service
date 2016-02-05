package introsde.models;



import java.util.Date;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;

import javax.xml.bind.annotation.XmlTransient;

import static java.lang.Math.toIntExact;

import introsde.dao.LifeCoachDb_Dao;
//  nice tutorial on annotations http://www.summa-tech.com/blog/2011/07/29/setting-up-sequential-ids-using-jpa-tablegenerator
//  ORACLE docs http://docs.oracle.com/javaee/5/api/javax/persistence/TableGenerator.html

@Entity   // Marks this class as a JPA persistable entity
@Table(name="DailyGoal")   // Denotes the name of the table in which this entity is stored
@NamedQueries({
	@NamedQuery(name="DailyGoal.findAll", query="SELECT g FROM  DailyGoal g"),
	@NamedQuery(name="DailyGoal.getDailyGoalsByPid", query="SELECT gl FROM DailyGoal gl WHERE gl.person = ?1")

	//@NamedQuery(name="Person.findByMeasureNameMinMax",
		//		query="SELECT p FROM Person p INNER JOIN p.lifeStatus l WHERE l.measureDefinition = ?1 AND "
			//			+ "CAST(l.value NUMERIC(10,2)) BETWEEN ?2 AND ?3")
})
public class DailyGoal{

  @Id 																			 // declares the field it refers to as the unique identifier for this entity
  @GeneratedValue(
    strategy = GenerationType.AUTO,          // Indicates that the persistence provider should pick an appropriate strategy for the particular database.
    generator="sqlite_daily_goal_id_generator")  // generator="sqlite_person_id_generator" must match the name of the @TableGenerator tag
                                             // to provide the specifics on how the value is to be generated.
  @TableGenerator(
   name="sqlite_daily_goal_id_generator",        // identifier for the generator binding.
   table="sqlite_sequence",                  // is the name of the table created to store the sequence values
   pkColumnName="name",                      // (Optional) Name of the primary key column in the table.
   valueColumnName="seq",                    // (Optional) Name of the column that stores the last value generated.
   pkColumnValue="DailyGoal",
   allocationSize=1													 // Increments the cointer by one during the insertion
   )
	@Column(name="idGoal")
	private int idGoal;
  @Column(name="idPerson")									 // is the name of the table column
  private int idPerson;
  @Column(name="value")
  private String value;
	@Column(name="question")
	private String question;

	@ManyToOne
	@JoinColumn( name = "idPerson", referencedColumnName = "idPerson", insertable=false, updatable=false)
	private Person person;

	public int getIdGoal() {
		return idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}


	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public static List<DailyGoal> getAllDailyGoals(){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		List<DailyGoal> list = em.createNamedQuery("DailyGoal.findAll", DailyGoal.class).getResultList();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return list;
	}

	public static List<DailyGoal> getDailyGoalsByPid(int idPerson) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		Person p = Person.getPersonById(idPerson);
		if(p!=null){
			TypedQuery<DailyGoal> query = em.createNamedQuery("DailyGoal.getDailyGoalsByPid", DailyGoal.class);
			query.setParameter(1, p);
			List<DailyGoal> goalList = query.getResultList();
			LifeCoachDb_Dao.instance.closeConnections(em);
			return goalList;
		} else {
			return null;
		}
	}

	public static DailyGoal getDailyGoalById(int dailygoalID){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		DailyGoal g = em.find(DailyGoal.class, dailygoalID);
		LifeCoachDb_Dao.instance.closeConnections(em);
		if (g==null){
				System.out.println("The given ID is NOT in our database" + dailygoalID);
		}
		return g;
	}

	public static DailyGoal saveDailyGoal(DailyGoal dg) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		dg=em.merge(dg);
		tx.commit();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return dg;
	}

	public static DailyGoal saveDailyGoal(int id, DailyGoal dg){
		dg.setIdPerson(id);
		dg.setPerson(Person.getPersonById(id));
		dg = DailyGoal.saveDailyGoal(dg);
		return dg;
	}

	public static DailyGoal saveDailyGoal1(DailyGoal dg){
		int id = dg.getIdPerson();
		dg.setPerson(Person.getPersonById(id));
		dg = DailyGoal.saveDailyGoal(dg);
		return dg;
	}

	public static void removeDailyGoal(DailyGoal g) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		g=em.merge(g);
		em.remove(g);
		tx.commit();
		LifeCoachDb_Dao.instance.closeConnections(em);
		System.out.println("DAILY GOAL REMOVED!");
	}

	public static void removeDailyGoal(int idGoal) {
		DailyGoal g = DailyGoal.getDailyGoalById(idGoal);
		if(g!=null){
			removeDailyGoal(g);
		}else{
			System.out.println("No DailyGoal with this ID");
		}
	}
}
