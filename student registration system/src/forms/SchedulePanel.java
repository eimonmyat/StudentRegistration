package forms;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

import entities.Course;
import entities.Schedule;
import services.CourseService;
import services.ScheduleServices;

public class SchedulePanel extends JPanel {
	private JTextField txtScheduleID;
	private JTable tblSchedule;
	JComboBox cboCourse = new JComboBox();
	JComboBox cboClassroomID = new JComboBox();
	JComboBox cboLectureID = new JComboBox();
	JComboBox cboStartTime = new JComboBox();
	JComboBox cboEndTime = new JComboBox();
	private JButton btnCreate;
	private DefaultTableModel dtm=new DefaultTableModel();
	private String co,startTime,endTime,sDate,eDate;
	private ScheduleServices scheduleServices;
	private CourseService courseService;
	private Schedule schedule;
	SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat dateFormat=DateFormat.getDateInstance();
	JDateChooser startDate,endDate;
	private List<Schedule> origianlCategoryList = new ArrayList<>();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	LocalDateTime now = LocalDateTime.now();
	
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public SchedulePanel() {	
		
		initialize();
		initializedepency();
		autoID();
		this.setTableDesign();
		this.loadAllCategories(Optional.empty());
		this.fillLecturer();
		this.fillClassroom();
		this.fillCourse();
		//System.out.println(String.valueOf(scheduleServices.add(LocalDate.of(2022,07,13),8)));
				
	}
	private void autoID() {
    	txtScheduleID.setText(String.valueOf((scheduleServices.getAutoId("scheduleID","S-"))));
    }
	private void setTableDesign() {
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
	public void setColumnWidth(int index,int width) {
		DefaultTableColumnModel tcm=(DefaultTableColumnModel)tblSchedule.getColumnModel();
		TableColumn tc=tcm.getColumn(index);
		tc.setPreferredWidth(width);
	}
	public void initializedepency() {
		this.scheduleServices=new ScheduleServices();
		this.courseService=new CourseService();
	}
	public void fillLecturer() {	
		   ArrayList<String> str=(ArrayList<String>)scheduleServices.getName("lecturerName","lecturer");
			for(int i=0;i<str.size();i++) {
				cboLectureID.addItem(str.get(i));
			}
		
	}
	
	public void fillClassroom() {		
		   ArrayList<String> str=(ArrayList<String>)scheduleServices.getName("classroomName","classroom");
			for(int i=0;i<str.size();i++) {
				cboClassroomID.addItem(str.get(i));
			}
		
	}
	
	public void fillCourse() {		
		   ArrayList<String> str=(ArrayList<String>)scheduleServices.getName("courseName","course");
			for(int i=0;i<str.size();i++) {
				cboCourse.addItem(str.get(i));
			}
		
	}
	
	private void loadAllCategories(Optional<List<Schedule>> optionalCategories) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlCategoryList = this.scheduleServices.findAllSchedule();
        List<Schedule> categoryList = optionalCategories.orElseGet(() -> origianlCategoryList);

        categoryList.forEach(c -> {
            Object[] row = new Object[9];
            row[0] = c.getId();
            String co=c.getCourse();
            row[1]=scheduleServices.findCourseName(co);
            row[2]=c.getstartTime();
            row[3]=c.getendTime();
            row[4]=c.getstartDate();
            row[5]=c.getendDate();
            String le=c.getLecturer();
            row[6]=scheduleServices.findLecturerName(le);
            String cla=c.getClassroom();
            row[7]=scheduleServices.findClassroomName(cla);
            row[8]=c.getRegisterUser();
            dtm.addRow(row);
        });
    }
	
