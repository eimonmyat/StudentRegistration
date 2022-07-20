package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import config.DBConfig;
import entities.Lecturer;
import entities.Registration;
import entities.Schedule;
import entities.Student;
import repositories.RegistrationRepo;

public class RegistrationServices implements RegistrationRepo{
	private final DBConfig dbConfig=new DBConfig();

	public void saveRegistration(String id, Registration registration) {
		try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("INSERT INTO registration (registrationID,registrationDate,studentID,scheduleID,amount)  VALUES (?,?,?,?,?);");
            ps.setString(1,registration.getId());
            ps.setString(2, registration.getDate());
            ps.setString(3, registration.getStudent().getId());
            ps.setString(4, registration.getSchedule().getId());
            ps.setDouble(5, registration.getAmount());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {	
           // if (e instanceof MySQLIntegrityConstraintViolationException) {
        	e.printStackTrace();
        	//JOptionPane.showMessageDialog(null, "Already Exists");
            //}
        }
	}

	
	public void updateRegistration(String id, Registration registration) {
		try {

            PreparedStatement ps = this.dbConfig.getConnection()
                    .prepareStatement("UPDATE registration SET registrationDate = ?, studentID=?, scheduleID=?, amount=?  WHERE registrationID = ?");

            ps.setString(1, registration.getDate());
            ps.setString(2, registration.getStudent().getId());
            ps.setString(3, registration.getSchedule().getId());
            ps.setDouble(4, registration.getAmount());
            ps.setString(5, registration.getId());
            ps.executeUpdate();

            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	
	public List<Registration> findAllRegistrations() {
		List<Registration> registrationList = new ArrayList<>();
        try (Statement st = this.dbConfig.getConnection().createStatement()) {

            String query = "SELECT * FROM registration";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
            	Registration c = new Registration();
            	Schedule schedule=new Schedule();
            	Student student=new Student();
                c.setId(rs.getString("registrationID"));
                c.setDate(rs.getString("registrationDate"));
                schedule.setId(rs.getString("scheduleID"));
                c.setSchedule(schedule);
                student.setId(rs.getString("studentID"));
                c.setStudent(student);
                c.setAmount(rs.getDouble("amount"));
                registrationList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registrationList;
	}

	
	public Registration findById(String id) {
		Registration registration = new Registration();

        try (Statement st = this.dbConfig.getConnection().createStatement()) {


            String query = "SELECT * FROM registration WHERE registrationID= '" + id + "';";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
            	Registration c = new Registration();
            	Schedule schedule=new Schedule();
            	Student student=new Student();
                c.setId(rs.getString("registrationID"));
                c.setDate(rs.getString("registrationDate"));
                schedule.setId(rs.getString("scheduleID"));
                c.setSchedule(schedule);
                student.setId(rs.getString("studentID"));
                c.setStudent(student);
                c.setAmount(rs.getDouble("amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registration;
	}

	public String getAutoId(String field, String prefix, String tableName) {
		try (Statement st = this.dbConfig.getConnection().createStatement()) {
			 String query="SELECT "+field+" from "+tableName;
			 ResultSet rs=st.executeQuery(query);
			 ArrayList<String> result=new ArrayList<String>();
			 int current;
			 while(rs.next()) {
				 result.add(rs.getString(field));
			 }
			 if(result.size()>0) {
				 current=Integer.parseInt(result.get(result.size()-1).toString().substring(2,10))+1;
				
				 if(current>0 && current<=9) {
					 return prefix+"0000000"+current;
				 }
				 else if(current>9 && current<=99) {
					 return prefix+"000000"+current;
				 }
				 else if(current>99 && current<=999) {
					 return prefix+"00000"+current;
				 }
				 else if(current>999 && current<=9999) {
					 return prefix+"0000"+current;
				 }
				 else if(current>9999 && current<=99999) {
					 return prefix+"000"+current;
				 }
				 else if(current>99999 && current<=999999) {
					 return prefix+"00"+current;
				 }
				 else if(current>999999 && current<=9999999) {
					 return prefix+"0"+current;
				 }
				 else if(current>9999999 && current<=99999999) {
					 return prefix+current;
				 }
				 }
		 }catch(SQLException e) {
			 //e.printStackTrace();
		 }
		 return prefix+"00000001";
	}
	public String getCategoryID(String id) {
		try(Statement st = this.dbConfig.getConnection().createStatement()) {
            String query = "SELECT categoryID FROM course where courseID='"+id+"'";
            ResultSet rs = st.executeQuery(query);
            String result=new String();
			 
			 while(rs.next()) {
				 result=rs.getString("categoryID");
			 }
			 return result;
		 }catch(SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
}


	public boolean isDuplicateStu(String scheduleID, String stuID) throws SQLException {
		String query="Select * from registration where scheduleID='"+scheduleID+"' and studentID='"+stuID+"'";
		 Statement st=this.dbConfig.getConnection().createStatement();
		 ResultSet rs;
		 try {
			 rs=st.executeQuery(query);
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
		 rs=st.executeQuery(query);
		 if(rs.next()) {
			 return true;
		 }
		 else {
			 return false;
		 }
	}

}