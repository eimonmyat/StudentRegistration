package services;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import entities.Student;
import repositories.StudentRepo;
public class StudentService implements StudentRepo {
	
	private final DBConfig dbConfig=new DBConfig();
	 public void saveStudent(String id,Student student) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("INSERT INTO student (studentID,studentName)  VALUES (?,?);");
	            ps.setString(1,id );
	            
	            ps.setString(2, student.getName());
	            ps.executeUpdate();
	            ps.close();

	        } catch (SQLException e) {	
	           // if (e instanceof MySQLIntegrityConstraintViolationException) {
	        	e.printStackTrace();
	        	//JOptionPane.showMessageDialog(null, "Already Exists");
	            //}
	        }
	    }
	 public void updateStudent(String id, Student student) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE student SET studentName = ?, WHERE studentID = ?");

	            ps.setString(1, student.getName());
	            
	            ps.setString(2, id);
	            ps.executeUpdate();

	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public List<Student> findAllStudent() {

	        List<Student> StudentList = new ArrayList<>();
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM Student";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	Student c = new Student();
	                c.setId(rs.getString("studentID"));
	                c.setName(rs.getString("studentName"));
	                
	                StudentList.add(c);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return StudentList;
	    }
	 public Student findById(String id) {
		 Student student = new Student();

	        try (Statement st = this.dbConfig.getConnection().createStatement()) {


	            String query = "SELECT * FROM student WHERE studentID= '" + id + "';";
	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	student.setId(rs.getString("studentID"));
	            	student.setName(rs.getString("studentName"));
	                
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return student;
	    }
	 public String getAutoId(String field,String prefix) {
		 try (Statement st = this.dbConfig.getConnection().createStatement()) {
			 String query="SELECT "+field+" from student";
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
	
	 
}