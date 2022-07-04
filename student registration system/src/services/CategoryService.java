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
	
	 public void saveCategory(Category category) {
	        try {

	            PreparedStatement ps = this.dbConfig.getConnection()
	                    .prepareStatement("INSERT INTO CourseCategory (categoryName)  VALUES (?);");

	            ps.setString(1, category.getName());
	            ps.executeUpdate();
	            ps.close();

	        } catch (SQLException e) {
	            //if (e instanceof MySQLIntegrityConstraintViolationException) {
	                JOptionPane.showMessageDialog(null, "Already Exists");
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
	                c.setId((rs.getInt("categoryID")));
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

	            String query = "SELECT * FROM courseCategory WHERE categoryID= " + id + ";";

	            ResultSet rs = st.executeQuery(query);

	            while (rs.next()) {
	                category.setId(rs.getInt("categoryID"));
	                category.setName(rs.getString("categoryName"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return category;
	    }
}
