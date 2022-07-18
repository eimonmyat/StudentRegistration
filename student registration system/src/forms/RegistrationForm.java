package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import entities.Category;
import entities.Course;
import entities.Registration;
import entities.Schedule;
import entities.Student;
import services.RegistrationServices;
import services.ScheduleServices;
import services.StudentService;
import services.CourseService;
import services.CategoryService;

import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class RegistrationForm {

	private JFrame frame;
	private JTextField txtStuID;
	private JTextField txtStuName;
	private JTextField txtRegID;
	private JTextField txtRegDate;
	private JTextField txtTotal;
	private JTextField txtCategoryName;
	private JTextField txtFee;
	private JTable tblSchedule;
	private Student student;
	private Schedule schedule;
	private Registration registration;
	private CourseService courseService;
	private StudentService studentService;
	private ScheduleServices scheduleService;
	private CategoryService categoryService;
	private RegistrationServices registrationService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Schedule> origianlScheduleList = new ArrayList<>();
	private List<Course> courseList = new ArrayList<>();
	JComboBox cboCourseName = new JComboBox();
	Date date=new Date();
	SimpleDateFormat dcn=new SimpleDateFormat("dd-MM-yyyy");
	Course course;
	Category category;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationForm window = new RegistrationForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void loadAllSchedule(Optional<List<Schedule>> optionalCategories) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlScheduleList = this.scheduleService.findAllSchedule();
        List<Schedule> categoryList = optionalCategories.orElseGet(() -> origianlScheduleList);

        categoryList.forEach(c -> {
            Object[] row = new Object[9];
            row[0] = c.getId();
            String co=c.getCourse();
            row[1]=scheduleService.findCourseName(co);
            row[2]=c.getstartTime();
            row[3]=c.getendTime();
            row[4]=c.getstartDate();
            row[5]=c.getendDate();
            String le=c.getLecturer();
            row[6]=scheduleService.findLecturerName(le);
            String cla=c.getClassroom();
            row[7]=scheduleService.findClassroomName(cla);
            row[8]=c.getRegisterUser();
            dtm.addRow(row);
        });
    }
	public void setColumnWidth(int index,int width) {
		DefaultTableColumnModel tcm=(DefaultTableColumnModel)tblSchedule.getColumnModel();
		TableColumn tc=tcm.getColumn(index);
		tc.setPreferredWidth(width);
	}

    private void setTableDesign() {
    	//dtm.addColumn("No");
    	 dtm.addColumn("ID");
         dtm.addColumn("Course Name");
         dtm.addColumn("Start Time");
         dtm.addColumn("End Time");
         dtm.addColumn("Start Date");
         dtm.addColumn("End Date");
         dtm.addColumn("Lecturer");
         dtm.addColumn("Classroom");
         dtm.addColumn("Total");
         this.tblSchedule.setModel(dtm);
         setColumnWidth(0,40);
         setColumnWidth(1,45);
         setColumnWidth(2,35);
         setColumnWidth(3,35);
         setColumnWidth(4,40);
         setColumnWidth(5,40);
         setColumnWidth(6,50);
         setColumnWidth(7,40);
         setColumnWidth(8,8);
    }

    private void autoID() {
    	txtStuID.setText(String.valueOf((studentService.getAutoId("studentID","U-"))));
    	txtRegID.setText(String.valueOf((registrationService.getAutoId("registrationID","E-","registration"))));
    }
    
    private void initializeDependency() {
        this.registrationService = new RegistrationServices();
        this.categoryService=new CategoryService();
        this.scheduleService=new ScheduleServices();
        this.studentService=new StudentService();
        this.courseService=new CourseService();
        //this.ClassroomService.setProductRepo(new ProductService());
    }
	private void cboFill() {
		cboCourseName.addItem("- Select -");
        courseList = courseService.findAllCourses();
        courseList.forEach(s -> this.cboCourseName.addItem(s.getName()));
		
	}
	
	private void resetFormData() {
        txtStuName.setText("");
        cboCourseName.setSelectedIndex(0);
	}

	/**
	 * Create the application.
	 */
	public RegistrationForm() {
		initialize();
        initializeDependency();
        autoID();
        txtRegDate.setText(dcn.format(date));
        this.setTableDesign();
        cboFill();
        this.loadAllSchedule(Optional.empty());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 981, 659);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(32, 178, 170));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(32, 178, 170));
		
		JPanel panel_2 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 927, Short.MAX_VALUE))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)))
		);
		
		JLabel lblNewLabel_1 = new JLabel("Student Registration");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		JLabel lblNewLabel_2 = new JLabel("Student ID");
		
		JLabel lblNewLabel_3 = new JLabel("Student Name");
		
		txtStuID = new JTextField();
		txtStuID.setEditable(false);
		txtStuID.setColumns(10);
		
		txtStuName = new JTextField();
		txtStuName.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Registration ID");
		
		JLabel lblNewLabel_10 = new JLabel("Registration Date");
		
		txtRegID = new JTextField();
		txtRegID.setEditable(false);
		txtRegID.setColumns(10);
		
		txtRegDate = new JTextField();
		txtRegDate.setEditable(false);
		txtRegDate.setColumns(10);
		
		JLabel lblNewLabel_16 = new JLabel("Total Amount");
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                /*if (cboCourseName.getSelectedIndex()!=0) {
                	String st=cboCourseName.getSelectedItem().toString();
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
                }*/
			}
			});
		
		JButton btnCancel = new JButton("Cancel");
		
		cboCourseName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String st=cboCourseName.getSelectedItem().toString();
				course=courseService.findById(courseService.findCourseID(st));
				//System.out.println(course.getCategory().getId());
				
				//String str=course.getCategory().getId();
                
               // txtCategoryName.setText(courseService.getCategoryName(str));
				
				
				//String name=courseService.getCategoryName(course.getCategory().getId());
				//txtCategoryName.setText(courseService.getCategoryName(course.getCategory().getId()));
				txtFee.setText(String.valueOf(course.getFee()));
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(226)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(41)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_2)
										.addComponent(lblNewLabel_3))
									.addGap(78)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtStuID)
										.addComponent(txtStuName))
									.addGap(156)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel_9)
										.addComponent(lblNewLabel_10))
									.addGap(98)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(txtRegDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtRegID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 743, GroupLayout.PREFERRED_SIZE))))
					.addGap(363))
				.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
					.addGap(547)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(12)
							.addComponent(btnRegister)
							.addGap(30)
							.addComponent(btnCancel))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_16)
							.addGap(32)
							.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(402, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblNewLabel_2)
						.addComponent(txtStuID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_9)
						.addComponent(txtRegID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(txtStuName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_10)
						.addComponent(txtRegDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_16))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnRegister))
					.addGap(45))
		);
		
		JLabel lblNewLabel_4 = new JLabel("Course Name");
		
		
		
		
		JLabel lblNewLabel_5 = new JLabel("Category Name");
		
		txtCategoryName = new JTextField();
		txtCategoryName.setEditable(false);
		txtCategoryName.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Fee");
		
		txtFee = new JTextField();
		txtFee.setEditable(false);
		txtFee.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_4)
					.addGap(71)
					.addComponent(cboCourseName, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addGap(161)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_5)
						.addComponent(lblNewLabel_6))
					.addPreferredGap(ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCategoryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(89))
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(24)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 682, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(35, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(cboCourseName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5)
						.addComponent(txtCategoryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
					.addGap(23))
		);
		
		tblSchedule = new JTable();
		scrollPane.setViewportView(tblSchedule);
		panel_3.setLayout(gl_panel_3);
		panel_2.setLayout(gl_panel_2);
		
		JButton btnHome = new JButton("Home");
		
		JButton btnStudent = new JButton("Students");
		
		JButton btnControl = new JButton("Control");
		
		JButton btnSearch = new JButton("Search");
		
		JButton btnLogout = new JButton("Log Out");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnLogout, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnControl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnStudent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnHome, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(32)
					.addComponent(btnHome)
					.addGap(44)
					.addComponent(btnStudent)
					.addGap(49)
					.addComponent(btnControl)
					.addGap(44)
					.addComponent(btnSearch)
					.addGap(50)
					.addComponent(btnLogout)
					.addContainerGap(63, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblNewLabel = new JLabel("KMT Computer Training Center");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 32));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(244)
					.addComponent(lblNewLabel)
					.addContainerGap(310, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}
}
