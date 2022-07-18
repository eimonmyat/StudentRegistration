package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Category;
import entities.Classroom;
import entities.Course;
import services.CourseService;
import services.CategoryService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CourseForm extends JFrame {

	private JPanel contentPane;
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseForm frame = new CourseForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public CourseForm() {
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
            Object[] row = new Object[4];
            row[0] = c.getId();
            row[1] = c.getName();
            row[2]=c.getFee();
            row[3]=courseService.getCategoryName(c.getCategory().getId());
            dtm.addRow(row);
        });
    }

    private void setTableDesign() {
    	//dtm.addColumn("No");
        dtm.addColumn("Course ID");
        dtm.addColumn("Course Name");
        dtm.addColumn("Fee");
        dtm.addColumn("Category ID");
        this.tblCourse.setModel(dtm);
    }

    private void autoID() {
    	txtCourseID.setText(String.valueOf((courseService.getAutoId("courseID","U-"))));
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
		autoID();
	}
	private void resetFormData() {
        txtCourseName.setText("");
        txtFee.setText("");
        cboCategoryName.setSelectedIndex(0);
	}
	/**
	 * Create the frame.
	 */
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Category Name");
		
		JLabel lblNewLabel_1 = new JLabel("Course ID");
		
		JLabel lblNewLabel_2 = new JLabel("Couse Name");
		
		JLabel lblNewLabel_3 = new JLabel("Fee");
		
		txtFee = new JTextField();
		txtFee.setColumns(10);
		
		txtCourseID = new JTextField();
		txtCourseID.setEditable(false);
		txtCourseID.setColumns(10);
		
		txtCourseName = new JTextField();
		txtCourseName.setColumns(10);
		
		
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
	                        		courseService.saveCourse(course);
                            		JOptionPane.showMessageDialog(null, "Success");
                            		resetFormData();
                            		autoID();
                            		
                            		loadAllCourses(Optional.empty());
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
                        				courseService.updateCourse(course);
                        				JOptionPane.showMessageDialog(null, "Update successful");
                        				resetFormData();
                        				autoID();
                        				
                        				loadAllCourses(Optional.empty());
                        				course = null;
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
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (null != course) {
                    courseService.deleteCourse(course.getId() + "");
                    JOptionPane.showMessageDialog(null, "Delete successfully");
                    resetFormData();
                    autoID();
                    loadAllCourses(Optional.empty());
                    course = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Choose Category");
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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(60)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel)
								.addComponent(btnSave, Alignment.TRAILING))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(57)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(txtCourseName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
												.addComponent(txtCourseID, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
											.addGap(325))
										.addGroup(Alignment.LEADING, gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
											.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(cboCategoryName, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addComponent(txtFee, Alignment.LEADING, 103, 103, 103))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addComponent(btnUpdate)
									.addGap(18)
									.addComponent(btnDelete)
									.addGap(18)
									.addComponent(btnCancel)
									.addGap(283))))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtCourseID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtCourseName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(cboCategoryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnUpdate)
						.addComponent(btnDelete)
						.addComponent(btnCancel))
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
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
                //String result=courseService.getCategoryName(str).get(0);
                //System.out.println(result);
                cboCategoryName.setSelectedItem(courseService.getCategoryName(str));
            }
        });
		contentPane.setLayout(gl_contentPane);
}
}
