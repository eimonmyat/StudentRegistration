package forms;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
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
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import entities.Category;
import entities.Course;
import entities.Registration;
import entities.Schedule;
import entities.Student;
import services.CategoryService;
import services.ClassroomService;
import services.CourseService;
import services.RegistrationServices;
import services.ScheduleServices;
import services.StudentService;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;

public class stuNewReg extends JPanel {
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
	JpanelLoader jLoader=new JpanelLoader();
	//studentPanel stuPanel=new studentPanel();
	//RegistrationPanel regPanel=new RegistrationPanel();
	
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
    	txtStuID.setText(String.valueOf(studentService.getAutoId("studentID", "U-")));
    	txtRegID.setText(String.valueOf(registrationService.getAutoId("registrationID", "E-", "registration")));
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
        txtCategoryName.setText("");
        txtFee.setText("");
        cboCourseName.setSelectedIndex(0);
	}

	/**
	 * Create the application.
	 */
    public stuNewReg() {
		initialize();
        initializeDependency();
        autoID();
        txtRegDate.setText(dcn.format(date));
        this.setTableDesign();
        cboFill();
        this.loadAllSchedule(Optional.empty());
        
	}

	/**
	 * Create the panel.
	 */
	public void initialize() {
		
		JPanel panel_2 = new JPanel();
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
		
		JLabel lblNewLabel_4 = new JLabel("Course Name");
		
		cboCourseName = new JComboBox();
		
		JLabel lblNewLabel_5 = new JLabel("Category Name");
		
		JLabel lblNewLabel_6 = new JLabel("Fee");
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
		
		txtFee = new JTextField();
		txtFee.setEditable(false);
		txtFee.setColumns(10);
		
		txtCategoryName = new JTextField();
		txtCategoryName.setEditable(false);
		txtCategoryName.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(55)
							.addComponent(lblNewLabel_4)
							.addGap(117)
							.addComponent(cboCourseName, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(122)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_5)
								.addComponent(lblNewLabel_6))
							.addGap(61)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCategoryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(30)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 970, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(38, Short.MAX_VALUE))
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
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		
		
		panel_3.setLayout(gl_panel_3);
		
		JLabel lblNewLabel_1_1 = new JLabel("Student Name");
		
		JLabel lblNewLabel_2 = new JLabel("Student ID");
		
		txtStuName = new JTextField();
		txtStuName.setColumns(10);
		
		txtStuID = new JTextField();
		txtStuID.setText("U-00000009");
		txtStuID.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Registration ID");
		
		JLabel lblNewLabel_10 = new JLabel("Registration Date");
		
		txtRegID = new JTextField();
		txtRegID.setText("E-00000003");
		txtRegID.setEditable(false);
		txtRegID.setColumns(10);
		
		txtRegDate = new JTextField();
		txtRegDate.setText("2022-07-21");
		txtRegDate.setEditable(false);
		txtRegDate.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {    
				Student s=new Student();
				String stuId=txtStuName.getText();
				String regId=txtRegID.getText();
				s.setId(txtStuName.getText());
				s.setName(txtStuID.getText());
				
				
				if(!s.getName().isBlank() && !s.getId().isBlank()) {
					studentService.saveStudent(stuId, s);
					Registration registration=new Registration();
					registration.setId(txtRegID.getText());
					registration.setDate(txtRegDate.getText());
					registration.setStudent(studentService.findById(stuId));
					registration.setSchedule(scheduleService.findById(id));
					
					 //System.out.println(studentService.findById(stuId).getName());
					
					
					if (null != registration && registration.getSchedule().getId() != null) {
						Schedule schedule=new Schedule();
						schedule.setId(id);
						schedule.setRegisterUser(Integer.parseInt(scheduleService.getField("registeredUser","schedule",id).get(0)));
						
						try {
							boolean ee=registrationService.isDuplicateStu(id, txtStuName.getText());
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
								schedule=null;
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
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFormData();
				autoID();
				loadAllSchedule(Optional.empty());
			}
		});
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
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(104)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_1_1)
							.addGap(113)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txtStuID, Alignment.LEADING)
								.addComponent(txtStuName, Alignment.LEADING))
							.addGap(138)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_10)
								.addComponent(lblNewLabel_9))
							.addGap(45)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(txtRegDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtRegID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(506, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
							.addGap(509))))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(45)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 1042, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(76, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(504)
					.addComponent(btnRegister)
					.addGap(76)
					.addComponent(btnCancel)
					.addContainerGap(445, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtStuID, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_9)
						.addComponent(txtRegID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_1)
						.addComponent(txtStuName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_10)
						.addComponent(txtRegDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRegister)
						.addComponent(btnCancel))
					.addGap(372))
		);
		panel_2.setLayout(gl_panel_2);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 583, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(141, Short.MAX_VALUE))
		);
		tblSchedule = new JTable();
		scrollPane.setViewportView(tblSchedule);
		this.tblSchedule.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblSchedule.getSelectionModel().isSelectionEmpty()) {
                id = tblSchedule.getValueAt(tblSchedule.getSelectedRow(), 0).toString();
                
                int regUsers=Integer.parseInt(scheduleService.getField("registeredUser","schedule",id).get(0));
                
				//Classroom r=new Classroom();
                String roomId=scheduleService.getRoomID(id);
                System.out.println(roomId);
                int totalUsers=Integer.parseInt(classroomService.getName("totalUsers","classroom",roomId).get(0));
				System.out.println(regUsers+","+totalUsers);
				if(regUsers>=totalUsers) {
					JOptionPane.showMessageDialog(null, "This schedule is full!");
					id=null;
				}
                
            }
        });
		setLayout(groupLayout);

	}
}
