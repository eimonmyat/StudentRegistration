package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Category;
import entities.Lecturer;
import services.CategoryService;
//import services.ProductService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CategoryForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtCategory;
	private Category category;
	private CategoryService categoryService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Category> origianlCategoryList = new ArrayList<>();
	private JTable tblCategory;
	private JTextField txtCategoryID;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryForm frame = new CategoryForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public CategoryForm() {
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
    	txtCategoryID.setText(String.valueOf((categoryService.getAutoId("categoryID","C-"))));
    }
    
    private void initializeDependency() {
        this.categoryService = new CategoryService();
        //this.categoryService.setProductRepo(new ProductService());
    }
	private void resetFormData() {
        txtCategory.setText("");
    }
	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Category Id");
		
		txtCategory = new JTextField();
		txtCategory.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String st[]=new String[1];
<<<<<<< HEAD
            	
            	if (null != category && category.getId() != null) {
=======

                if (null != category && category.getId() != 0) {

>>>>>>> branch 'master' of https://github.com/eimonmyat/StudentRegistration
                    category.setName(txtCategory.getText());
                    if (!category.getName().isBlank()) {
<<<<<<< HEAD
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
                        
=======
                        	st[0]=(String)txtCategory.getText();
							try {
								boolean ee=categoryService.isduplicate(st);
								if(ee) {
									JOptionPane.showMessageDialog(null,"Duplicate Record");
									resetFormData();
									loadAllCategories(Optional.empty());
								category=null;
								}
								else {
			                        categoryService.updateCategory(String.valueOf(category.getId()), category);
			                        resetFormData();
			                        loadAllCategories(Optional.empty());
			                        category = null;
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						

>>>>>>> branch 'master' of https://github.com/eimonmyat/StudentRegistration
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
                    }
                } else {
                    Category brand = new Category();
                    brand.setName(txtCategory.getText());

<<<<<<< HEAD
                        if (null != category.getName() && !category.getName().isBlank()) {
                        	st[0]=(String)txtCategory.getText();
                        	try {
                        		boolean ee=categoryService.isduplicate(st);
                        		if(ee) {
                        			JOptionPane.showMessageDialog(null, "Duplicate Record");
                        			autoID();
                        			resetFormData();
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
=======
                    if (null != brand.getName() && !brand.getName().isBlank()) {
                    	st[0]=(String)txtCategory.getText();
						try {
							boolean ee=categoryService.isduplicate(st);
							if(ee) {
								JOptionPane.showMessageDialog(null,"Duplicate Record! Enter again.");
								resetFormData();
								loadAllCategories(Optional.empty());
								category=null;
							}
							else {
								categoryService.saveCategory(brand);
		                        resetFormData();
		                        loadAllCategories(Optional.empty());
		                        category=null;
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
>>>>>>> branch 'master' of https://github.com/eimonmyat/StudentRegistration
                    }
                }
            }
        });
		JScrollPane scrollPane = new JScrollPane();
		
		txtCategoryID = new JTextField();
		txtCategoryID.setEditable(false);
		txtCategoryID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Category Name");
		
		JButton btnClose = new JButton("Close");
		//btnClose.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent e) {
		//		if((JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?","Confirm exiting",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE))==JOptionPane.YES_OPTION)
		//			dispose();
		//	}
		//});
		
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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(32)
									.addComponent(btnSave)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnDelete)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClose))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(33)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtCategoryID, Alignment.TRAILING)
										.addComponent(txtCategory, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))))))
					.addGap(39))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCategoryID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(txtCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnDelete)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(46)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(45))
		);
		
		tblCategory = new JTable();
		scrollPane.setViewportView(tblCategory);
		this.tblCategory.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblCategory.getSelectionModel().isSelectionEmpty()) {

                String id = tblCategory.getValueAt(tblCategory.getSelectedRow(), 0).toString();

                category = categoryService.findById(id);
                txtCategoryID.setText(category.getId());
                txtCategory.setText(category.getName());

            }
        });
		contentPane.setLayout(gl_contentPane);
	}
}
