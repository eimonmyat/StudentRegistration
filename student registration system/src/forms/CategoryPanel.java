package forms;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import entities.Category;
import services.CategoryService;

import javax.swing.JTable;

public class CategoryPanel extends JPanel {
	private JTextField txtCategory;
	private Category category;
	private CategoryService categoryService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Category> origianlCategoryList = new ArrayList<>();
	private JTable tblCategory;
	private JTextField txtCategoryID;
	/**
	 * Create the panel.
	 */
	public CategoryPanel() {
		initialize();
        initializeDependency();
        autoID();
        this.setTableDesign();
        this.loadAllCategories(Optional.empty());
	}
	
	private void loadAllCategories(Optional<List<Category>> optionalCategories) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlCategoryList = this.categoryService.findAllCategories();
        List<Category> categoryList = optionalCategories.orElseGet(() -> origianlCategoryList);

        categoryList.forEach(c -> {
            Object[] row = new Object[2];
            row[0] = c.getId();
            row[1] = c.getName();
            dtm.addRow(row);
        });
    }

    private void setTableDesign() {
    	//dtm.addColumn("No");
        dtm.addColumn("ID");
        dtm.addColumn("Category");
        this.tblCategory.setModel(dtm);
    }

    private void autoID() {
    	txtCategoryID.setText(String.valueOf((categoryService.getAutoId("categoryID","T-"))));
    }
    
    private void initializeDependency() {
        this.categoryService = new CategoryService();
        //this.categoryService.setProductRepo(new ProductService());
    }
	private void resetFormData() {
        txtCategory.setText("");
	}
	public void initialize() {
		
		JLabel lblNewLabel = new JLabel("Category ID");
		
		JLabel lblNewLabel_1 = new JLabel("Category Name");
		
		txtCategoryID = new JTextField();
		txtCategoryID.setEditable(false);
		txtCategoryID.setColumns(10);
		
		txtCategory = new JTextField();
		txtCategory.setColumns(10);
		
		JButton btnCreate = new JButton("Save");
		btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String st[]=new String[1];

                if (null != category && category.getId() != null) {
                    category.setName(txtCategory.getText());
                    if (!category.getName().isBlank()) {
                    	st[0]=(String)txtCategory.getText();
                    	try {
                    		boolean ee=categoryService.isduplicate(st);
                    		if(ee) {
                    			JOptionPane.showMessageDialog(null, "Duplicate Record");
                    			autoID();
                    			resetFormData();
                    			loadAllCategories(Optional.empty());
                    			category=null;
                    		}
                    		else {
                    			categoryService.updateCategory(txtCategoryID.getText(), category);
                                resetFormData();
                                autoID();
                                //JOptionPane.showMessageDialog(null, "Update successful");
                                loadAllCategories(Optional.empty());
                                category = null;
                    		}
                    	}catch(SQLException e1) {
                    		e1.printStackTrace();
                    	}
						
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
                    }
                } else {
                    Category category = new Category();
                    category.setName(txtCategory.getText());


                        if (null != category.getName() && !category.getName().isBlank()) {
                        	st[0]=(String)txtCategory.getText();
                        	try {
                        		boolean ee=categoryService.isduplicate(st);
                        		if(ee) {
                        			JOptionPane.showMessageDialog(null, "Duplicate Record");
                        			
                        			resetFormData();
                        			autoID();
                        			loadAllCategories(Optional.empty());
                        			category=null;
                        		}else
                        		{
                        			categoryService.saveCategory(txtCategoryID.getText(),category);
                                    resetFormData();
                                    autoID();
                                    JOptionPane.showMessageDialog(null, "Save successful");
                                    loadAllCategories(Optional.empty());
                        		}

                        } catch(SQLException e2) {
                        	e2.printStackTrace();
                        }
                        }
                        	else {
                            JOptionPane.showMessageDialog(null, "Enter Required Field!");
                        }
                }
            }
        });
		
		JButton btnUpdate = new JButton("Update");
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (null != category) {
                    categoryService.deleteCategory(category.getId() + "");
                    JOptionPane.showMessageDialog(null, "Delete successfully");
                    resetFormData();
                    autoID();
                    loadAllCategories(Optional.empty());
                    category = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Category");
                }
            }
        });
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(63)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1))
							.addGap(81)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCategoryID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(49)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(49)
									.addComponent(btnCreate)
									.addGap(34)
									.addComponent(btnUpdate)
									.addGap(28)
									.addComponent(btnDelete)))))
					.addContainerGap(79, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(txtCategoryID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDelete)
						.addComponent(btnUpdate)
						.addComponent(btnCreate))
					.addGap(49)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
					.addGap(107))
		);
		
		tblCategory = new JTable();
		scrollPane.setViewportView(tblCategory);
		setLayout(groupLayout);

	}
}
