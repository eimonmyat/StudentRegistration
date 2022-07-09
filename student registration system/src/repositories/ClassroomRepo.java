package repositories;

import java.util.List;

import entities.Classroom;

public interface ClassroomRepo {
    void saveClassroom(String id,Classroom classroom);

    void updateClassroom(String id, Classroom category);

    List<Classroom> findAllCategories();

    Classroom findById(String id);

    void deleteClassroom(String id);
    String getAutoId(String field,String prefix);
}
