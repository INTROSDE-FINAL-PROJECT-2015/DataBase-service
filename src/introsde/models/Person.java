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
@Table(name="Person")   // Denotes the name of the table in which this entity is stored
@NamedQueries({
	@NamedQuery(name="Person.findAll", query="SELECT p FROM  Person p") //,
	//@NamedQuery(name="Person.findByMeasureNameMinMax",
		//		query="SELECT p FROM Person p INNER JOIN p.lifeStatus l WHERE l.measureDefinition = ?1 AND "
			//			+ "CAST(l.value NUMERIC(10,2)) BETWEEN ?2 AND ?3")
})
public class Person{

  @Id 																			 // declares the field it refers to as the unique identifier for this entity
  @GeneratedValue(
    strategy = GenerationType.AUTO,          // Indicates that the persistence provider should pick an appropriate strategy for the particular database.
    generator="sqlite_person_id_generator")  // generator="sqlite_person_id_generator" must match the name of the @TableGenerator tag
                                             // to provide the specifics on how the value is to be generated.
  @TableGenerator(
   name="sqlite_person_id_generator",        // identifier for the generator binding.
   table="sqlite_sequence",                  // is the name of the table created to store the sequence values
   pkColumnName="name",                      // (Optional) Name of the primary key column in the table.
   valueColumnName="seq",                    // (Optional) Name of the column that stores the last value generated.
   pkColumnValue="Person",
   allocationSize=1													 // Increments the cointer by one during the insertion
   )
  @Column(name="idPerson")									 // is the name of the table column
  private int idPerson;
  @Column(name="firstname")
  private String firstname;
  @Column(name="lastname")
  private String lastname;

  @Temporal(TemporalType.DATE)  							// indicates to the persistency manager that this is a Date field
  @Column(name="birthdate")
  private Date birthday;

	@OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="idPerson", referencedColumnName="idPerson")
	private List<Measure> healthHistory;

	// @OneToMany(mappedBy="person")
	@OneToMany(targetEntity=CurrentHealth.class, cascade={})
	@JoinColumn(name="idPerson", referencedColumnName="idPerson", nullable=false, updatable=false, insertable=false)
	private List<CurrentHealth> currentHealth;

	@OneToMany(targetEntity=Goal.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="idPerson", referencedColumnName="idPerson")
	private List<Goal> goals;

	@OneToMany(targetEntity=DailyGoal.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="idPerson", referencedColumnName="idPerson")
	private List<DailyGoal> dailyGoals;

	public int getIdPerson() {
		return idPerson;
	}


	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<CurrentHealth> getCurrentHealth(){
		return this.currentHealth;
	}

	public void setCurrentHealth(List<CurrentHealth> currentHealth){
		this.currentHealth = currentHealth;
	}

	public List<Measure> getMeasure(){
		return this.healthHistory;
	}

	public void setMeasure( List<Measure> healthHistory){
		this.healthHistory = healthHistory;
	}

	public List<Goal> getGoals(){
		return this.goals;
	}

	public void setGoals(List<Goal> goals){
		this.goals = goals;
	}

	public List<DailyGoal> getDailyGoals(){
		return this.dailyGoals;
	}

	public void setDailyGoals(List<DailyGoal> dailyGoals){
		this.dailyGoals = dailyGoals;
	}
	// performing method overload, this method id more user friendly for testing
	public void setBirthday(int day, int month, int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		this.birthday = cal.getTime();
	}


	public static void removePerson(Long id){
		Person p = Person.getPersonById(toIntExact(id));
		if(p!=null)
			Person.removePerson(p);
	}


	public static void removePerson(Person p) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p);
		em.remove(p);
		tx.commit();
		LifeCoachDb_Dao.instance.closeConnections(em);
	}

	public static Person savePerson(Person p){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p = em.merge(p); // actually this code was used to update, but it works also if the entity is not present
		tx.commit();		 // in the DB.. We eliminated one method
		LifeCoachDb_Dao.instance.closeConnections(em);
		return p;
	}

	public static List<Person> getAllPeople(){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return list;
	}

	public static Person getPersonById(int id) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		Person person = em.find(Person.class, id);
		LifeCoachDb_Dao.instance.closeConnections(em);
		if (person==null){
				System.out.println("The given ID is NOT in our database" + id);
		}
		return person;
	}

	public static Measure savePersonMeasure(Long id, Measure hmh){
			Person p = Person.getPersonById(toIntExact(id));
			hmh.setPerson(p);
			hmh = Measure.saveMeasure(hmh);

		return hmh;
	}
}
