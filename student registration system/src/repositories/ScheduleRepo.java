package repositories;

import java.util.List;

import entities.Schedule;

public interface ScheduleRepo {
    void saveSchedule(String id,Schedule schedule);

    void updateSchedule(String id, Schedule schedule);

    List<Schedule> findAllSchedule();

    Schedule findById(String id);

    void deleteSchedule(String id);
    String getAutoId(String field,String prefix);
}