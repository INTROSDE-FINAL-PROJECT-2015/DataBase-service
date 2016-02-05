package introsde;

import java.util.List;

import java.util.Calendar;

import introsde.models.Person;
import introsde.models.MeasureDefinition;
import introsde.models.Measure;
import introsde.models.CurrentHealth;
import introsde.models.Goal;
import introsde.models.DailyGoal;

public class Test{
  public static void main(String[] args) {
    //viewTest();
    //updateMeasure();
    //updatePerson();
    getAllGoals();

    List<Person> personList = Person.getAllPeople();

    for (Person per : personList){
      System.out.println(per.getLastname());
      List<Goal> lg = per.getGoals();
      for (Goal g : lg){
        System.out.println(g.getValue());
      }
    }

    List<Goal> gl = Goal.getGoalByPid(1);
    Goal golle = gl.get(0);
    for(Goal g : gl){
      System.out.println(g.getValue());
    }

    List<DailyGoal> dgl = DailyGoal.getAllDailyGoals();
    for (DailyGoal dl : dgl){
      System.out.println(dl.getQuestion());
    }

    dgl = DailyGoal.getDailyGoalsByPid(1);
    for (DailyGoal dl : dgl){
      System.out.println(dl.getQuestion());
    }

    Person per =  Person.getPersonById(1);
    List<DailyGoal> ddgl = per.getDailyGoals();
    for (DailyGoal dl : ddgl){
      System.out.println(dl.getQuestion());
    }

    golle.setValue("99999");
    golle = Goal.saveGoal(golle);
    System.out.println("sono qui");
    System.out.println(golle.getValue());
    System.out.println(golle.getPerson().getFirstname());

    gl = Goal.getGoalByPid(1);
    for(Goal g : gl){
      System.out.println(g.getValue());
    }

    Goal newGoal = new Goal();
    newGoal.setValue("ciaone");
    newGoal.setIdMeasureDefinition(1);
    newGoal.setOperator("gt");
    newGoal.setIdPerson(1);
    //newGoal.setPerson(Person.getPersonById(1));
    newGoal = Goal.saveGoal(newGoal);
    System.out.println(Goal.getGoalByGoalId(newGoal.getIdGoal()).getPerson().getFirstname());

    DailyGoal dg = DailyGoal.getDailyGoalById(1);
    System.out.println(dg.getQuestion());

    DailyGoal newDailyGoal = new DailyGoal();
    newDailyGoal.setIdPerson(1);
    newDailyGoal.setQuestion("Did you run 4000 meters at 50 kmh!?!??!?");
    newDailyGoal = DailyGoal.saveDailyGoal(1,newDailyGoal);
    System.out.println(newDailyGoal.getQuestion());
    System.out.println(newDailyGoal.getPerson().getFirstname());
    newDailyGoal.setQuestion("nuovaDomanda");
    DailyGoal.saveDailyGoal(newDailyGoal);
    int dailygoalID = newDailyGoal.getIdGoal();
    newDailyGoal = DailyGoal.getDailyGoalById(dailygoalID);
    System.out.println(newDailyGoal.getQuestion());
    Goal.removeGoal(newGoal);
    DailyGoal.removeDailyGoal(newDailyGoal);
    System.out.println(MeasureDefinition.getMeasureDefinitionById(1).getMeasureType());

  }

  private static void viewTest(){
    List<Person> personList = Person.getAllPeople();
    for (Person per : personList){
      System.out.println(per.getLastname() + " " + per.getFirstname());
      List<CurrentHealth> lfList = per.getCurrentHealth();
      for(CurrentHealth lf : lfList){
        System.out.println(lf.getMeasureType() + " " + lf.getMeasureValue() + " " + lf.getDateRegistered().getYear() + " " + lf.getDateRegistered());
      }
    }
  }

  private static void testamelo(){
    Person p = new Person();
    p.setFirstname("Giacomo");
    p.setLastname("Menestò");
    p.setBirthday(30,12,1992);

    p = Person.savePerson(p);

    System.out.println(p.getFirstname());

    List<MeasureDefinition> mDefList = MeasureDefinition.getAllMeasureDefinitions();
    List<Person> personList = Person.getAllPeople();

    for (Person per : personList){
      System.out.println(per.getLastname());
    }
    p.setLastname("MENESTRO'");

    p = Person.savePerson(p);



    personList = Person.getAllPeople();

    for (Person per : personList){
      System.out.println(per.getLastname());
    }

    //Person.removePerson(p);

    personList = Person.getAllPeople();

    System.out.println("\n\n\n\n\n\n" + personList.size() + "\n\n\n\n\n\n");

    for (MeasureDefinition mdef : mDefList){
      System.out.println(mdef.getMeasureValueType() + " " + mdef.getMeasureValueType() + " " + mdef.getIdMeasureDefinition());
    }

    List<Measure> hmhList = Measure.getAll();
    System.out.println(hmhList.size());
    for(Measure hmh : hmhList){
      System.out.println(hmh.getPerson().getFirstname() + " " + hmh.getMeasureDefinition().getMeasureValueType() + " " + hmh.getMeasureValue()
      + " " + hmh.getDateRegistered().getYear());
    }

    p = personList.get(0);
    System.out.println(p.getFirstname());
    System.out.println(p.getCurrentHealth().get(0).getMeasureType());

    Person.removePerson(p);

    Person persona;
    List<CurrentHealth> lfList = CurrentHealth.getAll();
    for(CurrentHealth lf : lfList){
      System.out.println(lf.getMeasureType());
      System.out.println(lf.getMeasureValue());
      persona = lf.getPerson();
      System.out.println(persona.getFirstname());
    }
  }

  private static void getAllGoals(){
    List<Goal> goals = Goal.getAllGoals();
    for (Goal g : goals){
      System.out.println(g.getValue());
    }
  }

  private static void updateMeasure(){
    Person p = Person.getAllPeople().get(0);
    Measure hmh = p.getMeasure().get(0);
    System.out.println(hmh.getMeasureValue());
    hmh.setMeasureValue("987");
    Person.savePerson(p);

    p = Person.getAllPeople().get(0);
    hmh = p.getMeasure().get(0);

    System.out.println(hmh.getMeasureValue());
  }

    public static void updatePerson(){
       System.out.println("\n--> TEST: updatePerson");
       Person person = Person.getAllPeople().get(1);

       person.setMeasure(Person.getAllPeople().get(2).getMeasure());
      // if(Person.getAllPeople().get(2).getMeasure()==null)
        System.out.println(Person.getAllPeople().get(0).getMeasure().size());
       //System.out.println(person.getCurrentHealth().size());
       String oldName = person.getFirstname();
       System.out.println(oldName);
       person.setFirstname( new StringBuffer(oldName).reverse().toString());
       person = Person.savePerson(person);

       System.out.println(person.getFirstname());
       //assertFalse(person.getFirstname().equals(oldName));
    }
}
