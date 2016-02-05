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
@Table(name="Goal")   // Denotes the name of the table in which this entity is stored
@NamedQueries({
	@NamedQuery(name="Goal.findAll", query="SELECT p FROM  Goal p"),
	@NamedQuery(name="Goal.getGoalByPid", query="SELECT gl FROM Goal gl WHERE gl.person = ?1")
	//@NamedQuery(name="Person.findByMeasureNameMinMax",
		//		query="SELECT p FROM Person p INNER JOIN p.lifeStatus l WHERE l.measureDefinition = ?1 AND "
			//			+ "CAST(l.value NUMERIC(10,2)) BETWEEN ?2 AND ?3")
})
public class Goal{

  @Id 																			 // declares the field it refers to as the unique identifier for this entity
  @GeneratedValue(
    strategy = GenerationType.AUTO,          // Indicates that the persistence provider should pick an appropriate strategy for the particular database.
    generator="sqlite_goal_id_generator")  // generator="sqlite_person_id_generator" must match the name of the @TableGenerator tag
                                             // to provide the specifics on how the value is to be generated.
  @TableGenerator(
   name="sqlite_goal_id_generator",        // identifier for the generator binding.
   table="sqlite_sequence",                  // is the name of the table created to store the sequence values
   pkColumnName="name",                      // (Optional) Name of the primary key column in the table.
   valueColumnName="seq",                    // (Optional) Name of the column that stores the last value generated.
   pkColumnValue="Goal",
   allocationSize=1													 // Increments the cointer by one during the insertion
   )
	@Column(name="idGoal")
	private int idGoal;
  @Column(name="idMeasureDefinition")
  private int idMeasureDefinition;
  @Column(name="idPerson")									 // is the name of the table column
  private int idPerson;
  @Column(name="operator")
  private String operator;
  @Column(name="value")
  private String value;
	@Column(name="reached")
	private String reachDate;

	@ManyToOne
	@JoinColumn( name = "idPerson", referencedColumnName = "idPerson", insertable=false, updatable=false)
	private Person person;

	@OneToOne
	@JoinColumn( name = "idMeasureDefinition", referencedColumnName = "idMeasureDefinition", insertable=false, updatable=false)
	private MeasureDefinition measureDefinition;

	public MeasureDefinition getMeasureDefinition(){
		return this.measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition measureDefinition){
		this.measureDefinition = measureDefinition;
	}

	public int getIdGoal() {
		return idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}

	public int getIdMeasureDefinition() {
		return idMeasureDefinition;
	}

	public void setIdMeasureDefinition(int idMeasureDefinition) {
		this.idMeasureDefinition = idMeasureDefinition;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getOperator(){
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getReachDate() {
		return reachDate;
	}

	public void setReachDate(String reachDate) {
		this.reachDate = reachDate;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public static List<Goal> getAllGoals(){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		List<Goal> list = em.createNamedQuery("Goal.findAll", Goal.class).getResultList();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return list;
	}

	public static List<Goal> getGoalByPid(int idPerson) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		Person p = Person.getPersonById(idPerson);
		TypedQuery<Goal> query = em.createNamedQuery("Goal.getGoalByPid", Goal.class);
		query.setParameter(1, p);
		List<Goal> goalList = query.getResultList();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return goalList;
	}

	public static Goal getGoalByGoalId(int idGoal) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		Goal g = em.find(Goal.class, idGoal);
		LifeCoachDb_Dao.instance.closeConnections(em);
		if (g==null){
				System.out.println("The given ID is NOT in our database" + idGoal);
		}
		return g;
	}

	public static Goal saveGoal(Goal g) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		g=em.merge(g);
		tx.commit();
		LifeCoachDb_Dao.instance.closeConnections(em);
		System.out.println("\n\n\n\n"+g.getIdGoal()+"\n\n\n\n");
		g = Goal.getGoalByGoalId(g.getIdGoal());

		return g;
	}

	public static Goal saveGoal(int id, Goal g){
		g.setIdPerson(id);
		g = Goal.saveGoal(g);
		System.out.println("\n\n\n\n"+g.getIdGoal()+"\n\n\n\n");
		g = Goal.getGoalByGoalId(g.getIdGoal());
		return g;
	}

	public static void removeGoal(Goal g) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		g=em.merge(g);
		em.remove(g);
		tx.commit();
		LifeCoachDb_Dao.instance.closeConnections(em);
		System.out.println("GOAL REMOVED!");
	}

	public static void removeGoal(int idGoal) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Goal g = Goal.getGoalByGoalId(idGoal);
		if(g!=null){
			removeGoal(g);
		}else{
			System.out.println("No Goal with this ID");
		}
	}
}
