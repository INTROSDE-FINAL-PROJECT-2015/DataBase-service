package introsde.models;

import java.util.List;
import javax.persistence.*;

import javax.xml.bind.annotation.XmlTransient;

import introsde.dao.LifeCoachDb_Dao;

@Entity
@Table(name="MeasureDefinition")
@NamedQueries({
	@NamedQuery(name="MeasureDefinition.findAll", query="SELECT m FROM MeasureDefinition m"),
	@NamedQuery(name="MeasureDefinition.getMeasureDefinitionByName", query="SELECT d FROM MeasureDefinition d WHERE d.measureType = ?1 ")
})
public class MeasureDefinition{
  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO,                     // Indicates that the persistence provider should pick an appropriate strategy for the particular database.
    generator="sqlite_measure_definition_id_generator") // generator="sqlite_measure_definition_id_generator" must match the name of the @TableGenerator tag
  @TableGenerator(name="sqlite_measure_definition_id_generator", // the name of the generator
      table="sqlite_sequence",                                  // the name of the table where are stored sequences values
	    pkColumnName="name",                      				// name of the primary key (Optional)
      valueColumnName="seq",                   				  // the Column containing the actual value of the counter
	    pkColumnValue="MeasureDefinition",        				// the name of the Column related to the sequence to autoincrement
      allocationSize=1																	// Increments the cointer by one during the insertion
      )
  private int idMeasureDefinition;

  @Column(name="measureType")
  private String measureType;

  @Column(name="measureValueType")
  private String measureValueType;



	public int getIdMeasureDefinition() {
		return idMeasureDefinition;
	}


	public void setIdMeasureDefinition(int idMeasureDefinition) {
		this.idMeasureDefinition = idMeasureDefinition;
	}


	public String getMeasureType() {
		return measureType;
	}


	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	//@XmlTransient
	public String getMeasureValueType() {
		return measureValueType;
	}


	public void setMeasureValueType(String measureValueType) {
		this.measureValueType = measureValueType;
	}


	public static void removeMeasureDefinition(MeasureDefinition mdef){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		mdef = em.merge(mdef);
		em.remove(mdef);
		tx.commit();
		LifeCoachDb_Dao.instance.closeConnections(em);
	}


	public static MeasureDefinition saveMeasureDefinition(MeasureDefinition mdef){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(mdef);
		tx.commit();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return mdef;
	}


	public static List<MeasureDefinition> getAllMeasureDefinitions(){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		List<MeasureDefinition> list = em.createNamedQuery("MeasureDefinition.findAll", MeasureDefinition.class).getResultList();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return list;
	}

	public static MeasureDefinition getMeasureDefinitionById(int id){
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		MeasureDefinition md = em.find(MeasureDefinition.class, id);
		LifeCoachDb_Dao.instance.closeConnections(em);
		if (md==null){
				System.out.println("The given ID is NOT in our database" + id);
		}
		return md;
	}

	public static MeasureDefinition getMeasureDefinitionByName(String measureType) {
		EntityManager em = LifeCoachDb_Dao.instance.createEntityManager();
		MeasureDefinition p = em.createNamedQuery("MeasureDefinition.getMeasureDefinitionByName", MeasureDefinition.class).setParameter(1, measureType).getSingleResult();
		LifeCoachDb_Dao.instance.closeConnections(em);
		return p;
	}
}
