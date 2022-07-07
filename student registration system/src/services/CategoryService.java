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
import repositories.CategoryRepo;
public class CategoryService implements CategoryRepo {
	
	private final DBConfig dbConfig=new DBConfig();
	
<<<<<<< HEAD
	 public void saveCategory(String id,Category category) {
=======
	
	 public void saveCategory(Category category) {
>>>>>>> branch 'master' of https://github.com/eimonmyat/StudentRegistration
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
<<<<<<< HEAD
	                    .prepareStatement("INSERT INTO CourseCategory (categoryID,categoryName)  VALUES (?,?);");
	            ps.setString(1,id );
	            ps.setString(2, category.getName());
=======
	                    .prepareStatement("INSERT INTO coursecategory (categoryName)  VALUES (?);");
	            ps.setString(1, category.getName());
>>>>>>> branch 'master' of https://github.com/eimonmyat/StudentRegistration
	            ps.executeUpdate();
	            ps.close();

	        } catch (SQLException e) {	
	           // if (e instanceof MySQLIntegrityConstraintViolationException) {
	                JOptionPane.showMessageDialog(null, "Already Exists");
	            //}
	        }
	    }
	 public void updateCategory(String id, Category category) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE coursecategory SET categoryName = ? WHERE categoryID = ?");

	            ps.setString(1, category.getName());
	            ps.setString(2, id);
	            ps.executeUpdate();

	            ps.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 public void deleteCategory(String id) {
		 try {
			 PreparedStatement ps=this.dbConfig.getConnection()
					 .prepareStatement("DELETE FROM coursecategory WHERE categoryID=?;");
			 ps.setString(1, id);
			 ps.executeUpdate();
			 ps.close();
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 public List<Category> findAllCategories() {

	        List<Category> categoryList = new ArrayList<>();
	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

	            String query = "SELECT * FROM coursecategory";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                Category c = new Category();
	                c.setId(rs.getString("categoryID"));
	                c.setName(rs.getString("categoryName"));
	                categoryList.add(c);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return categoryList;
	    }
	 public Category findById(String id) {
	        Category category = new Category();

	        try (Statement st = this.dbConfig.getConnection().createStatement()) {

<<<<<<< HEAD
	            String query = "SELECT * FROM courseCategory WHERE categoryID= '" + id + "';";
=======
	            String query = "SELECT * FROM coursecategory WHERE categoryID= " + id + ";";
>>>>>>> branch 'master' of https://github.com/eimonmyat/StudentRegistration

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                category.setId(rs.getString("categoryID"));
	                category.setName(rs.getString("categoryName"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return category;
	    }
<<<<<<< HEAD
	 public String getAutoId(String field,String prefix) {
		 try (Statement st = this.dbConfig.getConnection().createStatement()) {
			 String query="SELECT "+field+" from courseCategory";
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
		 String query="Select * from coursecategory where categoryName='"+data[0]+"'";
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
=======
	 
	 public  boolean isduplicate(String[] data) throws SQLException {
			String query="select * from coursecategory where categoryName='"+data[0]+"'";
			Statement st = this.dbConfig.getConnection().createStatement();
			  ResultSet rs;
			try {
				rs = st.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs=st.executeQuery(query);
			if(rs.next())
				   return true;
			   else
				   return false;
			  
			   }
>>>>>>> branch 'master' of https://github.com/eimonmyat/StudentRegistration
}
