package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import entities.Lecturer;
import entities.Schedule;
import services.ScheduleServices;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;

public class ScheduleForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtScheduleID;
	private JTable tblSchedule;
	private JComboBox cboCourse;
	private JComboBox cboClassroomID;
	private JComboBox cboLectureID;
	JComboBox cboStartTime;
	JComboBox cboEndTime;
	private DefaultTableModel dtm=new DefaultTableModel();
	private String c,l,co,startTime,endTime,sDate,eDate;
	private ScheduleServices scheduleServices;
	private Schedule schedule;
	SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
	JDateChooser startDate,endDate;
	private List<Schedule> origianlCategoryList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScheduleForm frame = new ScheduleForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ScheduleForm() {
		setTitle("Schedule");
		initialize();
		initializedepency();
		autoID();
		this.setTableDesign();
		this.loadAllCategories(Optional.empty());
		this.fillLecturer();
		this.fillClassroom();
		this.fillCourse();
		
				
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
	
	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Schedule ID");
		
		JLabel lblNewLabel_1 = new JLabel("Start Date");
		
		JLabel lblNewLabel_2 = new JLabel("End Date");
		
		JLabel lblNewLabel_3 = new JLabel("Start Time");
		
		JLabel lblNewLabel_4 = new JLabel("End Time");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_6 = new JLabel("Course Name");
		
		JLabel lblNewLabel_7 = new JLabel("Classroom ID");
		
		JLabel lblNewLabel_8 = new JLabel("Lecture ID");
		
		txtScheduleID = new JTextField();
		txtScheduleID.setEditable(false);
		txtScheduleID.setColumns(10);
		
		startDate = new JDateChooser();
		
		endDate = new JDateChooser();
		
		cboCourse = new JComboBox();
		cboCourse.addItem("-Selected-");
		
		cboClassroomID = new JComboBox();		
		cboClassroomID.addItem("-Selected-");		
		
		cboLectureID = new JComboBox();
		cboLectureID.addItem("-Selected-");
		
        cboStartTime = new JComboBox();
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
        
		cboEndTime = new JComboBox();
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
		
		JButton btnCreate = new JButton("create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schedule=new Schedule();
				
				if(cboClassroomID.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "You must choose ClassroomID");
					cboClassroomID.requestFocus();
				}
				else {
					String cresult=cboClassroomID.getSelectedItem().toString();
					c=scheduleServices.findClassroomID(cresult);					
					}
				
				
				if(cboLectureID.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "You must choose LecturerID");
					cboLectureID.requestFocus();
				}
				else {
					String lecture=cboLectureID.getSelectedItem().toString();
				    l=scheduleServices.findLecturerID(lecture);
				
				}
				

				if(cboCourse.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "You must choose Course");
					cboCourse.requestFocus();
				}
				else {
					String course=cboCourse.getSelectedItem().toString();
					co=scheduleServices.findCourseID(course);
				
				}
				if(cboStartTime.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "You must choose Start time");
					cboStartTime.requestFocus();
				}
				else {
					startTime=cboStartTime.getSelectedItem().toString();
				
				}
				if(cboEndTime.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "You must choose End time");
					cboEndTime.requestFocus();
				}
				else {
					endTime=cboEndTime.getSelectedItem().toString();
				
				}
				
				sDate = dcn.format(startDate.getDate() );
				eDate=dcn.format(endDate.getDate());				
				schedule.setClassroom(c);
				schedule.setCourse(co);
				schedule.setLecturer(l);
				schedule.setstartDate(sDate);
				schedule.setendDate(eDate);
				schedule.setstartTime(startTime);
				schedule.setendTime(endTime);
				schedule.setRegisterUser(Integer.parseInt("0"));
				
				
				scheduleServices.saveSchedule(txtScheduleID.getText(), schedule);
				
				resetFormData();
				autoID();
				loadAllCategories(Optional.empty());
				schedule=null;
				
					
			}
		});
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				
				schedule.setCourse(scheduleServices.findCourseID(cboCourse.getSelectedItem().toString()));
				schedule.setClassroom(scheduleServices.findClassroomID(cboClassroomID.getSelectedItem().toString()));
				schedule.setLecturer(scheduleServices.findLecturerID(cboLectureID.getSelectedItem().toString()));
				schedule.setstartDate(dcn.format(startDate.getDate()));
				schedule.setendDate(dcn.format(endDate.getDate()));
				schedule.setstartTime(cboStartTime.getSelectedItem().toString());
				schedule.setendTime(cboEndTime.getSelectedItem().toString());
				schedule.setRegisterUser(Integer.parseInt("0"));				
				scheduleServices.updateSchedule(txtScheduleID.getText(), schedule);
				resetFormData();
				autoID();
				loadAllCategories(Optional.empty());
				schedule=null;
			}
			});
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dispose();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(62)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblNewLabel_7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblNewLabel)
												.addGap(33))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(lblNewLabel_6, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED))))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(cboCourse, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(txtScheduleID, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
										.addComponent(cboClassroomID, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cboLectureID, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(btnCreate)
									.addGap(61)
									.addComponent(btnUpdate)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addComponent(lblNewLabel_3)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNewLabel_2)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGap(12)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(startDate, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cboEndTime, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cboStartTime, 0, 147, Short.MAX_VALUE)
										.addComponent(endDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(46)
									.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 694, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(14)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel)
											.addComponent(txtScheduleID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(12))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
											.addComponent(cboStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18))))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(52)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(cboEndTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_4))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(58)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cboCourse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_6))))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(17)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_1)
								.addComponent(startDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cboClassroomID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_7))))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2)
								.addComponent(endDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(cboLectureID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_8))))
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCreate)
						.addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClose))
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tblSchedule = new JTable();
		scrollPane.setViewportView(tblSchedule);
		this.tblSchedule.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblSchedule.getSelectionModel().isSelectionEmpty()) {

                String id = tblSchedule.getValueAt(tblSchedule.getSelectedRow(), 0).toString();

                schedule = scheduleServices.findById(id);
                txtScheduleID.setText(schedule.getId());
                //txtRgisterUser.setText(String.valueOf(schedule.getRegisterUser()));
                cboStartTime.setSelectedItem(schedule.getstartTime());
                cboEndTime.setSelectedItem(schedule.getendTime()); 
                startDate.setDate(Date.valueOf(schedule.getstartDate()));
                endDate.setDate(Date.valueOf(schedule.getendDate()));
                cboCourse.setSelectedItem((scheduleServices.findCourseName(schedule.getCourse())));
                cboLectureID.setSelectedItem((scheduleServices.findLecturerName(schedule.getLecturer())));
                cboClassroomID.setSelectedItem((scheduleServices.findClassroomName(schedule.getClassroom())));

            }
        });
		contentPane.setLayout(gl_contentPane);
	}
}
