package introsde.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import java.util.List;

import introsde.models.Person;
import introsde.models.Measure;
import introsde.models.MeasureDefinition;
import introsde.models.DailyGoal;
import introsde.models.Goal;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface DataBaseService{
    // M #1
    @WebMethod List<Person> readPersonList();
    // M #2
    @WebMethod Person readPerson(int id);
    // M #3
    @WebMethod List<Measure> readPersonHistory(int id, String measureType);
    // M #4
    @WebMethod List<Goal> getPersonGoals(int pid);
    // M #5
    @WebMethod Goal saveGoal(Goal g);
    // M #6
    @WebMethod List<DailyGoal> getPersonDailyGoals(int pid);
    // M #7
    @WebMethod DailyGoal createPersonDailyGoal(int pid, DailyGoal g);
    // M #8
    @WebMethod DailyGoal updateDailyGoal(DailyGoal d);
    // M #9
    @WebMethod List<MeasureDefinition> readMeasureTypes();
    // M #10
    @WebMethod Measure readPersonMeasure(int id, String measureType, int mid);
    // M #11
    @WebMethod Measure savePersonMeasure(int id, Measure m);
    // M #12
    @WebMethod Measure updatePersonMeasure(int id, Measure m);
    // M #13
    @WebMethod void deleteDailyGoal(int dgid);
    // M #14
    @WebMethod void deleteGoal(int gid);
}
