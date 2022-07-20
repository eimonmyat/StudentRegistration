 package repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Schedule;

public interface ScheduleRepo {
    void saveSchedule(String id,Schedule schedule);

    void updateSchedule(String id, Schedule schedule);

    List<Schedule> findAllSchedule();

    Schedule findById(String id);

    void deleteSchedule(String id);
    String getAutoId(String field,String prefix);
    ArrayList<String> getName(String field,String Table);
    List<Schedule> findAllScheduleByID(String id,String todayDate);
    List<Schedule> findAllScheduleByToday(String todayDate);
    String getRoomID(String id);
    String findClassroomID(String name);
    String findLecturerID(String name);
    String findCourseID(String name);
    String findCourseName(String name);
    String findClassroomName(String name);
    String findLecturerName(String name);
    boolean isduplicateroom(String[]data)throws SQLException;
    void updateRegisteredUser(String id, Schedule schedule);
    boolean isduplicate(String[]data)throws SQLException;
}