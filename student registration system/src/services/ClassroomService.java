package services;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import entities.Classroom;
import entities.Schedule;
import repositories.ClassroomRepo;
public class ClassroomService implements ClassroomRepo {
	
	private final DBConfig dbConfig=new DBConfig();
	 public void saveClassroom(String id,Classroom Classroom) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("INSERT INTO Classroom (classroomID,classroomName,totalUsers)  VALUES (?,?,?);");
	            ps.setString(1,id );
	            ps.setInt(3, Classroom.getUsers());
	            ps.setString(2, Classroom.getName());
	            ps.executeUpdate();
	            ps.close();

	        } catch (SQLException e) {	
	           // if (e instanceof MySQLIntegrityConstraintViolationException) {
	        	e.printStackTrace();
	        	//JOptionPane.showMessageDialog(null, "Already Exists");
	            //}
	        }
	    }
	 public void updateClassroom(String id, Classroom Classroom) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE Classroom SET classroomName = ?, totalUsers=? WHERE classroomID = ?");

	            ps.setString(1, Classroom.getName());
	            ps.setInt(2, Classroom.getUsers());
	            ps.setString(3, id);
	            ps.executeUpdate();

	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public void deleteClassroom(String id) {
		 try {
			 PreparedStatement ps=this.dbConfig.getConnection()
					 .prepareStatement("DELETE FROM Classroom WHERE classroomID=?;");
			 ps.setString(1, id);
			 ps.executeUpdate();
			 ps.close();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 public List<Classroom> findAllCategories() {

	        List<Classroom> ClassroomList = new ArrayList<>();
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM Classroom";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                Classroom c = new Classroom();
	                c.setId(rs.getString("classroomID"));
	                c.setName(rs.getString("classroomName"));
	                c.setUsers(rs.getInt("totalUsers"));
	                ClassroomList.add(c);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return ClassroomList;
	    }
	 public Classroom findById(String id) {
	        Classroom Classroom = new Classroom();

	        try (Statement st = this.dbConfig.getConnection().createStatement()) {


	            String query = "SELECT * FROM classroom WHERE classroomID= '" + id + "';";
	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                Classroom.setId(rs.getString("classroomID"));
	                Classroom.setName(rs.getString("classroomName"));
	                Classroom.setUsers(rs.getInt("totalUsers"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return Classroom;
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
	 public boolean isduplicate(String[]data)throws SQLException{
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
	 }
	public ArrayList<String> getName(String field, String Table, String id) {
		try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query = "SELECT "+ field +" FROM "+ Table+" where classroomID='"+id+"'";
	            ResultSet rs = st.executeQuery(query);
               ArrayList<String> result=new ArrayList<String>();
	            while (rs.next()) {
	                result.add(rs.getString(field));
	            }
               return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	public int getTotalUsers(String id) {
		try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query = "SELECT totalUsers FROM classroom where classroomID='"+id+"'";
	            ResultSet rs = st.executeQuery(query);
	            int result=rs.getInt("totalUsers");
	            
               return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return 0;
	        }
	}
	public boolean isNotduplicate(String[]data)throws SQLException{
		int total=Integer.parseInt(data[1]);
		 String query="Select * from classroom where classroomName='"+data[0]+"' and totalUsers="+total+" and classroomID='"+data[2]+"'";
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