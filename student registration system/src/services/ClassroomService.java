package services;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import shared.exception.AppException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import config.DBConfig;
import entities.Classroom;
import repositories.ClassroomRepo;
public class ClassroomService implements ClassroomRepo {
	
	private final DBConfig dbConfig=new DBConfig();
	 public void saveClassroom(String id,Classroom classroom) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("INSERT INTO classroom (classroomID,classroomName)  VALUES (?,?);");
	            ps.setString(1,id );
	            ps.setString(2, classroom.getName());
	            ps.executeUpdate();
	            ps.close();
	            JOptionPane.showMessageDialog(null, "Save");

	        } catch (SQLException e) {	
	           //if (e instanceof MySQLIntegrityConstraintViolationException) {
	                JOptionPane.showMessageDialog(null, "Already Exists");
	            //}
	        }
	    }
	 public void updateClassroom(String id, Classroom classroom) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE classroom SET classroomName = ? WHERE classroomID = ?");

	            ps.setString(1, classroom.getName());
	            ps.setString(2, id);
	            ps.executeUpdate();

	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public void deleteClassroom(String id) {
		 try {
			 PreparedStatement ps=this.dbConfig.getConnection()
					 .prepareStatement("DELETE FROM classroom WHERE classroomID=?;");
			 ps.setString(1, id);
			 ps.executeUpdate();
			 ps.close();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 public List<Classroom> findAllCategories() {

	        List<Classroom> classroomList = new ArrayList<>();
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM classroom";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                Classroom c = new Classroom();
	                c.setId(rs.getString("classroomID"));
	                c.setName(rs.getString("classroomName"));
	                classroomList.add(c);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return classroomList;
	    }
	 public Classroom findById(String id) {
	        Classroom category = new Classroom();

	        try (Statement st = this.dbConfig.getConnection().createStatement()) {


	            String query = "SELECT * FROM classroom WHERE classroomID= '" + id + "';";
	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                category.setId(rs.getString("classroomID"));
	                category.setName(rs.getString("classroomName"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return category;
	    }
	 public String getAutoId(String field,String prefix) {
		 try (Statement st = this.dbConfig.getConnection().createStatement()) {
			 String query="SELECT "+field+" from classroom";
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
	 /*public boolean isduplicate(String[]data)throws SQLException{
		 String query="Select * from classroom where classroomName='"+data[0]+"'";
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
	 }*/
	 
}