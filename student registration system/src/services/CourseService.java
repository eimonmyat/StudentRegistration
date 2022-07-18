package services;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import config.DBConfig;
import entities.Category;
import entities.Course;
import repositories.CourseRepo;
import repositories.CategoryRepo;
public class CourseService implements CourseRepo {
	
	private final DBConfig dbConfig=new DBConfig();
	 public void saveCourse(Course course) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("INSERT INTO Course (courseID,courseName,fee,categoryID)  VALUES (?,?,?,?);");
	            ps.setString(1,course.getId());
	            ps.setString(2, course.getName());
	            ps.setDouble(3, course.getFee());
	            ps.setString(4, course.getCategory().getId());
	            ps.executeUpdate();
	            ps.close();

	        } catch (SQLException e) {	
	           // if (e instanceof MySQLIntegrityConstraintViolationException) {
	        	e.printStackTrace();
	        	//JOptionPane.showMessageDialog(null, "Already Exists");
	            //}
	        }
	    }
	 public void updateCourse(Course course) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE Course SET courseName = ?, fee=?, categoryID=?,  WHERE courseID = ?");

	            ps.setString(1, course.getName());
	            ps.setString(3, course.getCategory().getId());
	            ps.setDouble(2, course.getFee());
	            ps.setString(4, course.getId());
	            ps.executeUpdate();

	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public void deleteCourse(String id) {
		 try {
			 PreparedStatement ps=this.dbConfig.getConnection()
					 .prepareStatement("DELETE FROM Course WHERE courseID=?;");
			 ps.setString(1, id);
			 ps.executeUpdate();
			 ps.close();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 public List<Course> findAllCourses() {
		 	
	        List<Course> courseList = new ArrayList<>();
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM course";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                Course c = new Course();
	                Category category=new Category();
	                c.setId(rs.getString("courseID"));
	                c.setName(rs.getString("courseName"));
	                c.setFee(Double.parseDouble(rs.getString("fee")));
	                category.setId(rs.getString("categoryID"));
	                c.setCategory(category);
	                courseList.add(c);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return courseList;
	    }
	 public Course findById(String id) {
	        Course course = new Course();
	        System.out.println(id);
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {


	            String query = "SELECT * FROM course WHERE courseID= '" + id + "';";
	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	            	
	            	Category category=new Category();
	                course.setId(rs.getString("courseID"));
	                course.setName(rs.getString("courseName"));
	                course.setFee(Double.parseDouble(rs.getString("fee")));
	                category.setId(rs.getString("categoryID"));
	                course.setCategory(category);
	                //System.out.println(rs.getString(course.getCategory().getId().toString()));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return course;
	    }
	 public String getAutoId(String field,String prefix) {
		 try (Statement st = this.dbConfig.getConnection().createStatement()) {
			 String query="SELECT courseID from Course";
			 ResultSet rs=st.executeQuery(query);
			 ArrayList<String> result=new ArrayList<String>();
			 int current;
			 while(rs.next()) {
				 result.add(rs.getString("courseID"));
			 }
			 //int c=Integer.parseInt(result.get(result.size()-2).toString().substring(2,10));
			 //int r=c+1;
			 //System.out.println(c);
			 //System.out.println(result.get(2));
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
		 String query="Select * from Course where courseName='"+data[0]+"' and fee="+data[1]+" and categoryID='"+data[2]+"'";
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

	public String getCategoryName(String id) {
			try(Statement st = this.dbConfig.getConnection().createStatement()) {
	            String query = "SELECT categoryName FROM courseCategory where categoryID='"+id+"'";
	            ResultSet rs = st.executeQuery(query);
	            String result=new String();
				 
				 while(rs.next()) {
					 result=rs.getString("categoryName");
					 //System.out.println(result);
				 }
				 return result;
			 }catch(SQLException e) {
				 e.printStackTrace();
				 return null;
			 }
	}
	public ArrayList<String> findCategoryID(String name) {
		try(Statement st = this.dbConfig.getConnection().createStatement()) {
            String query = "SELECT categoryID FROM courseCategory where categoryName='"+name+"'";
            ResultSet rs = st.executeQuery(query);
            ArrayList<String> result=new ArrayList<String>();
			 while(rs.next()) {
				 result.add(rs.getString("categoryID"));
			 }
			 return result;
		 }catch(SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	}
	
	public String findCourseID(String name) {
		try(Statement st = this.dbConfig.getConnection().createStatement()) {
            String query = "SELECT courseID FROM course where courseName='"+name+"'";
            ResultSet rs = st.executeQuery(query);
            String result=new String();
			 while(rs.next()) {
				 result=rs.getString("courseID");
			 }
			 return result;
		 }catch(SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	}
	
	public String getCategoryID(String name) {
		try(Statement st = this.dbConfig.getConnection().createStatement()) {
            String query = "SELECT categoryID FROM course where courseName='"+name+"'";
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
}