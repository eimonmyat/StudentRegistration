package forms;

import javax.swing.JPanel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Category;
import entities.Course;
import services.CategoryService;
import services.CourseService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CoursePanel extends JPanel {
	private JTextField txtFee;
	private JTextField txtCourseID;
	private JTextField txtCourseName;
	private JTable tblCourse;
	private Course course;
	private Category category;
	private CourseService courseService;
	private CategoryService categoryService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Course> origianlCourseList = new ArrayList<>();
	private List<Category> categoryList = new ArrayList<>();
	JComboBox cboCategoryName = new JComboBox();
	private JTextField txtDuration;
	/**
	 * Launch the application.
	 */
	public CoursePanel() {
        initialize();
        initializeDependency();
        autoID();
        this.setTableDesign();
        this.cboFill();
        this.loadAllCourses(Optional.empty());
    }
	private void loadAllCourses(Optional<List<Course>> optionalCourses) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlCourseList = this.courseService.findAllCourses();
        List<Course> CourseList = optionalCourses.orElseGet(() -> origianlCourseList);

        CourseList.forEach(c -> {
            Object[] row = new Object[5];
            row[0] = c.getId();
            row[1] = c.getName();
            row[2]=c.getFee();
            row[3]=courseService.getCategoryName(c.getCategory().getId());
            row[4]=c.getDuration();
            dtm.addRow(row);
        });
    }

    private void setTableDesign() {
    	//dtm.addColumn("No");
        dtm.addColumn("Course ID");
        dtm.addColumn("Course Name");
        dtm.addColumn("Fee");
        dtm.addColumn("Category ID");
        dtm.addColumn("Duration");
        this.tblCourse.setModel(dtm);
    }

    private void autoID() {
    	txtCourseID.setText(String.valueOf((courseService.getAutoId("courseID","C-"))));
    }
    
    private void initializeDependency() {
        this.courseService = new CourseService();
        this.categoryService=new CategoryService();
        //this.ClassroomService.setProductRepo(new ProductService());
    }
	private void cboFill() {
		this.cboCategoryName.addItem("- Select -");
        categoryList = categoryService.findAllCategories();
        categoryList.forEach(s -> this.cboCategoryName.addItem(s.getName()));
		/*cboCategoryName.addItem("-Selected-");
		ArrayList<String> str=(ArrayList<String>) courseService.getCategoryName();
		for(int i=0;i<str.size();i++) {
			//System.out.println(str.length);
			cboCategoryName.addItem(str.get(i));
		}*/
	}
	public void clear() {
		txtCourseID.setText("");
		txtCourseName.setText("");
		txtFee.setText("");
		cboCategoryName.setSelectedIndex(0);
		txtDuration.setText("");
		autoID();
	}
	private void resetFormData() {
        txtCourseName.setText("");
        txtFee.setText("");
        txtDuration.setText("");
        cboCategoryName.setSelectedIndex(0);
	}
	/**
	 * Create the panel.
	 */
	public void initialize() {
		
		JLabel lblNewLabel_1 = new JLabel("Course ID");
		
		txtCourseID = new JTextField();
		txtCourseID.setText("C-00000003");
		txtCourseID.setEditable(false);
		txtCourseID.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Couse Name");
		
		txtCourseName = new JTextField();
		txtCourseName.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Fee");
		
		txtFee = new JTextField();
		txtFee.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Category Name");
		
		
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Optional<Category> selectedCategory = categoryList.stream()
                        .filter(s -> s.getName().equals(cboCategoryName.getSelectedItem())).findFirst();
                if (cboCategoryName.getSelectedIndex()!=0) {
                	String st=cboCategoryName.getSelectedItem().toString();
    				ArrayList<String> str= courseService.findCategoryID(st);
    				System.out.println(str);
    				
                        Course course = new Course();
                        course.setId(txtCourseID.getText());
                        course.setName(txtCourseName.getText());
                        course.setFee(Double.parseDouble(txtFee.getText()));
                        course.setDuration(Integer.parseInt(txtDuration.getText()));
                        course.setCategory(selectedCategory.get());
                            
                            String ch[]=new String[3];
		                if (null != course && course.getCategory().getId() != null) {
		                	
	                        if (null != course.getName() && !course.getName().isBlank()) {
	                        	ch[0]=(String)txtCourseName.getText();
	                        	ch[1]=(String)txtFee.getText();
                        		ch[2]=(String)cboCategoryName.getSelectedItem();
	                        	try {
	                        		boolean ee=courseService.isduplicate(ch);
	                        		if(ee) {
	                        			JOptionPane.showMessageDialog(null, "Duplicate Record");
	                        			resetFormData();
	                        			autoID();
	                        			
	                        			loadAllCourses(Optional.empty());
	                        			course=null;
	                        		}else
	                        		{
	                        			if(Integer.parseInt(txtDuration.getText())<0) {
	                        				JOptionPane.showMessageDialog(null, "Please enter positive duration");
	                        			}else {
	                        				courseService.saveCourse(course);
	                                		JOptionPane.showMessageDialog(null, "Success");
	                                		resetFormData();
	                                		autoID();
	                                		
	                                		loadAllCourses(Optional.empty());
	                        			}
	                        		
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
                else {
                	JOptionPane.showMessageDialog(null, "Select Category!");
                	//cboCategoryName.requestFocus();
                }
			}
			});
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Optional<Category> selectedCategory = categoryList.stream()
                        .filter(s -> s.getName().equals(cboCategoryName.getSelectedItem())).findFirst();
                if (selectedCategory.isPresent()) {
                	String st=cboCategoryName.getSelectedItem().toString();
    				ArrayList<String> str= courseService.findCategoryID(st);
    				System.out.println(str);
    				
                        Course course = new Course();
                        course.setId(txtCourseID.getText());
                        course.setName(txtCourseName.getText());
                        course.setFee(Double.parseDouble(txtFee.getText()));
                        course.setDuration(Integer.parseInt(txtDuration.getText()));
                        course.setCategory(selectedCategory.get());
                                              
                        String check[]=new String[3];
                        if (null != course && course.getId() != null) {
                        	
                        	if (!course.getName().isBlank()) {
                        		check[0]=(String)txtCourseName.getText();
                        		check[1]=(String)txtFee.getText();
                        		check[2]=(String)cboCategoryName.getSelectedItem();
                        		try {
                        			boolean ee=courseService.isduplicate(check);
                        			if(ee) {
                        				JOptionPane.showMessageDialog(null, "Duplicate Record");
                        				resetFormData();
                        				autoID();
                        				
                        				loadAllCourses(Optional.empty());
                        				course=null;
                        			}
                        			else {
                        				if(Integer.parseInt(txtDuration.getText())<0) {
	                        				JOptionPane.showMessageDialog(null, "Please enter positive duration");
	                        			}else {
	                        				courseService.updateCourse(course);
	                        				JOptionPane.showMessageDialog(null, "Update successful");
	                        				resetFormData();
	                        				autoID();
	                        				
	                        				loadAllCourses(Optional.empty());
	                        				course = null;
	                        			}
                        				
                        			}
                        		}catch(SQLException e1) {
                        			e1.printStackTrace();
                        		}
				
                        	}
                        }
                        else {
                        	JOptionPane.showMessageDialog(null, "Enter Required Field!");
                        }
                	}
                else {
                	JOptionPane.showMessageDialog(null, "Select Category!");
                	cboCategoryName.requestFocus();
                }
			}
        });
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clear();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNewLabel_4 = new JLabel("Duration");
		
		txtDuration = new JTextField();
		txtDuration.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(78)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblNewLabel_3, Alignment.LEADING)
										.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
										.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_4, Alignment.LEADING))
									.addGap(87)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(txtDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(cboCategoryName, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtCourseName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtCourseID, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(97)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtCourseID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtCourseName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(cboCategoryName, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(txtDuration, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnUpdate)
						.addComponent(btnCancel))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
					.addGap(26))
		);
		
		tblCourse = new JTable();
		scrollPane.setViewportView(tblCourse);
		this.tblCourse.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblCourse.getSelectionModel().isSelectionEmpty()) {

                String id = tblCourse.getValueAt(tblCourse.getSelectedRow(), 0).toString();

                course = courseService.findById(id);
                txtCourseID.setText(course.getId());
                txtCourseName.setText(course.getName());
                txtFee.setText(String.valueOf(course.getFee()));
                String str=course.getCategory().getId();
                txtDuration.setText(String.valueOf(course.getDuration()));
                //String result=courseService.getCategoryName(str).get(0);
                //System.out.println(result);
                cboCategoryName.setSelectedItem(courseService.getCategoryName(str));
            }
        });
		setLayout(groupLayout);

	}
}
