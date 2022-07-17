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
	 public void saveCategory(String id,Category category) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("INSERT INTO courseCategory (categoryID,categoryName)  VALUES (?,?);");
	            ps.setString(1,id );
	            ps.setString(2, category.getName());
	            ps.executeUpdate();
	            ps.close();

	        } catch (SQLException e) {	
	           // if (e instanceof MySQLIntegrityConstraintViolationException) {
	        	e.printStackTrace();
	        	//JOptionPane.showMessageDialog(null, "Already Exists");
	            //}
	        }
	    }
	 public void updateCategory(String id, Category category) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("UPDATE courseCategory SET categoryName = ? WHERE categoryID = ?");

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
					 .prepareStatement("DELETE FROM courseCategory WHERE categoryID=?;");
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

	            String query = "SELECT * FROM courseCategory";

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


	            String query = "SELECT * FROM courseCategory WHERE categoryID= '" + id + "';";
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
		 String query="Select * from courseCategory where categoryName='"+data[0]+"'";
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
	public Category getData(String name) {
		// TODO Auto-generated method stub
		Category category=new Category();
		
        try (Statement st = this.dbConfig.getConnection().createStatement()) {


            String query = "SELECT categoryID FROM courseCategory WHERE categoryName= '" + name + "';";
            ResultSet rs = st.executeQuery(query);
            ArrayList<String> result=new ArrayList<String>();
			while(rs.next()) {
				 result.add(rs.getString(name));
			 }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
	}
	 
}