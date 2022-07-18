package repositories;

import java.util.List;

import entities.Registration;

public interface RegistrationRepo {
	 void saveRegistration(String id,Registration registration);

	    void updateRegistration(String id, Registration registration);

	    List<Registration> findAllRegistrations();

	    Registration findById(String id);

	    String getAutoId(String field,String prefix,String tableName);

}
