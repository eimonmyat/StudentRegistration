package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import entities.Lecturer;
import entities.Schedule;
import entities.Category;
import entities.Classroom;
import entities.Course;
import repositories.LecturerRepo;
import repositories.ScheduleRepo;
import repositories.ClassroomRepo;

public class ScheduleServices implements ScheduleRepo{
	private final DBConfig dbConfig=new DBConfig();
	 public void saveSchedule(String id,Schedule schedule) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("INSERT INTO schedule (scheduleID,starttime,endtime,startDate,endDate,registeredUser,courseID,classroomID,lecturerID)  VALUES (?,?,?,?,?,?,?,?,?);");
	            ps.setString(1,id );
	            ps.setString(2, schedule.getstartTime());
	            ps.setString(3, schedule.getendTime());
	            ps.setString(4, schedule.getstartDate());
	            ps.setString(5, schedule.getendDate());
	            ps.setInt(6, schedule.getRegisterUser());
	            ps.setString(7, schedule.getCourse());
	            ps.setString(8, schedule.getClassroom());
	            ps.setString(9,schedule.getLecturer());
	            
	            ps.executeUpdate();
	            ps.close();

	        } catch (SQLException e) {	
	           // if (e instanceof MySQLIntegrityConstraintViolationException) {
	        	e.printStackTrace();
	        	//JOptionPane.showMessageDialog(null, "Already Exists");
	            //}
	        }
	    }
	 public void updateSchedule(String id, Schedule schedule) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE schedule SET starttime=?,endtime=?,startDate=?,endDate=?,registeredUser=?,courseID=?,classroomID=?,lecturerID=? WHERE scheduleID = ?");            
	          
	            
	            ps.setString(1,String.valueOf( schedule.getstartTime()));
	            ps.setString(2, String.valueOf( schedule.getendTime()));
	            ps.setString(3, schedule.getstartDate());
	            ps.setString(4, schedule.getendDate());
	            ps.setInt(5, schedule.getRegisterUser());
	            ps.setString(6, schedule.getCourse());
	            ps.setString(7, schedule.getClassroom());
	            ps.setString(8,schedule.getLecturer());
	            ps.setString(9,id );
	            ps.executeUpdate();
	            JOptionPane.showMessageDialog(null,"sucess");
	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public void updateRegisteredUser(String id, Schedule schedule) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE schedule SET registeredUser=? WHERE scheduleID = ?");            
	          
	            ps.setInt(1, schedule.getRegisterUser()+1);
	            ps.setString(2,id );
	            ps.executeUpdate();
	            JOptionPane.showMessageDialog(null,"sucess");
	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public void deleteSchedule(String id) {
		 try {
			 PreparedStatement ps=this.dbConfig.getConnection()
					 .prepareStatement("DELETE FROM lecturer WHERE lecturerID=?;");
			 ps.setString(1, id);
			 ps.executeUpdate();
			 ps.close();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 public List<Schedule> findAllSchedule() {

	        List<Schedule> scheduleList = new ArrayList<>();
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM schedule";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	Schedule c = new Schedule();
	                c.setId(rs.getString("scheduleID"));
	                c.setstartTime(rs.getString("starttime"));
	                c.setendTime(rs.getString("endtime"));
	                c.setstartDate(rs.getString("startDate"));
	                c.setendDate(rs.getString("endDate"));
	                c.setRegisterUser(rs.getInt("registeredUser"));
	                c.setCourse(rs.getString("courseID"));
	                c.setClassroom(rs.getString("classroomID"));
	                c.setLecturer(rs.getString("lecturerID"));
	                scheduleList.add(c);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return scheduleList;
	    }
	 public Schedule findById(String id) {
		 Schedule category = new Schedule();

	        try (Statement st = this.dbConfig.getConnection().createStatement()) {


	            String query = "SELECT * FROM schedule WHERE scheduleID= '" + id + "';";
	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	category.setId(rs.getString("scheduleID"));
	            	category.setstartTime(rs.getString("starttime"));
	            	category.setendTime(rs.getString("endtime"));
	            	category.setstartDate(rs.getString("startDate"));
	            	category.setendDate(rs.getString("endDate"));
	            	category.setRegisterUser(rs.getInt("registeredUser"));
	            	category.setCourse(rs.getString("courseID"));
	            	category.setClassroom(rs.getString("classroomID"));
	            	category.setLecturer(rs.getString("lecturerID"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return category;
	    }
	 public String getAutoId(String field,String prefix) {
		 try (Statement st = this.dbConfig.getConnection().createStatement()) {
			 String query="SELECT "+field+" from schedule";
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
		 String query="Select * from schedule where scheduleName='"+data[0]+"'";
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
	 public ArrayList<String> getName(String field,String Table){
		 try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query = "SELECT "+ field +" FROM "+ Table;
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
	 public String findClassroomID(String condition) {
		 try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query="Select classroomID from classroom where classroomName='"+condition+"'";
			 ResultSet rs=st.executeQuery(query);
			 rs.next();
			 String result=rs.getString(1);
                return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
		
	 }
	 public String findLecturerID(String condition) {
		 try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query="Select lecturerID from lecturer where lecturerName='"+condition+"'";
			 ResultSet rs=st.executeQuery(query);
			 rs.next();
			 String result=rs.getString(1);
                return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
		
	 }
	 
	 public String findCourseID(String condition) {
		 try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query="Select courseID from course where courseName='"+condition+"'";
			 ResultSet rs=st.executeQuery(query);
			 rs.next();
			 String result=rs.getString(1);
                return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
		
	 }
	 
	 public String findCourseName(String condition) {
		 try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query="Select courseName from course where courseID='"+condition+"'";
			 ResultSet rs=st.executeQuery(query);
			 rs.next();
			 String result=rs.getString(1);
                return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
		
	 }
	 
	 public String findClassroomName(String condition) {
		 try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query="Select classroomName from classroom where classroomID='"+condition+"'";
			 ResultSet rs=st.executeQuery(query);
			 rs.next();
			 String result=rs.getString(1);
                return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
		
	 }
	 public String findLecturerName(String condition) {
		 try(Statement st=this.dbConfig.getConnection().createStatement()){
			 String query="Select lecturerName from lecturer where lecturerID='"+condition+"'";
			 ResultSet rs=st.executeQuery(query);
			 rs.next();
			 String result=rs.getString(1);
                return result;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
		
	 }
	 public List<Schedule> findAllScheduleByID(String id) {
		 	
	        List<Schedule> scheduleList = new ArrayList<>();
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM schedule where courseID='"+id+"'";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	Schedule c = new Schedule();
	                c.setId(rs.getString("scheduleID"));
	                c.setstartTime(rs.getString("starttime"));
	                c.setendTime(rs.getString("endtime"));
	                c.setstartDate(rs.getString("startDate"));
	                c.setendDate(rs.getString("endDate"));
	                c.setRegisterUser(rs.getInt("registeredUser"));
	                c.setCourse(rs.getString("courseID"));
	                c.setClassroom(rs.getString("classroomID"));
	                c.setLecturer(rs.getString("lecturerID"));
	                scheduleList.add(c);
	            }


	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return scheduleList;
	    }
	
		 
	 }


