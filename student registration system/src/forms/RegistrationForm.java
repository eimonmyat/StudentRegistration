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
import entities.Classroom;
import entities.Course;
import entities.Registration;
import entities.Schedule;
import entities.Student;
import services.RegistrationServices;
import services.ScheduleServices;
import services.StudentService;
import services.CourseService;
import services.CategoryService;
import services.ClassroomService;

import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.border.BevelBorder;

public class RegistrationForm {

	private JFrame frame;
	private JTextField txtStuID;
	private JTextField txtStuName;
	private JTextField txtRegID;
	private JTextField txtRegDate;
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
	private ClassroomService classroomService;
	private RegistrationServices registrationService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Schedule> origianlScheduleList = new ArrayList<>();
	private List<Course> courseList = new ArrayList<>();
	
	JComboBox cboCourseName = new JComboBox();
	Date date=new Date();
	SimpleDateFormat dcn=new SimpleDateFormat("yyyy-MM-dd");
	Course course;
	Category category;
	String id;
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

        this.origianlScheduleList = this.scheduleService.findAllScheduleByToday(dcn.format(date));
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
        this.classroomService=new ClassroomService();
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
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 965, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 565, GroupLayout.PREFERRED_SIZE)))
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
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {    
				Student s=new Student();
				String stuId=txtStuID.getText();
				String regId=txtRegID.getText();
				s.setId(txtStuID.getText());
				s.setName(txtStuName.getText());
				
				
				if(!s.getName().isBlank()) {
					studentService.saveStudent(stuId, s);
					Registration registration=new Registration();
					registration.setId(txtRegID.getText());
					registration.setDate(txtRegDate.getText());
					registration.setStudent(studentService.findById(stuId));
					registration.setSchedule(scheduleService.findById(id));
					
					 System.out.println(studentService.findById(stuId).getName());
					
					
					if (null != registration && registration.getSchedule().getId() != null && registration.getStudent().getId()!=null) {
						Schedule schedule=new Schedule();
						schedule.setId(id);
						schedule.setRegisterUser(Integer.parseInt(scheduleService.getRegisteredUser("registeredUser","schedule",id).get(0)));
						
						try {
							boolean ee=registrationService.isDuplicateStu(id, txtStuID.getText());
							if(ee) {
								JOptionPane.showMessageDialog(null, "This student is already registered in this schedule!");
							}
							else {
								
								
								registrationService.saveRegistration(regId, registration);
								
		                    	scheduleService.updateRegisteredUser(id, schedule);
								JOptionPane.showMessageDialog(null, "Success");
								resetFormData();
								autoID();
								txtRegDate.setText(dcn.format(date));
								loadAllSchedule(Optional.empty());
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    	
                    	
                    }
					else {
						JOptionPane.showMessageDialog(null, "Select Schedule!");
					}
						
						
				}
					else {
                        JOptionPane.showMessageDialog(null, "Enter Student Name!");
                      }
						
					
					}
				
			});
		
		JButton btnCancel = new JButton("Cancel");
		
		cboCourseName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboCourseName.getSelectedIndex()!=0) {
					String st=cboCourseName.getSelectedItem().toString();
					String result=courseService.getCategoryID(st);
					txtCategoryName.setText(courseService.getCategoryName(result));
					course=courseService.findById(courseService.findCourseID(st));
					txtFee.setText(String.valueOf(course.getFee()));
					List<Schedule> sIDlist=scheduleService.findAllScheduleByID(course.getId(),dcn.format(date));
					loadAllSchedule(Optional.of(sIDlist));
					//System.out.println(txtStuName.getText());
				}
				else
					loadAllSchedule(Optional.empty());
			}
		});
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
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
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(559)
							.addComponent(btnRegister)
							.addGap(29)
							.addComponent(btnCancel)))
					.addGap(43))
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
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
					.addGap(41)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRegister)
						.addComponent(btnCancel))
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
					.addGap(80)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCategoryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(89))
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(24)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
					.addGap(33))
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
					.addGap(17)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
					.addGap(23))
		);
		
		tblSchedule = new JTable();
		scrollPane.setViewportView(tblSchedule);
		
		this.tblSchedule.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblSchedule.getSelectionModel().isSelectionEmpty()) {
                id = tblSchedule.getValueAt(tblSchedule.getSelectedRow(), 0).toString();
                int regUsers=Integer.parseInt(scheduleService.getRegisteredUser("registeredUser","schedule",id).get(0));
				//Classroom r=new Classroom();
                String roomId=scheduleService.getRoomID(id);
				int totalUsers=Integer.parseInt(classroomService.getName("totalUsers","classroom",roomId).get(0));
				System.out.println(regUsers+","+totalUsers);
				if(regUsers>=totalUsers) {
					JOptionPane.showMessageDialog(null, "This schedule is full!");
					id=null;
				}
                
            }
        });
		
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
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(327)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(609))
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