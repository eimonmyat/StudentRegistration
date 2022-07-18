package repositories;

import java.util.List;

import entities.Student;

public interface StudentRepo {
    void saveStudent(String id,Student student);

    void updateStudent(String id, Student student);

    List<Student> findAllStudent();

    Student findById(String id);

    //void deleteStudent(String id);
    String getAutoId(String field,String prefix);
}