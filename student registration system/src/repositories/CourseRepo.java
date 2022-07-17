package repositories;

import java.util.ArrayList;
import java.util.List;

import entities.Category;
import entities.Course;

public interface CourseRepo {
	void saveCourse(Course course);

	void updateCourse(Course course);

    List<Course> findAllCourses();

    Course findById(String id);

    void deleteCourse(String id);
    String getAutoId(String field,String prefix);
    ArrayList<String> getCategoryName(String id);
    ArrayList<String> findCategoryID(String name);
}