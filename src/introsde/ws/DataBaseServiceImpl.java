package introsde.ws;

import static java.lang.Math.toIntExact;

import javax.jws.WebService;
import java.util.List;

import introsde.models.Person;
import introsde.models.Measure;
import introsde.models.MeasureDefinition;
import introsde.models.DailyGoal;
import introsde.models.Goal;


@WebService(endpointInterface = "introsde.ws.DataBaseService")
public class DataBaseServiceImpl implements DataBaseService{

  // M #1
  @Override
  public List<Person> readPersonList(){
    return Person.getAllPeople();
  }
  // M #2
  @Override
  public Person readPerson(int id){
    return Person.getPersonById(toIntExact(id));
  }
  // M #3
  @Override
  public List<Measure> readPersonHistory(int id, String measureType){
    return Measure.readPersonHistory(id, measureType);
  }
  // M #4
  @Override
  public List<Goal> getPersonGoals(int pid){
    return Goal.getGoalByPid(pid);
  }
  // M #5
  @Override
  public Goal saveGoal(Goal g){
    return Goal.saveGoal(g);
  }
  // M #6
  @Override
  public List<DailyGoal> getPersonDailyGoals(int pid){
    return DailyGoal.getDailyGoalsByPid(pid);
  }

  // M #7
  @Override
  public DailyGoal createPersonDailyGoal(int pid,DailyGoal dg){
    return DailyGoal.saveDailyGoal(pid,dg);
  }
  // M #8
  @Override
  public DailyGoal updateDailyGoal(DailyGoal dg){
    return DailyGoal.saveDailyGoal1(dg);
  }
  // M #9
  @Override
  public List<MeasureDefinition> readMeasureTypes(){
    return MeasureDefinition.getAllMeasureDefinitions();
  }
  // M #10
  @Override
  public Measure readPersonMeasure(int id, String measureType, int mid){
    return Measure.getMeasureByPidAndMid(id,measureType, mid);
  }
  // M #11
  @Override
  public Measure savePersonMeasure(int id, Measure m){
    m.setMeasureDefinition(MeasureDefinition.getMeasureDefinitionByName(m.getMeasureDefinition().getMeasureType()));
    return Measure.savePersonMeasure(id, m);
  }
  // M #12
  @Override
  public Measure updatePersonMeasure(int id, Measure m){
    return Measure.updatePersonMeasure(id,m);
  }

  // M #13
  @Override
  public void deleteDailyGoal(int dgid){
    DailyGoal.removeDailyGoal(dgid);
  }

  // M #14
  @Override
  public void deleteGoal(int gid){
    Goal.removeGoal(gid);
  }



}
