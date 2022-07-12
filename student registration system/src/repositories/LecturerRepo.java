package repositories;

import java.util.List;

import entities.Lecturer;

public interface LecturerRepo {
	 void saveLecturer(String id,Lecturer lecturer);

	    void updateLecturer(String id, Lecturer lecturer);

	    List<Lecturer> findAllCategories();

	    Lecturer findById(String id);

	    void deleteLecturer(String id);
	    String getAutoId(String field,String prefix);

}