	private void resetFormData() {
        cboCourse.setSelectedItem("-Selected-");
        cboClassroomID.setSelectedItem("-Selected-");
        cboLectureID.setSelectedItem("-Selected-");
        cboStartTime.setSelectedItem("-Selected-");
        cboEndTime.setSelectedItem("-Selected-");
        startDate.setDate(null);
        endDate.setDate(null);
        
	}
	/**
	 * Create the panel.
	 */
	public void initialize() {
		
		JLabel lblNewLabel_1 = new JLabel("Start Date");
		
		JLabel lblNewLabel_4 = new JLabel("End Time");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel_3 = new JLabel("Start Time");
		
		JLabel lblNewLabel = new JLabel("Schedule ID");
		
		
		cboEndTime.addItem("-Selected-");
		LocalTime Time1 = LocalTime.of(9,00);
        LocalTime Time2= LocalTime.of(11,00);
        LocalTime Time3= LocalTime.of(2,00);
        LocalTime Time4= LocalTime.of(4,00);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String TimeString1 = Time1.format(formatter2);
        String TimeString2 = Time2.format(formatter2);
        String TimeString3 = Time3.format(formatter2);
        String TimeString4 = Time4.format(formatter2);
        cboEndTime.addItem(TimeString1);
        cboEndTime.addItem(TimeString2);
        cboEndTime.addItem(TimeString3);
        cboEndTime.addItem(TimeString4);  
		
		startDate = new JDateChooser();
		startDate.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("date")) {
					java.util.Date start=startDate.getDate();
		    		LocalDate localdate=Instant.ofEpochMilli(start.getTime())
		    			      .atZone(ZoneId.systemDefault())
		    			      .toLocalDate();
		    		//System.out.println(courseService.findById(scheduleServices.findCourseID(cboCourse.getSelectedItem().toString())));
		    		Course c=courseService.findById(scheduleServices.findCourseID(cboCourse.getSelectedItem().toString()));
		    		int duration=c.getDuration();
		    		LocalDate result=scheduleServices.add(localdate, duration);
		    		
		    		java.util.Date d=Date.from(result.atStartOfDay(ZoneId.systemDefault()).toInstant());
		    		endDate.setDate(d);
		    		//endDate.setDate(dateFormat.format(d));
				}
				
			}
		});
		
		    
		
		
		    cboStartTime.addItem("-Selected-");
	        LocalTime localTime1 = LocalTime.of(7,00);
	        LocalTime localTime2= LocalTime.of(9,00);
	        LocalTime localTime3= LocalTime.of(12,00);
	        LocalTime localTime4= LocalTime.of(2,00);
	        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
	        String localTimeString1 = localTime1.format(formatter1);
	        String localTimeString2 = localTime2.format(formatter1);
	        String localTimeString3 = localTime3.format(formatter1);
	        String localTimeString4 = localTime4.format(formatter1);
	        cboStartTime.addItem(localTimeString1);
	        cboStartTime.addItem(localTimeString2);
	        cboStartTime.addItem(localTimeString3);
	        cboStartTime.addItem(localTimeString4);
		
		txtScheduleID = new JTextField();
		txtScheduleID.setText("S-00000004");
		txtScheduleID.setEditable(false);
		txtScheduleID.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("End Date");
		
		JLabel lblNewLabel_7 = new JLabel("Classroom ID");
		
		JLabel lblNewLabel_8 = new JLabel("Lecture ID");
		
		JLabel lblNewLabel_6 = new JLabel("Course Name");
		
		
		cboCourse.addItem("-Selected-");
		
		
		cboClassroomID.addItem("-Selected-");
		
		endDate = new JDateChooser();
		
		
		cboLectureID.addItem("-Selected-");
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {											
				schedule=new Schedule();
				sDate = dcn.format(startDate.getDate() );		
				eDate=dcn.format(endDate.getDate());
				schedule.setstartTime(cboStartTime.getSelectedItem().toString());
				schedule.setendTime(cboEndTime.getSelectedItem().toString());
				schedule.setstartDate(sDate);
				schedule.setendDate(eDate);
				String c=scheduleServices.findClassroomID(cboClassroomID.getSelectedItem().toString());
				schedule.setClassroom(c);
				String l=scheduleServices.findLecturerID(cboLectureID.getSelectedItem().toString());
				schedule.setLecturer(l);
				String co=scheduleServices.findCourseID(cboCourse.getSelectedItem().toString());
				schedule.setCourse(co);
				if(!schedule.getstartDate().isBlank() && !schedule.getendDate().isBlank() && !schedule.getstartTime().isBlank() && !schedule.getendTime().isBlank() && !schedule.getClassroom().isBlank() && !schedule.getLecturer().isBlank() && !schedule.getCourse().isBlank()) {
					String cr[]=new String[5];
					cr[0]=schedule.getClassroom();
					cr[1]=schedule.getstartTime();
					cr[2]=schedule.getendTime();
					cr[3]=schedule.getstartDate();
					cr[4]=schedule.getendDate();
    				String ea[]=new String[5];
    				ea[0]=schedule.getLecturer();
    				ea[1]=schedule.getstartTime();
    				ea[2]=schedule.getendTime();
    				ea[3]=schedule.getstartDate();
    				ea[4]=schedule.getendDate();
    				if(cr!=null) {
              			try {
              				boolean u=scheduleServices.isduplicateroom(cr);
	             			System.out.println(u);
	             			if(u) {
	             			    JOptionPane.showMessageDialog(null, "This room has Schedule at this time and date.");
	             				resetFormData();
	             				autoID();                   				
	             				loadAllCategories(Optional.empty());
	             				schedule=null;
	             				cboClassroomID.setSelectedIndex(0);
	             				
	             			}
	             			else {
	             				if(ea!=null) {
	             					try {
	                      				boolean t=scheduleServices.isduplicateLecturer(ea);
	        	             			System.out.println(t);
	        	             			if(t) {
	        	             			    JOptionPane.showMessageDialog(null, "This Lecturer has already Schedule");
	        	             				resetFormData();
	        	             				autoID();                   				
	        	             				loadAllCategories(Optional.empty());
	        	             				schedule=null;
	        	             			}
	        	             			else {
	        	             				scheduleServices.saveSchedule(txtScheduleID.getText(), schedule);
	        	             				JOptionPane.showMessageDialog(null, "Sucessfully saved.");
	        	             				resetFormData();
	        	             				autoID();                   				
	        	             				loadAllCategories(Optional.empty());
	        	             				schedule=null;
	        	             				
	        	             			}
	             				}catch(SQLException e1) {
	        	          			e1.printStackTrace();
	        	          		}
	             				
	             			}else {
	             				JOptionPane.showMessageDialog(null, "not Completed");
	             			}
	             			}
	    			  }
	    				catch(SQLException e1) {
	          			e1.printStackTrace();
	          		}
              			}
				}
				else {
                    JOptionPane.showMessageDialog(null, "Enter Data!");
                  }
										
				}
			
		});
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				
				schedule=new Schedule();
				sDate = dcn.format(startDate.getDate() );		
				eDate=dcn.format(endDate.getDate());
				schedule.setstartTime(cboStartTime.getSelectedItem().toString());
				schedule.setendTime(cboEndTime.getSelectedItem().toString());
				schedule.setstartDate(sDate);
				schedule.setendDate(eDate);
				String m=scheduleServices.findClassroomID(cboClassroomID.getSelectedItem().toString());
				schedule.setClassroom(m);
				String n=scheduleServices.findLecturerID(cboLectureID.getSelectedItem().toString());
				schedule.setLecturer(n);
				String oo=scheduleServices.findCourseID(cboCourse.getSelectedItem().toString());
				schedule.setCourse(oo);
				if(!schedule.getstartDate().isBlank() && !schedule.getendDate().isBlank() && !schedule.getstartTime().isBlank() && !schedule.getendTime().isBlank() && !schedule.getClassroom().isBlank() && !schedule.getLecturer().isBlank() && !schedule.getCourse().isBlank()) {
					String up[]=new String[6];
    				up[0]=schedule.getClassroom();
    				up[1]=schedule.getstartTime();
    				up[2]=schedule.getendTime();
    				up[3]=schedule.getstartDate();
    				up[4]=schedule.getendDate();
    				up[5]=txtScheduleID.getText();
    				String da[]=new String[6];
    				da[0]=schedule.getLecturer();
    				da[1]=schedule.getstartTime();
    				da[2]=schedule.getendTime();
    				da[3]=schedule.getstartDate();
    				da[4]=schedule.getendDate();
    				da[5]=txtScheduleID.getText();
    				if(up!=null) {
    					try {
              				boolean u=scheduleServices.isduplicateroom1(up);
	             			System.out.println(u);
	             			if(u) {
	             			    JOptionPane.showMessageDialog(null, "This room has Schedule at this time and date.");
	             				resetFormData();
	             				autoID();                   				
	             				loadAllCategories(Optional.empty());
	             				schedule=null;
	             				cboClassroomID.setSelectedIndex(0);
	             				
	             			}
	             			else {
	             				if(da!=null) {
	             					try {
	                      				boolean t=scheduleServices.isduplicateLecturer1(da);
	        	             			System.out.println(t);
	        	             			if(t) {
	        	             			    JOptionPane.showMessageDialog(null, "This Lecturer has already Schedule");
	        	             				resetFormData();
	        	             				autoID();                   				
	        	             				loadAllCategories(Optional.empty());
	        	             				schedule=null;
	        	             				cboLectureID.setSelectedIndex(0);
	        	             			}
	        	             			else {
	        	             				scheduleServices.updateSchedule(txtScheduleID.getText(), schedule);
	        	             				JOptionPane.showMessageDialog(null, "Update is sucessful");
	        	             				resetFormData();
	        	             				autoID();                   				
	        	             				loadAllCategories(Optional.empty());
	        	             				schedule=null;
	        	             			}
	             				}catch(SQLException e1) {
	        	          			e1.printStackTrace();
	        	          		}
	             				
	             			}else {
	             				JOptionPane.showMessageDialog(null, "not Completed");
	             			}
	             			}
	    			  }
	    				catch(SQLException e1) {
	          			e1.printStackTrace();
	          		}	
    				}
				else {
                    JOptionPane.showMessageDialog(null, "Enter Data!");
                  }
				
				}
			}
		});
		
		JButton btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFormData();
				autoID();                   				
 				loadAllCategories(Optional.empty());
 				schedule=null;
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(93)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(cboClassroomID, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(txtScheduleID, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
									.addComponent(cboCourse, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(14)
										.addComponent(btnCreate, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
										.addGap(44)
										.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(cboStartTime, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
											.addComponent(cboEndTime, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
											.addComponent(startDate, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
											.addComponent(endDate, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
										.addGap(2))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(42)
										.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))))
							.addComponent(cboLectureID, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)))
					.addGap(32))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
					.addGap(96))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtScheduleID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(cboStartTime, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cboCourse, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(cboEndTime, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(startDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_7)
							.addComponent(cboClassroomID, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1)))
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_8)
							.addComponent(cboLectureID, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_2))
						.addComponent(endDate, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(52)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCreate)
						.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClose))
					.addGap(35)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tblSchedule = new JTable();
		scrollPane.setViewportView(tblSchedule);
        this.tblSchedule.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {			
            if (!tblSchedule.getSelectionModel().isSelectionEmpty()) {
            	
                String id = tblSchedule.getValueAt(tblSchedule.getSelectedRow(), 0).toString();
                schedule = scheduleServices.findById(id);
                txtScheduleID.setText(schedule.getId());
                cboStartTime.setSelectedItem(schedule.getstartTime());
                cboEndTime.setSelectedItem(schedule.getendTime()); 
                startDate.setDate(Date.valueOf(schedule.getstartDate()));
                endDate.setDate(Date.valueOf(schedule.getendDate()));
                cboCourse.setSelectedItem((scheduleServices.findCourseName(schedule.getCourse())));
                cboLectureID.setSelectedItem((scheduleServices.findLecturerName(schedule.getLecturer())));
               cboClassroomID.setSelectedItem((scheduleServices.findClassroomName(schedule.getClassroom())));

            }
        });
		setLayout(groupLayout);

	}
}
